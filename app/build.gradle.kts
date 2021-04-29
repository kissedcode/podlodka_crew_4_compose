plugins {
  id("com.android.application")
  id("kotlin-android")
  kotlin("plugin.serialization") version "1.4.30"
}

val composeVersion = "1.0.0-beta05"

android {
  compileSdk = 30

  defaultConfig {
    applicationId = "dev.kissed.podlodka_compose"
    minSdk = 21
    targetSdk = 30
    versionCode = 1
    versionName = "1.0"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }

  // compose
  buildFeatures {
    compose = true

    // Disable unused AGP features
    buildConfig = false
    aidl = false
    renderScript = false
    resValues = false
    shaders = false
  }

  composeOptions {
    kotlinCompilerExtensionVersion = composeVersion
  }

  // new gradle plugin 7
  packagingOptions {
    resources.excludes += "/META-INF/AL2.0"
    resources.excludes += "/META-INF/LGPL2.1"
  }
}

dependencies {

  implementation("androidx.core:core-ktx:1.3.2")
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("com.google.android.material:material:1.3.0")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

  // ktor
  implementation("io.ktor:ktor-client-core:1.5.3")
  implementation("io.ktor:ktor-client-cio:1.5.3")

  // compose
  implementation("androidx.activity:activity-compose:1.3.0-alpha07")
  implementation("androidx.compose.ui:ui:$composeVersion")
  implementation("androidx.compose.material:material:$composeVersion")
  implementation("androidx.compose.material:material-icons-extended:$composeVersion")
  implementation("androidx.compose.ui:ui-tooling:$composeVersion")
  implementation("androidx.navigation:navigation-compose:1.0.0-alpha10")

  // accoumpanist
  implementation("com.google.accompanist:accompanist-glide:0.8.1")
}