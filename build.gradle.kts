// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:4.1.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_SAFE_ARGS}")
        classpath ("com.google.gms:google-services:${Versions.GOOGLE_SERVIER}")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:${Versions.GOOGLE_CRASHLYTICS}")
        classpath ("com.google.android.gms:oss-licenses-plugin:${Versions.LICENSES}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
