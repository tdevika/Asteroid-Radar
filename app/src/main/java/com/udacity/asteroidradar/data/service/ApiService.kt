package com.udacity.asteroidradar.data.service

import com.udacity.asteroidradar.data.PictureOfDay
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("neo/rest/v1/feed?start_date=2021-01-03&end_date=2021-01-10&api_key=DxldAicOyvetgNHYy0wPqhNVrUhbEUd5w5DHCRn0")
    fun getAsteroidsFromServer(): Call<String>

    @GET("planetary/apod?api_key=DxldAicOyvetgNHYy0wPqhNVrUhbEUd5w5DHCRn0")
    suspend fun getImageFromServer():PictureOfDay
}