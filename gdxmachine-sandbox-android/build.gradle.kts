plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {

    compileSdkVersion(29)

    defaultConfig {
        applicationId = "com.disgraded.gdxmachine.sandbox.android"
        minSdkVersion(18)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0.0"

    }
    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        val main by getting {
            assets.srcDir("../assets")
        }
    }
}

dependencies {
    api((kotlin("stdlib")))
    api(project(":platform-android"))
    api(project(":sandbox-source"))
}