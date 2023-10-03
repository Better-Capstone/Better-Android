import org.gradle.api.JavaVersion

object Apps {
    const val compileSdk = 33
    const val minSdk = 24
    const val targetSdk = 33

    val javaCompileOption = JavaVersion.VERSION_17
    const val jvmTarget = "17"
}