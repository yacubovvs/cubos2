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

/*
########################################################
#                                                      #
#                      PROTOCOL +                      #
#                                                      #
########################################################
*/

#define _0_MODE_OPTION                                                -128 + 128
#define _0_0_MODE_SELECT                                              0x00 + 128
#define _0_1_OPTIONS_MODE                                             0x01 + 128
#define _0_2_DRAW_MODE                                                0x02 + 128
#define _0_3_EVENT_MODE                                               0x03 + 128
#define _FINISH_BYTES                                                 -127 + 128
#define _1_SET_OPTION                                                 0x01 + 128
#define _2_GET_OPTION                                                 0x02 + 128
#define _1_2_GO_SLEEP                                                 0x02 + 128
#define _1_3_WAKE_UP                                                  0x03 + 128
#define _1_4_SERVER_OPTION                                            0x04 + 128
#define _1_6_OPTIONS_SCREEN                                           0x06 + 128
#define _1_4_1_START_SERVER                                           0x01 + 128
#define _1_4_2_STOP_SERVER                                            0x02 + 128
#define _1_6_1_OPTIONS_SETTINGS_WIDTH                                 0x01 + 128
#define _1_6_2_OPTIONS_SETTINGS_HEIGHT                                0x02 + 128
#define _1_6_3_OPTIONS_SETTINGS_COLORS                                0x03 + 128
#define _1_6_4_UPDATE_SCREEN                                          0x04 + 128
#define _1_6_3_1_SCREEN_COLORS_1BIT_BLACK_WHITE                       0x01 + 128
#define _1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS                          0x02 + 128
#define _1_6_3_3_SCREEN_COLORS_2BIT_4_COLORS                          0x03 + 128
#define _1_6_3_4_SCREEN_COLORS_4BIT_16_COLORS                         0x04 + 128
#define _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS                        0x05 + 128
#define _1_6_3_6_SCREEN_COLORS_16BIT__5_6_5                           0x06 + 128
#define _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8                           0x07 + 128
#define _1_6_3_8_SCREEN_COLORS_8BIT__GRAY                             0x08 + 128
#define _1_6_3_9_SCREEN_COLORS_4BIT__GRAY                             0x09 + 128
#define _1_6_3_A_SCREEN_COLORS_2BIT__GRAY                             0x0A + 128
#define _1_DRAW_PIXEL                                                 0x01 + 128
#define _2_DRAW_PIXEL_COORDINATES_LESS_255                            0x02 + 128
#define _3_UPDATE_SCREEN                                              0x03 + 128
#define _4_DRAW_RECTS_ARRAY                                           0x04 + 128
#define _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255                    0x05 + 128
#define _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255                    0x06 + 128
#define _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255    0x07 + 128
#define _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255    0x08 + 128
#define _1_1_EVENT_TOUCH_TAP                                          0x01 + 128
#define _1_2_EVENT_TOUCH_UP                                           0x02 + 128
#define _1_3_EVENT_TOUCH_DOWN                                         0x03 + 128
#define _1_4_EVENT_TOUCH_MOVE                                         0x04 + 128
#define _1_5_EVENT_TOUCH_MOVE_FINISHED                                0x05 + 128
#define _1_6_EVENT_TOUCH_ZOOM_IN                                      0x06 + 128
#define _1_7_EVENT_TOUCH_ZOOM_OUT                                     0x07 + 128
#define _1_8_EVENT_TOUCH_ZOOM_FINISHED                                0x08 + 128

/*
########################################################
#                                                      #
#                      PROTOCOL -                      #
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
  //drawLine(20,20,50,50,255,255,255);

  byte message[] = {
    _0_MODE_OPTION,
    _0_2_DRAW_MODE,
    _2_DRAW_PIXEL_COORDINATES_LESS_255,
    5,
    5,
    255,
    0,
    0
  };

  
  decodeCommands(message, 8);

  #ifdef connector_serial
    //drawString("Test!!!", 10,10,255,255,255,1);
    //Serial.println("Test!!!");
    delay(3000);
  #endif

  #ifdef connector_wifi_socket_server
    socket_server_loop();
  #endif

}
