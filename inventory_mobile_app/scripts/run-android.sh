#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"

find_jdk17_home() {
  local candidate home
  for candidate in \
    "/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home" \
    "$HOME/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home" \
    "$HOME/Library/Java/JavaVirtualMachines"/temurin-17.*/Contents/Home; do
    if [[ -x "$candidate/bin/java" ]]; then
      echo "$candidate"
      return 0
    fi
  done

  while IFS= read -r home; do
    if "$home/bin/java" -version 2>&1 | grep -qE 'version "17(\.|")'; then
      echo "$home"
      return 0
    fi
  done < <(/usr/libexec/java_home -V 2>&1 | sed -n 's/.*\(\/.*\/Contents\/Home\)$/\1/p')

  return 1
}

if ! JAVA_HOME="$(find_jdk17_home)"; then
  echo "Android build needs JDK 17 (Gradle 8.6 does not support Java 23+)." >&2
  echo "Install: brew install --cask temurin@17" >&2
  exit 1
fi
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

LOCAL_PROPS="$ROOT/android/local.properties"
if [[ -f "$LOCAL_PROPS" ]]; then
  SDK_DIR="$(grep '^sdk.dir=' "$LOCAL_PROPS" | cut -d= -f2- || true)"
  if [[ -n "${SDK_DIR:-}" ]]; then
    export ANDROID_HOME="$SDK_DIR"
  fi
fi
export ANDROID_HOME="${ANDROID_HOME:-$HOME/Library/Android/sdk}"

cd "$ROOT"
exec expo run:android "$@"
