package com.app.vaccinenotifier.view.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vaccinenotifier.R
import com.app.vaccinenotifier.data.model.VaccineCenter
import com.app.vaccinenotifier.di.DaggerApplicationComponent
import com.app.vaccinenotifier.di.PrefsModule
import com.app.vaccinenotifier.view.common.State
import javax.inject.Inject

class VaccineCentersListingFragment : Fragment() {

    companion object {
        private const val ARG_DATE = "date"

        fun newInstance(date: String): VaccineCentersListingFragment {
            val args = Bundle()
            args.putString(ARG_DATE, date)
            val fragment = VaccineCentersListingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var emptyView: View
    private val adapter = VaccineCentersAdapter()
    private val vaccineDate by lazy {
        arguments?.getString(ARG_DATE) ?: ""
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(VaccineCentersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vaccine_center, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerApplicationComponent.builder()
            .prefsModule(PrefsModule(requireContext()))
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRecyclerView()
        bindViews()
        viewModel.loadVaccineCenters(vaccineDate)
    }

    private fun setupRecyclerView() {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.vaccineRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupViews() {
        progressBar = requireView().findViewById(R.id.progressBar)
        emptyView = requireView().findViewById(R.id.emptySlotsTextView)
    }

    private fun bindViews() {
        viewModel.listingLiveData.observe(viewLifecycleOwner, { viewState ->
            renderView(viewState)
        })
    }

    private fun renderView(viewState: State<List<VaccineCenter>>) {
        when (viewState) {
            is State.Loading -> {
                progressBar.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            }

            is State.Success -> {
                progressBar.visibility = View.GONE
                adapter.items = viewState.data.toMutableList()
                if (viewState.data.isEmpty()) {
                    emptyView.visibility = View.VISIBLE
                } else {
                    emptyView.visibility = View.GONE
                }
            }

            is State.Error -> {
                progressBar.visibility = View.GONE
            }
        }
    }
}