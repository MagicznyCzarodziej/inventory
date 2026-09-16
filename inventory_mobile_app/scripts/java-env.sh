# Sourced by Android build scripts. Requires bash.

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

setup_jdk17() {
  if ! JAVA_HOME="$(find_jdk17_home)"; then
    echo "Android build needs JDK 17 (Gradle 8.6 does not support Java 23+)." >&2
    echo "Install: brew install --cask temurin@17" >&2
    return 1
  fi
  export JAVA_HOME
  export PATH="$JAVA_HOME/bin:$PATH"
}
