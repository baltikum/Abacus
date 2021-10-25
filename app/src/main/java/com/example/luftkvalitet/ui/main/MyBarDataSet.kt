package com.example.luftkvalitet.ui.main


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
/*this class extends BarDataSet class so it can override its getColor function and change color of barchart as we want*/
class MyBarDataSet(yVals: MutableList<BarEntry>?, label: String?) : BarDataSet(yVals, label) {

    private var lowValue: Float = 10f
    private var highValue: Float = 20f
    var sensor: String = "NO2"

    @RequiresApi(Build.VERSION_CODES.O)

    override fun getEntryIndex(e: BarEntry?): Int {
        return 0
    }

    /**
     * Takes sensor input and adjust the color depending of its value
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getColor(index: Int): Int {
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
        if(sensor == "NOx"){
            lowValue = 15f
            highValue = 30f
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