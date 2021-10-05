package com.example.luftkvalitet.ui.main
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentKartaBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/*import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
/*
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener*/
import androidx.appcompat.app.AppCompatActivity
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentKartaBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions*/

class kartaFragment : Fragment(), OnMapReadyCallback{
    private var _binding: FragmentKartaBinding? = null
    private val binding get() = _binding!!
    /*private var permissionGranted : Boolean = false
    private var mapview: MapView? = null
     var map: GoogleMap? = null*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val mapview: SupportMapFragment?= childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        //val view: View = inflater.inflate(R.layout.fragment_karta, container, false)
        _binding = FragmentKartaBinding.inflate(inflater, container, false)
         val view = binding.root
        var permission: LocationActivity = LocationActivity(this.requireActivity())

        binding.gpsFetch.setOnClickListener {
            binding.gpsFetch.setBackgroundColor(Color.RED)
            permission.getLocation(this.requireActivity(),binding)
        }
        println("activity")
        println(requireActivity())
        val mapFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
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

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
    }



}