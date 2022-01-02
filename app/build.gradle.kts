import PropertiesExt.BASE_URL
import PropertiesExt.ENABLE_AGGREGATING_TASK
import PropertiesExt.KAKAO
import PropertiesExt.getBaseUrl
import PropertiesExt.getKakaoKey
import PropertiesExt.getKeyAlias
import PropertiesExt.getKeyPassword
import PropertiesExt.getStoreFile
import PropertiesExt.getStorePassword

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    setCompileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    signingConfigs {
        create("develop") {
            storeFile = getStoreFile()
            storePassword = getStorePassword()
            keyAlias = getKeyAlias()
            keyPassword = getKeyPassword()
        }
    }

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        resValue("string", KAKAO, getKakaoKey())
        resValue("string", BASE_URL, getBaseUrl())

        manifestPlaceholders[KAKAO] = getKakaoKey()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            multiDexEnabled = true
        }

        getByName("debug") {
            signingConfig = signingConfigs.getByName("develop")
            multiDexEnabled = true
        }
    }

    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }
    lint {
        isAbortOnError = false
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }

    buildFeatures {
        dataBinding = true
    }

    flavorDimensions("mode")
    productFlavors {
        create("playStore") {
            dimension = "mode"
            resValue("string", "app_name", "비어있다")
            multiDexEnabled = true
        }
        create("dev") {
            dimension = "mode"
            resValue("string", "app_name", "beer dev")
            multiDexEnabled = true
        }
    }

    androidExtensions {
        isExperimental = true
    }
    hilt {
        enableAggregatingTask = ENABLE_AGGREGATING_TASK
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Kotlin
    implementation(Libs.KOTLIN)
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Android
    implementation(Libs.CORE_KTX)
    implementation(Libs.APP_COMPAT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.COORDINATOR_LAYOUT)
    implementation(Libs.RECYCLER_VIEW)
    implementation(Libs.MATERIAL)
    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.FRAGMENT_KTX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_COMMON)
    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI_KTX)
    implementation(Libs.BILLING)

    // Libraries
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON_CONVERTER)
    implementation(Libs.OK_HTTP)
    implementation(Libs.LOGGING_INTERCEPTOR)
    implementation(Libs.GSON)
    implementation(Libs.GLIDE)
    implementation(Libs.TIMBER)
    implementation(Libs.HILT)
    kapt(Libs.HILT_ANNOTATION)
    implementation(Libs.HILT_VIEWMODEL)
    kapt(Libs.HILT_COMPILER)
    implementation(Libs.KAKAO)
    implementation(Libs.RANGE_SEEK_BAR)

    implementation(Libs.LICENSES)

    //
    implementation(Libs.SNAP_RECYCLERVIEW)
    implementation(Libs.LOTTIE)

    testImplementation(TestLibs.JUNIT)
    androidTestImplementation(TestLibs.ANDROID_X_JUNIT)
    androidTestImplementation(TestLibs.ESPRESSO_CORE)
}