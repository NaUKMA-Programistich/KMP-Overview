import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false

    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.spring.core) apply false
    alias(libs.plugins.spring.jpa) apply false
}

allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-Xwhen-guards")
            freeCompilerArgs.add("-Xskip-prerelease-check")
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }

    tasks.withType<KotlinNativeCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-opt-in=kotlinx.cinterop.ExperimentalForeignApi")
            freeCompilerArgs.add("-opt-in=kotlinx.cinterop.BetaInteropApi")
        }
    }
}
