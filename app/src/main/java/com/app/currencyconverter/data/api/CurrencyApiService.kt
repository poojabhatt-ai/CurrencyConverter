package com.app.currencyconverter.data.api

import com.app.currencyconverter.data.model.CurrencyDataModel
import com.app.currencyconverter.data.model.CurrencyValueModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("symbols")
    suspend fun getCurrencySymbols(
        @Query("access_key") key: String): CurrencyDataModel

    @GET("latest")
    suspend fun getCurrencyValues(
        @Query("access_key") key: String,@Query("base") base:String,@Query("symbols")symbols:String): CurrencyValueModel
}