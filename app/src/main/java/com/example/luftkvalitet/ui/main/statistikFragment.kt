package com.example.luftkvalitet.ui.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentStatistikBinding
import com.example.luftkvalitet.overview.OverViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class statistikFragment : Fragment() {


    private var _binding: FragmentStatistikBinding? = null
    private  val labels: ArrayList<String> = ArrayList()



    @RequiresApi(Build.VERSION_CODES.O)
    private val overViewModel = OverViewModel()


    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        val view = binding.root

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


       // overViewModel.updateGraphData("2020-02-08","2020-02-09","PM10","Femman")




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
        chart.setScaleEnabled(false)
        chart.setFitBars(true);


       // val labels: ArrayList<String> = ArrayList()
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





        binding.button2.setOnClickListener{

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



        binding.button.setOnClickListener {
            binding.button.setBackgroundColor(Color.RED)

            var entryIndex = 0f
            labels.clear()
            barDataSet.clear()

            var graphDat = overViewModel.returnApi().getGraphData() // Hämtar redan hämtad data via init uppstart.

            for ((key, list) in graphDat ) { // key= datum på formen 2020-02-08.... value är nu ArrayList med Pair< String tid, String värde på efterfrågad sensor > //
                println(key.plus("------"))
                for ( entry in list ) { // Varje pair i listan för key datum
                    var (time, value) = entry
                    println("Time: $time , SensorValue: $value")
                    binding.showText1.text = time
                    binding.showText2.text = value


                    labels.add(time.subSequence(0, 2) as String)


                    entries.add(BarEntry(entryIndex, value.toFloat()))
                    entryIndex = entryIndex +1


                }
            }
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()

        }


        return view
    }



}