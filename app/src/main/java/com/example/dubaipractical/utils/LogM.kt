package com.example.dubaipractical.utils

import android.util.Log
import com.example.dubaipractical.BuildConfig
import javax.inject.Inject

class LogM @Inject constructor() {
    companion object {
        fun d(massage: String) {
            if (BuildConfig.DEBUG) {
                Log.d("=> ", massage)
            }
        }

        fun i(massage: String) {
            if (BuildConfig.DEBUG) {
                Log.i("=> ", massage)
            }
        }

        fun v(massage: String) {
            if (BuildConfig.DEBUG) {
                Log.v("=> ", massage)
            }
        }

        fun e(massage: String) {
            if (BuildConfig.DEBUG) {
                Log.e("=> ", massage)
            }
        }
    }
}