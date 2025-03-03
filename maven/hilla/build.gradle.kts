import com.github.dkorotych.gradle.maven.exec.MavenExec

plugins {
    id("platform-workspace.maven-conventions")
}

dependencies {
    project(":maven:flow")
    project(":npm:hilla")
}

tasks.withType(MavenExec::class).configureEach {
    // Exclude Hilla Gradle plugin due to nested build issues
    options.projects(arrayOf("!com.vaadin:hilla-gradle-plugin"))
}
