package ru.cubos.commonHelpers;

import ru.cubos.server.helpers.ByteConverter;
import ru.cubos.server.system.events.*;

import static ru.cubos.connectors.Protocol.*;
import static ru.cubos.connectors.Protocol._3_DRAWING_PIXELS_ARRAY;
import static ru.cubos.server.helpers.ByteConverter.uByte;

public abstract class CommandDecoder {
    byte current_mode = 0;

    public void decodeCommands(byte data[]){
        decodeCommands(data, true, 0);
    }

    public byte[] decodeCommands(byte data[], boolean lastMessage, final int rest_count_max){
        char x0, y0, x1, y1, x_start, y_start;
        int current_position = 0;
        byte r, g, b;

        while (current_position < data.length) {

            if(data.length-current_position<=rest_count_max && !lastMessage){
                byte rest_data[] = new byte[data.length-current_position];
                for(int i=0; i<data.length-current_position; i++){
                    rest_data[i] = data[current_position + i];
                }
                return  rest_data;
            }

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
                                                case _1_6_3_1_SCREEN_COLORS_1BIT:
                                                    current_position+=4;
                                                    break;
                                                case _1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS:
                                                    current_position+=4;
                                                    break;
                                                case _1_6_3_2_SCREEN_COLORS_2BIT_4_COLORS:
                                                    current_position+=4;
                                                    break;
                                                case _1_6_3_2_SCREEN_COLORS_4BIT_16_COLORS:
                                                    current_position+=4;
                                                    break;
                                                case _1_6_3_2_SCREEN_COLORS_8BIT_256_COLORS:
                                                    current_position+=4;
                                                    break;
                                                case _1_6_3_2_SCREEN_COLORS_16BIT__5_6_5:
                                                    current_position+=4;
                                                    break;
                                                case _1_6_3_2_SCREEN_COLORS_24BIT__8_8_8:
                                                    current_position+=4;
                                                    break;
                                                default:
                                                    System.out.println("Unknown screen color parameter");
                                                    current_position+=4;
                                                    break;
                                            }
                                            break;
                                        case _1_6_4_UPDATE_SCREEN:
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
                        case _1_DRAWING_PIXEL:
                            x0 = ByteConverter.bytesToChar(data[current_position + 1], data[current_position + 2]);
                            y0 = ByteConverter.bytesToChar(data[current_position + 3], data[current_position + 4]);
                            r = data[current_position + 5];
                            g = data[current_position + 6];
                            b = data[current_position + 7];

                            java.awt.Color color = new java.awt.Color(r+128,g+128,b+128);
                            setPixel(x0, y0, color.getRGB());
                            current_position += 8;

                            break;
                        case _2_DRAWING_RECT:
                            x0 = ByteConverter.bytesToChar(data[current_position + 1], data[current_position + 2]);
                            y0 = ByteConverter.bytesToChar(data[current_position + 3], data[current_position + 4]);
                            x1 = ByteConverter.bytesToChar(data[current_position + 5], data[current_position + 6]);
                            y1 = ByteConverter.bytesToChar(data[current_position + 7], data[current_position + 8]);
                            r = data[current_position + 9];
                            g = data[current_position + 10];
                            b = data[current_position + 11];

                            current_position += 12;
                            //drawRect(x0, y0, x1, y1, new Color(r, g, b));
                            //System.out.printf("Drawing rectangle");
                            break;
                        case _4_DRAWING_RECTS_ARRAY:
                            //System.out.println("Emulator client: drawing rectangle array");
                            break;
                        case _3_DRAWING_PIXELS_ARRAY:
                            //System.out.println("Emulator client: drawing pixels array");
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
                            x0 = ByteConverter.bytesToChar(uByte(data[current_position + 1]), uByte(data[current_position + 2]));
                            y0 = ByteConverter.bytesToChar(uByte(data[current_position + 3]), uByte(data[current_position + 4]));
                            execTouchEvent(new TouchTapEvent(x0, y0));
                            current_position += 5;
                            break;
                        case _1_2_EVENT_TOUCH_UP:
                            x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                            y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                            current_position += 5;
                            execTouchEvent(new TouchUpEvent(x0, y0));
                            break;
                        case _1_3_EVENT_TOUCH_DOWN:
                            x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                            y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                            current_position += 5;
                            execTouchEvent(new TouchDownEvent(x0, y0));
                            break;
                        case _1_4_EVENT_TOUCH_MOVE:

                            x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                            y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                            x1      = ByteConverter.bytesToChar(uByte(data[current_position + 5]),  uByte(data[current_position + 6]));
                            y1      = ByteConverter.bytesToChar(uByte(data[current_position + 7]),  uByte(data[current_position + 8]));
                            x_start = ByteConverter.bytesToChar(uByte(data[current_position + 9]),  uByte(data[current_position + 10]));
                            y_start = ByteConverter.bytesToChar(uByte(data[current_position + 11]), uByte(data[current_position + 12]));
                            current_position += 13;
                            execTouchEvent(new TouchMoveEvent(x0, y0, x1, y1, x_start, y_start));
                            break;
                        case _1_5_EVENT_TOUCH_MOVE_FINISHED:

                            x0      = ByteConverter.bytesToChar(uByte(data[current_position + 1]),  uByte(data[current_position + 2]));
                            y0      = ByteConverter.bytesToChar(uByte(data[current_position + 3]),  uByte(data[current_position + 4]));
                            x_start = ByteConverter.bytesToChar(uByte(data[current_position + 5]),  uByte(data[current_position + 6]));
                            y_start = ByteConverter.bytesToChar(uByte(data[current_position + 7]),  uByte(data[current_position + 8]));
                            current_position += 9;
                            execTouchEvent(new TouchMoveFinishedEvent(x0, y0, x_start, y_start));
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

    protected void setPixel(int x, int y, int rgb){}
    protected void execTouchEvent(EventTouch event){}
    protected void startServer(){}
    protected void stopServer(){}
    protected void setScreenHeight(char value){}
    protected void setScreenWidth(char value){}


}
