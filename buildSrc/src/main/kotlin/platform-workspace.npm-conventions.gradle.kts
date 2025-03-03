import com.github.gradle.node.NodeExtension
import com.github.gradle.node.npm.task.NpmTask

plugins {
    `lifecycle-base`
    id("com.github.node-gradle.node")
}

group = "com.vaadin.platform-workspace"
version = "1.0-SNAPSHOT"

val npmProjectDir = file("${project.rootDir}/submodules/${project.name}")

node {
    nodeProjectDir = npmProjectDir
}

val prependShell = Action<ExecSpec> {
    val cmdString = commandLine.joinToString(" ")
    val platform = extensions.getByType(NodeExtension::class).resolvedPlatform.get()
    commandLine = if (platform.isWindows())
        listOf("cmd.exe", "/c", cmdString)
    else
        listOf("sh", "-c", cmdString)
}

tasks.withType(NpmTask::class) {
    execOverrides(prependShell)
}

tasks.register<NpmTask>("npmClean") {
    npmCommand.set(listOf("run", "clean:build"))
}

tasks.register<NpmTask>("npmBuild") {
    npmCommand.set(listOf("run", "build"))
}

tasks.register<NpmTask>("npmTest") {
    npmCommand.set(listOf("test"))
}

tasks.named(LifecycleBasePlugin.CLEAN_TASK_NAME).configure {
    dependsOn("npmClean")
}

tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure {
    dependsOn("npmInstall")
}

tasks.named(LifecycleBasePlugin.CHECK_TASK_NAME).configure {
    dependsOn("npmTest")
}
