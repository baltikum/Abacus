package com.example.luftkvalitet.overview

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.network.AirApi
import com.example.luftkvalitet.network.AirApiService
import com.example.luftkvalitet.network.AirHourApiDataResponse
import kotlinx.coroutines.launch
import java.lang.Exception


class OverViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        _status.value = "Some text"
    }

    suspend fun getHourData(station: String?, date: String?, time: String?) : AirHourApiDataResponse? {
        var result : AirHourApiDataResponse? = null
        try {
            result = AirApi.hour.getData(station, date, time)
            //_status.value = listResult

            //_status.value = "Success: ${listResult.size} data received"

            println("getData Success")
            println("results: " + result.results.size)

        } catch (e: Exception) {
            _status.value = "Failure: ${e.message}"
            println("getData Error ${e.message}")
        }
        return result
    }

    fun updateStationData(station: String, date: String, time: String ,binding: FragmentStartBinding) {
        viewModelScope.launch {
            val data = getHourData(station, date, time)

            if (data != null && data.results.size > 0) {
                val res = data.results[0]
                binding.showInfo1.text = res.station//"Mobil 3"
                binding.showInfo2.text = res.latitudeWgs84
                binding.showInfo3.text = res.longitudeWgs84
                binding.showInfo4.text = "todo"
                binding.showInfo5.text = "todo"
                binding.showInfo6.text = "todo"
                binding.showInfo7.text = "todo"



                if(res.latitudeWgs84.toDouble() > 57.71){
                    binding.showInfo2.setTextColor(Color.RED);
                } else {
                    binding.showInfo2.setTextColor(Color.GREEN);
                }


            } else {
                println("No results for station: " + station)
            }


        }

    }


    /*
    fun getHourData(station: String?, date: String?, time: String?) {
        viewModelScope.launch {
            try {
                val listResult = AirApi.hour.getData(station, date, time)
                println(listResult)
                //_status.value = listResult
                //_status.value = "Success: ${listResult.size} data received"
                println("getData Success")
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                println("getData Error ${e.message}")
            }
        }
    }
     */



}