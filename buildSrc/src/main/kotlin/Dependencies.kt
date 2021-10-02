import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

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