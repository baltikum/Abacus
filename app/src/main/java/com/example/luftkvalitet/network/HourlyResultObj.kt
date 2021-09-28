package com.example.luftkvalitet.network

class HourlyResultObj(
        val date: String,
        val unit: String,
        val raw_value: String,
        val parameter: String,
        val station: String,
        val unit_code: String,
        val latitude_wgs84: String,
        val station_code : String,
        val time: String,
        val longitude_wgs84: String, ){}

