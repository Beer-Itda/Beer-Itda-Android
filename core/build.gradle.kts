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
        create("dev") {
            dimension = "mode"
        }
        create("playStore") {
            dimension = "mode"
        }
    }
}

dependencies {

    implementation(Libs.TIMBER)
    implementation(Libs.HILT)
    implementation(Libs.HILT_AAR)
    kapt(Libs.HILT_ANNOTATION)
    implementation(Libs.HILT_VIEWMODEL)
    kapt(Libs.HILT_COMPILER)

}