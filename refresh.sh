#!/usr/bin/env bash

set -e

# ./gradlew assembleDebug
./gradlew installDebug
# ./gradlew clean installDebug
adb shell am force-stop com.example.myapplication
adb shell am start -n com.example.myapplication/.MainActivity
