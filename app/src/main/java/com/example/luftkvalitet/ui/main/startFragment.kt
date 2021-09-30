package com.example.luftkvalitet.ui.main

import android.R.attr
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.overview.OverViewModel


import android.R.attr.country
import android.R
import android.widget.*


val overViewModel = OverViewModel()

    class startFragment : Fragment() {


        // values for spinner
        val values = arrayOf<String>(
            "Femman",
            "Haga Norra",
            "Lejonet",
            "Mobil 1",
            "Mobil 2",
            "Mobil 3"
        )



        private var _binding: FragmentStartBinding? = null

        private val binding get() = _binding!!

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,

                                  savedInstanceState: Bundle?): View {

            _binding = FragmentStartBinding.inflate(inflater, container, false)
            val view = binding.root


            val date = "2021-09-17"
            val time = "22:00*"



            /* old spinner test
            val adapter =
                ArrayAdapter(this.requireActivity(), android.R.layout.simple_spinner_item, values)
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            binding.spinner.adapter = adapter;
            */

           // val spin = findViewById<Spinner>(R.id.coursesspinner)
           // binding.spinner.setOnItemSelectedListener {}

            //  spin.onItemSelectedListener = this

            val spin = binding.spinner

            val adapter =
                ArrayAdapter(this.requireActivity(), android.R.layout.simple_spinner_item, values)
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            spin.adapter = adapter;





            spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    when (position) {
                        0 -> overViewModel.updateStationData("Femman", date, time, binding)
                        1 -> overViewModel.updateStationData("Haga Norra", date, time, binding)
                        2 -> overViewModel.updateStationData("Lejonet", date, time, binding)
                        3 -> overViewModel.updateStationData("Mobil 1", date, time, binding)
                        4 -> overViewModel.updateStationData("Mobil 2", date, time, binding)
                        5 -> overViewModel.updateStationData("Mobil 3", date, time, binding)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }





            /*
            when (values[0]) {
                 "Femman" -> {
                    overViewModel.updateStationData("Femman", date, time, binding)
                }
                "Haga Norra" -> {
                    overViewModel.updateStationData("Femman", date, time, binding)
                }
            }
*/



            return view
        }









    }


