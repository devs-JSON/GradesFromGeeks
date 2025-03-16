import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
    id("kotlin-kapt")
}

android {
    namespace = "json.gradesfromgeeks"
    compileSdk = 34
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    defaultConfig {
        applicationId = "json.gradesfromgeeks"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("ApiKey")}\"")

    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.lifecycle.runtime.ktx.v270)
    implementation(libs.androidx.activity.compose.v182)
    implementation(libs.compose.bom.v20231001)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.compose.bom.v20231001)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.androidx.material.icons.core)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)


    //room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //navigation
    implementation(libs.androidx.navigation.compose)

    //accompanist
    implementation(libs.accompanist.systemuicontroller)

    //time
    implementation(libs.kotlinx.datetime)

    //coil
    implementation(libs.coil.compose)

    //lottie
    implementation(libs.lottie.compose)
    // DataStore
    implementation(libs.androidx.datastore.preferences.v100)


    implementation(libs.androidx.lifecycle.runtime.compose)

    //Gimien AI
    implementation(libs.generativeai)

    api(project(":design_system"))

    //SplashScreen Api
    implementation(libs.androidx.core.splashscreen)

    //constraint layout
    implementation(libs.androidx.constraintlayout.compose)

    // ExoPlayer
    implementation(libs.exoplayer)

    implementation(libs.datastore.preferences)

    implementation (libs.bouquet)

}
