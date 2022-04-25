package com.app.currencyconverter.data.repository

import com.app.currencyconverter.data.api.CurrencyApiService
import com.app.currencyconverter.data.model.CurrencyDataModel
import com.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: CurrencyApiService
) : CurrencyRepository {


    override suspend fun getCurrencyData(key: String): CurrencyDataModel {
        return apiService.getCurrencySymbols(key)
    }


}