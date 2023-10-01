// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_android_gradle_plugin}")
        classpath("com.google.gms:google-services:${Versions.google_services}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}