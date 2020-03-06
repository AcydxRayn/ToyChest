package com.ks49.toychest.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.ks49.toychest.utilities.TOY_DATA_FILENAME
import kotlinx.coroutines.coroutineScope
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.ks49.toychest.data.AppDatabase
import com.ks49.toychest.data.Toy
import java.lang.Exception

class ToyDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(TOY_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val toyType = object : TypeToken<List<Toy>>() {}.type
                    val toyList: List<Toy> = Gson().fromJson(jsonReader, toyType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.toyDao().insertAll(toyList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = ToyDatabaseWorker::class.java.simpleName
    }
}