package com.example.qlabtest.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qlabtest.R
import com.example.qlabtest.data.models.FlightsModel
import com.example.qlabtest.databinding.AdapterFlightsListBinding
import com.example.qlabtest.presentation.adapters.view_holders.FlightsListViewHolder

class FlightsListAdapter : RecyclerView.Adapter<FlightsListViewHolder>() {

    private var flightsModelArrayList: ArrayList<FlightsModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsListViewHolder {
        val binding: AdapterFlightsListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_flights_list,
            parent,
            false
        )
        return FlightsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightsListViewHolder, position: Int) {
        val flightsModel: FlightsModel = flightsModelArrayList[position]

        holder.binding.flightsModel = flightsModel
    }

    override fun getItemCount() = flightsModelArrayList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(flightsModelArrayList: ArrayList<FlightsModel>) {
        this.flightsModelArrayList = flightsModelArrayList
        notifyDataSetChanged()
    }

}
