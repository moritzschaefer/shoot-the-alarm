#include <SPI.h>
#include <boards.h>
#include <RBL_nRF8001.h>
#include <RBL_services.h>

//Input PIN
int inputPiezo = A0;
int outputLed = 13;
int val = 0;
int ledState = 0;
int threshold = 80;
int sensorReading = 0;

void setup() {
  ble_begin();
  Serial.begin(38400);
  pinMode(outputLed, OUTPUT);
  digitalWrite(outputLed, LOW);
}

void loop() {
  // read the sensor and store it in the variable sensorReading:
  sensorReading = analogRead(inputPiezo);

  // if the sensor reading is greater than the threshold:
  if (sensorReading >= threshold) {
    digitalWrite(outputLed, HIGH);
    sendBluetooth();
    delay(1000);
    ble_write('0');

    digitalWrite(outputLed, LOW);
    // send the string "Knock!" back to the computer, followed by newline
    //Serial.println("Knock!");
  }
  ble_do_events();

  //delay(100);  // delay to avoid overloading the serial port buffer
}

void sendBluetooth() {

}
