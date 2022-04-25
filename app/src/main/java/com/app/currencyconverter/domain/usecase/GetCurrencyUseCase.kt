package com.app.currencyconverter.domain.usecase

import com.app.currencyconverter.data.mapper.toCurrencyData
import com.app.currencyconverter.domain.model.CurrencyDataDomain
import com.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend fun execute(request: String): CurrencyDataDomain {
        return repository.getCurrencyData(request).toCurrencyData()
    }




}