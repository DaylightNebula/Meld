import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.serialization") version "1.8.22"
    id("io.ktor.plugin") version "2.3.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "io.github.daylightnebula"
version = "0.0.1"
application {
    mainClass.set("io.github.daylightnebula.MeldKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    maven("https://jitpack.io")

    // Floodgate, Cumulus etc.
    maven("https://repo.opencollab.dev/main")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")

    // Paper, Velocity
    maven("https://repo.papermc.io/repository/maven-public")

    // Spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots") {
        mavenContent { snapshotsOnly() }
    }

    // BungeeCord
    maven("https://oss.sonatype.org/content/repositories/snapshots") {
        mavenContent { snapshotsOnly() }
    }

    // Minecraft
    maven("https://libraries.minecraft.net") {
        name = "minecraft"
        mavenContent { releasesOnly() }
    }

    mavenLocal()
    mavenCentral()

    // ViaVersion
    maven("https://repo.viaversion.com") {
        name = "viaversion"
    }

    // Sponge
    maven("https://repo.spongepowered.org/repository/maven-public/")

    // For Adventure snapshots
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    // kotlin
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.json:json:20230227")

    // networking
    implementation("io.ktor:ktor-network-tls-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cio-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // nbt
    implementation("io.github.jglrxavpok.hephaistos:common:2.5.3")
    implementation("io.github.jglrxavpok.hephaistos:antlr:2.5.3")
    implementation("io.github.jglrxavpok.hephaistos:gson:2.5.3")

    // protocols
    implementation(libs.bundles.fastutil)
    api(libs.bundles.bedrockprotocol)
    api(libs.bundles.javaprotocol)

    // netty
    implementation("io.netty:netty-all:4.1.66.Final")
    implementation("io.netty:netty-codec-haproxy:4.1.66.Final")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}
