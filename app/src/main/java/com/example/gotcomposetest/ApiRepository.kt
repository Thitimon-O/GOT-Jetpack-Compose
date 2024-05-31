package com.example.gotcomposetest

class ApiRepository {
    private val apiService = ApiInstance.apiService

    suspend fun getHospitalData(): HospitalModel {
        return apiService.getHospitalData()
    }
}