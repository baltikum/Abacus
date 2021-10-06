package com.example.luftkvalitet.ui.main
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentKartaBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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







        return view
    }



    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
    }



}