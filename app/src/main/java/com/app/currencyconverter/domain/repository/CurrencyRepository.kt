package com.app.currencyconverter.domain.repository

import com.app.currencyconverter.data.model.CurrencyDataModel
import com.app.currencyconverter.data.model.CurrencyValueModel

interface CurrencyRepository {

    suspend fun getCurrencyData(key: String): CurrencyDataModel
    suspend fun getCurrencyValues(key: String,base:String,symbols:String): CurrencyValueModel

}