plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.github.dkorotych.gradle-maven-exec:com.github.dkorotych.gradle-maven-exec.gradle.plugin:4.0.0")
    implementation("com.github.node-gradle:gradle-node-plugin:7.1.0")
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}