# Run in development mode
npm run android

# Build app
npx expo prebuild
cd android && ./gradlew assembleRelease