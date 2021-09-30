package com.example.luftkvalitet.overview

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
            println("update hour data complete")

        }
    }

    fun updateStationData(station: String ,binding: FragmentStartBinding) {

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
                    "NO2" -> binding.showInfo4.text = data.raw_value
                    "NOx" -> binding.showInfo5.text = data.raw_value
                    "PM2" -> binding.showInfo6.text = data.raw_value
                    "PM10" -> binding.showInfo7.text = data.raw_value
                }
            }
        } else {
            println("No results for station: " + station)

        }

    }

}