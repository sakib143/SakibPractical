package com.example.dubaipractical.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dubaipractical.MyApplication
import com.example.dubaipractical.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

/**
 * An Utility class that having ViewModelModule for binding ViewModels
 * that provides App, context and resources to the scope
 * */
@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Singleton
    @Provides
    fun provideApp(app: Application): MyApplication {
        return app as MyApplication
    }

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideResource(context: Context): Resources {
        return context.resources
    }

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RoomDatabaseModule", "onCreate")
            CoroutineScope(Dispatchers.IO).launch {
            }
        }
    }

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "emp.db")
            .addCallback(databaseCallback)
            .allowMainThreadQueries()
            .build()


    @Singleton
    @Provides
    fun provideEmpList(database: AppDatabase) = database.getAllEmp()

}