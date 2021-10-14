package com.example.luftkvalitet.ui.main

import com.example.luftkvalitet.network.API
import com.example.luftkvalitet.ui.main.statistikFragment.Companion.sensor_input
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class MyBarDataSet(yVals: MutableList<BarEntry>?, label: String?) : BarDataSet(yVals, label) {


    private var lowValue: Float = 10f
    private var highValue: Float = 20f
    var sensor: String = ""

    override fun getEntryIndex(e: BarEntry?): Int {
        TODO("Not yet implemented")
    }

    override fun getColor(index: Int): Int {

        sensor = sensor_input

        if(sensor == "PM2.5"){

            lowValue = 9f
            highValue = 17f
        }
        if(sensor == "PM10"){

            lowValue = 25f
            highValue = 35f
        }
        if(sensor == "NO2"){

            lowValue = 36f
            highValue = 48f
        }
        if(sensor == "NOx"){ //researcha värdena!!

            lowValue = 4f
            highValue = 12f
        }
        if(getEntryForIndex(index).y <= lowValue){ //grön
            return mColors.get(0)
        }
        else if(getEntryForIndex(index).y > highValue ){ //orange
            return mColors.get(1)
        }
        return mColors.get(2) //röd

    }





}
/*
        if (getEntryForIndex(index).y < 10f) {
            return mColors.get(0)
        } else if (getEntryForIndex(index).y > 20f && getEntryForIndex(index).y ){
            return mColors.get(1)
        } else {
            return mColors.get(2)
        }

    }
*/
