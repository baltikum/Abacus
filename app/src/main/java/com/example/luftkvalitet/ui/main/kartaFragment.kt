package com.example.luftkvalitet.ui.main

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.luftkvalitet.MainActivity
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentKartaBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class kartaFragment : Fragment(), OnMapReadyCallback{

    private var _binding: FragmentKartaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKartaBinding.inflate(inflater, container, false)
        val view = binding.root

        var gps = LocationActivity(this.requireActivity())

        binding.gpsFetch.setOnClickListener {
            binding.gpsFetch.setBackgroundColor(Color.RED)
            binding.showInfo1.text = gps.getLocation()?.latitude.toString()
            binding.showInfo2.text = gps.getLocation()?.longitude.toString()
        }

        val fm = childFragmentManager // childFragmentManager för att man har en fragment i en fragment
        val mapFragment = fm.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
        val femman = LatLng(57.7087, 11.9705)
        val lejonet = LatLng(57.7157, 11.9923)
        val hagaNorra = LatLng(57.69972, 11.9561)
        val hagaSodra = LatLng(57.69722, 11.9525)
        val northEast = LatLng(59.0, 13.0)
        val southWest = LatLng(55.0,9.0)
        googleMap.addMarker(MarkerOptions().position(femman).title("Station Femman"))
        googleMap.addMarker(MarkerOptions().position(lejonet).title("Station Lejonet"))
        googleMap.addMarker(MarkerOptions().position(hagaNorra).title("Station Haga Norra"))
        googleMap.addMarker(MarkerOptions().position(hagaSodra).title("Station Haga Sodra"))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(femman))
    }



}