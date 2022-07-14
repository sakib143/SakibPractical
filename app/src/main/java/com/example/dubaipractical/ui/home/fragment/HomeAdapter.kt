package com.example.dubaipractical.ui.home.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.dubaipractical.databinding.AdapterHomeBinding
import com.example.dubaipractical.BR
import com.example.dubaipractical.data.db.table.EmpTable

class HomeAdapter (
    private val context: Context,
    val dataList: List<EmpTable>,
) : RecyclerView.Adapter<HomeAdapter.BindingViewHolder>() {

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val rootView: ViewDataBinding = AdapterHomeBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val model = dataList[position]
        holder.itemBinding.setVariable(BR.model, model)
        holder.itemBinding.setVariable(BR.position, position)
        holder.itemBinding.executePendingBindings()
    }

    class BindingViewHolder(val itemBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemBinding.root)

}