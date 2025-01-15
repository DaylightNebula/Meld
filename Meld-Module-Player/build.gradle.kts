plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
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
    implementation(project(":Meld-Module-Entities"))
    implementation(project(":Meld-Module-Login"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("dev.romainguy:kotlin-math:1.6.0")
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/modules/"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}