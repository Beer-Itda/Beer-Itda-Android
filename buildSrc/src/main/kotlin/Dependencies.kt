object Versions {
    // Kotlin
    const val GRADLE = "4.1.2"
    const val KOTLIN = "1.4.10"
    const val COROUTINES = "1.3.7"

    // Android Framework
    const val APP_COMPAT = "1.3.0-beta01"
    const val CONSTRAINT_LAYOUT = "2.0.0-rc1"
    const val COORDINATOR_LAYOUT = "1.1.0"
    const val RECYCLER_VIEW = "1.1.0"
    const val MATERIAL = "1.2.0"
    const val BILLING = "3.0.0"

    const val CORE_KTX = "1.3.0"
    const val ACTIVITY_KTX = "1.1.0"
    const val FRAGMENT_KTX = "1.2.4"
    const val LIFECYCLE = "2.2.0"
    const val NAVIGATION_FRAGMENT = "2.3.0"
    const val NAVIGATION_UI_KTX = "2.3.0"
    const val NAVIGATION_SAFE_ARGS = "2.3.0"
    const val GOOGLE_SERVIER = "4.3.4"

    // ThirdParty Libraries
    const val RETROFIT = "2.8.2"
    const val GLIDE = "4.11.0"
    const val OK_HTTP = "4.7.2"
    const val GSON = "2.8.6"
    const val HILT = "2.28-alpha"
    const val HILT_AAR = "2.28.3"
    const val HILT_VIEWMODEL = "1.0.0-alpha01"
    const val TIMBER = "4.7.1"
    const val KAKAO = "2.0.3"
    const val RANGE_SEEK_BAR = "3.0.0"
    const val GOOGLE_BOM = "26.3.0"
    const val GOOGLE_CRASHLYTICS = "2.3.0"
    const val FIREBASE_ANALYTICS = "17.5.0"
    const val FIREBASE_CRASHLYTICS = "17.2.1"
    const val LICENSES = "0.10.2"
    const val OSS_LICENSES = "17.0.0"

    //
    const val SNAP_RECYCLERVIEW = "1.5.1"
}

object Libs {
    // Kotlin
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    // Android Framework
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLER_VIEW}"
    const val COORDINATOR_LAYOUT =
        "androidx.coordinatorlayout:coordinatorlayout:${Versions.COORDINATOR_LAYOUT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVE_DATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_FRAGMENT}"
    const val NAVIGATION_UI_KTX =
        "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_UI_KTX}"
    const val BILLING = "com.android.billingclient:billing-ktx:${Versions.BILLING}"

    // Third Party Libraries
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${Versions.OK_HTTP}"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OK_HTTP}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_AAR = "com.google.dagger:dagger-lint-aar:${Versions.HILT_AAR}"
    const val HILT_VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.HILT_VIEWMODEL}"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:${Versions.HILT_VIEWMODEL}"
    const val HILT_ANNOTATION = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"

    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val KAKAO = "com.kakao.sdk:v2-user:${Versions.KAKAO}"
    const val RANGE_SEEK_BAR = "com.github.Jay-Goo:RangeSeekBar:${Versions.RANGE_SEEK_BAR}"

    const val FIREBASE = "com.google.firebase:firebase-bom:${Versions.GOOGLE_BOM}"
    const val FIREBASE_REMOTE_CONFIG = "com.google.firebase:firebase-config"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"

    const val LICENSES =
        "com.google.android.gms:play-services-oss-licenses:${Versions.OSS_LICENSES}"

    //
    const val SNAP_RECYCLERVIEW =
        "com.yarolegovich:discrete-scrollview:${Versions.SNAP_RECYCLERVIEW}"

}

object TestLibs {
    const val JUNIT = "junit:junit:4.12"
    const val ANDROID_X_JUNIT = "androidx.test.ext:junit:1.1.1"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.2.0"
}