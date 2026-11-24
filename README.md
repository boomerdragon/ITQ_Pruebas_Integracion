# ITQ - Pruebas de Integración y UI en Android

Proyecto de demostración y guía práctica para la implementación de **Pruebas Unitarias** y **Pruebas Instrumentadas de Interfaz de Usuario (UI con Espresso)** en aplicaciones Android siguiendo el patrón de arquitectura **MVVM (Model-View-ViewModel)**.

---

## 🚀 Descripción del Proyecto

Esta aplicación permite realizar la simulación y validación de módulos de software. El usuario ingresa el nombre de un módulo (por ejemplo, "Modulo Firebase"), y la aplicación procesa la entrada a través de un repositorio, calculando un puntaje de cobertura y un estado de validación que se refleja reactivamente en la interfaz de usuario.

El objetivo principal de este proyecto es servir como **referencia técnica y checklist de revisión de código (Code Review)** para estructurar y ejecutar pruebas automatizadas en Android.

---

## 🛠️ Arquitectura y Tecnologías

- **Lenguaje**: Java 8+
- **Patrón de Diseño**: MVVM (Model-View-ViewModel) + Repository Pattern
- **Componentes de Android Jetpack**:
  - `ViewModel`: Gestión del estado de UI resistente a cambios de configuración.
  - `LiveData`: Observación reactiva de datos en tiempo real.
  - `RecyclerView` & `CardView`: Visualización de la lista de módulos validados.
- **Frameworks de Prueba**:
  - **JUnit 4**: Framework base para pruebas unitarias e instrumentadas.
  - **Espresso (`androidx.test.espresso`)**: Automatización de interacciones UI en emuladores o dispositivos físicos.
  - **AndroidX Test Rules & Runner**: Orquestación y ciclo de vida del entorno de pruebas Android.
  - **AndroidX Arch Core Testing**: Manejo síncrono de tareas de segundo plano y LiveData (`InstantTaskExecutorRule`).

---

## 📁 Estructura del Proyecto

```text
ITQPruebasdeIntegracion/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/                        # Código fuente de producción
│       │   └── java/com/itq/pruebasintegracionuiapp/
│       │       ├── CalculadoraModuloDto.java     # Modelo de datos DTO
│       │       ├── MainActivity.java             # Capa de Vista (Activity)
│       │       ├── ModuloTestAdapter.java        # Adaptador para RecyclerView
│       │       ├── ValidacionRepository.java     # Lógica de negocio y repositorio
│       │       └── ValidacionViewModel.java      # ViewModel y LiveData
│       │
│       ├── test/                        # Pruebas Unitarias Locales (JVM)
│       │   └── java/com/itq/pruebasintegracionuiapp/
│       │       └── ValidacionViewModelTest.java  # Pruebas de Repositorio y LiveData
│       │
│       └── androidTest/                 # Pruebas Instrumentadas (Dispositivo/AVD)
│           └── java/com/itq/pruebasintegracionuiapp/
│               └── MainActivityUITest.java       # Pruebas de UI con Espresso
└── README.md
```

---

## 📋 Puntos de Revisión de Código: Guía de Estructuración de Pruebas

Al revisar o implementar pruebas en Android, asegúrate de cumplir con los siguientes puntos de control:

### 1. Ubicación y Separación de Entornos
- [x] **Pruebas Unitarias Locales (`src/test/java`)**: Las pruebas que evalúan clases puras de Java/Kotlin, repositorios o ViewModels sin depender del Framework Android deben estar en `src/test`. Se ejecutan en la Máquina Virtual de Java (JVM) local de forma ultrarrápida.
- [x] **Pruebas Instrumentadas (`src/androidTest/java`)**: Las pruebas que requieren el marco de trabajo de Android, pantallas (`Activity`) o componentes del sistema deben colocarse estrictamente en `src/androidTest`. No deben residir en `src/main`.

### 2. Estructura AAA (Arrange - Act - Assert)
Cada método de prueba (`@Test`) debe dividirse claramente en 3 fases:
- [x] **Arrange (Preparar)**: Configurar los datos de prueba, instancias e insumos iniciales.
- [x] **Act (Ejecutar)**: Invocar la función, método o acción específica que se desea evaluar.
- [x] **Assert (Verificar)**: Comprobar que los resultados obtenidos coincidan exactamente con lo esperado (`assertEquals`, `assertTrue`, `assertNotNull`).

### 3. Sincronización de LiveData en Pruebas Unitarias
- [x] **Uso de `InstantTaskExecutorRule`**: En pruebas unitarias JVM de `ViewModel` o `LiveData`, incluye siempre la regla `@Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();`. Esto fuerza a que las operaciones asíncronas de LiveData se ejecuten de manera instantánea y síncrona en el mismo hilo.

### 4. Flujo de Interacción en Pruebas de UI con Espresso
En las pruebas de UI, la sintaxis debe respetar la secuencia en cadena de Espresso:
- [x] **Buscar (`ViewMatchers`)**: Identificar el componente en pantalla mediante `onView(withId(R.id.mi_id))`.
- [x] **Actuar (`ViewActions`)**: Ejecutar la interacción del usuario mediante `.perform(...)` (ej. `click()`, `closeSoftKeyboard()`).
- [x] **Verificar (`ViewAssertions`)**: Confirmar el resultado visual usando `.check(matches(...))`.

### 5. Robustez en la Entrada de Texto y Configuración Regional
- [x] **Uso de `replaceText()` sobre `typeText()`**: Para campos de texto (`EditText`), se recomienda preferir `replaceText("texto")` sobre `typeText()`. Esto evita que el teclado virtual del emulador/dispositivo aplique autocorrección o sugerencias del diccionario que alteren la cadena ingresada.
- [x] **Tolerancia a Variantes Regionales**: Al evaluar textos resultantes en la interfaz, utiliza combinaciones flexibles como `CoreMatchers.anyOf(containsString("MODULO"), containsString("MÓDULO"))` para prevenir fallos causados por tildes o variaciones de idioma en distintas imágenes de AVD.

---

## 🧪 Ejecución de Pruebas

### Desde la Consola de Comandos (Gradle CLI)

1. **Ejecutar Pruebas Unitarias (JVM)**:
   ```bash
   ./gradlew testDebugUnitTest
   ```

2. **Ejecutar Pruebas Instrumentadas de UI (Espresso)**:
   *(Asegúrate de tener un emulador AVD en ejecución o un dispositivo físico conectado con depuración USB)*
   ```bash
   ./gradlew connectedDebugAndroidTest
   ```

### Desde Android Studio

1. Abre el panel **Project** lateral en vista *Android*.
2. Haz clic derecho sobre el paquete `com.itq.pruebasintegracionuiapp` dentro de `app/src/test/java` y selecciona **Run 'Tests in...'** para pruebas unitarias.
3. Haz clic derecho sobre `MainActivityUITest` dentro de `app/src/androidTest/java` y selecciona **Run 'MainActivityUITest'** para la prueba de interfaz de usuario.

---

## 📜 Licencia

Este proyecto se distribuye bajo la licencia MIT. Libre para uso académico y profesional.
