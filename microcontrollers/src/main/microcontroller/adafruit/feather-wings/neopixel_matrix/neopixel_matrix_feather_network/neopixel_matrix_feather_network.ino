
/**
 *  Required Libraries:
 *  
 *    Use Adafruit NeoMatrix version 1.1.0
 * 
 *  Board Details:
 *  
        Here are some details on board that this code was written:

            Adafruit HUZZAH ESP8266

            https://www.adafruit.com/product/2821

            Board Manager URL

                    http://arduino.esp8266.com/stable/package_esp8266com_index.json

            CPU frequency

                    80 MHz

            Upload Speed

                    115200 baud

*/


      /*
 *  Simple HTTP get webclient test
 */

#include <ESP8266WiFi.h>

// The next two commented lines are configuration with actutal values 
// in the header (info.h) file outside of the project source code.
#include "C:\home\owner\workspace\info.h"
//const char* wifi_ssid     = "beto-landia-105";
//const char* wifi_password = "!!!CORRECT-ME!!!";

const char* host = "www.adafruit.com";

void setup() {
  Serial.begin(115200);
  delay(100);

  // We start by connecting to a WiFi network

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

int value = 0;

void loop() 
{
  delay(5000);
  ++value;

  Serial.print("connecting to ");
  Serial.println(host);
  
  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) 
  {
    Serial.println("connection failed");
    return;
  }
  
  // We now create a URI for the request
  String url = "/testwifi/index.html";
  Serial.print("Requesting URL: ");
  Serial.println(url);
  
  // This will send the request to the server
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  delay(500);
  
  // Read all the lines of the reply from server and print them to Serial
  while(client.available())
  {
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }
  
  Serial.println();
  Serial.println("closing connection");
}
