package com.app.currencyconverter.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyValueModel (
        @SerializedName("success") val success : Boolean,
        @SerializedName("timestamp") val timestamp : Int,
        @SerializedName("base") val base : String,
        @SerializedName("date") val date : String,
        @SerializedName("rates")
        @Expose
        val rates:Map<String, Double>

    )


