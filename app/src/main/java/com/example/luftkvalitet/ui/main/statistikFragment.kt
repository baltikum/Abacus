package com.example.luftkvalitet.ui.main
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luftkvalitet.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.XAxis

import com.example.luftkvalitet.databinding.FragmentStatistikBinding



class statistikFragment : Fragment() {

    private var _binding: FragmentStatistikBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root
        val t = inflater.inflate(R.layout.fragment_statistik, container, false)




        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, 4f))
        entries.add(BarEntry(2f, 10f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 15f))
        entries.add(BarEntry(5f, 13f))
        entries.add(BarEntry(6f, 2f))

        val barDataSet = BarDataSet(entries, "")
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
        xAxis.position = XAxis.XAxisPosition.BOTTOM




        return view

    }






}