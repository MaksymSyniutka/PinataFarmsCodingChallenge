object Versions {
    object Project {
        const val kotlinGradlePlugin = Kotlin.common
        const val gradlePlugin = "7.0.2"
    }

    object Kotlin {
        const val common = "1.5.31"
        const val coroutines = "1.5.2"
    }

    object AndroidX {
        const val coreKtx = "1.6.0"
        const val appCompat = "1.3.1"
        const val material = "1.4.0"
        const val constraintLayout = "2.1.1"
        const val viewModel = "2.3.1"
        const val recyclerView = "1.2.1"

        //TODO: change to production version once released
        const val lifecycleRuntime = "2.4.0-rc01"
    }

    object Serialization {
        const val moshi = "1.12.0"
        const val kotlinSerializationPlugin = "1.5.31"
        const val kotlinSerializationLibrary = "1.3.0"
    }

    object DependencyInjection {
        const val koinCommon = "3.1.2"
    }

    object Navigation {
        const val common = "2.3.5"
    }

    object MLKit {
        const val faceDetection = "16.1.2"
    }

    object OtherThirdParty {
        const val coilImageLibrary = "1.3.2"
        const val timber = "5.0.1"
    }

    object Testing {
        const val junit = "4.13.2"
        const val androidXJunit = "1.1.3"
        const val espresso = "3.4.0"
    }
}