plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.itq.pruebasintegracionuiapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.itq.pruebasintegracionuiapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Runner de pruebas instrumentalizadas de AndroidX
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Importación del Firebase BoM (v32.8.0)
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))

    // Componentes de la Interfaz Gráfica (AndroidX & Material Design)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Jetpack Lifecycle (MVVM: ViewModel & LiveData)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")

    // --- DEPENDENCIAS DE PRUEBAS UNITARIAS LOCALES (JVM / src/test) ---
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // --- DEPENDENCIAS DE PRUEBAS INSTRUMENTALES Y UI (AVD / src/androidTest) ---
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
}