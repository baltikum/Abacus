package com.example.luftkvalitet.network

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://catalog.goteborg.se/rowstore/dataset/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    //.addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AirApiService {
    @GET("12e75096-583d-4c0b-afac-093e90d8489e") // 12e75096-583d-4c0b-afac-093e90d8489e?date=2021-06-12&time=13:00*
    suspend fun getData(@Query("") station: String?, // remove station...
                        @Query("date") date: String?,
                        @Query("time") time: String?): String
}

interface AirHourApiService {
    @GET("cb541050-487e-4eea-b7b6-640d58f28092")
    suspend fun getData(
        @Query("station") station: String?,
        @Query("date") date: String?,
        @Query("time") time: String?
    ): AirHourApiDataResponse
}

object AirApi {
    val retrofitService: AirApiService by lazy {
        retrofit.create(AirApiService::class.java)
    }
    val hour: AirHourApiService by lazy { retrofit.create(AirHourApiService::class.java)}
}

data class AirHourApiData(
    // val id: String,
    @Json(name = "date") val date: String,
    @Json(name = "unit") val unit: String,
    @Json(name = "raw_value") val rawValue: String,
    @Json(name = "parameter") val parameter: String,
    @Json(name = "station") val station: String,
    @Json(name = "unit_code") val unitCode: String,
    @Json(name = "latitude_wgs84") val latitudeWgs84: String,
    @Json(name = "station_code") val stationCode: String,
    @Json(name = "time") val time: String,
    @Json(name = "longitude_wgs84") val longitudeWgs84: String
)

data class AirHourApiDataResponse(
    @Json(name = "resultCount") val resultCount: String,
    @Json(name = "offset") val offset: String,
    @Json(name = "limit") val limit: String,
    @Json(name = "queryTime") val queryTime: String,
    @Json(name = "results") val results: List<AirHourApiData>
)