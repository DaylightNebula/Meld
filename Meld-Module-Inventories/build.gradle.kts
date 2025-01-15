plugins {
    kotlin("jvm") version "2.1.0"
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
    implementation(project(":Meld-Module-Player"))
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/modules/"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}