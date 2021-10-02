import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    object Project {
        const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Project.kotlinGradlePlugin}"
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.Project.gradlePlugin}"
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

        fun DependencyHandler.implementCommonAndroidX() {
            implementation(coreKtx)
            implementation(material)
            implementation(appCompat)
            implementation(constraintLayout)
        }
    }

    object Testing {
        const val junit = "junit:junit:${Versions.Testing.junit}"
        const val androidXJunit = "androidx.test.ext:junit:${Versions.Testing.androidXJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Testing.espresso}"
    }
}