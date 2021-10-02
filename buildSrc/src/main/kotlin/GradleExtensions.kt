import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.project

internal fun DependencyHandler.projectImplementation(projectName: String) {
    add("implementation", project(projectName))
}

internal fun DependencyHandler.platformImplementation(projectName: String) {
    add("implementation", platform(projectName))
}

internal fun DependencyHandler.implementation(dependencyName: String, configuration: (ExternalModuleDependency.() -> Unit)? = null) {
    if (configuration == null) add("implementation", dependencyName)
    else add("implementation", dependencyName, configuration)
}

internal fun DependencyHandler.kapt(dependencyName: String, configuration: (ExternalModuleDependency.() -> Unit)? = null) {
    if (configuration == null) add("kapt", dependencyName)
    else add("kapt", dependencyName, configuration)
}

internal fun DependencyHandler.compileOnly(dependencyName: String, configuration: (ExternalModuleDependency.() -> Unit)? = null) {
    if (configuration == null) add("compileOnly", dependencyName)
    else add("compileOnly", dependencyName, configuration)
}

internal fun DependencyHandler.testImplementation(dependencyName: String, configuration: (ExternalModuleDependency.() -> Unit)? = null) {
    if (configuration == null) add("testImplementation", dependencyName)
    else add("testImplementation", dependencyName, configuration)
}

internal fun DependencyHandler.androidTestImplementation(dependencyName: String, configuration: (ExternalModuleDependency.() -> Unit)? = null) {
    if (configuration == null) add("androidTestImplementation", dependencyName)
    else add("androidTestImplementation", dependencyName, configuration)
}

internal fun DependencyHandler.testRuntimeOnly(dependencyName: String, configuration: (ExternalModuleDependency.() -> Unit)? = null) {
    if (configuration == null) add("testRuntimeOnly", dependencyName)
    else add("testRuntimeOnly", dependencyName, configuration)
}