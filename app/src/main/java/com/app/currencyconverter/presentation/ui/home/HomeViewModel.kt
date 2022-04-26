package com.app.currencyconverter.presentation.ui.home

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Log.DEBUG
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.currencyconverter.domain.usecase.GetCurrencyUseCase
import com.app.currencyconverter.domain.usecase.GetValuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.DateFormatSymbols
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyRepo: GetCurrencyUseCase,private val valuesUseCase: GetValuesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultDataState>(ResultDataState.Empty)
    private val _uiStateResult = MutableStateFlow<ResultDataState>(ResultDataState.Empty)
    val uiState: StateFlow<ResultDataState> = _uiState
    val uiStateResult: StateFlow<ResultDataState> = _uiStateResult
    val calculatedValue = MutableLiveData<String>()
    val fromValue = MutableLiveData<Int>()

    val selectedValue = MutableLiveData("10")
    val checkCalculatedValue = MutableLiveData<Boolean>()


    fun getCurrencyData(key: String) {
        _uiState.value = ResultDataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = currencyRepo.execute(key)
                _uiState.value = ResultDataState.Loaded(result)
            } catch (error: IOException) {
                _uiState.value = ResultDataState.Error("Something went Wrong")
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
                _uiStateResult.value = ResultDataState.Error("Something went Wrong")
            }catch (error: Exception) {
                _uiStateResult.value = ResultDataState.Error("Something went Wrong")
            }
        }
    }

    fun onDoneClicked(view: View, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_DONE) {
           Log.d("","action done")
            checkCalculatedValue.postValue(true)
            return true
        }
        return false
    }
    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        //pos                                 get selected item position
        //view.getText()                      get lable of selected item
        //parent.getAdapter().getItem(pos)    get item by pos
        //parent.getAdapter().getCount()      get item count
        //parent.getCount()                   get item count
        //parent.getSelectedItem()            get selected item
        //and other...
    }


}