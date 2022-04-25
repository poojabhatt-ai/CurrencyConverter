package com.app.currencyconverter.data.mapper

import com.app.currencyconverter.data.model.CurrencyDataModel
import com.app.currencyconverter.domain.model.CurrencyDataDomain


    fun CurrencyDataModel.toCurrencyData()= CurrencyDataDomain(
        success = success,
        symbols = symbol)
