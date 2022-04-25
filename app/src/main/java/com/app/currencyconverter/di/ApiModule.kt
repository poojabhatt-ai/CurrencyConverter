package com.app.currencyconverter.di


import com.app.currencyconverter.data.api.CurrencyApiService
import com.app.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.app.currencyconverter.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApiService = retrofit.create(CurrencyApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: CurrencyApiService): CurrencyRepository {
        return CurrencyRepositoryImpl(api)
    }
}