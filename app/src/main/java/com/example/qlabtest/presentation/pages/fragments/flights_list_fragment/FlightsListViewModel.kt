package com.example.qlabtest.presentation.pages.fragments.flights_list_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.qlabtest.core.MyApplication
import com.example.qlabtest.data.models.FlightsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class FlightsListViewModel : ViewModel() {

    var flightsModelSearchParams: FlightsModel? = null
    private var flightsModelList = arrayListOf<FlightsModel>()
    var flightsModelListFiltered = arrayListOf<FlightsModel>()

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

            val checkStartDateParsing = flightModel.departuresDate!!.replace("/", "-").split("-")
            val checkStartDate =
                LocalDate.parse("${checkStartDateParsing[2]}-${checkStartDateParsing[1]}-${checkStartDateParsing[0]}")

            val checkEndDateParsing = flightModel.arrivalDate!!.replace("/", "-").split("-")
            val checkEndDate =
                LocalDate.parse("${checkEndDateParsing[2]}-${checkEndDateParsing[1]}-${checkEndDateParsing[0]}")

            val startDateParsing =
                flightsModelSearchParams?.departuresDate!!.replace("/", "-").split("-")
            val startDate =
                LocalDate.parse("${startDateParsing[2]}-${startDateParsing[1]}-${startDateParsing[0]}")

            val endDateParsing =
                flightsModelSearchParams?.arrivalDate!!.replace("/", "-").split("-")
            val endDate =
                LocalDate.parse("${endDateParsing[2]}-${endDateParsing[1]}-${endDateParsing[0]}")

            if (flightModel.departuresFrom.equals(
                    flightsModelSearchParams?.departuresFrom,
                    ignoreCase = true
                ) &&
                flightModel.arrivalTo.equals(
                    flightsModelSearchParams?.arrivalTo,
                    ignoreCase = true
                ) &&
                (checkStartDate.isAfter(startDate) || checkStartDate.equals(startDate)) &&
                (checkEndDate.isBefore(endDate) || checkEndDate.equals(endDate)) &&
                flightModel.isDirect.equals(flightsModelSearchParams?.isDirect, ignoreCase = true)
            ) {
                flightsModelListFiltered.add(flightModel)
            }
        }

        Log.i("check1", flightsModelListFiltered.toString())
    }

}
