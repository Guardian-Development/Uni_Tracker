# Uni_Tracker
An application for university students to track their current grade and calculate what grade they need to reach a desired final grade.

## Run Emulator with Internet Access
- cd /Users/username/Library/Android/sdk/tools (Mac)
- ./emulator -avd Pixel_XL_API_26 -dns-server 8.8.8.8

## Running Unit Tests
To be able to run unit tests within android studio you must set the VM options to be '-noverify'.
This is needed as we require to mock Firebase classes.
- ./gradlew clean build test
- Android Studio -> Edit Configurations -> VM Options -> '-noverify'
