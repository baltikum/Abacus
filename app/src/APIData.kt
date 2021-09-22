
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse



class APIData {

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
    val columnnames = arrayOf(
        "date",
        "unit",
        "raw_value",
        "parameter",
        "station",
        "latitude_wgs84",
        "unit_code",
        "station_code",
        "time",
        "longitude_wgs84" )


    /**
     * a = 0-2 choose API, Daily,yearly, alltime.
     * b = 0-5 choose station.
     * c = 0-11 chose parameter
     */

    fun buildRequest(a: Int, b: Int, c: Int): String{
        return  chooseAPI(a).plus(getStation(b)).plus(getParameter(c));
    }

    val API = arrayOf(
        "https://catalog.goteborg.se/rowstore/dataset/cb541050-487e-4eea-b7b6-640d58f28092?",
        "https://catalog.goteborg.se/rowstore/dataset/12e75096-583d-4c0b-afac-093e90d8489e?",
        "https://catalog.goteborg.se/rowstore/dataset/3ec70191-60d2-4cdd-823e-f92f9938034b?" )


    fun chooseAPI(i: Int): String {
        return API[i];
    }

    fun getStation(i: Int): String {
        val station = stations[i];
        return "station=$station";
    }

    fun getParameter(i: Int): String {
        val parameter = parameters[i];
        return "parameter=$parameter";
    }

    fun httprequest(url: String): String {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}