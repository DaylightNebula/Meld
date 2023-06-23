import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.22"
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
    // Floodgate, Cumulus etc.
    maven("https://repo.opencollab.dev/main")

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

    maven("https://jitpack.io") {
        content { includeGroupByRegex("com\\.github\\..*") }
    }

    // For Adventure snapshots
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("org.json:json:20230227")
    implementation("io.ktor:ktor-network-tls-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cio-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("net.benwoodworth.knbt:knbt:0.11.3")
    implementation(libs.bundles.fastutil)
//    implementation("it.unimi.dsi:fastutil:8.5.12")
    api(libs.bundles.protocol) {
//        substitute
    }
//    compileOnly("org.cloudburstmc.protocol:bedrock-connection:3.0.0.Beta1-SNAPSHOT")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation(kotlin("stdlib-jdk8"))
}
