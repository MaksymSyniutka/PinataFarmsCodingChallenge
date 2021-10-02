import Dependencies.AndroidX.implementCommonAndroidX
import Dependencies.Kotlin.implementCommonKotlin

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = AppConfiguration.compileSdkVersion

    defaultConfig {
        minSdk = AppConfiguration.minSdkVersion
        targetSdk = AppConfiguration.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfiguration.sourceCompatibility
        targetCompatibility = AppConfiguration.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = AppConfiguration.kotlinJvmTarget
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.AndroidX.coreKtx)
    implementCommonKotlin()
}