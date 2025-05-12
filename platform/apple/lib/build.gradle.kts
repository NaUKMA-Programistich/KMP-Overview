import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    explicitApi()

    val xcFramework = XCFramework("SharedDomain")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "SharedDomain"
            xcFramework.add(this)
            export(projects.shared.domain)
            export(projects.shared.data)
            export(projects.core)
        }
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
