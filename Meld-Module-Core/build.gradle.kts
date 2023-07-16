plugins {
    kotlin("jvm") version "1.8.22"
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

    // protocols
    implementation(libs.bundles.fastutil)
    api(libs.bundles.bedrockprotocol)
    api(libs.bundles.javaprotocol)
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/modules/"))
    }
}