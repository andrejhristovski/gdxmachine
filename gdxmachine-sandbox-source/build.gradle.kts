plugins {
    kotlin("jvm")
}

dependencies {
    api((kotlin("stdlib")))
    api((kotlin("stdlib-jdk8")))
    api(project(":core"))
}