package com.example.dubaipractical.di

import android.app.Application
import com.example.dubaipractical.MyApplication
import com.example.dubaipractical.di.worker.WorkersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Interface to bind all module components with Application
 * */
@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class), (NetworkModule::class), (ActivityBuilder::class), (WorkersModule::class),])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

//        @BindsInstance
//        fun roomDatabase(application: RoomDatabaseModule): Builder

        fun build(): AppComponent
    }

    fun inject(instance: MyApplication)

}