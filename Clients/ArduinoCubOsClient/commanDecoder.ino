byte current_mode = 0;
byte currentColorScheme = _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;

int getDrawOperationLength(byte operation){
  switch(operation){
    case _1_DRAW_PIXEL:
      return 8;
      break;
    case _2_DRAW_PIXEL_COORDINATES_LESS_255:
      return 6;
      break;
    case _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255:
      return 10;
      break;
    case _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255:
      return 8;
      break;
    case _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255:
      return 9;
      break;
    case _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255:
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

    if(current_byte==_0_MODE_OPTION){
      debugString("Setting mode");
        switch (serialRead()){
            case _0_1_OPTIONS_MODE:
                current_mode = _0_1_OPTIONS_MODE; return true;
            case _0_2_DRAW_MODE:
                debugString("Setting DRAW mode");
                current_mode = _0_2_DRAW_MODE; return true;
            case _0_3_EVENT_MODE:
                current_mode = _0_3_EVENT_MODE; return true;
            default:
                debugString("Setting UNKNOWN mode");
                current_mode = _0_0_MODE_SELECT;return false;
        }
    }

    switch (current_mode){
        case _0_1_OPTIONS_MODE:
            switch (current_byte){
                case _1_SET_OPTION:
                case _2_GET_OPTION:
                    switch (serialRead()){
                        case _1_2_GO_SLEEP:
                            return true;
                        case _1_3_WAKE_UP:
                            return true;
                        case _1_4_SERVER_OPTION:
                            switch (serialRead()){
                                case _1_4_1_START_SERVER:
                                    startServer();
                                    return true;
                                case _1_4_2_STOP_SERVER:
                                    stopServer();
                                    return true;
                            }
                            return false;
                        case _1_6_OPTIONS_SCREEN:
                            switch (serialRead()){
                                case _1_6_1_OPTIONS_SETTINGS_WIDTH:
                                    serialRead();
                                    serialRead();
                                    return true;
                                case _1_6_2_OPTIONS_SETTINGS_HEIGHT:
                                    serialRead();
                                    serialRead();
                                    return true;
                                case _1_6_3_OPTIONS_SETTINGS_COLORS:
                                    byte colorSheme = serialRead();
                                    switch (colorSheme){
                                        case _1_6_3_1_SCREEN_COLORS_1BIT_BLACK_WHITE:
                                        case _1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS:
                                        case _1_6_3_3_SCREEN_COLORS_2BIT_4_COLORS:
                                        case _1_6_3_4_SCREEN_COLORS_4BIT_16_COLORS:
                                        case _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS:
                                        case _1_6_3_6_SCREEN_COLORS_16BIT__5_6_5:
                                        case _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8:
                                        case _1_6_3_8_SCREEN_COLORS_8BIT__GRAY:
                                        case _1_6_3_9_SCREEN_COLORS_4BIT__GRAY:
                                        case _1_6_3_A_SCREEN_COLORS_2BIT__GRAY:
                                            setColorScheme(colorSheme);
                                            return true;
                                        default:
                                            debugString("Unknown screen color parameter");
                                            return false;
                                    }
                                    break;
                                case _1_6_4_UPDATE_SCREEN:
                                    updateScreen();
                                    return true;
                                default:
                                    debugString("Unknown screen parameter");
                                    //current_position+=3;
                                    
                                    return false;
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
        case _0_2_DRAW_MODE:
            switch (current_byte) {
                case _1_DRAW_PIXEL:
                    f_1_DRAW_PIXEL();
                    return true;
                case _2_DRAW_PIXEL_COORDINATES_LESS_255:
                    f_2_DRAW_PIXEL_COORDINATES_LESS_255();
                    return true;
                case _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255:
                    f_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255();
                    return true;
                case _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255:
                    f_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255();
                    return true;
                case _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255:
                    f_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255();
                    return true;
                case _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255:
                    f_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255();
                    return true;
                case _3_UPDATE_SCREEN:
                    //updateScreen();
                    return true;
                default:
                    debugString("Unknown draw mode command: " + String(current_byte));
                    return false;
            }
            break;
        case _0_3_EVENT_MODE:
            switch (current_byte){
                case _1_1_EVENT_TOUCH_TAP:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    return true;
                case _1_2_EVENT_TOUCH_UP:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    break;
                case _1_3_EVENT_TOUCH_DOWN:
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    serialRead();
                    return true;
                case _1_4_EVENT_TOUCH_MOVE:
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
                case _1_5_EVENT_TOUCH_MOVE_FINISHED:
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
                case _1_6_EVENT_TOUCH_ZOOM_IN:
                    return true;
                case _1_7_EVENT_TOUCH_ZOOM_OUT:
                    return true;
                case _1_8_EVENT_TOUCH_ZOOM_FINISHED:
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

void f_1_DRAW_PIXEL(){
    if (currentColorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
    } else if (currentColorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
    }
}

void f_2_DRAW_PIXEL_COORDINATES_LESS_255(){
    debugString("f_2_DRAW_PIXEL_COORDINATES_LESS_255");
    if(currentColorScheme==_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      byte x = serialRead();
      byte y = serialRead();
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      drawPixel(x,y,r,g,b);
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void f_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255(){
    debugString("f_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255");
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      int x = bytes_to_value(serialRead(), serialRead());
      int y = bytes_to_value(serialRead(), serialRead());
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      drawPixel(x,y,r,g,b);
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void f_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255(){
    debugString("f_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255");
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      int x = serialRead();
      int y = serialRead();
      int length = bytes_to_value(serialRead(), serialRead());
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      drawLine(x,y, x+length, y, r,g,b);
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void f_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255(){
    debugString("f_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255");
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      int x = bytes_to_value(serialRead(), serialRead());
      int y = bytes_to_value(serialRead(), serialRead());
      byte length = serialRead();
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      drawLine(x,y, x+length, y, r,g,b);
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
    }
}

void f_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255(){
    debugString("f_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255");
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
      byte x = serialRead();
      byte y = serialRead();
      byte length = serialRead();
      byte r = serialRead();
      byte g = serialRead();
      byte b = serialRead();
      drawLine(x,y, x+length, y, r,g,b);
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
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

byte* readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(byte *data, unsigned int position){
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
