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


    class startFragment : Fragment() {

        private var _binding: FragmentStartBinding? = null

        private val overViewModel = OverViewModel()

        private val binding get() = _binding!!

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View {

            _binding = FragmentStartBinding.inflate(inflater, container, false)
            val view = binding.root


            binding.button.setOnClickListener {
                overViewModel.updateStationData("Femman", binding)
            }
            binding.button2.setOnClickListener {
                overViewModel.updateStationData("Haga_Norra", binding) //todo: add Haga_Södra
            }
            binding.button3.setOnClickListener {
                overViewModel.updateStationData("Lejonet", binding)
            }

            binding.button4.setOnClickListener {
                overViewModel.updateStationData("Mobil_1", binding)
            }

            binding.button5.setOnClickListener {
                overViewModel.updateStationData("Mobil_2", binding)
            }

            binding.button6.setOnClickListener {
                overViewModel.updateStationData("Mobil_3", binding)
            }

            return view
        }



    }


