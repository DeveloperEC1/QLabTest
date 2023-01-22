package com.example.qlabtest.presentation.pages.fragments.search_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import com.example.qlabtest.SearchGraphDirections
import com.example.qlabtest.data.models.FlightsModel
import java.util.*

class SearchViewModel : ViewModel() {

    private val flightsModel = FlightsModel()
    val startDateText = MutableLiveData<String>()
    val endDateText = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")
    private var activity: Activity? = null

    fun init(activity: Activity) {
        this.activity = activity
    }

    fun departuresOnTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        flightsModel.departuresFrom = s.toString()
    }

    fun targetOnTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        flightsModel.arrivalTo = s.toString()
    }

    private fun startDateOnChanged(dateStr: String) {
        startDateText.value = dateStr
        flightsModel.departuresDate = dateStr
    }

    private fun endDateOnChanged(dateStr: String) {
        endDateText.value = dateStr
        flightsModel.arrivalDate = dateStr
    }

    fun isDirectFlightOnChanged(buttonView: CompoundButton, isChecked: Boolean) {
        flightsModel.isDirect = if (isChecked) "true" else "false"
    }

    fun btnSearchClick(v: View) {
        findNavController(v).navigate(
            SearchGraphDirections.actionNavigateToFlightsListFragment(flightsModel)
        )
    }

    fun openDatePickerStartDate(v: View) {
        openDatePicker(true)
    }

    fun openDatePickerEndDate(v: View) {
        openDatePicker(false)
    }

    private fun openDatePicker(isStartDate: Boolean) {
        val currentDateStr = getCurrentDateStr(isStartDate)

        val c = Calendar.getInstance()

        if (currentDateStr.isNotEmpty()) {
            val parts = currentDateStr.split("/")
            val day = parts[0].toInt()
            val month = parts[1].toInt() - 1
            val year = parts[2].toInt()

            c.set(Calendar.DAY_OF_MONTH, day)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.YEAR, year)
        }

        val dpd = DatePickerDialog(
            activity!!,
            { view, year, monthOfYear, dayOfMonth ->
                val dateStr = "$dayOfMonth/${String.format("%02d", monthOfYear + 1)}/$year"

                if (isStartDate) {
                    startDateOnChanged(dateStr)
                } else {
                    endDateOnChanged(dateStr)
                }
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )

        dpd.show()
    }

    private fun getCurrentDateStr(isStartDate: Boolean): String {
        var dateStr = ""

        if (isStartDate && flightsModel.departuresDate != null) {
            dateStr = flightsModel.departuresDate!!
        } else if (!isStartDate && flightsModel.arrivalDate != null) {
            dateStr = flightsModel.arrivalDate!!
        }

        return dateStr
    }

}
