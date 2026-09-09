import java.net.URL

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    jvmToolchain(17)
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
            buildConfigField("String", "DEBUG_DICTATION_TEXT_TITLE", "\"Animals short story\"")
            buildConfigField(
                "String",
                "DEBUG_DICTATION_TEXT_ADDRESS",
                "\"Animals short story.txt\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    androidResources {
        noCompress += listOf("onnx", "tflite", "vox")
    }

    sourceSets {
        getByName("main") {
            assets.srcDirs("build/generated/assets/espeak-ng-data")
        }
    }
}

val downloadEspeakData by tasks.registering {
    val outputFile = file("build/intermediates/espeak-ng-data/espeak-ng-data.tar.bz2")
    outputs.file(outputFile)

    doLast {
        outputFile.parentFile.mkdirs()
        val url = "https://github.com/k2-fsa/sherpa-onnx/releases/download/tts-models/espeak-ng-data.tar.bz2"
        println("Downloading $url...")
        URL(url).openStream().use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}

val extractEspeakData by tasks.registering(Copy::class) {
    dependsOn(downloadEspeakData)
    val archive = downloadEspeakData.get().outputs.files.singleFile
    from(tarTree(resources.bzip2(archive)))
    into("build/generated/assets/espeak-ng-data")
}

tasks.withType<com.android.build.gradle.tasks.MergeSourceSetFolders> {
    dependsOn(extractEspeakData)
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

    implementation(libs.sherpa.onnx.android)

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
