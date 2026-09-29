# Smart Pantry Manager

A Java Android application that helps reduce food waste by tracking pantry ingredients and suggesting recipes that can be made using only what you already have at home.

## Features
- Add, edit, and delete pantry ingredients (name, quantity, unit, optional expiry date)
- Browse 20 pre-loaded recipes
- Strict recipe matching: only suggests recipes where you have ALL required ingredients in sufficient quantity
- Settings for expiry alerts and unit preferences

## Database
**SQLite via Room Persistence Library** was chosen because:
- It works fully offline with no external dependencies
- It is covered in the module's persistent data chapter
- It provides type-safe queries through Room's DAO pattern
- Data persists reliably across app sessions on-device

## Setup & Run Instructions
1. Clone this repository: `git clone <repo-url>`
2. Open the project in Android Studio (Electric Eel or newer)
3. Wait for Gradle sync to complete
4. Run on an emulator (API 24+) or physical Android device
5. The app seeds 20 recipes automatically on first launch

## Tech Stack
- Java, Android SDK 34
- Room (SQLite) for persistence
- RecyclerView with custom adapters
- Navigation Component with BottomNavigationView
- Material Design 3 components

## Module: Mobile App Development 700
