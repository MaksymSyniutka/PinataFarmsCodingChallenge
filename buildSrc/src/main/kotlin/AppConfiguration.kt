import org.gradle.api.JavaVersion

object AppConfiguration {
    const val compileSdkVersion = 31
    const val minSdkVersion = 23
    const val targetSdkVersion = 31
    const val versionCode = 1
    const val versionName = "1.0"
    const val kotlinJvmTarget = "1.8"
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}