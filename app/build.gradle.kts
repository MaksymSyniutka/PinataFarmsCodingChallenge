import Dependencies.AndroidX.implementCommonAndroidX
import Dependencies.DependencyInjection.implementKoin
import Dependencies.Kotlin.implementCommonKotlin
import Dependencies.Navigation.implementNavigation
import Dependencies.Serialization.implementSerialization
import Dependencies.Testing.implementCommonTesting
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization") version Versions.Serialization.kotlinSerializationPlugin
}

android {
    compileSdk = AppConfiguration.compileSdkVersion

    defaultConfig {
        applicationId = "com.pinatafarms.app"
        minSdk = AppConfiguration.minSdkVersion
        targetSdk = AppConfiguration.targetSdkVersion
        versionCode = AppConfiguration.versionCode
        versionName = AppConfiguration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        all {
            applicationVariants.all {
                outputs.all {
                    (this as BaseVariantOutputImpl).outputFileName = "${name}-${versionName}(${versionCode}).apk"
                }
            }
        }
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
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementCommonAndroidX()
    implementSerialization()
    implementCommonKotlin()
    implementNavigation()
    implementKoin()

    implementation(Dependencies.OtherThirdParty.coilImageLibrary)
    implementation(Dependencies.OtherThirdParty.timber)

    implementCommonTesting()
}