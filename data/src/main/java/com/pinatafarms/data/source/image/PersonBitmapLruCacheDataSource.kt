package com.pinatafarms.data.source.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.collection.LruCache
import com.pinatafarms.data.AppDispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException

interface PersonBitmapLruCacheDataSource {
    suspend fun getBitmapFromCache(filePath: String): Bitmap?

    suspend fun saveBitmapToCache(filePath: String, bitmap: Bitmap)

    suspend fun saveOrGetBitmapFromCache(filePath: String): Bitmap?
}

class PersonBitmapLruCacheDataSourceImpl(
    private val applicationContext: Context,
    private val appDispatchers: AppDispatchers
) : PersonBitmapLruCacheDataSource {
    private val bitmapCache = LruCache<String, Bitmap>(CACHE_SIZE)

    override suspend fun getBitmapFromCache(filePath: String): Bitmap? = withContext(appDispatchers.IO) {
        bitmapCache.get(filePath)
    }

    override suspend fun saveBitmapToCache(
        filePath: String, bitmap: Bitmap
    ): Unit = withContext(appDispatchers.IO) {
        bitmapCache.put(filePath, bitmap)
        Timber.d("saved bitmap for: $filePath")
    }

    override suspend fun saveOrGetBitmapFromCache(filePath: String): Bitmap? = withContext(appDispatchers.IO) {
        var cachedBitmap = getBitmapFromCache(filePath)
        if (cachedBitmap == null) {
            Timber.d("not able to find cached bitmap for: $filePath")
            cachedBitmap = getBitmapFromAssets(filePath) ?: return@withContext null
            saveBitmapToCache(filePath, cachedBitmap)
        }
        return@withContext cachedBitmap
    }

    private suspend fun getBitmapFromAssets(filePath: String): Bitmap? = withContext(appDispatchers.Default) {
        return@withContext try {
            BitmapFactory.decodeStream(applicationContext.assets.open(filePath))
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }

    private companion object {
        const val CACHE_SIZE = 10 * 1024 * 1024 //10MiB
    }
}