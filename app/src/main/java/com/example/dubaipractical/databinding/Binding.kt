package com.example.dubaipractical.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dubaipractical.data.model.EmpModel
import com.example.dubaipractical.ui.home.fragment.HomeAdapter


@BindingAdapter("bindEmpList")
fun bindEmpList(view: RecyclerView, list: List<EmpModel.EmployesModelItem>?) {
    if(list != null) {
        view.layoutManager = LinearLayoutManager(view.context)
        var adapter = view.adapter
        adapter = HomeAdapter(view.context, list)
        view.adapter = adapter
    }
}
