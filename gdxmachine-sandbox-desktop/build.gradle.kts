plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "com.disgraded.gdxmachine.sandbox.desktop.DesktopLauncher"
}

tasks.run.get().workingDir = File("../assets")
tasks.run.get().isIgnoreExitValue = true

dependencies {
    implementation((kotlin("stdlib")))
    implementation((kotlin("stdlib-jdk8")))
    implementation(project(":platform-desktop"))
    implementation(project(":framework"))
    implementation(project(":sandbox-source"))
}