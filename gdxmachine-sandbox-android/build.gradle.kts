plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {

    compileSdkVersion(28)

    defaultConfig {
        applicationId = "com.disgraded.gdxmachine.sandbox.android"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0.0"
    }
    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    api((kotlin("stdlib")))
    api(project(":platform-android"))
    api(project(":sandbox-source"))
}