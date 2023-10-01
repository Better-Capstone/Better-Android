plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Apps.javaCompileOption
    targetCompatibility = Apps.javaCompileOption
}

dependencies {
    api(project(":domain"))
    api("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("javax.inject:javax.inject:${Versions.javax_inject}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines_core}")
    implementation("androidx.paging:paging-common-ktx:${Versions.paging_common}")
    implementation("com.google.code.gson:gson:${Versions.gson}")
}