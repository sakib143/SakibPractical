package com.example.dubaipractical.di

import com.example.dubaipractical.ui.home.HomeActivity
import com.example.dubaipractical.ui.home.fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Helps to generate an {@link AndroidInjector} for all activities
 * */
@Suppress("unused")
@Module
abstract class ActivityBuilder {
    /**
     * fun to bind HomeActivity Activity, making Injection enable
     **/
    @ContributesAndroidInjector()
    abstract fun bindHomeActivity() : HomeActivity

    /**
     * fun to bind HomeFragment, making Injection enable
     **/
    @ContributesAndroidInjector()
    abstract fun bindHomeFragment() : HomeFragment

}