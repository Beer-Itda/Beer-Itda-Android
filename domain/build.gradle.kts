import PropertiesExt.BASE_URL
import PropertiesExt.getBaseUrl

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    setCompileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        resValue("string", BASE_URL, getBaseUrl())
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }
    flavorDimensions("mode")
    productFlavors {
        create("playStore") {
            dimension = "mode"
        }
        create("dev") {
            dimension = "mode"
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)
    implementation(Libs.GSON)
    implementation(Libs.HILT)
    kapt(Libs.HILT_ANNOTATION)
    implementation(Libs.HILT_VIEWMODEL)
    kapt(Libs.HILT_COMPILER)

//    implementation("androidx.core:core-ktx:1.3.2")
//    implementation("androidx.appcompat:appcompat:1.3.1")
//    implementation("com.google.android.material:material:1.4.0")

    testImplementation(TestLibs.JUNIT)
    androidTestImplementation(TestLibs.ANDROID_X_JUNIT)
}