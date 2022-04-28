package com.app.currencyconverter.presentation.ui.details

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.currencyconverter.CoroutineTestRule
import com.app.currencyconverter.domain.model.CurrencyValueDomain
import com.app.currencyconverter.domain.usecase.GetHistoricalUseCase
import com.app.currencyconverter.domain.usecase.GetValuesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @MockK
    lateinit var useCaseValuesCurrency: GetHistoricalUseCase
    @MockK
    lateinit var useCaseOtherCurrency: GetValuesUseCase

    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coRoutineTestRule = CoroutineTestRule()

    private lateinit var detailsViewModel: DetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailsViewModel = DetailsViewModel( useCaseValuesCurrency,useCaseOtherCurrency)
    }



    @Test
    fun getCurrencyValueSuccess() {
        runBlocking {
            coEvery {
                useCaseValuesCurrency.execute(any(), any(), any(),any())
            } answers {
                CurrencyValueDomain(true, "", emptyMap())
            }
            detailsViewModel.getCurrencyValues("", "","")

        }
        coVerify { useCaseValuesCurrency.execute(any(), any(), any(),any()) }
    }

    @Test
    fun getOtherValueSuccess() {
        runBlocking {
            coEvery {
                useCaseOtherCurrency.execute(any(),any(),any())
            } answers   {
                CurrencyValueDomain(true,"", emptyMap())
            }
            detailsViewModel.getOtherValues("","")

        }
        coVerify { useCaseOtherCurrency.execute(any(),any(),any()) }
    }
}