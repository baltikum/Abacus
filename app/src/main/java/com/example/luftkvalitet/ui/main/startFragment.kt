package com.example.luftkvalitet.ui.main


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.luftkvalitet.R
import com.example.luftkvalitet.databinding.FragmentStartBinding
import com.example.luftkvalitet.overview.OverViewModel


    class startFragment : Fragment() {

        private var _binding: FragmentStartBinding? = null
        private val overViewModel = OverViewModel()

        private val binding get() = _binding!!

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?
        ): View {

            _binding = FragmentStartBinding.inflate(inflater, container, false)
            val view = binding.root

            binding.spinner.adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.stations_array, android.R.layout.simple_spinner_item).also{
                    adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spinner.adapter = adapter
            }
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    val out: String = "error"
                    Toast.makeText(activity, out, Toast.LENGTH_LONG).show()
                    println(out)
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val type = parent?.getItemAtPosition(position).toString()
                    overViewModel.updateStationData(type, binding)

                }
             }
            var gps = LocationActivity(this.requireActivity())

            //Get nearest station with gps
            binding.imageView.setOnClickListener{
                if(gps.getLocation()?.equals(null) == false)
                {
                    var station = overViewModel.returnApi().getClosestStationName(gps.getLocation()!!.latitude,gps.getLocation()!!.longitude)
                    binding.spinner.setSelection((binding.spinner.adapter as ArrayAdapter<CharSequence>).getPosition(station))
                }
            }


            return view
        }



    }


