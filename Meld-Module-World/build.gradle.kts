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
    mingwX64()

    sourceSets["commonMain"].dependencies {
        implementation(project(":Meld-Server"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
        implementation("dev.romainguy:kotlin-math:1.6.0")
    }
}