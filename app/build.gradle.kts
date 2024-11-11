plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Add this line to apply the Google services plugin
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
    // Existing dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase BoM (Bill of Materials) to manage Firebase dependencies automatically
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // Firebase Analytics (replace with the specific Firebase SDKs you need)
    implementation("com.google.firebase:firebase-analytics")

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    implementation("com.google.firebase:firebase-firestore")

    // Firebase Firestore (optional, depending on your needs)
    // implementation("com.google.firebase:firebase-firestore")
}
