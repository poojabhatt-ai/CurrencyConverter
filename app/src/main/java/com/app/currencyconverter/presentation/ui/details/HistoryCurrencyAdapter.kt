package com.app.currencyconverter.presentation.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.currencyconverter.databinding.LayoutItemViewBinding

class HistoryCurrencyAdapter : RecyclerView.Adapter<HistoryCurrencyAdapter.MyViewHolder>() {


    var list = ArrayList<CurrencyHistoryData>()

    fun addItem(data: CurrencyHistoryData) {
        list.add(data)
        notifyDataSetChanged()
    }


    inner class MyViewHolder(val viewDataBinding: LayoutItemViewBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryCurrencyAdapter.MyViewHolder {
        val binding = LayoutItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: HistoryCurrencyAdapter.MyViewHolder, position: Int) {

        holder.viewDataBinding.run {
            item = list[position]
        }
    }
}