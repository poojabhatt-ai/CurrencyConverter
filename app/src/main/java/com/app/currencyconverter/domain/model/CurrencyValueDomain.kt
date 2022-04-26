package com.app.currencyconverter.domain.model

data class CurrencyValueDomain(
     val success : Boolean,
    val rates:Map<String, Double>
)