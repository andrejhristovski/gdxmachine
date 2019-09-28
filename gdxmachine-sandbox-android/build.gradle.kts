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
    implementation((kotlin("stdlib")))
    implementation(project(":platform-android"))
    implementation(project(":sandbox-source"))
}