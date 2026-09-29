# Smart Pantry Manager

A Java Android application that helps reduce food waste by tracking pantry ingredients and suggesting recipes that can be made using only what you already have at home.

**Module:** Mobile App Development 700

---

## Table of Contents
1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Requirements](#requirements)
4. [Getting the Code](#getting-the-code)
5. [Running in Android Studio (recommended)](#running-in-android-studio-recommended)
6. [Building from the Command Line](#building-from-the-command-line)
7. [Using the App](#using-the-app)
8. [Project Structure](#project-structure)
9. [Database](#database)
10. [Troubleshooting](#troubleshooting)

---

## Features
- **Pantry management** – add, edit and delete ingredients with a name, quantity, unit and optional expiry date (`yyyy-MM-dd`).
- **Expiry warnings** – items expiring within 7 days are highlighted in the pantry list.
- **20 pre-loaded recipes** – seeded automatically into the database on first launch.
- **Strict recipe matching** – the *Suggested Recipes* screen only lists recipes for which you have **all** required ingredients in sufficient quantity.
- **"Almost There" section** – shows recipes that are missing exactly one ingredient.
- **Smart ingredient name matching** – case-insensitive, whitespace-collapsing and plural-aware (e.g. `Tomatoes` matches `tomato`, `olive  oil` matches `Olive Oil`).
- **Recipe details** – full ingredient list and preparation steps for each recipe.
- **Settings** – toggle expiry alerts and choose Metric/Imperial units (stored with `SharedPreferences`).

## Tech Stack
| Area | Technology |
|------|------------|
| Language | Java 8 (source/target compatibility) |
| Min / Target / Compile SDK | 24 (Android 7.0) / 34 / 34 |
| Build system | Gradle 8.4 (via wrapper) + Android Gradle Plugin 8.2.2 |
| Persistence | Room 2.6.1 (SQLite) |
| UI | AndroidX AppCompat, Material Components, RecyclerView, CardView, ConstraintLayout |
| Navigation | Navigation Component + `BottomNavigationView` |
| Architecture helpers | ViewModel / LiveData |

## Requirements
Install the following before building:

| Tool | Version | Notes |
|------|---------|-------|
| **JDK** | **17** (required) | AGP 8.x will **not** run on JDK 8 or 11. Android Studio's bundled JDK (JBR 17+) works. |
| **Android Studio** | Hedgehog (2023.1.1) or newer | Needed for AGP 8.2. Older versions (e.g. Electric Eel) cannot open this project. |
| **Android SDK Platform** | API 34 | Install via *SDK Manager → SDK Platforms → Android 14.0 (API 34)*. |
| **Android SDK Build-Tools** | 34.x | Install via *SDK Manager → SDK Tools*. Gradle may download it automatically. |
| **Device / Emulator** | Android 7.0 (API 24) or newer | |

> You do **not** need to install Gradle yourself. The project includes the Gradle wrapper (`gradlew` / `gradlew.bat`), which downloads the correct Gradle version on first run.

## Getting the Code
```bash
git clone <repo-url>
cd Mobile_App_Development_700/SmartPantryManager
```
The Android project lives in the `SmartPantryManager` folder — open/build **that** folder, not the repository root.

## Running in Android Studio (recommended)
1. **Open the project:** *File → Open…* and select the `SmartPantryManager` folder.
2. **Set the Gradle JDK to 17:**
   *File → Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK*
   (on macOS: *Android Studio → Settings…*) and choose a JDK 17 (e.g. *jbr-17* or your installed `jdk-17`).
3. **SDK location:** Android Studio creates `local.properties` automatically with your SDK path. If it doesn't, create it yourself (see [Building from the Command Line](#building-from-the-command-line)).
4. **Sync:** click *Sync Project with Gradle Files* (elephant icon) and wait for it to finish. The first sync downloads Gradle and all dependencies, so it can take several minutes.
5. **Install missing SDK packages** if Android Studio prompts you (e.g. "Install missing platform(s) and sync project").
6. **Create/start a device:** *Tools → Device Manager → Create Device* (any phone image with API 24+), or connect a physical phone with USB debugging enabled.
7. **Run:** select the `app` configuration and the device, then press **Run ▶** (`Shift+F10`).

## Building from the Command Line
1. **Point Gradle at JDK 17.** Either set `JAVA_HOME` for the session:
   ```powershell
   # Windows (PowerShell)
   $env:JAVA_HOME = "C:\Program Files\Java\jdk-17.0.1"
   ```
   ```bash
   # macOS / Linux
   export JAVA_HOME=/path/to/jdk-17
   ```
   Check with `java -version` — it must report 17 or newer.

2. **Tell Gradle where the Android SDK is.** Create `local.properties` in the `SmartPantryManager` folder (it is git-ignored, each developer has their own):
   ```properties
   # Windows (escape backslashes and the colon)
   sdk.dir=C\:\\Users\\<you>\\AppData\\Local\\Android\\Sdk
   # macOS
   # sdk.dir=/Users/<you>/Library/Android/sdk
   # Linux
   # sdk.dir=/home/<you>/Android/Sdk
   ```
   Alternatively set the `ANDROID_HOME` environment variable.

3. **Build the debug APK** (run all Gradle commands from inside the `SmartPantryManager` folder):
   ```bash
   ./gradlew assembleDebug        # macOS / Linux
   gradlew.bat assembleDebug      # Windows
   ```
   The APK is written to `SmartPantryManager/app/build/outputs/apk/debug/app-debug.apk`.

4. **Install on a running emulator / connected device:**
   ```bash
   ./gradlew installDebug
   # or
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

Other useful tasks:
| Command | Purpose |
|---------|---------|
| `gradlew clean` | Delete build outputs |
| `gradlew test` | Run local unit tests |
| `gradlew connectedAndroidTest` | Run instrumented tests on a device/emulator |
| `gradlew lint` | Run Android Lint |

## Using the App
The app has three tabs in the bottom navigation bar:

1. **Pantry**
   - Tap the **+** button to add an ingredient (name, quantity, unit, optional expiry date in `yyyy-MM-dd` format).
   - Tap an item to edit it; **long-press** an item to delete it (with a confirmation dialog).
   - Items expiring within the next 7 days are highlighted.
2. **Recipes (Suggested)**
   - Lists recipes you can make right now using only your pantry contents.
   - The **Almost There** section lists recipes missing just one ingredient.
   - Tap a recipe to view its ingredients and preparation steps.
3. **Settings**
   - Enable/disable expiry alerts.
   - Choose Metric or Imperial units.

**Quick test:** add `Eggs – 2`, `Butter – 1`, `Milk – 2` and `Salt – 1` to your pantry, then open the Recipes tab — *Scrambled Eggs* and *Boiled Eggs* will be suggested. Matching compares ingredient names and quantities only (units are not converted). All recipe requirements are defined in `DatabaseSeeder.java`.

The 20 recipes are seeded on first launch only. To reset the app data, uninstall the app or use *Settings → Apps → Smart Pantry Manager → Storage → Clear data* on the device.

## Project Structure
```
Mobile_App_Development_700/          # Repository root
├── README.md                        # This file
├── .gitignore
└── SmartPantryManager/              # Android project — open this folder in Android Studio
    ├── build.gradle                 # Root build file (AGP plugin version)
    ├── settings.gradle              # Repositories and included modules
    ├── gradle.properties            # Gradle/AndroidX flags
    ├── gradlew, gradlew.bat         # Gradle wrapper scripts
    ├── gradle/wrapper/              # Wrapper jar + Gradle version (8.4)
    └── app/
        ├── build.gradle             # App module config & dependencies
        └── src/main/
            ├── AndroidManifest.xml
            ├── java/com/smartpantry/manager/
            │   ├── MainActivity.java            # Hosts bottom navigation, triggers DB seeding
            │   ├── SmartPantryApp.java          # Application class
            │   ├── database/
            │   │   ├── AppDatabase.java         # Room database singleton
            │   │   ├── DatabaseSeeder.java      # Inserts the 20 recipes on first run
            │   │   ├── dao/                     # IngredientDao, RecipeDao, RecipeIngredientDao
            │   │   └── entity/                  # Ingredient, Recipe, RecipeIngredient
            │   ├── model/RecipeWithIngredients.java
            │   ├── ui/
            │   │   ├── pantry/                  # PantryFragment, PantryAdapter, AddEditIngredientActivity
            │   │   ├── recipes/                 # SuggestedRecipesFragment, RecipesAdapter, RecipeDetailActivity
            │   │   └── settings/SettingsFragment.java
            │   └── util/IngredientMatcher.java  # Name normalisation + recipe matching logic
            └── res/                             # Layouts, navigation graph, menus, strings, themes, icons
```

## Database
**SQLite via the Room Persistence Library** was chosen because:
- It works fully offline with no external services.
- It is covered in the module's persistent data chapter.
- It provides compile-time checked, type-safe queries through Room's DAO pattern.
- Data persists reliably across app sessions on-device.

Tables:
| Table | Purpose |
|-------|---------|
| `Ingredient` | Pantry items (name, quantity, unit, expiry date) |
| `Recipe` | Recipe name and preparation steps |
| `RecipeIngredient` | Ingredients required by each recipe (foreign key → `Recipe`) |

## Troubleshooting

**`... has been compiled by a more recent version of the Java Runtime (class file version 61.0), this version of the Java Runtime only recognizes class file versions up to 55.0`**
Gradle is running on JDK 11 (55) but AGP 8.x needs JDK 17 (61). Switch the Gradle JDK to 17 — see step 2 of [Running in Android Studio](#running-in-android-studio-recommended) or step 1 of [Building from the Command Line](#building-from-the-command-line).

**`SDK location not found`**
Create `local.properties` with `sdk.dir=...` or set `ANDROID_HOME` (see above).

**`Failed to find target with hash string 'android-34'` / missing build tools**
Open *SDK Manager* and install *Android 14.0 (API 34)* and *Build-Tools 34*, then re-sync.

**`This version of the Android Support plugin ... cannot open this project` / AGP version not supported**
Update Android Studio to Hedgehog (2023.1.1) or newer.

**First build is very slow**
The first build downloads Gradle 8.4 and all dependencies. Subsequent builds are much faster.

**Recipes list is empty**
Your pantry must contain *every* ingredient of a recipe in sufficient quantity. Check the *Almost There* section, or add more ingredients.
