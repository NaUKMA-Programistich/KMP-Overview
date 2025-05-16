import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Locale

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    explicitApi()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries {
            executable {
                entryPoint = "ukma.edu.ua.platform.compose.ios.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }

    listOf(
        macosX64(),
        macosArm64(),
    ).forEach {
        it.binaries {
            executable {
                entryPoint = "ukma.edu.ua.platform.compose.macos.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }

    val xcFramework = XCFramework("SharedUI")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "SharedUI"
            xcFramework.add(this)
            export(projects.shared.domain)
            export(projects.shared.data)
            export(projects.core)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.shared.ui)
                api(projects.shared.domain)
                api(projects.shared.data)
                api(projects.core)
            }
        }
    }
}

val sdkName: String? = System.getenv("SDK_NAME")
val targetBuildDir: String? = System.getenv("TARGET_BUILD_DIR")
val executablePath: String? = System.getenv("EXECUTABLE_PATH")

enum class Target(val simulator: Boolean, val key: String) {
    WATCHOS_X86(true, "watchos"),
    WATCHOS_ARM64(false, "watchos"),
    IOS_X64(true, "iosX64"),
    IOS_ARM64(false, "iosArm64"),
    IOS_SIMULATOR_ARM64(true, "iosSimulatorArm64"),
    TVOS_X64(true, "tvosX64"),
    TVOS_ARM64(true, "tvosArm64"),
    TVOS_SIMULATOR_ARM64(true, "tvosSimulatorArm64"),
    MACOS_X64(false, "macosX64"),
    MACOS_ARM64(false, "macosArm64"),
}

val osName = System.getProperty("os.name")
val hostOs = when {
    osName == "Mac OS X" -> "macos"
    osName.startsWith("Win") -> "windows"
    osName.startsWith("Linux") -> "linux"
    else -> error("Unsupported OS: $osName")
}

val osArch = System.getProperty("os.arch")
var hostArch = when (osArch) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported arch: $osArch")
}

val host = "$hostOs-$hostArch"

val target = sdkName.orEmpty().let {
    when {
        it.startsWith("iphoneos") -> Target.IOS_ARM64
        it.startsWith("appletvsimulator") -> when (host) {
            "macos-x64" -> Target.TVOS_X64
            "macos-arm64" -> Target.TVOS_SIMULATOR_ARM64
            else -> throw GradleException("Host OS is not supported")
        }
        it.startsWith("appletvos") -> Target.TVOS_ARM64
        it.startsWith("watchos") -> Target.WATCHOS_ARM64
        it.startsWith("watchsimulator") -> Target.WATCHOS_X86
        it.startsWith("iphonesimulator") -> when (host) {
            "macos-x64" -> Target.IOS_X64
            "macos-arm64" -> Target.IOS_SIMULATOR_ARM64
            else -> throw GradleException("Host OS is not supported")
        }
        else -> when (host) {
            "macos-x64" -> Target.MACOS_X64
            "macos-arm64" -> Target.MACOS_ARM64
            else -> throw GradleException("Host OS is not supported")
        }
    }
}

val buildType = System.getenv("CONFIGURATION")?.let {
    NativeBuildType.valueOf(it.uppercase(Locale.getDefault()))
} ?: NativeBuildType.DEBUG

val currentTarget = kotlin.targets[target.key] as KotlinNativeTarget
val kotlinBinary = currentTarget.binaries.getExecutable(buildType)

if (sdkName == null || targetBuildDir == null || executablePath == null) {
    tasks.create("packSkikoAppForXCode").doLast {
        throw GradleException("Environment variables SDK_NAME, TARGET_BUILD_DIR, and EXECUTABLE_PATH must be set")
    }
} else {
    tasks.create("packSkikoAppForXCode") {
        dependsOn(kotlinBinary.linkTaskProvider)

        val dsymSource = kotlinBinary.outputFile.absolutePath + ".dSYM"
        val dsymDestination = File(executablePath).parentFile.name + ".dSYM"
        val oldExecName = kotlinBinary.outputFile.name
        val newExecName = File(executablePath).name

        copy {
            into(targetBuildDir)

            from(dsymSource) {
                into(dsymDestination)
                rename(oldExecName, newExecName)
            }

            from(kotlinBinary.outputFile) {
                rename { executablePath }
            }
        }
    }
}
