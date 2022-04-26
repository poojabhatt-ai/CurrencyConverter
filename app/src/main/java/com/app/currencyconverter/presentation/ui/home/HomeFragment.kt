package com.app.currencyconverter.presentation.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.currencyconverter.common.Constants
import com.app.currencyconverter.databinding.FragmentHomeBinding
import com.app.currencyconverter.domain.model.CurrencyDataDomain
import com.app.currencyconverter.domain.model.CurrencyValueDomain
import com.app.currencyconverter.presentation.ui.home.adapter.Adapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
   private val homeViewModel: HomeViewModel by viewModels()
    private val accessKey = Constants.ACCESS_KEY


    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getCurrencyData(accessKey)
        observerListeners()
        observerApiResponse()
    }

    private fun observerListeners() {
        homeViewModel.checkCalculatedValue.observe(viewLifecycleOwner) {
            callConversionApi()
        }
        homeViewModel.fromValue.observe(viewLifecycleOwner){
            callConversionApi()
        }

    }

    private fun callConversionApi() {
        val toCurrency = binding.spinnerTo.selectedItem
        val fromCurrency = binding.spinnerFrom.selectedItem
        val symbols = "$fromCurrency,$toCurrency"
        homeViewModel.getCurrencyValues(accessKey, symbols)
    }

    private fun observerApiResponse() {
        lifecycleScope.launch {

            homeViewModel.uiState.collect { state ->
                when(state){
                    is ResultDataState.Loaded -> onLoaded(state.resultValues as CurrencyDataDomain)
                    is ResultDataState.Error -> showError(state.message)
                    else -> showLoading()
                }
            }


        }
        lifecycleScope.launch {
            homeViewModel.uiStateResult.collect { state ->
                when(state){
                    is ResultDataState.Loaded -> onResultData(state.resultValues as CurrencyValueDomain)
                    is ResultDataState.Error -> showError(state.message)
                    else -> showLoading()
                }
            }
        }
    }



    private fun onResultData(currencyValueDomain: CurrencyValueDomain) {
       val currentRates= currencyValueDomain.rates
       // val data1= currentRates[binding.spinnerFrom.selectedItem]
        val data2= currentRates[binding.spinnerFrom.selectedItem]
        val getSelected: Double? = homeViewModel.selectedValue.value?.toDouble()
        val result = data2?.let { getSelected?.times(it) }
        homeViewModel.calculatedValue.value = result.toString()

    }

    private fun showLoading() {

    }
    private fun onLoaded(itemData: CurrencyDataDomain) {
        val rateValues=ArrayList<String>()
        for(data in itemData.symbols){
            rateValues.add(data.key)
        }

        binding.spinnerFrom.adapter = context?.let { Adapter(it.applicationContext, R.layout.simple_spinner_dropdown_item, rateValues) }
        binding.spinnerTo.adapter = context?.let { Adapter(it.applicationContext, R.layout.simple_spinner_dropdown_item, rateValues) }

    }


    private fun showError(message: String)  {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}