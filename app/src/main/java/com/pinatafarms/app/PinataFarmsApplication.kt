package com.pinatafarms.app

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import com.pinatafarms.app.di.*
import com.pinatafarms.app.utils.CoilCustomLruCacheInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import timber.log.Timber
import com.pinatafarms.data.source.image.PersonBitmapLruCacheDataSource

@ExperimentalCoilApi
class PinataFarmsApplication : Application(), ImageLoaderFactory, KoinComponent {
    private val coilInterceptor: CoilCustomLruCacheInterceptor by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PinataFarmsApplication)
            modules(appModule, useCasesModule, repositoriesModule, dataSourcesModule, viewModelsModule)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    /**
     * Substitutes default Coil's image loader to the one that utilises [PersonBitmapLruCacheDataSource].
     *
     * TODO:
     *  Once image fetching from server is introduces within the app - remove this method and use default Coil's image
     *  loader for performance optimization (even though this one should fetch images from remote too).
     * */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .componentRegistry { add(coilInterceptor) }
            .build()
    }

    private inner class CrashReportingTree : DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority >= Log.INFO) super.log(priority, tag, message, t)
        }
    }

    private open inner class DebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return String.format("(%s:%s):%s ", element.fileName, element.lineNumber, element.methodName)
        }
    }
}