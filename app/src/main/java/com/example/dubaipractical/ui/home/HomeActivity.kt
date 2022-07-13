package com.example.dubaipractical.ui.home

import android.os.Bundle
import com.example.dubaipractical.base.BaseActivity
import com.example.dubaipractical.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolBar)
    }
}