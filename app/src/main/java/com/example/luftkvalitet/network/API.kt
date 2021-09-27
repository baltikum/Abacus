package com.example.luftkvalitet.network

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL


private const val BASE_URL = "https://catalog.goteborg.se/rowstore/dataset/"
private const val HOURLY = "cb541050-487e-4eea-b7b6-640d58f28092/json?" //0
private const val YEARLY ="12e75096-583d-4c0b-afac-093e90d8489e/json?"  //1
private const val ALLTIME = "3ec70191-60d2-4cdd-823e-f92f9938034b/json?" //2



class API {

    public fun getData(api: Int,
                       station: String,
                       date: String,
                       time: String,
                       parameter: String): ArrayList<HourlyResultObj> { // Dynamisk typ av array ???

        when(api) {
            0 -> {
                return parseJSONtoHourlyObj(
                    findJSONObjects(
                        requestData(
                            buildUrl(api, station, date, time, parameter)
                        )
                    )
                )
            }
            else -> {  /// om 1 eller 2 AnytimeResultObjects.
                println("Something went wrong")
                return parseJSONtoHourlyObj(
                    findJSONObjects(
                        requestData(
                            buildUrl(api, station, date, time, parameter)
                        )
                    )
                )
            }
        }
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
            0 -> completeUrl.plus(HOURLY)
            1 -> completeUrl.plus(YEARLY)
            2 -> completeUrl.plus(ALLTIME)
            else -> {
                completeUrl.plus(HOURLY)
                println("Wrong choice, hourlyAPI selected by default")
            }
        }
        if (station != "" ) {
            completeUrl.plus("&station=$station")
        }
        if (date != "" ) {
            completeUrl.plus("&date=$date")
        }
        if (time != "" ) {
            completeUrl.plus("&time=$time")
        }
        if (parameter != "" ) {
            completeUrl.plus("&parameter=$parameter")
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
        } catch (e: NullPointerException) {
        }
        return response.toString()
    }


    /**
     *
     * Go through string and determine JSONObjects.
     */
    private fun findJSONObjects(jsonString: String): ArrayList<JSONObject> {

        val listOfResults = ArrayList<JSONObject>();

        try {
            val obj: JSONObject = JSONObject(jsonString)
            val array: JSONArray = obj.getJSONArray("results");

            if (array.length() != 0) {
                for (n in 0 until array.length()) {
                    listOfResults.add(array.getJSONObject(n));
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return listOfResults;
    }

    /**
     *
     * Used to parse JSON into HourlyResultObjects.
     */
    private fun parseJSONtoHourlyObj(listOfObjects: ArrayList<JSONObject>): ArrayList<HourlyResultObj> {
        val parsedObjects = ArrayList<HourlyResultObj>()

        for (n in listOfObjects) {
            val parsedObj: HourlyResultObj = HourlyResultObj(
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
            val parsedObj: AnytimeResultObj = AnytimeResultObj(
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