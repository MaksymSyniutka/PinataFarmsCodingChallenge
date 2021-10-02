import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    object Project {
        const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Project.kotlinGradlePlugin}"
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.Project.gradlePlugin}"
        const val navigationSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Navigation.common}"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.common}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"

        fun DependencyHandler.implementCommonKotlin() {
            implementation(stdLib)
            implementation(coroutines)
        }
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val material = "com.google.android.material:material:${Versions.AndroidX.material}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.viewModel}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerView}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycleRuntime}"

        fun DependencyHandler.implementCommonAndroidX() {
            implementation(coreKtx)
            implementation(material)
            implementation(appCompat)
            implementation(viewModel)
            implementation(recyclerView)
            implementation(lifecycleRuntime)
            implementation(constraintLayout)
        }
    }

    object Serialization {
        const val kotlinSerialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Serialization.kotlinSerializationLibrary}"

        fun DependencyHandler.implementSerialization() {
            implementation(kotlinSerialization)
        }
    }

    object DependencyInjection {
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.DependencyInjection.koinCommon}"

        fun DependencyHandler.implementKoin() {
            implementation(koinAndroid)
        }
    }

    object Navigation {
        const val fragmentNavigation = "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation.common}"
        const val uiExtensions = "androidx.navigation:navigation-ui-ktx:${Versions.Navigation.common}"

        fun DependencyHandler.implementNavigation() {
            implementation(fragmentNavigation)
            implementation(uiExtensions)
        }
    }

    object MLKit {
        const val faceDetection = "com.google.mlkit:face-detection:${Versions.MLKit.faceDetection}"

        fun DependencyHandler.implementMLKit() {
            implementation(faceDetection)
        }
    }

    object OtherThirdParty {
        const val coilImageLibrary = "io.coil-kt:coil:${Versions.OtherThirdParty.coilImageLibrary}"
        const val timber = "com.jakewharton.timber:timber:${Versions.OtherThirdParty.timber}"
    }

    object Testing {
        const val junit = "junit:junit:${Versions.Testing.junit}"
        const val androidXJunit = "androidx.test.ext:junit:${Versions.Testing.androidXJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Testing.espresso}"

        fun DependencyHandler.implementCommonTesting() {
            testImplementation(junit)
            androidTestImplementation(androidXJunit)
            androidTestImplementation(espresso)
        }
    }
}