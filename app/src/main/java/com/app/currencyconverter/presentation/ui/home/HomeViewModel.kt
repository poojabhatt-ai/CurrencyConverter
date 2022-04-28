package com.app.currencyconverter.presentation.ui.home

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.currencyconverter.common.ResultDataState
import com.app.currencyconverter.domain.usecase.GetCurrencyUseCase
import com.app.currencyconverter.domain.usecase.GetValuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepo: GetCurrencyUseCase,private val valuesUseCase: GetValuesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultDataState>(ResultDataState.Empty)
    private val _uiStateResult = MutableStateFlow<ResultDataState>(ResultDataState.Empty)
    val uiState: StateFlow<ResultDataState> = _uiState
    val uiStateResult: StateFlow<ResultDataState> = _uiStateResult
    val calculatedValue = MutableLiveData<String>()
    val spinnerFromPos = MutableLiveData<Int>()
    val spinnerToPos = MutableLiveData<Int>()

    val selectedValue = MutableLiveData("1")
    val convertCurrency = MutableLiveData<Boolean>()


    fun getCurrencyData(key: String) {
        _uiState.value = ResultDataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = currencyRepo.execute(key)
                _uiState.value = ResultDataState.Loaded(result)
            } catch (error: IOException) {
                _uiState.value = ResultDataState.Error("Network Error")
            }catch (error: Exception) {
                _uiState.value = ResultDataState.Error("Something went Wrong")
            }
        }
    }

    fun getCurrencyValues(key: String,symbols: String) {
        _uiStateResult.value = ResultDataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = valuesUseCase.execute(key,"EUR",symbols)
                _uiStateResult.value = ResultDataState.Loaded(result)
            } catch (error: IOException) {
                _uiStateResult.value = ResultDataState.Error("Network Error")
            }catch (error: Exception) {
                _uiStateResult.value = ResultDataState.Error("Something went Wrong")
            }
        }
    }

   /* fun callConversionApi(){

        val symbols = "${fromSelectedValue.value},${toSelectedValue.value}"
        getCurrencyValues(Constants.ACCESS_KEY, symbols)
    }*/

    fun onDoneClicked(view: View, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
            convertCurrency.postValue(true)
            return true
        }
        return false
    }

    fun valueCalculation(fromCurrency: Double, toCurrency: Double) {
        val getSelected: Double? = selectedValue.value?.toDouble()?.times(fromCurrency)
        val result = toCurrency?.let { getSelected?.times(it) }
        calculatedValue.value = result.toString()

    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }
}