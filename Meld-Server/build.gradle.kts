plugins {
    kotlin("multiplatform") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
}

group = "io.github.daylightnebula"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main")
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
}

kotlin {
    jvm()
    mingwX64()

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir(file("$buildDir/generated/ksp/metadata/commonMain/kotlin"))

            dependencies {
                implementation(project(":Meld-KSP-Data"))

                // kotlin
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("dev.romainguy:kotlin-math:1.6.0")
                implementation("com.squareup.okio:okio:3.10.0")

                // networking
                implementation("io.ktor:ktor-network-tls:3.0.3")
                implementation("io.ktor:ktor-server-core:3.0.3")
                implementation("io.ktor:ktor-server-cio:3.0.3")
                implementation("ch.qos.logback:logback-classic:1.2.11")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio-jvm:3.3.0")
            }
        }
    }
}

dependencies {
//    ksp(project(":Meld-KSP"))

    add("kspCommonMainMetadata", project(":Meld-KSP"))
    add("kspJvm", project(":Meld-KSP"))
//    add("kspMingwX64", project(":Meld-KSP"))
}

task<Exec>("generate-packets") {
    executable = "python"
    args("../generators/packet_generator/packet_generator.py")
}
