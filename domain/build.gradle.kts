import Dependencies.Kotlin.implementCommonKotlin
import Dependencies.Testing.implementCommonTesting

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
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
    implementCommonKotlin()
    implementation(Dependencies.OtherThirdParty.timber)

    implementCommonTesting()
}