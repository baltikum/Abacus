package com.example.luftkvalitet.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luftkvalitet.network.AirApi
import com.example.luftkvalitet.network.AirApiService
import kotlinx.coroutines.launch
import java.lang.Exception


class OverViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        _status.value = "Some text"
    }

    fun getData(station: String?, date: String?, time: String?) {
        viewModelScope.launch {
            try {
                val listResult = AirApi.retrofitService.getData(station, date, time) // "date=2021-06-12&time=13:00*"
                _status.value = listResult

                //_status.value = "Success: ${listResult.size} data received"

                println("getData Success")

            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                println("getData Error ${e.message}")
            }
        }
    }
}