plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
}

dependencies {
    // Ktor server
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors)

    // Database
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2)

    // Kotlinx
    implementation(libs.kotlinx.datetime)

    // Logging
    implementation(libs.logback.classic)

    // Shared
    implementation(projects.shared.data)

    // Testing
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.test.host)

    testImplementation(libs.ktor.client.content.negotiation)
}

application {
    mainClass.set("ukma.edu.ua.server.ktor.ApplicationKt")
}
