package com.example.dubaipractical.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.dubaipractical.R
import com.example.dubaipractical.api.ApiExceptions
import com.example.dubaipractical.api.NoInternetException
import com.example.dubaipractical.data.repository.HomeRepository
import com.example.dubaipractical.di.worker.IWorkerFactory
import com.example.dubaipractical.ui.home.HomeActivity
import com.example.dubaipractical.utils.*
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class FetchEmpWorker(
    private val appContext: Context,
    private val workerParams: WorkerParameters,
    private val repo: HomeRepository,
) : ListenableWorker(appContext, workerParams) {

    private val NOTIFICATION_ID = 10

    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture { completer ->
            CoroutineScope(Default).launch {
                setForegroundAsync(createForeGroundInfo())
                Coroutines.main {
                    try {
                        val apiCallForEmpList = repo.callEmpList()
                        if(apiCallForEmpList.size > 0) {
                            val globalMethods = GlobalMethods()
                            val prefUtils = PrefUtils(appContext)
                            prefUtils.saveLastSyncTime(globalMethods.getCurrentDateAndTime())
                            completer.set(Result.success(createOutputData(true)))
                        } else {
                            completer.set(Result.success(createOutputData(false)))
                        }
                    }catch (e: ApiExceptions) {
                        appContext.toast(e.message!!)
                    } catch (e: NoInternetException) {
                        appContext.toast(e.message!!)
                    }
                }
            }
        }
    }

    private fun createOutputData(status: Boolean): Data {
        return Data.Builder()
            .putBoolean(Constant.IS_API_CALLED,status)
            .build()
    }

    /**
     * Dependency injection for workout manager
     */
    class Factory @Inject constructor(
        private val userRepository: Provider<HomeRepository>, // <-- Add your providers parameters
    ) : IWorkerFactory<FetchEmpWorker> {
        override fun create(appContext: Context, params: WorkerParameters ): FetchEmpWorker {
            return FetchEmpWorker(appContext,params, userRepository.get())
        }
    }

    /**
     * Forground service related stuff START
     */
    private fun createForeGroundInfo(): ForegroundInfo {
        var notification: Notification? = null
        notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) getNotificationForOrio(
            HomeActivity::class.java
        ) else getNotification(
            HomeActivity::class.java,
            "Randon Number",
            1,
            false,
            NOTIFICATION_ID
        )
        return ForegroundInfo(NOTIFICATION_ID, notification!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun getNotificationForOrio(targetNotificationActivity: Class<*>): Notification? {
        val NOTIFICATION_CHANNEL_ID = "com.example.simpleapp"
        val channelName = "My Background Service"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        //Pending intent is used to open activity on notification click.
        val intent = Intent(appContext, targetNotificationActivity)
        val pendingIntent = PendingIntent.getActivity(appContext, 1, intent, 0)
        val manager =
            (appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder =
            NotificationCompat.Builder(appContext, NOTIFICATION_CHANNEL_ID)
        return notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun getNotification(
        targetNotificationActivity: Class<*>?,
        title: String?,
        priority: Int,
        autoCancel: Boolean,
        notificationId: Int,
    ): Notification? {
        val intent = Intent(appContext, targetNotificationActivity)
        val pendingIntent = PendingIntent.getActivity(appContext, 1, intent, 0)
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(appContext, "123")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        appContext.getResources(),
                        R.mipmap.ic_launcher
                    )
                )
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId("123")
                .setAutoCancel(true)
        return builder.build()
    }
    /**
     * Forground service related stuff END
     */
}
