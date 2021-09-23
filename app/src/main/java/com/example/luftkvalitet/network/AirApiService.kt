package com.example.luftkvalitet.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://catalog.goteborg.se/rowstore/dataset/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AirApiService {
    @GET("12e75096-583d-4c0b-afac-093e90d8489e") // 12e75096-583d-4c0b-afac-093e90d8489e?date=2021-06-12&time=13:00*
    suspend fun getData(@Query("") station: String?,
                        @Query("date") date: String?,
                        @Query("time") time: String?): String
}

object AirApi {
    val retrofitService: AirApiService by lazy {
        retrofit.create(AirApiService::class.java)
    }
}