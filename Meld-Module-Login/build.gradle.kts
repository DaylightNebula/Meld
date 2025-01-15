plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "1.8.22"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":Meld-Server"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("net.benwoodworth.knbt:knbt:0.11.8")
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/modules/"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}