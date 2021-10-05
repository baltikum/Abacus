package com.example.luftkvalitet.overview

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.network.*
import kotlinx.coroutines.launch


class OverViewModel : ViewModel() {

    private val api = API()

    init {
        updateHourData()
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

}