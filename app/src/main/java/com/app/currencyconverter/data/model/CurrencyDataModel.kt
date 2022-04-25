package com.app.currencyconverter.data.model

import com.app.currencyconverter.domain.model.CurrencyDataDomain
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyDataModel(

	@SerializedName("success") val success : Boolean,
	@SerializedName("symbols")
	@Expose
	val symbol:Map<String, String>

)
