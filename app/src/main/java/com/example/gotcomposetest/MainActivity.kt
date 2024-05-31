package com.example.gotcomposetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.gotcomposetest.ui.theme.GOTComposeTestTheme
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    private val hospitalViewModel: HospitalViewModel by viewModels()
    private val currentLocation = CurrentLocationModel(13.723884, 100.529435)
    private val newList = ArrayList<HospitalDesModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GOTComposeTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        HospitalTitle("Hospital")
                        HospitalDataManage(hospitalViewModel, currentLocation, newList)
                    }
                }
            }
        }
    }
}

@Composable
private fun MyEventListener(onEvent: (event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(newValue = onEvent)
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)
    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { source, event -> eventHandler.value(event) }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
}

@Composable
private fun HospitalTitle(title: String) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.purple_100))
        ) {
            Text(
                text = title,
                fontSize = 30.sp,
                color = colorResource(R.color.purple_500),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
            )
        }
    }
}

@Composable
private fun HospitalDataManage(
    hospitalViewModel: HospitalViewModel,
    currentLocation: CurrentLocationModel,
    newList: ArrayList<HospitalDesModel>
) {
    MyEventListener {
        when (it) {
            Lifecycle.Event.ON_RESUME -> {}
            Lifecycle.Event.ON_PAUSE -> {}
            else -> {}
        }
    }

    val hospitalData by hospitalViewModel.hospitalData.observeAsState()
    LaunchedEffect(Unit) { hospitalViewModel.getHospitalData() }
    hospitalData?.let { hospital ->
        hospital.list.forEach { data ->
            val splitData = data.gps.split(",").toTypedArray()
            val latitude = splitData[0].toDouble()
            val longitude = splitData[1].filterNot { it.isWhitespace() }.toDouble()
            newList.add(
                HospitalDesModel(
                    data.name,
                    data.location,
                    data.tel,
                    data.gps,
                    LocationsModel(latitude, longitude)
                )
            )
        }
        newList.forEach {
            val result = calcDistance(it, currentLocation)
            it.distance = result
        }
        newList.sortBy { it.distance }
        HospitalList(newList)
    }
}

@Composable
private fun HospitalList(hospitals: List<HospitalDesModel>) {
    val context = LocalContext.current
    LazyColumn {
        items(hospitals.size) { index ->
            HospitalDetail(hospitals[index]) {
                val intent = Intent(context, HospitalDetailActivity::class.java)
                intent.putExtra("name", hospitals[index].name)
                intent.putExtra("tel", hospitals[index].tel)
                intent.putExtra("location", hospitals[index].location)
                intent.putExtra("distance", hospitals[index].distance)
                context.startActivity(intent)
            }
        }
    }
}

@Composable
private fun HospitalDetail(hospitalDesModel: HospitalDesModel, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = hospitalDesModel.name,
                style = TextStyle(color = colorResource(R.color.purple_500), fontSize = 16.sp),
                modifier = Modifier.weight(1f)
            )
            if (hospitalDesModel.distance!! >= 1000) {
                val converted = ("%.1f".format(hospitalDesModel.distance?.div(1000) ?: 0)) + " km"
                Text(
                    text = converted,
                    style = TextStyle(color = colorResource(R.color.purple_500), fontSize = 16.sp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            } else {
                val converted = (hospitalDesModel.distance?.toInt().toString() + " m")
                Text(
                    text = converted,
                    style = TextStyle(color = colorResource(R.color.purple_500), fontSize = 16.sp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

private fun calcDistance(hospital: HospitalDesModel, current: CurrentLocationModel): Double {
    val r = 637100.0
    val d2r = Math.PI / 180.0
    val rLat1 = hospital.newLocation.latitude * d2r
    val rLat2 = current.latitude * d2r
    val dLat = (current.latitude - hospital.newLocation.latitude) * d2r
    val dLon = (current.longitude - hospital.newLocation.longitude) * d2r
    val a =
        (sin(dLat / 2) * sin(dLat / 2)) + (cos(rLat1) * cos(rLat2) * (sin(dLon / 2) * sin(dLon / 2)))
    return 2 * r * atan2(sqrt(a), sqrt(1 - a))
}