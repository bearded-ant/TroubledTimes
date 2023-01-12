package com.ant.troubledtimes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ant.troubledtimes.databinding.FragmentLocationsBinding
import com.ant.troubledtimes.databinding.ItemLocationBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FragmentLocations : Fragment() {
    private var _binding: FragmentLocationsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(layoutInflater)
        imageBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationStatusReference = getDatabaseReference()

        locationStatusReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children
                for (child in data) {
                    when (child.key) {
                        "Пещера Стонов" ->
                            bindLocationItem(binding.locationCave, child.getValue<Location>()!!)
                        "Разрушенный Портал" ->
                            bindLocationItem(binding.locationPortal, child.getValue<Location>()!!)
                        "Ближний Лес" ->
                            bindLocationItem(
                                binding.locationNearForest,
                                child.getValue<Location>()!!
                            )
                        "Дальний Лес" ->
                            bindLocationItem(
                                binding.locationFarForest,
                                child.getValue<Location>()!!
                            )
                        "Ближняя Шахта" ->
                            bindLocationItem(binding.locationNearMine, child.getValue<Location>()!!)
                        "Западная Шахта" ->
                            bindLocationItem(
                                binding.locationWesternMine,
                                child.getValue<Location>()!!
                            )
                        "Озеро" ->
                            bindLocationItem(binding.locationLake, child.getValue<Location>()!!)
                        "Северное Болото" ->
                            bindLocationItem(
                                binding.locationNorthernSwamp,
                                child.getValue<Location>()!!
                            )
                        "Южное Болото" ->
                            bindLocationItem(
                                binding.locationSouthernSwamp,
                                child.getValue<Location>()!!
                            )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "error status loading ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindLocationItem(itemView: ItemLocationBinding, location: Location) {
        if (location.nextRun !=  null) {
            itemView.itemLocationCard.visibility = View.VISIBLE
            itemView.itemLocationName.text = location.name
            itemView.itemLocationNextRun.text = location.nextRun
            itemView.itemLocationStatus.text = location.status
        }
    }

    private fun imageBindings() {
        binding.locationCave.itemLocationImage.setBackgroundResource(R.drawable.cave)
        binding.locationPortal.itemLocationImage.setBackgroundResource(R.drawable.portal)
        binding.locationNearMine.itemLocationImage.setBackgroundResource(R.drawable.mine)
        binding.locationWesternMine.itemLocationImage.setBackgroundResource(R.drawable.mine)
        binding.locationSouthernSwamp.itemLocationImage.setBackgroundResource(R.drawable.swamp)
        binding.locationNorthernSwamp.itemLocationImage.setBackgroundResource(R.drawable.swamp)
        binding.locationNearForest.itemLocationImage.setBackgroundResource(R.drawable.forest)
        binding.locationFarForest.itemLocationImage.setBackgroundResource(R.drawable.forest)
        binding.locationLake.itemLocationImage.setBackgroundResource(R.drawable.lake)
    }

    private fun getDatabaseReference(): DatabaseReference {
        val realTimeBase = Firebase.database
        return realTimeBase.getReference("locstatus")
    }
}