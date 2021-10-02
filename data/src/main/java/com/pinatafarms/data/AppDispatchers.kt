package com.pinatafarms.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

open class AppDispatchers {
    var IO: CoroutineDispatcher = Dispatchers.IO
    var Main: CoroutineDispatcher = Dispatchers.Main
    var Default: CoroutineDispatcher = Dispatchers.Default
    var MainImmediate: MainCoroutineDispatcher = Dispatchers.Main.immediate

    /**
     * Used to reset dispatchers when running unit tests.
     * */
    fun resetAll() {
        IO = Dispatchers.IO
        Main = Dispatchers.Main
        Default = Dispatchers.Default
        MainImmediate = Dispatchers.Main.immediate
    }
}