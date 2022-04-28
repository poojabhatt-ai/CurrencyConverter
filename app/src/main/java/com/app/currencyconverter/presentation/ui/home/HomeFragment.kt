package com.app.currencyconverter.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.currencyconverter.R
import com.app.currencyconverter.common.Constants
import com.app.currencyconverter.common.ResultDataState
import com.app.currencyconverter.common.Utility
import com.app.currencyconverter.databinding.FragmentHomeBinding
import com.app.currencyconverter.domain.model.CurrencyDataDomain
import com.app.currencyconverter.domain.model.CurrencyValueDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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

        observerListeners()
        observerApiResponse()
    }

    private fun observerListeners() {
        homeViewModel.convertCurrency.observe(viewLifecycleOwner) {
            callConversionApi()
        }
        homeViewModel.spinnerFromPos.observe(viewLifecycleOwner){
            callConversionApi()
        }
        homeViewModel.spinnerToPos.observe(viewLifecycleOwner){
            callConversionApi()
        }


        binding.swapCurrency.setOnClickListener {
            swapSpinnerValues()
        }
        binding.detailsPage.setOnClickListener {
            val bundle = bundleOf("fromCurrency" to binding.spinnerFrom.selectedItem,
            "toCurrency" to binding.spinnerTo.selectedItem)
            findNavController().navigate(R.id.navigation_dashboard, bundle)
        }
    }

    private fun swapSpinnerValues() {
        val pos1 = binding.spinnerTo.selectedItemPosition
        val pos2 = binding.spinnerFrom.selectedItemPosition
        binding.fromEditText.setText("1")
        binding.spinnerFrom.setSelection(pos1)
        binding.spinnerTo.setSelection(pos2)
    }

    private fun callConversionApi(){

        val symbols = "${binding.spinnerFrom.selectedItem},${binding.spinnerTo.selectedItem}"
        homeViewModel.getCurrencyValues(Constants.ACCESS_KEY, symbols)
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
        if(currencyValueDomain.success){
        val fromCurrency= currencyValueDomain.rates[binding.spinnerFrom.selectedItem]
        val toCurrency= currencyValueDomain.rates[binding.spinnerTo.selectedItem]
            if (fromCurrency != null && toCurrency!=null) {
                homeViewModel.valueCalculation(fromCurrency,toCurrency)
            }
        }
        binding.progressBar.visibility = View.GONE
        Utility.hideKeyboard(requireActivity())
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private suspend fun onLoaded(itemData: CurrencyDataDomain) {
        if(itemData.success) {
            binding.progressBar.visibility = View.GONE
            binding.currencyData = convertToList(itemData)
            delay(500)
            binding.spinnerFrom.setSelection(43)
        }
    }


    private fun showError(message: String)  {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}