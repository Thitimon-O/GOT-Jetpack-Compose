package com.example.gotcomposetest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotcomposetest.ui.theme.GOTComposeTestTheme

class HospitalDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent?.getStringExtra("name")
        val tel = intent?.getStringExtra("tel")
        val location = intent?.getStringExtra("location")
        val distance = intent?.getDoubleExtra("distance", 0.0)

        setContent {
            GOTComposeTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        HospitalTitle(
                            title = "Detail",
                            onImageClick = { finish() },
                            onTextClick = {
                                Toast.makeText(
                                    this@HospitalDetailActivity,
                                    "$name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                        Box(modifier = Modifier.size(20.dp))
                        if (name != null && tel != null && location != null && distance != null) HospitalDetailData(
                            name,
                            tel,
                            location,
                            distance
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HospitalTitle(title: String, onImageClick: () -> Unit, onTextClick: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.purple_100))
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterStart)
                    .padding(20.dp)
                    .clickable(onClick = onImageClick)
            )
            Text(
                text = title,
                fontSize = 24.sp,
                color = colorResource(R.color.purple_500),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
                    .clickable(onClick = onTextClick)
            )
        }
    }
}

@Composable
private fun HospitalDetailData(
    name: String,
    tel: String,
    location: String,
    distance: Double?
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.purple_500)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(20.dp)
                )
                Text(
                    text = name,
                    style = TextStyle(color = colorResource(R.color.white), fontSize = 16.sp)
                )
            }
            Box(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.purple_500)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tel),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(20.dp)
                )
                Text(
                    text = tel,
                    style = TextStyle(color = colorResource(R.color.white), fontSize = 16.sp)
                )
            }
            Box(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.purple_500)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(20.dp)
                )
                Text(
                    text = location,
                    style = TextStyle(color = colorResource(R.color.white), fontSize = 16.sp)
                )
            }
            Box(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.purple_500)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.road),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(20.dp)
                )
                if (distance!! >= 1000) {
                    val converted = ("%.1f".format(distance.div(1000))) + " km"
                    Text(
                        text = converted,
                        style = TextStyle(color = colorResource(R.color.white), fontSize = 16.sp)
                    )
                } else {
                    val converted = (distance.toInt().toString() + " m")
                    Text(
                        text = converted,
                        style = TextStyle(color = colorResource(R.color.white), fontSize = 16.sp)
                    )
                }
            }
        }

    }
}