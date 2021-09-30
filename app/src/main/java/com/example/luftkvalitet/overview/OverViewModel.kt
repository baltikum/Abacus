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
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val api = API()

    init {
        _status.value = "Some text"
    }

    fun updateStationData(station: String, date: String, time: String ,binding: FragmentStartBinding) {
        viewModelScope.launch {
            val dataList = api.getHourlyData(station, date, time, "")
            // clear all text
            binding.showStation.text = ""
            binding.showLatitude.text = ""
            binding.showLongitude.text = ""
            binding.showNo2.text = ""
            binding.showNox.text = ""
            binding.showPm25.text = ""
            binding.showPm10.text = ""

            if (dataList.size > 0) {

                binding.showStation.text = dataList[0].station
                binding.showLatitude.text = dataList[0].latitude_wgs84
                binding.showLongitude.text = dataList[0].longitude_wgs84


                /*
                if(dataList[0].latitude_wgs84.toDouble() > 57.71){
                   binding.showLatitude.setTextColor(Color.RED)
                } else {
                    binding.showLatitude.setTextColor(Color.GREEN)
                }

                 */


                for (data in dataList) {
                    when (data.parameter) {
                        "NO2" -> {
                            if(data.raw_value.toDouble() < (36)){
                                binding.showNo2.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 48) {
                                binding.showNo2.setTextColor(Color.RED)
                            } else {
                                binding.showNo2.setTextColor(Color.parseColor ("#D1D100"))
                            }
                            /*
                          var test = 10
                          if(test < 4){
                              binding.showNo2.setTextColor(Color.GREEN)
                          } else if (test > 12) {
                              binding.showNo2.setTextColor(Color.RED)
                          } else {
                              binding.showNo2.setTextColor(Color.parseColor ("#D1D100"))
                          }
                          binding.showNo2.text = data.raw_value
*/
                        }

                        "NOx" -> {
                            if(data.raw_value.toDouble() < 4){
                                binding.showNox.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 12) {
                                binding.showNox.setTextColor(Color.RED)
                            } else {
                                binding.showNox.setTextColor(Color.parseColor ("#D1D100"))
                            }
                            binding.showNox.text = data.raw_value
                        }

                        "PM2" -> {
                            if(data.raw_value.toDouble() < 9){
                                binding.showPm25.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 17) {
                                binding.showPm25.setTextColor(Color.RED)
                            } else {
                                binding.showPm25.setTextColor(Color.parseColor ("#D1D100"))
                            }
                            binding.showPm25.text = data.raw_value
                        }

                        "PM10" -> {
                            if(data.raw_value.toDouble() < 25){
                                binding.showPm10.setTextColor(Color.GREEN)
                            } else if (data.raw_value.toDouble() > 35) {
                                binding.showPm10.setTextColor(Color.RED)
                            } else {
                                binding.showPm10.setTextColor(Color.parseColor ("#D1D100"))
                            }
                            binding.showPm10.text = data.raw_value
                        }
                    }
                }
            } else {
                println("No results for station: " + station)

            }
        }
    }

}