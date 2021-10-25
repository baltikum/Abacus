package com.example.luftkvalitet.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentStatistikBinding
import com.example.luftkvalitet.network.API
import com.example.luftkvalitet.network.APIListener
import com.example.luftkvalitet.overview.OverViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.lang.Boolean
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class statistikFragment : Fragment() , APIListener {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onGraphDataUpdated() { //callbacker for overviewmodel,

        println("fyll grafen nu här !------")
        if(week_day == "week"){
            week()
            notifyChanges()
        }
        if(week_day == "day"){
            day()
            notifyChanges()
        }
    }


    private var _binding: FragmentStatistikBinding? = null
    private  var labels: ArrayList<String> = ArrayList()
    private var entries: ArrayList<BarEntry> = ArrayList()
    var dataSets: ArrayList<MyBarDataSet> = ArrayList()
    private lateinit var chart: BarChart


    /**
     * Creates a bar graph and a spinner
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private val overViewModel = OverViewModel()
    private var barDataSet = MyBarDataSet(entries, "")
    private var graphData = HashMap<String,ArrayList<Pair<String,String>>>()
    private val binding get() = _binding!!
    private var week_day = "day"
    private var sensor_input: String = "NOx"
    private var station_input: String = "Femman"
    private var entryIndex: Float = 0f
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root
        API.addListener(this)
        entries = ArrayList()
        chart = binding.barChart
        dataSets.add(barDataSet)
        val data = BarData(dataSets as List<IBarDataSet>?)

        chart.data = data
        chart.axisLeft.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawAxisLine(false)
        chart.axisLeft.axisMinimum = 0F
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
        chart.animateY(1000)
        chart.setScaleEnabled(false)
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.setAutoScaleMinMaxEnabled(true);
        chart.invalidate()
        val xAxis: XAxis = chart.getXAxis()
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        binding.setDay.setOnClickListener{
            week_day = "day"
            updateAPI()
        }

        binding.setWeek.setOnClickListener{
            week_day = "week"
            updateAPI()
        }

        binding.spinner2.adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.stations_array, android.R.layout.simple_spinner_item).also{
                adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner2.adapter = adapter
        }
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            /**
             * Sets the station that is picked in the spinner
             */
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                station_input = parent?.getItemAtPosition(position).toString()
                if (API.isSensorAvailable(sensor_input, API.convertStationNames(station_input))) {
                    updateAPI()
                }
                else{
                    Toast.makeText(activity, "station not available", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val out = "error"
                Toast.makeText(activity, out, Toast.LENGTH_LONG).show()
                println(out)
            }
        }
        binding.setNo2.setOnClickListener {
            sensor_input= "NO2"
            if (API.isSensorAvailable(sensor_input, API.convertStationNames(station_input))) {
                binding.setValue.text = sensor_input
                updateAPI()
            }
            else{
                Toast.makeText(activity, "data not available", Toast.LENGTH_SHORT).show()
            }
        }
        binding.setNox.setOnClickListener {
            sensor_input= "NOx"
            if (API.isSensorAvailable(sensor_input, API.convertStationNames(station_input))) {
                binding.setValue.text = sensor_input
                updateAPI()
            }
            else{
                Toast.makeText(activity, "data not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.setPm25.setOnClickListener {
            sensor_input = "PM2.5"
            if (API.isSensorAvailable(sensor_input, API.convertStationNames(station_input))) {
                binding.setValue.text = sensor_input
                updateAPI()
            }
            else{
                Toast.makeText(activity, "data not available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.setPm10.setOnClickListener {
            sensor_input = "PM10"
            if (API.isSensorAvailable(sensor_input, API.convertStationNames(station_input))) {
                binding.setValue.text = sensor_input
                updateAPI()
            }
            else{
                Toast.makeText(activity, "data not available", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }


    /**
     * Updates the API with the values have choosen
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateAPI(){
      if (week_day == "week") {
              overViewModel.updateGraphData(
                  API.rewindOneWeek("2021-09-16"),
                  "2021-09-16",
                  sensor_input,
                  API.convertStationNames(station_input),
                  "13:00+01:00",
                  Boolean.TRUE
              )
          } else if (week_day == "day") {
              overViewModel.updateGraphData(
                  "2021-09-16",
                  "2021-09-16",
                  sensor_input,
                  API.convertStationNames(station_input),
                  "",
                  Boolean.FALSE
              )
          }
    }

    /**
     * Updates the barchart when having chosen last week
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun week(){
        updateBarColor()
        entryIndex = 0f
        labels.clear()
        barDataSet.clear()
        graphData = API.getGraphData()

        for ((date, list) in graphData ) {
            println(date.plus("------"))
            for ( entry in list ) {
                var (time, value) = entry //time == time average eller nonaverage
                println("Time: $time , SensorValue: $value") //sensor value
                if(value.toFloatOrNull() == null)
                    value = "0"
                if(value.toFloat() < 0)
                    value = "0"

                labels.add(date.subSequence(5, 10) as String) //lägger ut datumet
                barDataSet.addEntry(BarEntry(entryIndex, value.toFloat()))
                entryIndex += 1

            }
        }
        chart.data //set the chart data to be able to modify it later
        chart.setVisibleXRangeMinimum(7f) //set x axis range
        //allow 7 values to be displayed at once on the x-axis, not more
        chart.setVisibleXRangeMaximum(7f)
    }

    /**
     * Updates the barchart when having chosen last day
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun day(){
        updateBarColor()
        entryIndex = 0f
        labels.clear()
        barDataSet.clear()

        graphData = API.getGraphData()

        for ((date, list) in graphData ) {
            for ( entry in list ) {
                var (time, value) = entry //time == clock time
                println("Time: $time , SensorValue: $value") //sensor value
                if(value.toFloatOrNull() == null)
                    value = "0"
                if(value.toFloat() < 0)
                    value = "0"

                labels.add(time.subSequence(0, 5) as String)
                barDataSet.addEntry(BarEntry(entryIndex, value.toFloat()))
                entryIndex += 1
            }
        }
        chart.data //set the chart data to be able to modify it later
        chart.setVisibleXRangeMinimum(24f) //set x axis range
        chart.setVisibleXRangeMaximum(24f)
    }

    /**
     * Notify changes for the barchart
     */
    private fun notifyChanges(){
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.animateY(1000)
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    /**
     * Calls on MyBarDataSet and customize the color according to sensor input
     */
    private fun updateBarColor(){
        barDataSet.sensor = sensor_input
        barDataSet.setColors(
            ContextCompat.getColor(chart.context, R.color.green),
            ContextCompat.getColor(chart.context, R.color.red),
            ContextCompat.getColor(chart.context, R.color.yellow)
        )
    }
}