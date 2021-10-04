package com.example.luftkvalitet.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.luftkvalitet.databinding.FragmentKartaBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationActivity(activity: FragmentActivity) : AppCompatActivity() {

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    fun getLocation(activity: FragmentActivity, binding: FragmentKartaBinding) {
        //Permission check
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),101)  //Popup "Toast" asking for permission
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            // Got last known location. In some rare situations this can be null.
            println("Success gps")
            binding.showInfo1.text = location?.latitude.toString()
            binding.showInfo2.text = location?.longitude.toString()
            //Unable to return location to kartaFragment. Don't know why.
            //Debugged with println. Results does not show up in kartaFragment, only here.
        }
    }




}

