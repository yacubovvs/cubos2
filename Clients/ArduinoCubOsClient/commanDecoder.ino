byte current_mode = 0;
byte currentColorScheme = _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;

int decodeCommands(byte* data, unsigned int data_length){
    unsigned int x0, y0, x1, y1, x_start, y_start, length;
    unsigned int current_position = 0;
    byte rgb[3];

    while (current_position < data_length) {

        byte current_byte = data[current_position];

        if(current_byte==_0_MODE_OPTION){
            switch (data[current_position+1]){
                case _0_1_OPTIONS_MODE:
                    current_mode = _0_1_OPTIONS_MODE; break;
                case _0_2_DRAW_MODE:
                    current_mode = _0_2_DRAW_MODE; break;
                case _0_3_EVENT_MODE:
                    current_mode = _0_3_EVENT_MODE; break;
                default:
                    current_mode = _0_0_MODE_SELECT; break;
            }
            Serial.println("Setting mode");
            current_position += 2;
            continue;
        }

        switch (current_mode){
            case _0_1_OPTIONS_MODE:
                switch (current_byte){
                    case _1_SET_OPTION:
                    case _2_GET_OPTION:
                        switch (data[current_position+1]){
                            case _1_2_GO_SLEEP:
                                current_position+=2;
                                break;
                            case _1_3_WAKE_UP:
                                current_position+=2;
                                break;
                            case _1_4_SERVER_OPTION:
                                switch (data[current_position+2]){
                                    case _1_4_1_START_SERVER:
                                        startServer();
                                        current_position+=3;
                                        break;
                                    case _1_4_2_STOP_SERVER:
                                        stopServer();
                                        current_position+=3;
                                        break;
                                }
                                break;
                            case _1_6_OPTIONS_SCREEN:
                                switch (data[current_position+2]){
                                    case _1_6_1_OPTIONS_SETTINGS_WIDTH:
                                        current_position+=5;
                                        break;
                                    case _1_6_2_OPTIONS_SETTINGS_HEIGHT:
                                        current_position+=5;
                                        break;
                                    case _1_6_3_OPTIONS_SETTINGS_COLORS:
                                        switch (data[current_position+3]){
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
                                                setColorScheme(data[current_position+3]);
                                                break;
                                            default:
                                                //System.out.println("Unknown screen color parameter");
                                                break;
                                        }
                                        current_position+=4;
                                        break;
                                    case _1_6_4_UPDATE_SCREEN:
                                        current_position++;
                                        updateScreen();
                                        break;
                                    default:
                                        //System.out.println("Unknown screen parameter");
                                        current_position+=3;
                                        break;
                                }
                                break;
                            default:
                                //System.out.println("Unknown parameter");
                                current_position+=2;
                                break;
                        }
                        break;
                    default:
                        //System.out.println("Unknown parameter operation");
                        current_position++;
                        break;
                }
                break;
            case _0_2_DRAW_MODE:
                switch (current_byte) {
                    case _1_DRAW_PIXEL:
                        current_position++;
                        current_position +=f_1_DRAW_PIXEL(data, current_position);
                        break;
                    case _2_DRAW_PIXEL_COORDINATES_LESS_255:
                        current_position++;
                        current_position +=f_2_DRAW_PIXEL_COORDINATES_LESS_255(data, current_position);
                        break;
                    case _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255:
                        current_position++;
                        current_position +=f_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255(data, current_position);
                        break;
                    case _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255:
                        current_position++;
                        current_position +=f_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255(data, current_position);
                        break;
                    case _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255:
                        current_position++;
                        current_position +=f_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255(data, current_position);
                        break;
                    case _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255:
                        current_position++;
                        current_position +=f_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255(data, current_position);
                        break;
                    case _3_UPDATE_SCREEN:
                        current_position++;
                        updateScreen();
                        break;
                    default:
                        //System.out.println("Unknown draw mode command");
                        current_position += 1;
                        break;
                }
                break;
            case _0_3_EVENT_MODE:
                switch (current_byte){
                    case _1_1_EVENT_TOUCH_TAP:
                        current_position += 5;
                        break;
                    case _1_2_EVENT_TOUCH_UP:
                        current_position += 5;
                        break;
                    case _1_3_EVENT_TOUCH_DOWN:
                        current_position += 5;
                        break;
                    case _1_4_EVENT_TOUCH_MOVE:
                        current_position += 13;
                        break;
                    case _1_5_EVENT_TOUCH_MOVE_FINISHED:
                        current_position += 9;
                        break;
                    case _1_6_EVENT_TOUCH_ZOOM_IN:
                        break;
                    case _1_7_EVENT_TOUCH_ZOOM_OUT:
                        break;
                    case _1_8_EVENT_TOUCH_ZOOM_FINISHED:
                        break;
                    default:
                        //System.out.println("CommandDecoder: unknown protocol command recieved");
                        current_position += 1;
                }
                break;
        }
    }
    return current_position;
}


/*
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*/


unsigned int f_1_DRAW_PIXEL(byte* data, int current_position){
    if (currentColorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
        return 7;
    } else if (currentColorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
        return 5;
    }
}

unsigned int f_2_DRAW_PIXEL_COORDINATES_LESS_255(byte* data, int current_position){
    Serial.println("f_2_DRAW_PIXEL_COORDINATES_LESS_255");
    if(currentColorScheme==_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
        drawPixel(
          data[current_position], 
          data[current_position+1], 
          data[current_position+2], 
          data[current_position+3],
          data[current_position+4]
        );
        return 5;
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
        return 4;
    }
}

unsigned int f_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255(byte* data, int current_position){
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
        return 9;
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
        return 7;
    }
}

unsigned int f_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255(byte* data, int current_position){
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
        return 7;
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
        return 5;
    }
}

unsigned int f_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255(byte* data, int current_position){
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
        return 8;
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
        return 6;
    }
}

unsigned int f_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255(byte* data, int current_position){
    if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
        return 6;
    }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
        return 4;
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
