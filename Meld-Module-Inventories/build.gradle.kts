plugins {
    kotlin("multiplatform") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

kotlin {
    jvm()
    linuxX64()

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(project(":Meld-Server"))
        implementation(project(":Meld-Module-Entities"))
        implementation(project(":Meld-Module-Player"))
    }
}