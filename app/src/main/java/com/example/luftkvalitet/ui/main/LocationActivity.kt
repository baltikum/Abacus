package com.example.luftkvalitet.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.luftkvalitet.databinding.FragmentKartaBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import android.content.Intent
import android.net.Uri
import android.widget.Toast

import com.example.luftkvalitet.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
class LocationActivity(activity: FragmentActivity) : AppCompatActivity() {

    private var position: Location? = null
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

   // private var permissionGranted : Boolean = false

    private var activity: FragmentActivity = activity


    //Get the location on class construction. Otherwise position will return null and require 2x getLocation() calls.
    init {
        getLocation()
    }

    fun getLocation(): Location? {
        //Permission check
         //checkMyPermission()
        /*if(permissionGranted){
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                println("Success gps")
                binding.showInfo1.text = location?.latitude.toString()
                binding.showInfo2.text = location?.longitude.toString()
                //Unable to return location to kartaFragment. Don't know why.
                //Debugged with println. Results does not show up in kartaFragment, only here.
            }
        }*/
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
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            // Got last known location. In some rare situations this can be null.
            position = location
        }
        return position
    }


    /*private fun checkMyPermission(){
        Dexter.withContext(context).withPermission("android.permission.ACCESS_FINE_LOCATION").withListener( object : PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
               Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
                permissionGranted = true
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                var intent = Intent()
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.setData(Uri.fromParts("package", activity?.packageName, ""))
                startActivity(intent)
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }


        }).check()
    }*/

}

