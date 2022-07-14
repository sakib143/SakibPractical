package com.example.dubaipractical.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dubaipractical.data.db.table.EmpTable
//import com.example.dubaipractical.data.db.table.EmpModel
import com.example.dubaipractical.ui.home.fragment.HomeAdapter
import com.example.dubaipractical.utils.GlobalMethods


@BindingAdapter("bindEmpList")
fun bindEmpList(view: RecyclerView, list: List<EmpTable>?) {
    if(list != null) {
        view.layoutManager = LinearLayoutManager(view.context)
        var adapter = view.adapter
        adapter = HomeAdapter(view.context, list)
        view.adapter = adapter
    }
}

@BindingAdapter("setFormattedDateToDispay")
fun setFormattedDateToDispay(view: TextView, date: String) {
    val globalMethods = GlobalMethods()
    view.text = globalMethods.getDateformateToDisplay(date)
}
