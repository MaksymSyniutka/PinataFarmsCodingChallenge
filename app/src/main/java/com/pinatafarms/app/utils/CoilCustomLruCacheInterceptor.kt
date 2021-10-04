package com.pinatafarms.app.utils

import android.content.Context
import androidx.core.graphics.drawable.toDrawable
import coil.annotation.ExperimentalCoilApi
import coil.decode.DataSource
import coil.intercept.Interceptor
import coil.memory.MemoryCache
import coil.request.ImageResult
import coil.request.SuccessResult
import com.pinatafarms.data.source.image.PersonBitmapLruCacheDataSource
import com.pinatafarms.domain.AppConstants

@ExperimentalCoilApi
class CoilCustomLruCacheInterceptor(
    private val applicationContext: Context,
    private val lruCacheDataSource: PersonBitmapLruCacheDataSource
) : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val bitmap = lruCacheDataSource.getBitmapFromCache(
            chain.request.data.toString().replace(AppConstants.ASSETS_FILE_PATH_PREFIX, "")
        ) ?: return chain.proceed(chain.request)
        return SuccessResult(
            drawable = bitmap.toDrawable(applicationContext.resources),
            request = chain.request,
            metadata = ImageResult.Metadata(
                memoryCacheKey = MemoryCache.Key(chain.request.data.toString()),
                isSampled = false,
                dataSource = DataSource.MEMORY_CACHE,
                isPlaceholderMemoryCacheKeyPresent = false
            )
        )
    }
}