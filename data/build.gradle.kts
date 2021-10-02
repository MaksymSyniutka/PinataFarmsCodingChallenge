import Dependencies.Kotlin.implementCommonKotlin
import Dependencies.MLKit.implementMLKit
import Dependencies.Serialization.implementSerialization
import Dependencies.Testing.implementCommonTesting

plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("plugin.serialization") version Versions.Serialization.kotlinSerializationPlugin
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

    implementation(Dependencies.OtherThirdParty.timber)
    implementation(Dependencies.AndroidX.coreKtx)
    implementSerialization()
    implementCommonKotlin()
    implementMLKit()

    implementCommonTesting()
}