# Inventory (mobile)

Expo / React Native app for inventory management.

## Prerequisites

- Node.js (LTS recommended)
- [Android Studio](https://developer.android.com/studio) with Android SDK (for Android)
- **JDK 17** for Android builds (Gradle 8.6 does not support Java 23+)

  ```bash
  brew install --cask temurin@17
  ```

  You do not need to set `JAVA_HOME` globally. The Android npm script picks JDK 17 automatically (see below).

## Setup

```bash
npm install
```

### Android SDK path (first time)

Copy the example file and set your SDK location:

```bash
cp android/local.properties.example android/local.properties
```

Edit `android/local.properties` — on macOS the SDK is usually:

```properties
sdk.dir=/Users/YOUR_USERNAME/Library/Android/sdk
```

`local.properties` is gitignored and stays on your machine only.

## Development

```bash
npm start          # Expo dev server
npm run android    # build & run on emulator/device (uses scripts/run-android.sh)
npm run ios        # iOS (macOS + Xcode)
npm run web
```

`npm run android` runs `scripts/run-android.sh`, which uses JDK 17 and `ANDROID_HOME` from `android/local.properties` only for that command. Your system default Java is unchanged.

## Release build (Android)

```bash
npx expo prebuild
cd android && ./gradlew assembleRelease
```

For a direct Gradle build, use JDK 17 (same as above), e.g.:

```bash
export JAVA_HOME="/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home"
cd android && ./gradlew assembleRelease
```

## Formatting

```bash
npm run format
npm run format:check
```
