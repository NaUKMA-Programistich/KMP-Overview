@file:Suppress("UnstableApiUsage")

rootProject.name = "KMP-Overview"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

include(":core")

include(":server")
include(":server:ktor")
include(":server:spring")

include(":shared")
include(":shared:data")
include(":shared:domain")
include(":shared:ui")

include(":platform")
include(":platform:desktop") // JVM
include(":platform:android") // Android

include(":platform:apple:compose") // Apple Compose
include(":platform:apple:lib") // xcframework

include(":platform:js:wasm") // Compose
include(":platform:js:lib") // npm
