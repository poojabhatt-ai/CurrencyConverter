package com.app.currencyconverter.domain.usecase

import com.app.currencyconverter.data.mapper.toConvertedValues
import com.app.currencyconverter.domain.model.CurrencyValueDomain
import com.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetHistoricalUseCase@Inject constructor(private val repository: CurrencyRepository) {

    suspend fun execute(
        url: String,
        key: String,
        base: String,
        symbols: String
    ): CurrencyValueDomain {
        return repository.getHistoricalValues(url, key, base, symbols).toConvertedValues()
    }

}