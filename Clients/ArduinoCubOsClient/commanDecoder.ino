#ifdef display_M5Stack
  #include <M5Stack.h>
  uint32_t get_m5ColorQuick(byte r, byte g, byte b){
    return (((( r*31/255) <<11)|( g*31/255) <<6)|( b*31/255) <<0);
    //return (((( r ) <<11)|( g ) <<6)|( b ) <<0);
  }
#endif

byte current_mode = 0;
byte currentColorScheme = L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;

int getDrawOperationLength(byte operation){
  switch(operation){
    case L_1_DRAW_PIXEL:
      return 8;
      break;
    case L_2_DRAW_PIXEL_COORDINATES_LESS_255:
      return 6;
      break;
    case L_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255:
      return 10;
      break;
    case L_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255:
      return 8;
      break;
    case L_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255:
      return 9;
      break;
    case L_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255:
      return 7;
      break;
    default:
      return 1;
  }
}


/*
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*/
int i = 0;
void debugString(String string){
  //drawString(string, 0,8*i, 255,255,255);
  //i++;
}

boolean decodeCommands(){
    unsigned int x0, y0, x1, y1, x_start, y_start, length;
    byte rgb[3];

    byte current_byte = serialRead();

    debugString("Data got: " + String(current_byte));

    if(current_byte==L_0_MODE_OPTION){
      debugString("Setting mode");
        switch (serialRead()){
            case L_0_1_OPTIONS_MODE:
                current_mode = L_0_1_OPTIONS_MODE; return true;
            case L_0_2_DRAW_MODE:
                debugString("Setting DRAW mode");
                current_mode = L_0_2_DRAW_MODE; return true;
            case L_0_3_EVENT_MODE:
                current_mode = L_0_3_EVENT_MODE; return true;
            default:
                debugString("Setting UNKNOWN mode");
                current_mode = L_0_0_MODE_SELECT;return false;
        }
    }

    switch (current_mode){
        case L_0_1_OPTIONS_MODE:
            switch (current_byte){
                case L_1_SET_OPTION:
                case L_2_GET_OPTION:
                    switch (serialRead()){
                        case L_1_2_GO_SLEEP:
                            return true;
                        case L_1_3_WAKE_UP:
                            return true;
                        case L_1_4_SERVER_OPTION:
                            switch (serialRead()){
                                case L_1_4_1_START_SERVER:
                                    startServer();
                                    return true;
                                case L_1_4_2_STOP_SERVER:
                                    stopServer();
                                    return true;
                            }
                            return false;
                        case L_1_6_OPTIONS_SCREEN:
                            switch (serialRead()){
                                case L_1_6_1_OPTIONS_SETTINGS_WIDTH:
                                    serialRead();
                                    serialRead();
                                    return true;
                                case L_1_6_2_OPTIONS_SETTINGS_HEIGHT:
                                    serialRead();
                                    serialRead();
                                    return true;
                                case L_1_6_3_OPTIONS_SETTINGS_COLORS:
                                    byte colorSheme = serialRead();
                                    setColorScheme(colorSheme);
                                    /*
                                    switch (colorSheme){
                                        case L_1_6_3_1_SCREEN_COLORS_1BIT_BLACK_WHITE:
                                        case L_1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS:
                                        case L_1_6_3_3_SCREEN_COLORS_2BIT_4_COLORS:
                                        case L_1_6_3_4_SCREEN_COLORS_4BIT_16_COLORS:
                                        case L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS:
                                        case L_1_6_3_6_SCREEN_COLORS_16BIT__5_6_5:
                                        case L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8:
                                        case L_1_6_3_8_SCREEN_COLORS_8BIT__GRAY:
                                        case L_1_6_3_9_SCREEN_COLORS_4BIT__GRAY:
                                        case L_1_6_3_A_SCREEN_COLORS_2BIT__GRAY:
                                            setColorScheme(colorSheme);
                                            return true;
                                        default:
                                            debugString("Unknown screen color parameter");
                                            return false;
                                    }
                                    break;*/
                                /*
                                default:
                                    debugString("Unknown screen parameter");
                                    //current_position+=3;
                                    
                                    return false;*/
                            }
                            break;
                        default:
                            debugString("Unknown parameter");
                            //current_position+=2;
                            return false;
                    }
                    break;
                default:
                    debugString("Unknown parameter operation");
                    return false;
                    break;
            }
            break;
        case L_0_2_DRAW_MODE:
            switch (current_byte) {
                case L_1_DRAW_PIXEL:
                    fL_1_DRAW_PIXEL();
                    return true;
                case L_2_DRAW_PIXEL_COORDINATES_LESS_255:
                    fL_2_DRAW_PIXEL_COORDINATES_LESS_255();
                    return true;
                case L_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255:
                    fL_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255();
                    return true;
                case L_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255:
                    fL_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255();
                    return true;
                case L_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255:
                    fL_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255();
                    return true;
                case L_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255:
                    fL_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255();
                    return true;
                case L_3_UPDATE_SCREEN:
                    //updateScreen();
                    return true;
                default:
                    debugString("Unknown draw mode command: " + String(current_byte));
                    return false;
            }
            break;
        case L_0_3_EVENT_MODE:
            switch (current_byte){
                case L_1_1_EVENT_TOUCH_TAP:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    return true;
                case L_1_2_EVENT_TOUCH_UP:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    break;
                case L_1_3_EVENT_TOUCH_DOWN:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    return true;
                case L_1_4_EVENT_TOUCH_MOVE:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    return true;
                case L_1_5_EVENT_TOUCH_MOVE_FINISHED:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    return true;
                case L_1_6_EVENT_TOUCH_ZOOM_IN:
                    return true;
                case L_1_7_EVENT_TOUCH_ZOOM_OUT:
                    return true;
                case L_1_8_EVENT_TOUCH_ZOOM_FINISHED:
                    return true;
                default:
                    debugString("CommandDecoder: unknown protocol command recieved");
                    return false;
            }
            return false;
    }
    
    return true;
}

/*
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*/

void fL_1_DRAW_PIXEL(){
    if (currentColorScheme == L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
      int x = bytes_to_value_inverse(serialRead(), serialRead());
      int y = bytes_to_value_inverse(serialRead(), serialRead());
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      drawPixel(x,y,r,g,b);
    } else if (currentColorScheme == L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
    }
}

void fL_2_DRAW_PIXEL_COORDINATES_LESS_255(){
    debugString("fL_2_DRAW_PIXEL_COORDINATES_LESS_255");
    if(currentColorScheme==L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      byte x = serialRead();
      byte y = serialRead();
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      drawPixel(x,y,r,g,b);
    }else if(currentColorScheme==L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void fL_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255(){
    debugString("fL_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255");
    if(currentColorScheme== L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      int x = bytes_to_value_inverse(serialRead(), serialRead());
      int y = bytes_to_value_inverse(serialRead(), serialRead());
      int length = bytes_to_value_inverse(serialRead(), serialRead());
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      #ifdef display_M5Stackp32
        M5.Lcd.drawFastHLine(x, y, length, get_m5ColorQuick(r, g, b));
      #else
        drawLine(x,y, x+length, y, r,g,b);
      #endif
    }else if(currentColorScheme==L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void fL_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255(){
    debugString("fL_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255");
    if(currentColorScheme== L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      int x = serialRead();
      int y = serialRead();
      int length = bytes_to_value_inverse(serialRead(), serialRead());
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      #ifdef display_M5Stackp32
        M5.Lcd.drawFastHLine(x, y, length, get_m5ColorQuick(r, g, b));
      #else
        drawLine(x,y, x+length, y, r,g,b);
      #endif
    }else if(currentColorScheme==L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void fL_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255(){
    debugString("fL_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255");
    if(currentColorScheme== L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      int x = bytes_to_value_inverse(serialRead(), serialRead());
      int y = bytes_to_value_inverse(serialRead(), serialRead());
      byte length = serialRead();
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      #ifdef display_M5Stack
        M5.Lcd.drawFastHLine(x, y, length, get_m5ColorQuick(r, g, b));
      #else
        drawLine(x,y, x+length, y, r,g,b);
      #endif
    }else if(currentColorScheme==L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void fL_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255(){
    debugString("fL_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255");
    if(currentColorScheme== L_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      byte x = serialRead();
      byte y = serialRead();
      byte length = serialRead();
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
        
      #ifdef display_M5Stack
        M5.Lcd.drawFastHLine(x, y, length, get_m5ColorQuick(r, g, b));
      #else
        drawLine(x,y, x+length, y, r,g,b);
      #endif
    }else if(currentColorScheme==L_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}


void setLine(unsigned int x, unsigned int y, unsigned int length, byte* rgb) {
    for(unsigned int i=0; i<length; i++){
        //setPixel(x+i, y, rgb);
    }
}

byte* readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(byte* data, unsigned int position){
    //byte rgb[] = new byte[3];
    //rgb[0] = data[position];
    //rgb[1] = data[position + 1];
    //rgb[2] = data[position + 2];
    //return rgb;
    return data;
}

byte* readRgbL_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(byte *data, unsigned int position){
    //byte rgb[] = Colors.color_256_to_rgb(data[position]);
    //return rgb;
    return data;
}

void setPixel(int x, int y, byte rgb[]){}
void execTouchEvent(){}
void startServer(){}
void stopServer(){}
void setScreenHeight(char value){}
void setScreenWidth(char value){}
void setColorScheme(byte value){}
void updateScreen(){}
