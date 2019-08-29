import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val gdxVersion: String by extra
val ashleyVersion: String by extra

plugins {
    kotlin("jvm")
    id("maven-publish")
}

dependencies {
    implementation((kotlin("stdlib")))
    implementation((kotlin("reflect")))
    api("com.badlogicgames.gdx:gdx:$gdxVersion")
    api("com.badlogicgames.ashley:ashley:$ashleyVersion")
    api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
    api("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
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

publishing {
    repositories {
        maven {
            url = uri("$buildDir/release")
        }
    }

    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            artifactId = project.name
            artifact(sourcesJar.get())

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
                    connection.set("scm:git:git://github.com/kalevski/gdxmachine.git")
                    developerConnection.set("scm:git:ssh://github.com:kalevski/gdxmachine.git")
                    url.set("https://github.com/kalevski/gdxmachine")
                }
            }
        }
    }
}