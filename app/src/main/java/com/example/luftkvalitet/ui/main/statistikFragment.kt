package com.example.luftkvalitet.ui.main

import android.graphics.Color
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
import androidx.lifecycle.viewModelScope
import com.example.luftkvalitet.R

import com.example.luftkvalitet.databinding.FragmentStatistikBinding
import com.example.luftkvalitet.network.API
import com.example.luftkvalitet.network.APIListener
import com.example.luftkvalitet.network.AnytimeResultObj
import com.example.luftkvalitet.overview.OverViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.Utils.init
import kotlinx.coroutines.launch
import java.lang.Boolean
import java.lang.Boolean.FALSE

class statistikFragment : Fragment() , APIListener {

    override fun onGraphDataUpdated() {

        println("snoppar plenty------")
        println("fyll grafen nu här !------")

    }


    private var _binding: FragmentStatistikBinding? = null
    private  var labels: ArrayList<String> = ArrayList()
    private var entries: ArrayList<BarEntry> = ArrayList()
    //private lateinit var barDataSet : BarDataSet
    var dataSets: ArrayList<MyBarDataSet> = ArrayList()
    private lateinit var chart: BarChart
    private var week_day: String = "week"
    @RequiresApi(Build.VERSION_CODES.O)
    private val overViewModel = OverViewModel()
    private var barDataSet = MyBarDataSet(entries, "")
    private var graphData = HashMap<String,ArrayList<Pair<String,String>>>()
    private var compareData = HashMap<String,ArrayList<Pair<String,String>>>()
    //var station_input: String = "Femman"
    private val binding get() = _binding!!
    companion object{
        var sensor_input: String = "NO2"
        var station_input: String = "Femman"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root
        API.addListener(this) // Lägg till oss som lyssnare på API

        /**
         *      TILL JOHNNY OCH VICTORIA
         *
         *      Använd ett liknande call för att hämta grafdata
         *     overViewModel.updateGraphData("2020-02-08","2020-02-09","PM10","Femman")
         *
         *
         *     OBS!!! En stor hämtning med flera dagar tar lite tid, då funktionen gör ett kall för varje
         *     dag.
         *
         *     Hämtningen körs i bakgrunden.
         *
         *     Vi kanske behöver skapa en listener för detta framöver... om någon vill kalla på fornminnen
         *     Alltså att via en listener väcka fyllning av grafen.
         *
         *     via init i OverViewModel hämtas nu 3 senaste dagarnas NOx ifrån Femman
         *
         * MVH BALTIKUM
         * */




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
            barDataSet.clear()
            updateEntries()
            labels.clear()
            updateChart()
            //labels.clear()

        }
        //last week
        binding.button3.setOnClickListener{
            week_day = "week"
            updateEntries()
            week()

            //xAxis.labelCount = 10

            chart.xAxis.granularity = 1f //only intervals of 1 float
            notifyChanges()
        }

        binding.spinner2.adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.stations_array, android.R.layout.simple_spinner_item).also{

                adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner2.adapter = adapter
        }
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val out = "error"
                Toast.makeText(activity, out, Toast.LENGTH_LONG).show()
                println(out)
            }
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


                station_input = parent?.getItemAtPosition(position).toString()


                Toast.makeText(activity, station_input, Toast.LENGTH_LONG).show()
                overViewModel.station_input = station_input
                updateChart()


            }



        }
        binding.setNo2.setOnClickListener {
            sensor_input= "NO2"
            updateChart()
        }
        binding.setNox.setOnClickListener {
            sensor_input= "NOx"
            updateChart()
        }

        binding.setPm25.setOnClickListener {
            sensor_input = "PM2.5"
            updateChart()
        }

        binding.setPm10.setOnClickListener {
            sensor_input = "PM10"
            updateChart()
        }
        binding.button.setOnClickListener { // behaves like last_day, keep as days
            updateChart()
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateChart() {
        barDataSet.sensor = sensor_input
        barDataSet.setColors(
            ContextCompat.getColor(chart.context, R.color.green),
            ContextCompat.getColor(chart.context, R.color.orange),
            ContextCompat.getColor(chart.context, R.color.red)
        )

    if ( !sensor_input.isNullOrEmpty() && !station_input.isNullOrEmpty() ) {
        overViewModel.updateGraphData(
            API.rewindOneWeek("2021-09-16"),
            "2021-09-16",
            "NOx",
           "Femman",
            "13:00+01:00",
            Boolean.TRUE
        )

    }





        var entryIndex = 0f
        //labels.clear()
        barDataSet.clear()

        graphData = API.getGraphData()

        for ((date, list) in graphData ) {
            //println(date.plus("------"))
            for ( entry in list ) {
                var (time, value) = entry //time == time average eller nonaverage
                //println("Time: $time , SensorValue: $value") //sensor value
                binding.showText1.text = time
                binding.showText2.text = value

                if(week_day == "week"){
                    week()
                }
                labels.add(date.subSequence(5, 10) as String) //lägger ut datumet
                //entries.add(BarEntry(entryIndex, value.toFloat()))
                barDataSet.addEntry(BarEntry(entryIndex, value.toFloat()))
                entryIndex = entryIndex + 1

            }
        }

        notifyChanges()
    }

    fun updateEntries(){
        barDataSet.clear()

        barDataSet.addEntry(BarEntry(0f, 12f))
        barDataSet.addEntry(BarEntry(1f, 35f))
        barDataSet.addEntry(BarEntry(2f, 14f))
        barDataSet.addEntry(BarEntry(3f, 15f))
        barDataSet.addEntry(BarEntry(4f, 20f))
        barDataSet.addEntry(BarEntry(5f, 13f))
        barDataSet.addEntry(BarEntry(6f, 8f))
    }

    fun week(){

        labels.clear()
        labels.add("Mån")
        labels.add("Tis")
        labels.add("Ons")
        labels.add("Tors")
        labels.add("Fre")
        labels.add("Lör")
        labels.add("Sön")
    }

    fun notifyChanges(){
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

}