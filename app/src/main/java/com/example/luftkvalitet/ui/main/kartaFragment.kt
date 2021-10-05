package com.example.luftkvalitet.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.luftkvalitet.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class kartaFragment : Fragment(){

    /*private var permissionGranted : Boolean = false
    private var mapview: MapView? = null
     var map: GoogleMap? = null*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val mapview: SupportMapFragment?= childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        val view: View = inflater.inflate(R.layout.fragment_karta, container, false)

        //gets the mapview from xml layout and creates it
        //mapview = view.findViewById(R.id.mapView)
        //mapview?.onCreate(savedInstanceState) // call oncreate before async method

       /* checkMyPermission()
        if(permissionGranted){
            mapview?.getMapAsync(this) //returns google map instance through the callback, mapview automatically initializes the maps system and view
        }*/

        return view
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