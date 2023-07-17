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
    implementation(project(":Meld-Module-Entities"))
    implementation(project(":Meld-Module-Login"))
    implementation("org.json:json:20230227")

    // protocols
    implementation(libs.bundles.fastutil)
    api(libs.bundles.bedrockprotocol)
    api(libs.bundles.javaprotocol)

    // nbt
    implementation("io.github.jglrxavpok.hephaistos:common:2.5.3")
    implementation("io.github.jglrxavpok.hephaistos:antlr:2.5.3")
    implementation("io.github.jglrxavpok.hephaistos:gson:2.5.3")
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/modules/"))
    }
}