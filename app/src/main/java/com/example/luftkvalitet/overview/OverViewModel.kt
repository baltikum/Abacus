package com.example.luftkvalitet.overview

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.network.*
import com.example.luftkvalitet.ui.main.statistikFragment
import kotlinx.coroutines.launch

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



    init {
        updateHourData()
        updateGraphData("2021-09-16","2021-09-16","NOx","Femman", "", false)
        calculateMinMaxNOx(4)
    }


    fun updateHourData() {
        viewModelScope.launch {
            // todo get current time and date
            val date = "2021-09-17"
            val time = "22:00*"
            API.updateHourData(date, time)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun updateStationData(stationName : String, station: String, binding: FragmentStartBinding) {

        val dataList = API.getStationDataHourly(station)

        // clear all text
        binding.showInfo1.text = ""
        binding.showInfo2.text = ""
        binding.showInfo3.text = ""
        binding.showInfo4.text = ""
        binding.showInfo5.text = ""
        binding.showInfo6.text = ""
        binding.showInfo7.text = ""

        if (dataList != null && dataList.size > 0) {

            binding.showInfo1.text = stationName
            binding.showInfo2.text = dataList[0].latitude_wgs84
            binding.showInfo3.text = dataList[0].longitude_wgs84

            for (data in dataList) {
                when (data.parameter) {
                    "NO2" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 36){
                                binding.showInfo4.setTextColor(R.color.green)
                            } else if (data.raw_value.toDouble() > 48) {
                                binding.showInfo4.setTextColor(R.color.red)
                            } else {
                                binding.showInfo4.setTextColor(R.color.yellow)
                            }
                        }
                        binding.showInfo4.text = data.raw_value
                    }
                    "NOx" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 4){
                                binding.showInfo5.setTextColor(R.color.green)
                            } else if (data.raw_value.toDouble() > 12) {
                                binding.showInfo5.setTextColor(R.color.red)
                            } else {
                                binding.showInfo5.setTextColor(R.color.yellow)
                            }
                        }
                        binding.showInfo5.text = data.raw_value
                    }
                    "PM2" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 9){
                                binding.showInfo6.setTextColor(R.color.green)
                            } else if (data.raw_value.toDouble() > 17) {
                                binding.showInfo6.setTextColor(R.color.red)
                            } else {
                                binding.showInfo6.setTextColor(R.color.yellow)
                            }
                        }
                        binding.showInfo6.text = data.raw_value
                    }
                    "PM10" -> {

                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() < 25){
                                binding.showInfo7.setTextColor(R.color.green)
                            } else if (data.raw_value.toDouble() > 35) {
                                binding.showInfo7.setTextColor(R.color.red)
                            } else {
                                binding.showInfo7.setTextColor(R.color.yellow)
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
     * updates GraphData inside API and initiates callback to listeners.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateGraphData(startDate: String,
                        endDate:String,
                        sensor: String,
                        station: String,
                        time: String,
                        average: Boolean) {

        viewModelScope.launch {
            API.fetchGraphData(startDate,endDate,sensor,station,time,average)
            API.updateListeners()
        }
    }


    /**
     * Call this with an integer saying for how many days you want the min max values to be calculated form.
     */
    private fun calculateMinMaxNOx(rangeInDays: Int) {
        viewModelScope.launch {
            API.calculateMinMax("NOx", rangeInDays)
        }
    }
}