package com.app.currencyconverter.presentation.ui.details

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.currencyconverter.common.Constants
import com.app.currencyconverter.common.ResultDataState
import com.app.currencyconverter.domain.usecase.GetHistoricalUseCase
import com.app.currencyconverter.domain.usecase.GetValuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val useCase: GetHistoricalUseCase,private val valueCurrencyCase: GetValuesUseCase): ViewModel() {

    private val _uiStateResult = MutableStateFlow<ResultDataState>(ResultDataState.Empty)
    val uiStateResult: StateFlow<ResultDataState> = _uiStateResult

    private val _uiStateResult1 = MutableStateFlow<ResultDataState>(ResultDataState.Empty)
    val uiStateResult1: StateFlow<ResultDataState> = _uiStateResult1


    fun getCurrencyValues(date:String,key: String,symbols: String) {
        _uiStateResult.value = ResultDataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url=Constants.BASE_URL+ date
                val result = useCase.execute(url,key,"EUR",symbols)
                _uiStateResult.value = ResultDataState.Loaded(result)
            } catch (error: IOException) {
                _uiStateResult.value = ResultDataState.Error("Network Error")
            }catch (error: Exception) {
                _uiStateResult.value = ResultDataState.Error("Something went Wrong")
            }
        }
    }

    fun getOtherValues(key: String,symbols: String) {
        _uiStateResult1.value = ResultDataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = valueCurrencyCase.execute(key,"EUR",symbols)
                _uiStateResult1.value = ResultDataState.Loaded(result)
            } catch (error: IOException) {
                _uiStateResult1.value = ResultDataState.Error("Network Error")
            }catch (error: Exception) {
                _uiStateResult1.value = ResultDataState.Error("Something went Wrong")
            }
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }
}