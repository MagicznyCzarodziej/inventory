#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
# shellcheck source=java-env.sh
source "$ROOT/scripts/java-env.sh"
setup_jdk17

LOCAL_PROPS="$ROOT/android/local.properties"
if [[ -f "$LOCAL_PROPS" ]]; then
  SDK_DIR="$(grep '^sdk.dir=' "$LOCAL_PROPS" | cut -d= -f2- || true)"
  if [[ -n "${SDK_DIR:-}" ]]; then
    export ANDROID_HOME="$SDK_DIR"
  fi
fi
export ANDROID_HOME="${ANDROID_HOME:-$HOME/Library/Android/sdk}"

cd "$ROOT"
exec npx expo run:android "$@"
