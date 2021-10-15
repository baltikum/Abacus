package com.example.luftkvalitet.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.LocationManager
import android.widget.Toast


class LocationActivity(activity: FragmentActivity) : AppCompatActivity() {

    private var position: Location? = null
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    private var activity: FragmentActivity = activity

    //Get the location on class construction. Otherwise position will return null and require 2x getLocation() calls.
    init {
        getLocation()
    }

    fun getLocation(): Location? {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),101)  //Popup "Toast" asking for permission
        }
        if(!isLocationEnabled())
        {
            Toast.makeText(activity,"Turn on GPS",Toast.LENGTH_SHORT).show()
        }
        else{
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                position = location
            }
        }
        return position
    }

    /**
     * Check if gps is enabled on device
     */
    fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}

