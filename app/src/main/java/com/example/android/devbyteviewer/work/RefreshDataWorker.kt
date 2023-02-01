package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.VideoDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
CoroutineWorker(appContext, params){
    override suspend fun doWork(): Result {
        val database = VideoDatabase.getInstance(applicationContext)
        val repository=VideosRepository(database)
        try {
            repository.refreshVideo()
            Timber.d("work request for sync is run")
        }catch (e: HttpException){
            return Result.retry()
        }

        return Result.success()
    }

}