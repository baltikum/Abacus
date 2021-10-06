package com.example.luftkvalitet.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.luftkvalitet.databinding.FragmentStatistikBinding

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

import kotlin.collections.ArrayList






class statistikFragment : Fragment() {

    private var _binding: FragmentStatistikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root

        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 12f))
        entries.add(BarEntry(1f, 4f))
        entries.add(BarEntry(2f, 10f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 15f))
        entries.add(BarEntry(5f, 13f))

        val barDataSet = BarDataSet(entries, "BarDatatest")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val data = BarData(barDataSet)
        val chart = binding.barChart

        chart.data = data
        //hide grid lines
        chart.axisLeft.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawAxisLine(false)

        //remove right y-axis
        chart.axisRight.isEnabled = false

        //remove legend
        chart.legend.isEnabled = false
        //remove description label
        chart.description.isEnabled = false
        //add animation
        chart.animateY(1000)
        //draw chart
        chart.invalidate()

        val xAxis: XAxis = chart.getXAxis()



        val labels: ArrayList<String> = ArrayList()
        /*labels.add("Jan")
        labels.add("Feb")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")
        labels.add("July")
        labels.add("Aug")*/

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.invalidate()
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        binding.button.setOnClickListener{

            barDataSet.clear()
            barDataSet.addEntry(BarEntry(1f, 5f))
            barDataSet.setColors(Color.BLUE)

            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()

           chart.invalidate() //uppdaterar
        }


        return view


    }




}