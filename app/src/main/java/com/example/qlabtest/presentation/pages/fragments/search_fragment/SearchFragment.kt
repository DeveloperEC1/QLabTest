package com.example.qlabtest.presentation.pages.fragments.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.qlabtest.R
import com.example.qlabtest.databinding.FragmentSearchBinding
import com.example.qlabtest.presentation.pages.fragments.BaseFragment

class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewModel()
        initDataBinding(inflater, container)

        return binding.root
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.init(requireActivity())
    }

    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.searchViewModel = searchViewModel
        binding.lifecycleOwner = this
    }

}
