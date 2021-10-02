// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Project.kotlinGradlePlugin)
        classpath(Dependencies.Project.navigationSafeArgs)
        classpath(Dependencies.Project.gradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
