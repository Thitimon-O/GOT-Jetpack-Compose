package com.example.gotcomposetest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance {
    private const val BASE_API = "https://gdev.geotalent.co.th/dmd/exam/"
    const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVfYnkiOiJHT1QgRE1EIiwiaWF0IjoxNzE1MzI4NTgyLCJleHAiOjE3MTUzMzkzODJ9.ysG9QMjSc1Iubbs_3qfXr1AUbXD-gB0sU2adDDbeDgU"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}