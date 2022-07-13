package com.example.dubaipractical.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.dubaipractical.utils.autoCleared

abstract class BaseDialogBindingFragment<B : ViewDataBinding> : BaseDialogFragment() {

    /**
     * Object of autoClearedValue @see #AutoClearedValue
     * */
    private var binding by autoCleared<B>()

    /**
     * One of the fragment life cycle methods, gets called when fragment's view gets created
     * */
    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    /**
     * Use this if your subclass requires to have some sort of hooks into the inflation process of the view
     */
    open fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding: B = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        binding = dataBinding
        initializeBinding(dataBinding)
        return dataBinding.root
    }

    /**
     * Used to not forget to initialize the binding
     */
    abstract fun initializeBinding(binding: B)

}