package com.app.currencyconverter.presentation.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.truth.app.NotificationActionSubject.assertThat
import com.app.currencyconverter.CoroutineTestRule
import com.app.currencyconverter.domain.model.CurrencyDataDomain
import com.app.currencyconverter.domain.model.CurrencyValueDomain
import com.app.currencyconverter.domain.usecase.GetCurrencyUseCase
import com.app.currencyconverter.domain.usecase.GetValuesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCaseCurrency: GetCurrencyUseCase

    @MockK
    lateinit var useCaseValuesCurrency: GetValuesUseCase

    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coRoutineTestRule = CoroutineTestRule()

    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(useCaseCurrency,useCaseValuesCurrency)
    }

    @Test
    fun getCurrencyDataSuccess() {
        runBlocking {
            coEvery {
                useCaseCurrency.execute(any())
            } answers   {
                CurrencyDataDomain(true, emptyMap())
            }
            homeViewModel.getCurrencyData("")

        }
        coVerify { useCaseCurrency.execute(any()) }
    }
    @Test
    fun getCurrencyValueSuccess() {
        runBlocking {
            coEvery {
                useCaseValuesCurrency.execute(any(),any(),any())
            } answers   {
                CurrencyValueDomain(true,"", emptyMap())
            }
            homeViewModel.getCurrencyValues("","")

        }
        coVerify { useCaseValuesCurrency.execute(any(),any(),any()) }
    }


}