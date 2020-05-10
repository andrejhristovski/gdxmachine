import java.net.URI

val versionNumber: Int by extra

val gdxVersion: String by extra
val kotlinVersion: String by extra
val ashleyVersion: String by extra


val jniPath = "src/main/jniLibs"

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

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

publishing {

    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/disgraded/gdxmachine")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

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
    api("com.badlogicgames.gdx:gdx-controllers-android:$gdxVersion")
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

val copyAssets by tasks.register("copyAssets") {
    group = "dependencies"
    copy {
        from("${project(":framework").projectDir}/src/main/resources")
        include("**/*")
        into("src/main/assets")
    }
}

val preBuild: Task = tasks["preBuild"]
preBuild.dependsOn("copyNatives")
preBuild.dependsOn("copyAssets")