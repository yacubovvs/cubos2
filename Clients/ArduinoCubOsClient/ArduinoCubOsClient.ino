/*
########################################################
#                                                      #
#                      SETTINGS +                      #
#                                                      #
########################################################
*/

// CPU
//#define esp32
#define mega256

// DISPLAY
//#define display_M5Stack
#define megaUnoShield
#define megaUnoShield_2_8_inc


// CONNECTOR TYPE
#define connector_serial
//#define connector_wifi_socket_server

//#define ssid "DIR-615"
//#define password "tsdurovo6200"

char * ssid = "DIR-615";
char * password = "tsdurobo6200";
/*
########################################################
#                                                      #
#                      SETTINGS -                      #
#                                                      #
########################################################
*/
#ifdef esp32
  #include <M5Stack.h>
#endif

void setup() {
  #ifdef esp32
    M5.begin();
  #endif
  
  setup_displayDriver();
  
  #ifdef connector_serial
    Serial.begin(9600);
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

  drawLine(20,20,50,50,255,0,0);

  #ifdef connector_serial
    drawString("Test!!!", 10,10,255,0,0,1);
    Serial.println("Test!!!");
    delay(1000);
  #endif

  #ifdef connector_wifi_socket_server
    socket_server_loop();
  #endif

}
