@file:OptIn(ExperimentalDistributionDsl::class)

import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    explicitApi()

    js(IR) {
        outputModuleName = "kmp-lib"
        browser {
            webpackTask {
                mainOutputFileName = "kmp_lib.js"
                output.library = "kmpLib"
            }
        }
        nodejs()
        binaries.library()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.shared.domain)
                api(projects.shared.data)
                api(projects.core)
            }
        }
    }
}
