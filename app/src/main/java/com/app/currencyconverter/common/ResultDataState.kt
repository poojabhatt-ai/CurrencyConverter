package com.app.currencyconverter.common

sealed class ResultDataState {
        object Empty : ResultDataState()
        object Loading : ResultDataState()
        class Loaded(val resultValues: Any) : ResultDataState()
        class Error(val message: String) : ResultDataState()
    }


