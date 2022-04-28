package com.app.currencyconverter.presentation.ui.home

import com.app.currencyconverter.domain.model.CurrencyDataDomain

fun convertToList(itemData: CurrencyDataDomain): CurrencyDetailsModel {
    val rateValues = ArrayList<String>()
    for (data in itemData.symbols) {
        rateValues.add(data.key)
    }
    return CurrencyDetailsModel(rateValues)
}