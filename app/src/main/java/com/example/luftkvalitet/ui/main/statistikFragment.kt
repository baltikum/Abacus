package com.example.luftkvalitet.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.ColorRes
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.databinding.FragmentStatistikBinding
import com.example.luftkvalitet.overview.OverViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class statistikFragment : Fragment() {


    private var _binding: FragmentStatistikBinding? = null



    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root
        val t = inflater.inflate(R.layout.fragment_statistik, container, false)




        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 12f))
        entries.add(BarEntry(1f, 4f))
        entries.add(BarEntry(2f, 10f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 15f))
        entries.add(BarEntry(5f, 13f))
        entries.add(BarEntry(6f, 2f))
        entries.add(BarEntry(7f, 12f))


        val barDataSet = BarDataSet(entries, "test")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.valueTextSize = 10f

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
        chart.setScaleEnabled(false)
        chart.setFitBars(true);


        val labels: ArrayList<String> = ArrayList()
        labels.add("Jan")
        labels.add("Feb")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")
        labels.add("July")
        labels.add("Aug")


        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        chart.setAutoScaleMinMaxEnabled(true);

        chart.invalidate()

        val xAxis: XAxis = chart.getXAxis()

        xAxis.position = XAxis.XAxisPosition.BOTTOM



        binding.button.setOnClickListener{

            barDataSet.clear()

            barDataSet.addEntry(BarEntry(0f, 12f))
            barDataSet.addEntry(BarEntry(1f, 13f))
            barDataSet.addEntry(BarEntry(2f, 14f))
            barDataSet.addEntry(BarEntry(3f, 15f))
            barDataSet.addEntry(BarEntry(4f, 12f))
            barDataSet.addEntry(BarEntry(5f, 13f))
            barDataSet.addEntry(BarEntry(6f, 14f))

            labels.clear()
            labels.add("Mon")
            labels.add("Tue")
            labels.add("Wed")
            labels.add("Thur")
            labels.add("Fri")
            labels.add("Sat")
            labels.add("Sun")


            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)


            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
        }

        binding.button2.setOnClickListener{

            barDataSet.clear()

            entries.add(BarEntry(0f, 12f))
            entries.add(BarEntry(1f, 4f))
            entries.add(BarEntry(2f, 10f))
            entries.add(BarEntry(3f, 2f))
            entries.add(BarEntry(4f, 15f))
            entries.add(BarEntry(5f, 49f))
            entries.add(BarEntry(6f, 2f))
            entries.add(BarEntry(7f, 12f))

            labels.clear()
            labels.add("Jan")
            labels.add("Feb")
            labels.add("March")
            labels.add("April")
            labels.add("May")
            labels.add("June")
            labels.add("July")
            labels.add("Aug")


            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
        }








        return view
    }






}