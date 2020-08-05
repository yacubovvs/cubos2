/*
########################################################
#                                                      #
#                      SETTINGS +                      #
#                                                      #
########################################################
*/

// DISPLAY
#define display_M5Stack

// CONNECTOR TYPE
//#define connector_serial
#define connector_wifi_socket_server

//#define ssid "DIR-615"
//#define password "tsdurovo6200"

char * ssid = "len12-75";
char * password = "doc12345";
/*
########################################################
#                                                      #
#                      SETTINGS -                      #
#                                                      #
########################################################
*/
#include <M5Stack.h>

void setup() {
  M5.begin();
  setup_displayDriver();
  #ifdef connector_serial
    Serial.begin(115200);
  #endif

  #ifdef connector_wifi_socket_server
    socket_server_setup();
  #endif

}

void loop() {
  // put your main code here, to run repeatedly:
  //drawRect(10, 10, 40,  40,  255,     0,     0,  true);
  //drawRect(40, 10, 70,  40,    0,   255,     0,  true);
  //drawRect(70, 10, 100, 40,    0,     0,   255,  true);

  #ifdef connector_serial
    Serial.println("Test " + millis());
  #endif

  #ifdef connector_wifi_socket_server
    socket_server_loop();
  #endif

}
