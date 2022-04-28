package com.app.currencyconverter.data.repository

import com.app.currencyconverter.data.api.CurrencyApiService
import com.app.currencyconverter.data.model.CurrencyDataModel
import com.app.currencyconverter.data.model.CurrencyValueModel
import com.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: CurrencyApiService
) : CurrencyRepository {


    override suspend fun getCurrencyData(key: String): CurrencyDataModel {
        return apiService.getCurrencySymbols(key)
    }

    override suspend fun getCurrencyValues(
        key: String,base:String,symbol:String): CurrencyValueModel {
        return apiService.getCurrencyValues(key, base, symbol)
    }

    override suspend fun getHistoricalValues(url: String, key: String,base:String,symbol:String): CurrencyValueModel {
        return apiService.getHistoricalValues(url, key,base,symbol)
    }


}