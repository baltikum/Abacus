package com.example.luftkvalitet.ui.main

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luftkvalitet.databinding.FragmentKartaBinding

class kartaFragment : Fragment(){

    private var _binding: FragmentKartaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKartaBinding.inflate(inflater, container, false)
        val view = binding.root

        var permission: LocationActivity = LocationActivity(this.requireActivity())

        binding.gpsFetch.setOnClickListener {
            binding.gpsFetch.setBackgroundColor(Color.RED)
            permission.getLocation(this.requireActivity(),binding)
        }

        return view
    }




}