package com.example.dubaipractical.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dubaipractical.ui.home.fragment.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This class used to bind ViewModels
 * */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    /**
     * To Bind ViewModelFactory
     * */
    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    /**
     * To Bind Home Activity View Model
     * */
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeActivity(viewModel: HomeViewModel): ViewModel

}