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
        //uppdatera chart efter ny data har hämtats
        if(week_day == "week"){
            updateEntries()
            week()
            notifyChanges()
        }
        if(week_day == "day"){
            updateEntries()
            day()
            notifyChanges()
        }
    }


    private var _binding: FragmentStatistikBinding? = null
    private  var labels: ArrayList<String> = ArrayList()
    private var entries: ArrayList<BarEntry> = ArrayList()

    var dataSets: ArrayList<MyBarDataSet> = ArrayList()
    private lateinit var chart: BarChart

    @RequiresApi(Build.VERSION_CODES.O)
    private val overViewModel = OverViewModel()
    private var barDataSet = MyBarDataSet(entries, "")
    private var graphData = HashMap<String,ArrayList<Pair<String,String>>>()
    private val binding get() = _binding!!
    private var week_day = "week"
    private var sensor_input: String = "NOx"
    private var station_input: String = "Femman"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root
        API.addListener(this) // Lägg till oss som lyssnare på API


        entries = ArrayList()
        entries.add(BarEntry(1f, 4f))
        entries.add(BarEntry(2f, 10f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 15f))
        entries.add(BarEntry(5f, 13f))
        entries.add(BarEntry(6f, 2f))



        chart = binding.barChart



        barDataSet.setColors(
            ContextCompat.getColor(chart.context, R.color.green),
            ContextCompat.getColor(chart.context, R.color.orange),
            ContextCompat.getColor(chart.context, R.color.red)
        )



        dataSets.add(barDataSet)

        val data = BarData(dataSets as List<IBarDataSet>?)





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
        //chart.setFitBars(true);



        labels.add("Jan")
        labels.add("Feb")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")
        labels.add("July")
        labels.add("Aug")



        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        //chart.xAxis.labelCount = 8
        chart.setAutoScaleMinMaxEnabled(true);

        chart.invalidate()

        val xAxis: XAxis = chart.getXAxis()

        xAxis.position = XAxis.XAxisPosition.BOTTOM

        //last day
        binding.button2.setOnClickListener{
            week_day = "day"
            updateAPI()
            /*updateEntries()
            day()
            notifyChanges()*/

        }
        //last week
        binding.button3.setOnClickListener{
            week_day = "week"
            updateAPI()
            /*updateEntries()
            week()
            notifyChanges()*/
        }

        binding.spinner2.adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.stations_array, android.R.layout.simple_spinner_item).also{
                adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner2.adapter = adapter
        }
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


                station_input = parent?.getItemAtPosition(position).toString()


                Toast.makeText(activity, station_input, Toast.LENGTH_LONG).show()
                //updateChart()
                updateAPI()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val out = "error"
                Toast.makeText(activity, out, Toast.LENGTH_LONG).show()
                println(out)
            }



        }
        binding.setNo2.setOnClickListener {
            sensor_input= "NO2"
            if (API.isSensorAvailable(sensor_input, station_input)) {
                updateAPI()
            }
        }
        binding.setNox.setOnClickListener {
            sensor_input= "NOx"
            if (API.isSensorAvailable(sensor_input, station_input)) {
                updateAPI()
            }
        }

        binding.setPm25.setOnClickListener {
            sensor_input = "PM2.5"
            if (API.isSensorAvailable(sensor_input, station_input)) {
                updateAPI()
            }
        }

        binding.setPm10.setOnClickListener {
            sensor_input = "PM10"
            if (API.isSensorAvailable(sensor_input, station_input)) {
                updateAPI()
            }
        }
        binding.button.setOnClickListener {
            if (API.isSensorAvailable(sensor_input, station_input)) {
                updateAPI()
            }
        }

        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateAPI(){
        if(sensor_input != null && station_input != null) {
            Toast.makeText(activity, "no null values", Toast.LENGTH_LONG).show()
            if(week_day == "week") {
                    println("................................" )
                    println("sensor in: " + sensor_input)
                    println("sensor in: " + station_input)
                    println("................................" )
                    overViewModel.updateGraphData(
                        API.rewindOneWeek("2021-09-16"),
                        "2021-09-16",
                        sensor_input,
                        API.convertStationNames(station_input),
                        "13:00+01:00",
                        Boolean.TRUE
                    )
            }
            else if(week_day == "day") {
                    overViewModel.updateGraphData(
                        "2021-09-16",
                        "2021-09-17",
                        sensor_input,
                        API.convertStationNames(station_input),
                        "13:00+01:00",
                        Boolean.TRUE
                    )
            }
        }
    }
    private fun updateEntries(){
        barDataSet.clear()

        barDataSet.addEntry(BarEntry(0f, 12f))
        barDataSet.addEntry(BarEntry(1f, 35f))
        barDataSet.addEntry(BarEntry(2f, 14f))
        barDataSet.addEntry(BarEntry(3f, 15f))
        barDataSet.addEntry(BarEntry(4f, 20f))
        barDataSet.addEntry(BarEntry(5f, 13f))
        barDataSet.addEntry(BarEntry(6f, 8f))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun week(){
        updateBarColor()

        var entryIndex = 0f
        labels.clear()
        barDataSet.clear()
        graphData = API.getGraphData()

        for ((date, list) in graphData ) {
            //println(date.plus("------"))
            for ( entry in list ) {
                var (time, value) = entry //time == time average eller nonaverage
                //println("Time: $time , SensorValue: $value") //sensor value
                if(value.toFloat() < 0){
                    value = "0f"
                }
                binding.showText1.text = time
                binding.showText2.text = value

                labels.add(date.subSequence(5, 10) as String) //lägger ut datumet
                //entries.add(BarEntry(entryIndex, value.toFloat()))
                barDataSet.addEntry(BarEntry(entryIndex, value.toFloat()))
                entryIndex = entryIndex + 1

            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun day(){
        updateBarColor()

        var entryIndex = 0f
        labels.clear()
        barDataSet.clear()
        graphData = API.getGraphData()

        for ((clock, list) in graphData ) {
            //println(clock.plus("------"))
            for ( entry in list ) {
                var (time, value) = entry //time == time average eller nonaverage
               // println("Time: $time , SensorValue: $value") //sensor value
                if(value.toFloat() < 0){
                    value = "0f"
                }
                binding.showText1.text = time
                binding.showText2.text = value

                labels.add(clock as String) //lägger ut tid
                //entries.add(BarEntry(entryIndex, value.toFloat()))
                barDataSet.addEntry(BarEntry(entryIndex, value.toFloat()))
                entryIndex = entryIndex + 1

            }
        }
    }

    private fun notifyChanges(){
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }
    private fun updateBarColor(){
        barDataSet.sensor = sensor_input
        barDataSet.setColors(
            ContextCompat.getColor(chart.context, R.color.green),
            ContextCompat.getColor(chart.context, R.color.orange),
            ContextCompat.getColor(chart.context, R.color.red)
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun callWeekOrDay(){
        if(week_day == "week"){
            week()
            notifyChanges()
        }
        if(week_day == "day"){
            day()
            notifyChanges()
        }
    }

}