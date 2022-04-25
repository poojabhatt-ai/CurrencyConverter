package com.app.currencyconverter.domain.repository

import com.app.currencyconverter.data.model.CurrencyDataModel

interface CurrencyRepository {

    suspend fun getCurrencyData(key: String): CurrencyDataModel

}