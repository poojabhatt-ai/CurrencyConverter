package com.app.currencyconverter.data.mapper

import com.app.currencyconverter.data.model.CurrencyDataModel
import com.app.currencyconverter.data.model.CurrencyValueModel
import com.app.currencyconverter.domain.model.CurrencyDataDomain
import com.app.currencyconverter.domain.model.CurrencyValueDomain


fun CurrencyDataModel.toCurrencyData()= CurrencyDataDomain(
        success = success,
        symbols = symbol)

   fun CurrencyValueModel.toConvertedValues()= CurrencyValueDomain(
        success = success,
       date =date,
        rates = rates)
