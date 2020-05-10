repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
    google()
}

plugins {
    base
    java
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.3.71" apply false
    id("digital.wup.android-maven-publish") version "3.6.2" apply false
}

buildscript {

    extra["kotlinVersion"] = "1.3.71"
    val kotlinVersion: String by extra

    repositories {
        mavenCentral()
        mavenLocal()
        jcenter()
        google()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:3.5.3")
    }
}

allprojects {

    group = "com.disgraded.gdxmachine"
    version = "0.7.0"
    extra["versionNumber"] = 4

    extra["gdxVersion"] = "1.9.10"
    extra["ashleyVersion"] = "1.7.3"
    extra["kotlinVersion"] = "1.3.41"
    extra["junit5Version"] = "5.5.2"
    extra["mockitoVersion"] = "3.0.0"

    repositories {
        mavenCentral()
        mavenLocal()
        jcenter()
        google()
    }
}