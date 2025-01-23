plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
//    mingwX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":Meld-KSP-Data"))
                implementation("com.squareup:javapoet:1.12.1")
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("com.google.devtools.ksp:symbol-processing-api:2.1.0-1.0.29")
                implementation("com.squareup:kotlinpoet-ksp:2.0.0")
                implementation("com.squareup:kotlinpoet-ksp:2.0.0")
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}