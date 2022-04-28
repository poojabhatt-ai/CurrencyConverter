@file:Suppress("DEPRECATION")

package com.app.currencyconverter.common

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    //hide keyboard
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //check if network is connected
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
    private fun getCalculatedDate(date: String, days: Int): String? {
        return try{
            val cal = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            if (date.isNotEmpty()) {
                cal.time = s.parse(date)
            }
            cal.add(Calendar.DAY_OF_YEAR, -days)
            val newDate: Date = cal.time
            s.format(newDate)
        } catch (exception:ParseException){
            null
        }
    }

    private fun getCurrentDate(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.US)
        return simpleDateFormat.format(Date())

    }

    fun getLastThreeDateList(): ArrayList<String> {
        val dateString = ArrayList<String>()
        dateString.add(getCurrentDate())
        getCalculatedDate(getCurrentDate(), 1)?.let { dateString.add(it) }
        getCalculatedDate(getCurrentDate(), 2)?.let { dateString.add(it) }
        return dateString
    }

    fun formatValue(value: Double?): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        val df = DecimalFormat("0.0000",symbols)
        return df.format(value)
    }
}