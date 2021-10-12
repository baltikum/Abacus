package com.example.luftkvalitet.ui.main


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentKartaBinding
import com.example.luftkvalitet.network.API
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.*
import com.google.android.gms.maps.model.LatLng
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


        val fm = childFragmentManager // childFragmentManager för att man har en fragment i en fragment
        val mapFragment = fm.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {

        // display blue marker at position
        //googleMap.isMyLocationEnabled = true

        val femman = LatLng(57.7087, 11.9705)
        val lejonet = LatLng(57.7157, 11.9923)
        val hagaNorra = LatLng(57.69972, 11.9561)
        val hagaSodra = LatLng(57.69722, 11.9525)

        googleMap.addMarker(MarkerOptions().position(femman).title("Station Femman").icon(
            BitmapDescriptorFactory.defaultMarker(getStationMarkerColor("Femman"))))
        googleMap.addMarker(MarkerOptions().position(lejonet).title("Station Lejonet").icon(
            BitmapDescriptorFactory.defaultMarker(getStationMarkerColor("Lejonet"))))
        googleMap.addMarker(MarkerOptions().position(hagaNorra).title("Station Haga Norra").icon(
            BitmapDescriptorFactory.defaultMarker(getStationMarkerColor("Haga Norra"))))
        googleMap.addMarker(MarkerOptions().position(hagaSodra).title("Haga Sodra").icon(
            BitmapDescriptorFactory.defaultMarker(getStationMarkerColor("Haga Sodra"))))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(femman))

        googleMap.setOnMarkerClickListener { marker ->
            println("station: "+ marker.title)
            var id = ""
            when (marker.title) {
                "Station Femman" -> id = "Femman"
                "Station Lejonet" -> id = "Lejonet"
                "Station Haga Norra" -> id = "Haga_Norra"
                "Station Haga Sodra" -> id = "Haga_Sodra"
            }
            // open popup...

            var popupText = marker.title
            val dataList = API.getStationDataHourly(id)
            if (dataList != null && dataList.size > 0) {

                for (data in dataList) {
                    when (data.parameter) {
                        "NO2" -> {
                            if (data.raw_value.toDoubleOrNull() == null) {
                                continue
                            } else {
                                popupText += "\nNO2: " + data.raw_value
                            }
                        }
                        "NOx" -> {
                            if (data.raw_value.toDoubleOrNull() == null) {
                                continue
                            } else {
                                popupText += "\nNOx: " + data.raw_value
                            }
                        }
                        "PM2" -> {
                            if (data.raw_value.toDoubleOrNull() == null) {
                                continue
                            } else {
                                popupText += "\nPM2: " + data.raw_value
                            }
                        }
                        "PM10" -> {
                            if (data.raw_value.toDoubleOrNull() == null) {
                                continue
                            } else {
                                popupText += "\nPM10: " + data.raw_value
                            }
                        }
                    }
                }
            }
            //startActivity(Intent(this, MapPopup.class))
            Toast.makeText(
                context,
                popupText,
                Toast.LENGTH_LONG

            ).show()



            true
        }

    }

    private fun getStationMarkerColor(station : String) : Float {

        var returnValue = HUE_GREEN
        //if-satser som kollar på värdena

        val dataList = API.getStationDataHourly(station)
        if (dataList != null && dataList.size > 0) {
            for (data in dataList) {
                when (data.parameter) {
                    "NO2" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() > 36){
                                if (data.raw_value.toDouble() > 48) {
                                    return HUE_RED
                                }
                                returnValue = HUE_ORANGE
                            }
                        }
                    }
                    "NOx" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() > 4){
                                if (data.raw_value.toDouble() > 12) {
                                    return HUE_RED
                                }
                                returnValue = HUE_ORANGE
                            }
                        }
                    }
                    "PM2" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() > 9){
                                if (data.raw_value.toDouble() > 17) {
                                    return HUE_RED
                                }
                                returnValue = HUE_ORANGE
                            }
                        }
                    }
                    "PM10" -> {
                        if (data.raw_value.toDoubleOrNull() == null) {
                            continue
                        } else {
                            if(data.raw_value.toDouble() > 25){
                                if (data.raw_value.toDouble() > 35) {
                                    return HUE_RED
                                }
                                returnValue = HUE_ORANGE
                            }
                        }
                    }
                }
            }
        }



        return returnValue
    }



}