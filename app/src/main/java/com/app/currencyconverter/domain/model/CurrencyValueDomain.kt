package com.app.currencyconverter.domain.model

data class CurrencyValueDomain(
     val success : Boolean,
     val date : String,
    val rates:Map<String, Double>
)