package com.example.androidcubosclient.helpers;


import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.androidcubosclient.CanvasScreen;

import static com.example.androidcubosclient.helpers.ByteConverter.uByte;
import static com.example.androidcubosclient.helpers.Protocol.*;

public class CommandDecoder {
    byte current_mode = _0_2_DRAW_MODE;
    public byte currentColorScheme = _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8;
    Bitmap bitmap;
    CanvasScreen canvasScreen;

    public CommandDecoder(Bitmap bitmap, CanvasScreen canvasScreen) {
        this.bitmap = bitmap;
        this.canvasScreen = canvasScreen;
    }

    public byte[] decodeCommands(byte data[], boolean lastMessage, final int rest_count_max){
        char x0, y0, x1, y1, x_start, y_start, length;
        int current_position = 0;
        byte rgb[] = null;

        while (current_position < data.length) {

            if(data.length-current_position<=rest_count_max && !lastMessage){
                byte rest_data[] = new byte[data.length-current_position];
                for(int i=0; i<data.length-current_position; i++){
                    rest_data[i] = data[current_position + i];
                }
                return  rest_data;
            }

            byte current_byte = data[current_position];

            if(current_byte==_FINISH_BYTES){
                current_position += 1;
                continue;
            }

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
                                            x0 = ByteConverter.bytesToChar((byte)(data[current_position + 3]-128), (byte)(data[current_position + 4]-128));
                                            current_position+=5;
                                            setScreenWidth(x0);
                                            break;
                                        case _1_6_2_OPTIONS_SETTINGS_HEIGHT:
                                            x0 = ByteConverter.bytesToChar((byte)(data[current_position + 3]-128), (byte)(data[current_position + 4]-128));
                                            current_position+=5;
                                            setScreenHeight(x0);
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
                                                    System.out.println("Unknown screen color parameter");
                                                    break;
                                            }
                                            current_position+=4;
                                            break;
                                        case _1_6_4_UPDATE_SCREEN:
                                            current_position++;
                                            updateScreen();
                                            break;
                                        default:
                                            System.out.println("Unknown screen parameter");
                                            current_position+=3;
                                            break;
                                    }
                                    break;
                                default:
                                    System.out.println("Unknown parameter");
                                    current_position+=2;
                                    break;
                            }
                            break;
                        default:
                            System.out.println("Unknown parameter operation");
                            current_position++;
                            break;
                    }
                    break;
                case _0_2_DRAW_MODE:
                    switch (current_byte) {
                        case _1_DRAW_PIXEL:
                            current_position++;
                            current_position +=_1_DRAW_PIXEL(data, current_position);
                            break;
                        case _2_DRAW_PIXEL_COORDINATES_LESS_255:
                            current_position++;
                            current_position +=_2_DRAW_PIXEL_COORDINATES_LESS_255(data, current_position);
                            break;
                        case _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255:
                            current_position++;
                            current_position +=_5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255(data, current_position);
                            break;
                        case _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255:
                            current_position++;
                            current_position +=_6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255(data, current_position);
                            break;
                        case _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255:
                            current_position++;
                            current_position +=_7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255(data, current_position);
                            break;
                        case _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255:
                            current_position++;
                            current_position +=_8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255(data, current_position);
                            break;
                        case _3_UPDATE_SCREEN:
                            current_position++;
                            updateScreen();
                            break;
                        default:
                            System.out.println("Unknown draw mode command");
                            current_position += 1;
                            break;
                    }
                    break;
                case _0_3_EVENT_MODE:
                    switch (current_byte){
                        case _1_1_EVENT_TOUCH_TAP:
                        case _1_2_EVENT_TOUCH_UP:
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
                            System.out.println("CommandDecoder: unknown protocol command recieved");
                            current_position += 1;
                    }
                    break;
            }
        }
        return null;
    }

    public char _1_DRAW_PIXEL(byte[] data, int current_position){
        char x0, y0;
        byte rgb[] = null;
        char current_position_iter = 0;
        x0 = ByteConverter.bytesToChar(data[current_position], data[current_position + 1]);
        y0 = ByteConverter.bytesToChar(data[current_position + 2], data[current_position + 3]);

        if (currentColorScheme == _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8) {
            rgb = readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(data, current_position + 4);
            current_position_iter = 7;
        } else if (currentColorScheme == _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS) {
            rgb = readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(data, current_position + 4);
            current_position_iter = 5;
        }

        setPixel(x0, y0, rgb);
        return current_position_iter;
    }

    public char _2_DRAW_PIXEL_COORDINATES_LESS_255(byte[] data, int current_position){
        char x0, y0;
        byte rgb[] = null;
        char current_position_iter = 0;

        x0 = (char)(data[current_position] + 128);
        y0 = (char)(data[current_position + 1] + 128);

        if(currentColorScheme==_1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
            rgb = readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(data, current_position + 2);
            current_position_iter += 5;
        }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
            rgb = readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(data, current_position + 2);
            current_position_iter += 4;
        }

        setPixel(x0, y0, rgb);
        return current_position_iter;
    }

    public char _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255(byte[] data, int current_position){
        char x0, y0, length;
        byte rgb[] = null;
        char current_position_iter = 0;

        x0 = ByteConverter.bytesToChar(data[current_position], data[current_position + 1]);
        y0 = ByteConverter.bytesToChar(data[current_position + 2], data[current_position + 3]);
        length = ByteConverter.bytesToChar(data[current_position + 4], data[current_position + 5]);
        if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
            setLine(x0, y0, length, readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(data, current_position + 6));
            current_position_iter += 9;

        }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
            rgb = readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(data, current_position + 6);
            setLine(x0, y0, length, rgb);
            current_position_iter += 7;
        }

        return current_position_iter;
    }

    public char _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255(byte[] data, int current_position){
        char x0, y0, length;
        byte rgb[] = null;
        char current_position_iter = 0;

        x0 = (char)(data[current_position] + 128);
        y0 = (char)(data[current_position + 1] + 128);
        length = ByteConverter.bytesToChar(data[current_position + 2], data[current_position + 3]);
        if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
            setLine(x0, y0, length, readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(data, current_position + 4));
            current_position_iter += 7;

        }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
            rgb = readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(data, current_position + 4);
            setLine(x0, y0, length, rgb);
            current_position_iter += 5;
        }
        return current_position_iter;
    }

    public char _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255(byte[] data, int current_position){
        char x0, y0, length;
        byte rgb[] = null;
        char current_position_iter = 0;

        x0 = ByteConverter.bytesToChar(data[current_position], data[current_position + 1]);
        y0 = ByteConverter.bytesToChar(data[current_position + 2], data[current_position + 3]);
        length = (char)(data[current_position + 4] + 128);
        if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
            setLine(x0, y0, length, readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(data, current_position + 5));
            current_position_iter += 8;

        }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
            rgb = readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(data, current_position + 5);
            setLine(x0, y0, length, rgb);
            current_position_iter += 6;
        }
        return current_position_iter;
    }

    public char _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255(byte[] data, int current_position){
        char x0, y0, length;
        byte rgb[] = null;
        char current_position_iter = 0;

        x0 = (char)(data[current_position] + 128);
        y0 = (char)(data[current_position + 1] + 128);
        length = (char)(data[current_position + 2] + 128);
        if(currentColorScheme== _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8){
            setLine(x0, y0, length, readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(data, current_position + 3));
            current_position_iter += 6;

        }else if(currentColorScheme==_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS){
            rgb = readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(data, current_position + 3);
            setLine(x0, y0, length, rgb);
            current_position_iter += 4;
        }
        return current_position_iter;
    }


    private void setLine(char x, char y, char length, byte[] rgb) {
        for(char i=0; i<length; i++){
            setPixel(x+i, y, rgb);
        }
    }

    private byte[] readRgb_1_6_3_2_SCREEN_COLORS_24BIT__8_8_8(byte data[], int position){
        byte rgb[] = new byte[3];
        rgb[0] = data[position];
        rgb[1] = data[position + 1];
        rgb[2] = data[position + 2];

        return rgb;
    }

    private byte[] readRgb_1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS(byte data[], int position){
        byte rgb[] = Colors.color_256_to_rgb(data[position]);
        return rgb;
    }

    protected void setPixel(int x, int y, byte rgb[]){
        int color = Color.rgb(rgb[0] + 128, rgb[1] + 128, rgb[2] + 128);
        bitmap.setPixel(x, y, color);
    }
    protected void startServer(){}
    protected void stopServer(){}
    protected void setScreenHeight(char value){}
    protected void setScreenWidth(char value){}
    protected void setColorScheme(byte value){}
    protected void updateScreen(){
        canvasScreen.invalidate();
    }


}
