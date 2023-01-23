package com.example.qlabtest.presentation.pages.fragments.flights_list_fragment

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.qlabtest.core.MyApplication
import com.example.qlabtest.data.models.FlightsModel
import com.example.qlabtest.presentation.adapters.FlightsListAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.LocalDate

class FlightsListViewModel : ViewModel() {

    var flightsModelSearchParams: FlightsModel? = null
    val flightsListAdapter = FlightsListAdapter()
    private var flightsModelList = arrayListOf<FlightsModel>()
    private val flightsModelListFiltered = arrayListOf<FlightsModel>()

    fun importFlightsJSON() {
        val jsonString: String =
            MyApplication.application.assets.open("flights.json").bufferedReader()
                .use { it.readText() }
        val flightsListType = object : TypeToken<ArrayList<FlightsModel>>() {}.type

        flightsModelList = Gson().fromJson(jsonString, flightsListType)
    }

    fun filteringList() {
        flightsModelListFiltered.clear()

        for (flightModel in flightsModelList) {

            val checkStartDate = if (flightModel.departuresDate!!.isNotEmpty())
                LocalDate.parse(changeDateFormat(flightModel.departuresDate!!)) else null
            val checkEndDate = if (flightModel.arrivalDate!!.isNotEmpty())
                LocalDate.parse(changeDateFormat(flightModel.arrivalDate!!)) else null
            val startDate = if (flightsModelSearchParams?.departuresDate!!.isNotEmpty())
                LocalDate.parse(changeDateFormat(flightsModelSearchParams?.departuresDate!!)) else null
            val endDate = if (flightsModelSearchParams?.arrivalDate!!.isNotEmpty())
                LocalDate.parse(changeDateFormat(flightsModelSearchParams?.arrivalDate!!)) else null

            if (departuresFromFilterCondition(flightModel) &&
                arrivalToFilterCondition(flightModel) &&
                startDateFilterCondition(checkStartDate!!, startDate) &&
                endDateFilterCondition(checkEndDate!!, endDate) &&
                isDirectFilterCondition(flightModel)
            ) {
                flightsModelListFiltered.add(flightModel)
            }
        }
    }

    private fun departuresFromFilterCondition(flightModel: FlightsModel): Boolean {
        val departuresFromCheckParam = flightsModelSearchParams?.departuresFrom

        return departuresFromCheckParam!!.isEmpty() ||
                flightModel.departuresFrom.equals(departuresFromCheckParam, ignoreCase = true)
    }

    private fun arrivalToFilterCondition(flightModel: FlightsModel): Boolean {
        val arrivalToCheckParam = flightsModelSearchParams?.arrivalTo

        return arrivalToCheckParam!!.isEmpty() ||
                flightModel.arrivalTo.equals(arrivalToCheckParam, ignoreCase = true)
    }

    private fun startDateFilterCondition(
        checkStartDate: LocalDate,
        startDate: LocalDate?
    ): Boolean {

        return flightsModelSearchParams?.departuresDate!!.isEmpty() ||
                checkStartDate.isAfter(startDate) ||
                checkStartDate == startDate
    }

    private fun endDateFilterCondition(
        checkEndDate: LocalDate,
        endDate: LocalDate?
    ): Boolean {

        return (flightsModelSearchParams?.arrivalDate!!.isEmpty() ||
                checkEndDate.isBefore(endDate) ||
                checkEndDate == endDate)
    }

    private fun isDirectFilterCondition(flightModel: FlightsModel): Boolean {

        return flightModel.isDirect.equals(flightsModelSearchParams?.isDirect, ignoreCase = true)
    }

    @SuppressLint("SimpleDateFormat")
    private fun changeDateFormat(dateStr: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd")
        val inputDate = inputFormat.parse(dateStr) ?: ""

        return outputFormat.format(inputDate)
    }

    fun setDataToRecyclerView() {
        flightsListAdapter.setData(flightsModelListFiltered)
    }

}
