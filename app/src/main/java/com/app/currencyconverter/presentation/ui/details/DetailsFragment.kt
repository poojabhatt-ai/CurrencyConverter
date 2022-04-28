package com.app.currencyconverter.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.currencyconverter.common.Constants
import com.app.currencyconverter.common.ResultDataState
import com.app.currencyconverter.common.Utility
import com.app.currencyconverter.databinding.FragmentDashboardBinding
import com.app.currencyconverter.domain.model.CurrencyValueDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var from: String? = null
    private var toCurrency: String? = null
    private var _binding: FragmentDashboardBinding? = null

    private val detailsViewModel: DetailsViewModel by viewModels()

    private val binding get() = _binding!!
    private val recyclerAdapter = HistoryCurrencyAdapter()
    private val recyclerAdapter1 = DataBindingRecyclerAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.lifecycleOwner = this
        binding.detailsViewModel = detailsViewModel

        observeResponse()
        return root
    }

    private fun observeResponse() {
        lifecycleScope.launch {
            detailsViewModel.uiStateResult.collect { state ->
                when(state){
                    is ResultDataState.Loaded -> onResultData(state.resultValues as CurrencyValueDomain)
                    is ResultDataState.Error -> showError(state.message)
                    else -> showLoading()
                }
            }
        }

        lifecycleScope.launch {
            detailsViewModel.uiStateResult1.collect { state ->
                when(state){
                    is ResultDataState.Loaded -> onResultData1(state.resultValues as CurrencyValueDomain)
                    is ResultDataState.Error -> showError(state.message)
                    else -> showLoading()
                }
            }
        }
    }

    private fun onResultData1(resultData: CurrencyValueDomain) {
        if(resultData.success) {
            val fromCurrency = resultData.rates[from]
            for(data in resultData.rates){
                val toCurrencyValue=data.value
               val calculated= Utility.formatValue((fromCurrency!!).times(toCurrencyValue))
                val historyData = CurrencyHistoryData(data.key, calculated)
                recyclerAdapter1.addItem(historyData)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        from=  arguments?.getString("fromCurrency")
        toCurrency=  arguments?.getString("toCurrency")
       val dateList= Utility.getLastThreeDateList()
        if (Utility.isNetworkAvailable(context)) {

            for (data in dateList) {
                detailsViewModel.getCurrencyValues(data, Constants.ACCESS_KEY, "$from,$toCurrency")
            }
            val symbols = "$from,EGP,USD,CAD,EUR,GBP,JPY,INR,NPR,NZD,OMR"
            detailsViewModel.getOtherValues(Constants.ACCESS_KEY, symbols)
        }
        binding.recyclerHistory.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = recyclerAdapter
        }
        binding.recyclerOther.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = recyclerAdapter1
        }
    }

    private fun onResultData(resultData: CurrencyValueDomain) {
        if(resultData.success) {
            val fromCurrency = resultData.rates[from]
            val toCurrencyValue = resultData.rates[toCurrency]
            val calculateValue = Utility.formatValue((fromCurrency!!).times(toCurrencyValue!!))
            val euroConversion = "1 $from= $calculateValue $toCurrency"
            val historyData = CurrencyHistoryData(resultData.date,euroConversion)
            recyclerAdapter.addItem(historyData)
        }
    }

    private fun showLoading() {
    }



    private fun showError(message: String)  {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}