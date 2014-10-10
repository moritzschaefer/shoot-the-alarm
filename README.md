shoot-the-alarm
===============

## Description

This project contains code for a "Blend Micro" (an Arduino with Bluetooth Low Energy) and an Android App. The Android App is basically an Alarm clock whick scans waits for a Bluetooth signal to stop the alarm.
The Blend Micro board is connecting to a piezo which triggers the shot of an airgun and sends the bluetooth signal to stop the alarm of the Android phone.

## Structure

Find the code for the Arduino board in arduino/main2/main2.ino.
The file states as well the outputs/inputs as does hardware/link.txt as well.
The smartphone app is in androidApp/ API Level 18 (4.3) is required (due to BLE)


