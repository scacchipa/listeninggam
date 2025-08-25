plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ar.com.westsoft.listening"
    compileSdk = 36

    defaultConfig {
        applicationId = "ar.com.westsoft.listening"
        minSdk = 26
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "DEBUG_DICTATION_TEXT_TITLE", "null")
            buildConfigField("String", "DEBUG_DICTATION_TEXT_ADDRESS", "null")
        }
        debug {
            buildConfigField("String", "DEBUG_DICTATION_TEXT_TITLE", "\"LICENCIA\"")
            buildConfigField(
                "String",
                "DEBUG_DICTATION_TEXT_ADDRESS",
                "\"https://d-scholarship.pitt.edu/5725/6/licence.txt\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    implementation(project(":keyboard"))
    implementation(project(":epub"))

    implementation(libs.androidx.compose.runtime)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.activity.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.androidx.room.testing)

    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
