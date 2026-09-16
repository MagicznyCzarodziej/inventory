#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
# shellcheck source=java-env.sh
source "$ROOT/scripts/java-env.sh"
setup_jdk17

cd "$ROOT"
npx expo prebuild
cd android
./gradlew assembleRelease

echo "APK: $ROOT/android/app/build/outputs/apk/release/app-release.apk"
