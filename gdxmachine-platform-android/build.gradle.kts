val versionNumber: Int by extra

val gdxVersion: String by extra
val kotlinVersion: String by extra
val ashleyVersion: String by extra


val jniPath = "libs"

val armeabiDir = "$jniPath/armeabi/"
val armeabi_v7aDir = "$jniPath/armeabi-v7a/"
val arm64_v8a = "$jniPath/arm64-v8a/"
val x86_64Dir = "$jniPath/x86_64/"
val x86Dir = "$jniPath/x86/"

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("maven-publish")
    id("digital.wup.android-maven-publish")
}


android {

    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(18)
        targetSdkVersion(29)
        versionCode = versionNumber
        versionName = project.version.toString()
    }
    sourceSets {
        val main by getting {
            jniLibs.srcDirs(jniPath)
        }
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

publishing {
    (publications) {
        register("release", MavenPublication::class) {
            from(components["android"])
            groupId = project.group.toString()
            version = "${project.version}"
        }
    }
}

val natives = configurations.create("natives")
val library = configurations.create("library")

dependencies {

    implementation(kotlin("stdlib"))
    implementation(project(":framework"))
    api("com.badlogicgames.gdx:gdx-backend-android:$gdxVersion")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-arm64-v8a")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86_64")
    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-armeabi")
    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-armeabi-v7a")
    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-arm64-v8a")
    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-x86")
    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-x86_64")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi-v7a")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-arm64-v8a")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86_64")
}

val copyNatives by tasks.register("copyNatives") {
    group = "dependencies"
    doFirst {
        file(armeabiDir).mkdirs()
        file(armeabi_v7aDir).mkdirs()
        file(arm64_v8a).mkdirs()
        file(x86_64Dir).mkdirs()
        file(x86Dir).mkdirs()
    }

    natives.forEach {
        var outputDir : File? = null
        if (it.name.endsWith("natives-armeabi.jar")) outputDir = file(armeabiDir)
        if (it.name.endsWith("natives-armeabi-v7a.jar")) outputDir = file(armeabi_v7aDir)
        if (it.name.endsWith("natives-arm64-v8a.jar")) outputDir = file(arm64_v8a)
        if (it.name.endsWith("natives-x86_64.jar")) outputDir = file(x86_64Dir)
        if (it.name.endsWith("natives-x86.jar")) outputDir = file(x86Dir)
        if (outputDir != null) {
            copy {
                from(zipTree(it))
                into(outputDir)
                include("*.so")
            }
        }
    }
}

val preBuild by tasks.getting
preBuild.dependsOn("copyNatives")