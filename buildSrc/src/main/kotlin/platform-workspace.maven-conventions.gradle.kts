import com.github.dkorotych.gradle.maven.exec.MavenExec

plugins {
    `lifecycle-base`
    id("com.github.dkorotych.gradle-maven-exec")
}

group = "com.vaadin.platform-workspace"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://maven.vaadin.com/vaadin-prereleases") }
    maven { url = uri("https://repo.spring.io/milestone") }
}

val mavenProjectDir = file("${project.rootDir}/submodules/${project.name}")
val mavenWrapperDir = file("${project.rootDir}/maven")

tasks.withType(MavenExec::class.java).configureEach {
    mavenDir = mavenWrapperDir
    workingDir = mavenProjectDir
}

tasks.register<MavenExec>("mavenClean") {
    goals("clean")
}

tasks.register<MavenExec>("mavenInstall") {
    define(mapOf("skipTests" to "true"))
    goals("install")
}

tasks.register<MavenExec>("mavenVerify") {
    goals("verify")
}

tasks.named(LifecycleBasePlugin.CLEAN_TASK_NAME).configure {
    dependsOn("mavenClean")
}

tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure {
    dependsOn("mavenInstall")
}

tasks.named(LifecycleBasePlugin.CHECK_TASK_NAME).configure {
    dependsOn("mavenVerify")
}
