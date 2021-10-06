package com.example.luftkvalitet.network

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sqrt





private const val BASE_URL = "https://catalog.goteborg.se/rowstore/dataset/"
private const val HOURLY = "cb541050-487e-4eea-b7b6-640d58f28092/json?" //0
private const val YEARLY ="12e75096-583d-4c0b-afac-093e90d8489e/json?"  //1
private const val ALLTIME = "3ec70191-60d2-4cdd-823e-f92f9938034b/json?" //2



class API {

    private val hourData = HashMap<String, ArrayList<HourlyResultObj>>()
    private val graphData = HashMap<String,ArrayList<Pair<String,String>>>()

    /**
     *
     * Fetch data from the hourly API
     */
    private suspend fun fetchHourlyData(station: String, date: String, time: String, parameter: String) : ArrayList<HourlyResultObj> {
        val url = buildUrl(0, station, date, time, parameter)
        // runs requestData on the Dispatchers.IO thread to not block main thread
        val stringData = withContext(Dispatchers.IO) { requestData(url) }

        val json = findJSONObjects(stringData)
        return parseJSONtoHourlyObj(json)
    }

    /**
     * Fetch Daily data, all sensors.
     */
    private suspend fun fetchDailyData(date: String): ArrayList<AnytimeResultObj>  {
        val url = buildUrl(2, "", date,"","")
        val stringData = withContext(Dispatchers.IO) { requestData(url) }
        val json = findJSONObjects(stringData)
        return parseJSONtoAnytimeObj(json)
    }

    /**
     *
     * Fetch range of data from API
     */
    suspend fun fetchMonthData(year:String,
                               month:String,
                               time: String): ArrayList<AnytimeResultObj> {

        val date = year.plus("-").plus(month).plus("-*")
        val url = buildUrl(2, "", date, time,"")
        val stringData = withContext(Dispatchers.IO) { requestData(url) }
        val json = findJSONObjects(stringData)
        return parseJSONtoAnytimeObj(json)
    }

    /**
     * Helper to convert string to LocalDate
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun stringToDateConverter(date:String): LocalDate {
        return LocalDate.parse(date)
    }

    /**
     * Fetches the requested data
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchGraphData(dateStart: String,
                                dateEnd: String,
                                sensor: String,
                                station: String) {
        graphData.clear()
        val fetchedData = HashMap<String, ArrayList<AnytimeResultObj>>()

        var start = stringToDateConverter(dateStart)
        var end = stringToDateConverter(dateEnd)
        var dayToFetch = start

        do {
            val format: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd")
            val dateToFetch: String = dayToFetch.format(format)
            val dataList = fetchDailyData(dateToFetch)

            if (dataList.size > 0) {
                fetchedData[dataList[0].date] = dataList
            }
            dayToFetch.plusDays(1)
        } while (dayToFetch != end)

        for ((key, value) in fetchedData) {
            graphData[key] = filterToTimeValue(value,sensor,station)
        }
    }

    /**
     * Helper function to filter per sensor and station.
     * Returns a list of Pairs time,value
     */
    private fun filterToTimeValue(list: ArrayList<AnytimeResultObj>,
                             sensor: String,
                             station: String): ArrayList<Pair<String,String>> {
        var filtered = ArrayList<Pair<String,String>>()
        for ( entry in list ) {
            var value = entry.getValue(sensor,station)
            if ( !value.equals(null) ) {
                filtered.add(value)
            }
        }
        return filtered
    }

    /**
     *
     * Fetches data from hourly API and saves the result in hourData
     */
    suspend fun updateHourData(date: String, time: String) {
        hourData.clear()
        val dataList = fetchHourlyData("", date, time, "")
        for (data in dataList) {

            if (!hourData.containsKey(data.station)) {
                hourData[data.station] = ArrayList<HourlyResultObj>()
            }

            hourData[data.station]?.add(data)
        }
    }



    /**
     *
     * get station data
     */
    fun getStationDataHourly(station: String) : ArrayList<HourlyResultObj>? {
        return hourData[station]
    }

    /**
     *
     * Get graphdata hashtable.
     */
    fun getGraphData() : HashMap<String,ArrayList<Pair<String,String>>> {
        return graphData
    }

    /**
     *
     * Calculate distance in meters between two points
     * Translated from http://www.movable-type.co.uk/scripts/latlong.html
     *
     */
    private fun distanceInMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val x = Math.toRadians(lon1 - lon2) * cos(Math.toRadians(lat1 - lat2) / 2)
        val y = Math.toRadians(lat1 - lat2)
        return 6371000.0 * sqrt(x * x + y * y)
    }

    /**
     *
     * return the closest station
     */
    fun getClosestStationName(lat: Double, lon: Double): String {
        var closestDist = Double.MAX_VALUE
        var closestStation = ""
        for ((key, value) in hourData) {
            if (value.size > 0) {
                val stationLat = value[0].latitude_wgs84.toDoubleOrNull()
                val stationLon = value[0].longitude_wgs84.toDoubleOrNull()
                if (stationLat == null || stationLon == null)
                    continue
                val dist = distanceInMeters(lat, lon, stationLat, stationLon)
                if (dist <= closestDist) {
                    closestDist = dist
                    closestStation = key
                }
            }
        }
        return closestStation
    }


    /**
     *
     * Build the complete request URL towards the datasource API.
     */

    private fun buildUrl(
        api: Int,
        station: String,
        date: String,
        time: String,
        parameter: String
    ): String {
        var completeUrl: String = BASE_URL

        when (api) {
            0 -> completeUrl = completeUrl.plus(HOURLY)
            1 -> completeUrl = completeUrl.plus(YEARLY)
            2 -> completeUrl = completeUrl.plus(ALLTIME)
            else -> {
                completeUrl.plus(HOURLY)
                println("Wrong choice, hourlyAPI selected by default")
            }
        }
        if (station != "" ) {
            completeUrl = completeUrl.plus("&station=$station")
        }
        if (date != "" ) {
            completeUrl = completeUrl.plus("&date=$date")
        }
        if (time != "" ) {
            completeUrl = completeUrl.plus("&time=$time")
        }
        if (parameter != "" ) {
            completeUrl = completeUrl.plus("&parameter=$parameter")
        }
        return completeUrl
    }

    /**
     *
     * Request data from the API.
     */
    private fun requestData(url: String): String {
        val response = try {
            URL(url)
                .openStream()
                .bufferedReader()
                .use { it.readText() }
        } catch (e: Exception) {
            println("Request Data error: ")
            e.printStackTrace()
        }
        return response.toString()
    }


    /**
     *
     * Go through string and determine JSONObjects.
     */
    private fun findJSONObjects(jsonString: String): ArrayList<JSONObject> {

        val listOfResults = ArrayList<JSONObject>()

        try {
            val obj = JSONObject(jsonString)
            val array: JSONArray = obj.getJSONArray("results")

            if (array.length() != 0) {
                for (n in 0 until array.length()) {
                    listOfResults.add(array.getJSONObject(n))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return listOfResults
    }

    /**
     *
     * Used to parse JSON into HourlyResultObjects.
     */
    private fun parseJSONtoHourlyObj(listOfObjects: ArrayList<JSONObject>): ArrayList<HourlyResultObj> {
        val parsedObjects = ArrayList<HourlyResultObj>()

        for (n in listOfObjects) {
            val parsedObj = HourlyResultObj(
                n.getString("date"),
                n.getString("unit"),
                n.getString("raw_value"),
                n.getString("parameter"),
                n.getString("station"),
                n.getString("unit_code"),
                n.getString("latitude_wgs84"),
                n.getString("station_code"),
                n.getString("time"),
                n.getString("longitude_wgs84")
            )
            parsedObjects.add(parsedObj)
        }
        return parsedObjects
    }

    /**
     *
     * Used to parse JSON into AnytimeResultObjects.
     */
    private fun parseJSONtoAnytimeObj(listOfObjects: ArrayList<JSONObject>): ArrayList<AnytimeResultObj> {
        val parsedObjects = ArrayList<AnytimeResultObj>()

        for (n in listOfObjects) {
            val parsedObj = AnytimeResultObj(
                n.getString("date"),
                n.getString("femman_airpressure"),
                n.getString("mobil2_pm10"),
                n.getString("mobil2_no2"),
                n.getString("mobil2_nox"),
                n.getString("hagasodra_pm10"),
                n.getString("lejonet_rain"),
                n.getString("femman_nox"),
                n.getString("lejonet_temp"),
                n.getString("femman_windspeed"),
                n.getString("lejonet_rh"),
                n.getString("mobil3_nox"),
                n.getString("femman_pm10"),
                n.getString("mobil3_pm10"),
                n.getString("femman_temp"),
                n.getString("femman_rain"),
                n.getString("lejonet_windspeed"),
                n.getString("femman_o3"),
                n.getString("mobil3_no2"),
                n.getString("haganorra_nox"),
                n.getString("femman_winddir"),
                n.getString("lejonet_globrad"),
                n.getString("hagasodra_pm25"),
                n.getString("haganorra_no2"),
                n.getString("femman_rh"),
                n.getString("lejonet_airpressure"),
                n.getString("femman_pm25"),
                n.getString("femman_no2"),
                n.getString("time"),
                n.getString("mobil1_nox"),
                n.getString("femman_globrad"),
                n.getString("mobil1_no2"),
                n.getString("lejonet_winddir"),
                n.getString("mobil1_pm10")
            )
            parsedObjects.add(parsedObj)
        }
        return parsedObjects
    }

}