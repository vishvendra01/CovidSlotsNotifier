package com.app.vaccinenotifier.view.choosecity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.vaccinenotifier.R
import com.app.vaccinenotifier.data.model.District
import com.app.vaccinenotifier.data.model.State
import com.app.vaccinenotifier.di.DaggerApplicationComponent
import com.app.vaccinenotifier.di.PrefsModule
import com.app.vaccinenotifier.view.listing.ListingContainerFragment
import javax.inject.Inject

class ChooseCityFragment : Fragment(), ChooseCityNavigator {

    private lateinit var cityTextView: TextView
    private lateinit var stateTextView: TextView
    private lateinit var confirmButton: Button

    private var selectedState: State? = null
    private var selectedCity: District? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ChooseCityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerApplicationComponent.builder()
            .prefsModule(PrefsModule(requireContext()))
            .build()
            .inject(this)
        viewModel.setNavigator(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        bindViews()
    }

    private fun setupViews() {
        cityTextView = requireView().findViewById(R.id.cityTextView)
        stateTextView = requireView().findViewById(R.id.stateTextView)
        confirmButton = requireView().findViewById(R.id.confirmButton)

        cityTextView.setOnClickListener {
            selectedState?.let { viewModel.getCities(it) }
        }

        stateTextView.setOnClickListener {
            viewModel.getStates()
        }

        confirmButton.setOnClickListener {
            if (selectedState != null && selectedCity != null) {
                viewModel.confirmCity(selectedCity!!)
            }
        }
    }

    private fun bindViews() {
        viewModel.cityLiveData.observe(viewLifecycleOwner, {
            displayCitySelectDialog(it)
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            displayStateSelectDialog(it)
        })
    }

    private fun displayStateSelectDialog(states: List<State>) {
        AlertDialog.Builder(requireContext())
            .setTitle("Select State")
            .setItems(states.map { it.name }.toTypedArray()) { dialog, position ->
                stateTextView.text = states[position].name
                selectedState = states[position]
                dialog.dismiss()
            }
            .show()
    }

    private fun displayCitySelectDialog(cities: List<District>) {
        AlertDialog.Builder(requireContext())
            .setTitle("Select City")
            .setItems(cities.map { it.name }.toTypedArray()) { dialog, position ->
                cityTextView.text = cities[position].name
                selectedCity = cities[position]
                dialog.dismiss()
            }
            .show()
    }

    override fun navigateToListing() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ListingContainerFragment()).commit()
    }
}