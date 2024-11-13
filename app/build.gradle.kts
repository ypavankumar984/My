plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Apply Google services plugin for Firebase
}

android {
    namespace = "com.example.my"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.my"
        minSdk = 24
        targetSdk = 34
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
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // AndroidX dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:33.5.1")) // Firebase BoM (Bill of Materials)
    implementation("com.google.firebase:firebase-analytics") // Firebase Analytics
    implementation("com.google.firebase:firebase-auth") // Firebase Authentication
    implementation("com.google.firebase:firebase-firestore") // Firebase Firestore

    // RecyclerView for lists
    implementation("androidx.recyclerview:recyclerview:1.2.1")


    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.google.android.gms:play-services-maps:18.0.2")
    implementation ("com.google.android.gms:play-services-location:18.0.0")
}

// Apply the Google services plugin (necessary for Firebase)
apply(plugin = "com.google.gms.google-services")
