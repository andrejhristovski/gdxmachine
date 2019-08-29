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
    api((kotlin("stdlib")))
    api((kotlin("stdlib-jdk8")))
    api(project(":platform-desktop"))
    api(project(":sandbox-source"))
}