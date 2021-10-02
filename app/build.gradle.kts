import Dependencies.AndroidX.implementCommonAndroidX
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
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
}

dependencies {
    implementCommonAndroidX()
}