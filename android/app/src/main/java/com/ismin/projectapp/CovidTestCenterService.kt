package com.ismin.projectapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CovidTestCenterService {
    @GET("CovidCenters")
    fun getAllCovidCenters(): Call<List<CovidTestCenter>>
}