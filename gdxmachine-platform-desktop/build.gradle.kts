import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("com.github.johnrengelman.shadow")
}

val gdxVersion: String by extra

dependencies {
    runtime((kotlin("stdlib")))
    runtime((kotlin("stdlib-jdk8")))

    runtime(project(":core"))

    api("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    api("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    api("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop")
    api("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop")
    api("com.badlogicgames.gdx:gdx-tools:$gdxVersion")
}
repositories {
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.get("main").allSource)
}

//val shadowJar: ShadowJar by tasks
//shadowJar.apply {
//    archiveBaseName.set(project.name)
//    archiveVersion.set(project.version.toString())
//    archiveClassifier.set("")
//}

publishing {
    repositories {
        maven {
            url = uri("$buildDir/release")
        }
    }

    publications {
        create<MavenPublication>("release") {
            from(components["java"])
            artifactId = project.name

            pom {
                name.set(project.name)
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("kalevski")
                        name.set("Daniel Kalevski")
                        email.set("kalevski@outlook.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/disgraded/gdxmachine.git")
                    developerConnection.set("scm:git:ssh://github.com:disgraded/gdxmachine.git")
                    url.set("https://github.com/disgraded/gdxmachine")
                }
            }
        }
    }
}