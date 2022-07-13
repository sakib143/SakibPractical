package com.example.dubaipractical

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.dubaipractical.di.AppInjector
import com.example.dubaipractical.di.worker.WorkerInjectorFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication: Application(), HasAndroidInjector {

    /**
     * @see Work Manager factory
     * */
    @Inject
    lateinit var workerInjectorFactory: WorkerInjectorFactory

    /**
     * @see [dagger.android.DispatchingAndroidInjector]
     * */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    /**
     * @see [dagger.android.DispatchingAndroidInjector]
     * */
    override fun androidInjector() = dispatchingAndroidInjector

    /**
     * this method gets called when the Application gets created
     * */
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        configureWorkerWithDagger()

    }

    private fun configureWorkerWithDagger() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerInjectorFactory)
            .build()
        WorkManager.initialize(this, config)
    }
}