import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.c

plugins {
    kotlin("multiplatform") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

kotlin {
    jvm {
        withJava()
    }
    linuxX64()

    sourceSets["commonMain"].dependencies {
        implementation(project(":Meld-Server"))
        implementation(project(":Meld-Module-Entities"))
        implementation(project(":Meld-Module-Player"))
        implementation(project(":Meld-Module-Login"))
        implementation(project(":Meld-Module-Inventories"))
        implementation(project(":Meld-Module-World"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
        implementation("dev.romainguy:kotlin-math:1.6.0")
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "io.github.daylightnebula.meld.test.TestKt"
    }
}

task<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    from(kotlin.jvm().compilations["main"].output)  // Include JVM classes
    configurations = listOf(project.configurations.getByName("jvmRuntimeClasspath"))
    mergeServiceFiles()
}

tasks.create<Exec>("runJar") {
    dependsOn("shadowJar")
    executable = "java"
    workingDir = rootDir
    args("-jar", "build/libs/Meld-Test.jar")
}
