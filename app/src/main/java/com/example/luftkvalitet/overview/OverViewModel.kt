package com.example.luftkvalitet.overview

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.databinding.FragmentStatistikBinding
import com.example.luftkvalitet.network.*
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.launch
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

val stations_lista = arrayOf(
    "Femman",
    "Haga_Norra",
    "Haga_Sodra",
    "Lejonet",
    "Mobil_1",
    "Mobil_2",
    "Mobil_3" )

val parameter_lista = arrayOf(
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
@RequiresApi(Build.VERSION_CODES.O)
class OverViewModel : ViewModel() {

    var station_input: String = "Femman"
    var sensor_input: String = "NOx"
    private val api = API()

    init {
        updateHourData()
        updateGraphData(api.rewindOneWeek("2021-09-16"),"2021-09-16","NOx","Mobil_2","12:00+01:00",TRUE)
        // updateGraphData(api.todaysDate(),api.rewindOneWeek(api.todaysDate()),"NOx","Femman") // AppPresets??
    }

    fun setStation(input: String) : API{
        station_input = input
        updateGraphData(api.rewindOneWeek("2021-09-16"),"2021-09-16","NOx",station_input,"12:00+01:00",TRUE)
        return api
    }
    fun updateHourData() {
        viewModelScope.launch {
            // todo get current time and date
            val date = "2021-09-17"
            val time = "22:00*"
            api.updateHourData(date, time)
        }
    }

    fun updateStationData(station: String, binding: FragmentStartBinding) {

        val dataList = api.getStationDataHourly(station)

        // clear all text
        binding.showInfo1.text = ""
        binding.showInfo2.text = ""
        binding.showInfo3.text = ""
        binding.showInfo4.text = ""
        binding.showInfo5.text = ""
        binding.showInfo6.text = ""
        binding.showInfo7.text = ""

        if (dataList != null && dataList.size > 0) {

            binding.showInfo1.text = dataList[0].station
            binding.showInfo2.text = dataList[0].latitude_wgs84
            binding.showInfo3.text = dataList[0].longitude_wgs84

            for (data in dataList) {
                when (data.parameter) {
                    "NO2" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 36){
                                binding.showInfo4.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 48) {
                                binding.showInfo4.setTextColor(Color.RED)
                            } else {
                                binding.showInfo4.setTextColor(Color.parseColor ("#D1D100"))
                            }
                        }
                        binding.showInfo4.text = data.raw_value
                    }
                    "NOx" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 4){
                                binding.showInfo5.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 12) {
                                binding.showInfo5.setTextColor(Color.RED)
                            } else {
                                binding.showInfo5.setTextColor(Color.parseColor ("#D1D100"))
                            }
                        }
                        binding.showInfo5.text = data.raw_value
                    }
                    "PM2" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 9){
                                binding.showInfo6.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 17) {
                                binding.showInfo6.setTextColor(Color.RED)
                            } else {
                                binding.showInfo6.setTextColor(Color.parseColor ("#D1D100"))
                            }
                        }
                        binding.showInfo6.text = data.raw_value
                    }
                    "PM10" -> {

                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 25){
                                binding.showInfo7.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 35) {
                                binding.showInfo7.setTextColor(Color.RED)
                            } else {
                                binding.showInfo7.setTextColor(Color.parseColor ("#D1D100"))
                            }
                        }

                        binding.showInfo7.text = data.raw_value
                    }
                }
            }
        } else {
            println("No results for station: " + station)

        }

    }



    /**
     * update GraphData
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateGraphData(startDate: String,
                        endDate:String,
                        sensor: String,
                        station: String,
                        time: String,
                        average: Boolean ) {

        viewModelScope.launch {
            api.fetchGraphData(startDate,endDate,sensor,station,time,average)
            println("Finished fetching DATA---------------------------------")
            println("graph size is " + api.getGraphData().size )
        }
    }

    fun returnApi(): API
    {
        return api
    }
}