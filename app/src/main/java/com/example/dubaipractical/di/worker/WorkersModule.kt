package com.example.dubaipractical.di.worker

import androidx.work.ListenableWorker
import com.example.dubaipractical.worker.FetchEmpWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Modulo para registrar os Workers
 * Modulo to the Workers register
 */
@Suppress("unused")
@Module
interface WorkersModule {

    /**********************************************
     * ADICIONAR OS WORKERS QUE UTILIZAM DI AQUI!
     * ADD THE WORKERS WITH DI USAGE HERE!
     *********************************************/

    @Binds
    @IntoMap
    @WorkerKey(FetchEmpWorker::class)
    fun bindGithubUsersWorker(worker: FetchEmpWorker.Factory): IWorkerFactory<out ListenableWorker>

}