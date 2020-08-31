/*
########################################################
#                                                      #
#                      SETTINGS +                      #
#                                                      #
########################################################
*/

// CPU
#define esp32
//#define mega256

// DISPLAY
#define display_M5Stack
//#define megaUnoShield
//#define megaUnoShield_2_8_inc


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

#define L_0_MODE_OPTION                                                (byte)(-128 + 128)
#define L_0_0_MODE_SELECT                                              (byte)(0x00 + 128)
#define L_0_1_OPTIONS_MODE                                             (byte)(0x01 + 128)
#define L_0_2_DRAW_MODE                                                (byte)(0x02 + 128)
#define L_0_3_EVENT_MODE                                               (byte)(0x03 + 128)
#define L_FINISH_BYTES                                                 (byte)(-127 + 128)
#define L_1_SET_OPTION                                                 (byte)(0x01 + 128)
#define L_2_GET_OPTION                                                 (byte)(0x02 + 128)
#define L_1_2_GO_SLEEP                                                 (byte)(0x02 + 128)
#define L_1_3_WAKE_UP                                                  (byte)(0x03 + 128)
#define L_1_4_SERVER_OPTION                                            (byte)(0x04 + 128)
#define L_1_6_OPTIONS_SCREEN                                           (byte)(0x06 + 128)
#define L_1_4_1_START_SERVER                                           (byte)(0x01 + 128)
#define L_1_4_2_STOP_SERVER                                            (byte)(0x02 + 128)
#define L_1_6_1_OPTIONS_SETTINGS_WIDTH                                 (byte)(0x01 + 128)
#define L_1_6_2_OPTIONS_SETTINGS_HEIGHT                                (byte)(0x02 + 128)
#define L_1_6_3_OPTIONS_SETTINGS_COLORS                                (byte)(0x03 + 128)
#define L_1_6_4_UPDATE_SCREEN                                          (byte)(0x04 + 128)
#define L_1_6_3_1_SCREEN_COLORS_1BIT_BLACK_WHITE                       (byte)(0x01 + 128)
#define L_1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS                          (byte)(0x02 + 128)
#define L_1_6_3_3_SCREEN_COLORS_2BIT_4_COLORS                          (byte)(0x03 + 128)
#define L_1_6_3_4_SCREEN_COLORS_4BIT_16_COLORS                         (byte)(0x04 + 128)
#define L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS                        (byte)(0x05 + 128)
#define L_1_6_3_6_SCREEN_COLORS_16BIT__5_6_5                           (byte)(0x06 + 128)
#define L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8                           (byte)(0x07 + 128)
#define L_1_6_3_8_SCREEN_COLORS_8BIT__GRAY                             (byte)(0x08 + 128)
#define L_1_6_3_9_SCREEN_COLORS_4BIT__GRAY                             (byte)(0x09 + 128)
#define L_1_6_3_A_SCREEN_COLORS_2BIT__GRAY                             (byte)(0x0A + 128)
#define L_1_DRAW_PIXEL                                                 (byte)(0x01 + 128)
#define L_2_DRAW_PIXEL_COORDINATES_LESS_255                            (byte)(0x02 + 128)
#define L_3_UPDATE_SCREEN                                              (byte)(0x03 + 128)
#define L_4_DRAW_RECTS_ARRAY                                           (byte)(0x04 + 128)
#define L_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255                    (byte)(0x05 + 128)
#define L_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255                    (byte)(0x06 + 128)
#define L_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255    (byte)(0x07 + 128)
#define L_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255    (byte)(0x08 + 128)
#define L_1_1_EVENT_TOUCH_TAP                                          (byte)(0x01 + 128)
#define L_1_2_EVENT_TOUCH_UP                                           (byte)(0x02 + 128)
#define L_1_3_EVENT_TOUCH_DOWN                                         (byte)(0x03 + 128)
#define L_1_4_EVENT_TOUCH_MOVE                                         (byte)(0x04 + 128)
#define L_1_5_EVENT_TOUCH_MOVE_FINISHED                                (byte)(0x05 + 128)
#define L_1_6_EVENT_TOUCH_ZOOM_IN                                      (byte)(0x06 + 128)
#define L_1_7_EVENT_TOUCH_ZOOM_OUT                                     (byte)(0x07 + 128)
#define L_1_8_EVENT_TOUCH_ZOOM_FINISHED                                (byte)(0x08 + 128)

/*
#define L_0_MODE_OPTION                                                -128 + 128
#define L_0_0_MODE_SELECT                                              0x00 + 128
#define L_0_1_OPTIONS_MODE                                             0x01 + 128
#define L_0_2_DRAW_MODE                                                0x02 + 128
#define L_0_3_EVENT_MODE                                               0x03 + 128
#define L_FINISH_BYTES                                                 -127 + 128
#define L_1_SET_OPTION                                                 0x01 + 128
#define L_2_GET_OPTION                                                 0x02 + 128
#define L_1_2_GO_SLEEP                                                 0x02 + 128
#define L_1_3_WAKE_UP                                                  0x03 + 128
#define L_1_4_SERVER_OPTION                                            0x04 + 128
#define L_1_6_OPTIONS_SCREEN                                           0x06 + 128
#define L_1_4_1_START_SERVER                                           0x01 + 128
#define L_1_4_2_STOP_SERVER                                            0x02 + 128
#define L_1_6_1_OPTIONS_SETTINGS_WIDTH                                 0x01 + 128
#define L_1_6_2_OPTIONS_SETTINGS_HEIGHT                                0x02 + 128
#define L_1_6_3_OPTIONS_SETTINGS_COLORS                                0x03 + 128
#define L_1_6_4_UPDATE_SCREEN                                          0x04 + 128
#define L_1_6_3_1_SCREEN_COLORS_1BIT_BLACK_WHITE                       0x01 + 128
#define L_1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS                          0x02 + 128
#define L_1_6_3_3_SCREEN_COLORS_2BIT_4_COLORS                          0x03 + 128
#define L_1_6_3_4_SCREEN_COLORS_4BIT_16_COLORS                         0x04 + 128
#define L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS                        0x05 + 128
#define L_1_6_3_6_SCREEN_COLORS_16BIT__5_6_5                           0x06 + 128
#define L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8                           0x07 + 128
#define L_1_6_3_8_SCREEN_COLORS_8BIT__GRAY                             0x08 + 128
#define L_1_6_3_9_SCREEN_COLORS_4BIT__GRAY                             0x09 + 128
#define L_1_6_3_A_SCREEN_COLORS_2BIT__GRAY                             0x0A + 128
#define L_1_DRAW_PIXEL                                                 0x01 + 128
#define L_2_DRAW_PIXEL_COORDINATES_LESS_255                            0x02 + 128
#define L_3_UPDATE_SCREEN                                              0x03 + 128
#define L_4_DRAW_RECTS_ARRAY                                           0x04 + 128
#define L_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255                    0x05 + 128
#define L_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255                    0x06 + 128
#define L_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255    0x07 + 128
#define L_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255    0x08 + 128
#define L_1_1_EVENT_TOUCH_TAP                                          0x01 + 128
#define L_1_2_EVENT_TOUCH_UP                                           0x02 + 128
#define L_1_3_EVENT_TOUCH_DOWN                                         0x03 + 128
#define L_1_4_EVENT_TOUCH_MOVE                                         0x04 + 128
#define L_1_5_EVENT_TOUCH_MOVE_FINISHED                                0x05 + 128
#define L_1_6_EVENT_TOUCH_ZOOM_IN                                      0x06 + 128
#define L_1_7_EVENT_TOUCH_ZOOM_OUT                                     0x07 + 128
#define L_1_8_EVENT_TOUCH_ZOOM_FINISHED                                0x08 + 128
*/

/*
#define L_0_MODE_OPTION                                                -128
#define L_0_0_MODE_SELECT                                              0x00
#define L_0_1_OPTIONS_MODE                                             0x01
#define L_0_2_DRAW_MODE                                                0x02
#define L_0_3_EVENT_MODE                                               0x03
#define L_FINISH_BYTES                                                 -127
#define L_1_SET_OPTION                                                 0x01
#define L_2_GET_OPTION                                                 0x02
#define L_1_2_GO_SLEEP                                                 0x02
#define L_1_3_WAKE_UP                                                  0x03
#define L_1_4_SERVER_OPTION                                            0x04
#define L_1_6_OPTIONS_SCREEN                                           0x06
#define L_1_4_1_START_SERVER                                           0x01
#define L_1_4_2_STOP_SERVER                                            0x02
#define L_1_6_1_OPTIONS_SETTINGS_WIDTH                                 0x01
#define L_1_6_2_OPTIONS_SETTINGS_HEIGHT                                0x02
#define L_1_6_3_OPTIONS_SETTINGS_COLORS                                0x03
#define L_1_6_4_UPDATE_SCREEN                                          0x04
#define L_1_6_3_1_SCREEN_COLORS_1BIT_BLACK_WHITE                       0x01
#define L_1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS                          0x02
#define L_1_6_3_3_SCREEN_COLORS_2BIT_4_COLORS                          0x03
#define L_1_6_3_4_SCREEN_COLORS_4BIT_16_COLORS                         0x04
#define L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS                        0x05
#define L_1_6_3_6_SCREEN_COLORS_16BIT__5_6_5                           0x06
#define L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8                           0x07
#define L_1_6_3_8_SCREEN_COLORS_8BIT__GRAY                             0x08
#define L_1_6_3_9_SCREEN_COLORS_4BIT__GRAY                             0x09
#define L_1_6_3_A_SCREEN_COLORS_2BIT__GRAY                             0x0A
#define L_1_DRAW_PIXEL                                                 0x01
#define L_2_DRAW_PIXEL_COORDINATES_LESS_255                            0x02
#define L_3_UPDATE_SCREEN                                              0x03
#define L_4_DRAW_RECTS_ARRAY                                           0x04
#define L_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255                    0x05
#define L_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255                    0x06
#define L_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255    0x07
#define L_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255    0x08
#define L_1_1_EVENT_TOUCH_TAP                                          0x01
#define L_1_2_EVENT_TOUCH_UP                                           0x02
#define L_1_3_EVENT_TOUCH_DOWN                                         0x03
#define L_1_4_EVENT_TOUCH_MOVE                                         0x04
#define L_1_5_EVENT_TOUCH_MOVE_FINISHED                                0x05
#define L_1_6_EVENT_TOUCH_ZOOM_IN                                      0x06
#define L_1_7_EVENT_TOUCH_ZOOM_OUT                                     0x07
#define L_1_8_EVENT_TOUCH_ZOOM_FINISHED                                0x08
*/

/*
########################################################
#                                                      #
#                      PROTOCOL -                      #
#                                                      #
########################################################
*/

/*
########################################################
#                                                      #
#                     ERROR CODES +                    #
#                                                      #
########################################################
*/

#define _error_decodeCommands_toFewBytes   -1001

/*
########################################################
#                                                      #
#                     ERROR CODES -                    #
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
    //Serial.begin(115200);
    Serial.begin(512000);
    //Serial.begin(512000);

    byte message[] = {
            L_0_MODE_OPTION - 128,                         // Switch mode
            L_0_1_OPTIONS_MODE - 128,                      // Switch to COMMON MODE 1

            L_1_SET_OPTION  - 128,                          // Command to set option
            L_1_6_OPTIONS_SCREEN  - 128,                    // Screen param
            L_1_6_1_OPTIONS_SETTINGS_WIDTH  - 128,          // Setting screen width
            1,
            45,

            L_1_SET_OPTION  - 128,                           // Command to set option
            L_1_6_OPTIONS_SCREEN  - 128,                     // Screen param
            L_1_6_2_OPTIONS_SETTINGS_HEIGHT - 128,          // Setting screen height
            0,
            240,

            L_1_SET_OPTION - 128,                           // Command to set option
            L_1_6_OPTIONS_SCREEN - 128,                     // Screen param
            L_1_6_3_OPTIONS_SETTINGS_COLORS - 128,          // Setting screen color
            L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8 - 128,

            L_1_SET_OPTION - 128,                           // Command to set option
            L_1_4_SERVER_OPTION - 128,
            L_1_4_1_START_SERVER - 128,

            L_0_MODE_OPTION - 128,                          // Switch mode
            L_0_3_EVENT_MODE - 128,                         // Switch to COMMON MODE 1

            L_FINISH_BYTES - 128,
            L_FINISH_BYTES - 128,
            L_FINISH_BYTES - 128,
            L_FINISH_BYTES - 128,
            L_FINISH_BYTES - 128,
            L_FINISH_BYTES - 128,
            L_FINISH_BYTES - 128,
            L_FINISH_BYTES - 128,
    };

    sendMessage(message, 29);
    //sendMessage("Ososo");
  #endif

  #ifdef connector_wifi_socket_server
    socket_server_setup();
  #endif

}

#ifdef connector_serial
  void sendMessage(byte message[], int length){
    for(int i=0; i<length; i++){
      Serial.print((char)(message[i]));
    }
  }
  
  void sendMessage(byte message){
    Serial.print((char)message);
  }

  boolean isSerialAvailable(){
    return Serial.available() > 0;
  }
  
  
  byte serialRead(){
    while (!isSerialAvailable()){}
    return Serial.read();
  }
#endif

void loop() {

  #ifdef connector_serial
    if(isSerialAvailable()){
      decodeCommands();
    }
  #endif

  #ifdef connector_wifi_socket_server
    socket_server_loop();
  #endif

}
