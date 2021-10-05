package com.example.luftkvalitet.network
val stations = arrayOf(
    "Femman",
    "Haga_Norra",
    "Lejonet",
    "Mobil_1",
    "Mobil_2",
    "Mobil_3" )

val parameters = arrayOf(
    "Temperature",
    "Relative_Humidity",
    "Global_Radiation",
    "Air_Pressure",
    "Wind_Speed",
    "Wind_Direction",
    "Rain",
    "NO2",
    "NOx",
    "O3",
    "PM10",
    "PM2.5" )

class AnytimeResultObj (

    val date: String,
    val femman_airpressure: String,
    val mobil2_pm10: String,
    val mobil2_no2: String,
    val mobil2_nox: String,
    val hagasodra_pm10: String,
    val lejonet_rain: String,
    val femman_nox: String,
    val lejonet_temp: String,
    val femman_windspeed: String,
    val lejonet_rh: String,
    val mobil3_nox: String,
    val femman_pm10: String,
    val mobil3_pm10: String,
    val femman_temp: String,
    val femman_rain: String,
    val lejonet_windspeed: String,
    val femman_o3: String,
    val mobil3_no2: String,
    val haganorra_nox: String,
    val femman_winddir: String,
    val lejonet_globrad: String,
    val hagasodra_pm25: String,
    val haganorra_no2: String,
    val femman_rh: String,
    val lejonet_airpressure: String,
    val femman_pm25: String,
    val femman_no2: String,
    val time: String,
    val mobil1_nox: String,
    val femman_globrad: String,
    val mobil1_no2: String,
    val lejonet_winddir: String,
    val mobil1_pm10: String ) {

    fun getValue(sensor: String, station: String): Pair<String, String>  {
        var ret: Pair<String, String>
        when (sensor) {
            "NOx" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_nox) }
                    "Haga_Norra" -> { ret = Pair(date, haganorra_nox) }
                    "Mobil_1" -> { ret = Pair(date, mobil1_nox) }
                    "Mobil_2" -> { ret = Pair(date, mobil2_nox) }
                    "Mobil_3" -> { ret = Pair(date, mobil3_nox) }
                    else -> { return null!! }
                }
            }

            "NO2" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_no2) }
                    "Haga_Norra" -> { ret = Pair(date, haganorra_no2) }
                    "Mobil_1" -> { ret = Pair(date, mobil1_no2) }
                    "Mobil_2" -> { ret = Pair(date, mobil2_no2) }
                    "Mobil_3" -> { ret = Pair(date, mobil3_no2) }
                    else -> { return null!! }
                }
            }

            "O3" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_o3) }
                    else -> { return null!! }
                }
            }

            "PM10" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_pm10) }
                    "Haga_Sodra" -> { ret = Pair(date, hagasodra_pm10) }
                    "Mobil_1" -> { ret = Pair(date, mobil1_pm10) }
                    "Mobil_2" -> { ret = Pair(date, mobil2_pm10) }
                    "Mobil_3" -> { ret = Pair(date, mobil3_pm10) }
                    else -> { return null!! }
                }
            }

            "PM2.5" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_pm25) }
                    "Haga_Sodra" -> { ret = Pair(date, hagasodra_pm25) }
                    else -> { return null!! }
                }
            }

            "Wind_Speed" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_windspeed) }
                    "Lejonet" -> { ret = Pair(date, lejonet_windspeed) }
                    else -> { return null!! }
                }
            }

            "Wind_Direction" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_winddir) }
                    "Lejonet" -> { ret = Pair(date, lejonet_winddir) }
                    else -> { return null!! }
                }
            }

            "Rain" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_rain) }
                    "Lejonet" -> { ret = Pair(date, lejonet_rain) }
                    else -> { return null!! }
                }
            }

            "Temperature" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_temp) }
                    "Lejonet" -> { ret = Pair(date, lejonet_temp) }
                    else -> { return null!! }
                }
            }

            "Relative_Humidity" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_rh) }
                    "Lejonet" -> { ret = Pair(date, lejonet_rh) }
                    else -> { return null!! }
                }
            }

            "Global_Radiation" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_globrad) }
                    "Lejonet" -> { ret = Pair(date, lejonet_globrad) }
                    else -> { return null!! }
                }
            }

            "Air_Pressure" -> {
                when (station) {
                    "Femman" -> { ret = Pair(date, femman_airpressure) }
                    "Lejonet" -> { ret = Pair(date, lejonet_airpressure) }
                    else -> { return null!! }
                }
            }

            else -> {
                return null!!
            }
        }
        return ret
    }
}