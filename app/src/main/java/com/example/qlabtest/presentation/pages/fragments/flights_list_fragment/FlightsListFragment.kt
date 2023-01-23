package com.example.qlabtest.presentation.pages.fragments.flights_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.qlabtest.FlightsListGraphArgs
import com.example.qlabtest.R
import com.example.qlabtest.databinding.FragmentFlightsListBinding
import com.example.qlabtest.presentation.pages.fragments.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightsListFragment : BaseFragment() {

    private lateinit var binding: FragmentFlightsListBinding
    private lateinit var flightsListViewModel: FlightsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewModel()
        setContactDataToParam()
        initDataBinding(inflater, container)

        CoroutineScope(Dispatchers.IO).launch {
            flightsListViewModel.importFlightsJSON()
            flightsListViewModel.filteringList()

            withContext(Dispatchers.Main) {
                binding.rvFlightsList.post {
                    flightsListViewModel.setDataToRecyclerView()
                }
            }
        }

        return binding.root
    }

    private fun initViewModel() {
        flightsListViewModel =
            ViewModelProvider(requireActivity())[FlightsListViewModel::class.java]
    }

    private fun setContactDataToParam() {
        flightsListViewModel.flightsModelSearchParams =
            FlightsListGraphArgs.fromBundle(requireArguments()).flightsModelData
    }

    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_flights_list, container, false)
        binding.flightsListViewModel = flightsListViewModel
        binding.lifecycleOwner = this
    }

}
