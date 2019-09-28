plugins {
    kotlin("jvm")
}

dependencies {
    implementation((kotlin("stdlib")))
    implementation((kotlin("stdlib-jdk8")))
    implementation(project(":core"))
}