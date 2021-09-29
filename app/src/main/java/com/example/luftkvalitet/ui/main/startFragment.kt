package com.example.luftkvalitet.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.overview.OverViewModel
import android.widget.ArrayAdapter



import android.widget.Spinner




val overViewModel = OverViewModel()

    class startFragment : Fragment() {

        private var _binding: FragmentStartBinding? = null

        private val binding get() = _binding!!

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {

            _binding = FragmentStartBinding.inflate(inflater, container, false)
            val view = binding.root


            val date = "2021-09-17"
            val time = "22:00*"
            binding.button.setOnClickListener {

                println("Click Femman")
                //overViewModel.getHourData("Femman", date, time)

                overViewModel.updateStationData("Femman", date, time, binding)
            }
            binding.button2.setOnClickListener {
                overViewModel.updateStationData("Haga Norra", date, time, binding)

            }
            binding.button3.setOnClickListener {

                overViewModel.updateStationData("Lejonet", date, time, binding)
            }

            binding.button4.setOnClickListener {

                overViewModel.updateStationData("Mobil 1", date, time, binding)
            }

            binding.button5.setOnClickListener {

                overViewModel.updateStationData("Mobil 2", date, time, binding)
            }

            binding.button6.setOnClickListener {

                overViewModel.updateStationData("Mobil 3", date, time, binding)
            }


            val values = arrayOf(
                "Femman",
                "Haga Norra",
                "Lejonet",
                "Mobil 1",
                "Mobil 2",
                "Mobil 3"
            )
            val adapter =
                ArrayAdapter(this.requireActivity(), android.R.layout.simple_spinner_item, values)
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            binding.spinner.adapter = adapter;










            return view
        }



    }


