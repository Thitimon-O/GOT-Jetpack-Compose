package com.example.gotcomposetest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HospitalViewModel : ViewModel() {

    private val repository = ApiRepository()

    private val _hospitalData = MutableLiveData<HospitalModel>()
    val hospitalData: LiveData<HospitalModel> get() = _hospitalData

    fun getHospitalData() {
        viewModelScope.launch {
            try {
                val response = repository.getHospitalData()
                _hospitalData.value = response
            } catch (e: Exception) {
                Log.d("getHospitalData", "api error : ${e.message}")
            }

            // ---------------- mock ----------------
            val newList = ArrayList<HospitalDesModel>()
            newList.add(
                0,
                HospitalDesModel(
                    "โรงพยาบาลเวชธานี",
                    "pra",
                    "456",
                    "13.7624197, 100.6149298",
                    LocationsModel(0.0, 0.0)
                )
            )
            newList.add(
                1,
                HospitalDesModel(
                    "โรงพยาบาลยันฮี",
                    "bkk",
                    "000",
                    "13.7960123, 100.4886804",
                    LocationsModel(0.0, 0.0)
                )
            )
            newList.add(
                2,
                HospitalDesModel(
                    "โรงพยาบาลพระมงกุฎเกล้า",
                    "cnx",
                    "123",
                    "13.7668534, 100.5211537",
                    LocationsModel(0.0, 0.0)
                )
            )
            newList.add(
                3,
                HospitalDesModel(
                    "โรงพยาบาลจุฬาลงกรณ์ สภากาชาดไทย",
                    "pkt",
                    "999",
                    "13.734004, 100.5142644",
                    LocationsModel(0.0, 0.0)
                )
            )
            _hospitalData.value = HospitalModel(newList)
        }
    }

}