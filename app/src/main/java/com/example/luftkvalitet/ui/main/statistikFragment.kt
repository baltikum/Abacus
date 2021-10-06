package com.example.luftkvalitet.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.databinding.FragmentStatistikBinding
import com.example.luftkvalitet.overview.OverViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.XAxis




// TODO: Rename parameter arguments, choose names that match


class statistikFragment : Fragment() {


    private var _binding: FragmentStatistikBinding? = null

    private val overViewModel = OverViewModel()


    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root
        val t = inflater.inflate(R.layout.fragment_statistik, container, false)


        binding.stationSpinn.adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.stations_array,
            android.R.layout.simple_spinner_item).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.stationSpinn.adapter = adapter
        }

        binding.sensorSpinn.adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.stations_array,
            android.R.layout.simple_spinner_item).also{
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sensorSpinn.adapter = adapter
        }


        /**
         * Använd ett liknande call för att hämta grafdata
         */
        var graphData = HashMap<String,ArrayList<Pair<String,String>>>()
        graphData = overViewModel.updateGraphData("2020-02-08","2020-02-09","","NOx","Femman")

        var arr = graphData["2020-02-08"] // Array av Pairs på det datumet

        if (arr != null) {
            for ( entry in arr ) {
                val (time, value) = entry
                    println("Time: $time , SensorValue: $value")
            }
        }




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