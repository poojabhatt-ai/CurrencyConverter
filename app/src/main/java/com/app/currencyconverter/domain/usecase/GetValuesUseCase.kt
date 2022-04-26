package com.app.currencyconverter.domain.usecase

import com.app.currencyconverter.data.mapper.toConvertedValues
import com.app.currencyconverter.domain.model.CurrencyValueDomain
import com.app.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetValuesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend fun execute(key: String,base:String,symbols:String): CurrencyValueDomain {
        return repository.getCurrencyValues(key, base, symbols).toConvertedValues()
    }
}