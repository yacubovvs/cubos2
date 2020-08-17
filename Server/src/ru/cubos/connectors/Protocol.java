package ru.cubos.connectors;

public class Protocol {

    public static final byte _0_MODE_OPTION         = -128;
    public static final byte _0_0_MODE_SELECT       = 0x00;
    public static final byte _0_1_OPTIONS_MODE      = 0x01;
    public static final byte _0_2_DRAW_MODE         = 0x02;
    public static final byte _0_3_EVENT_MODE        = 0x03;

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static final byte _FINISH_BYTES = -127;
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // OPTIONS MODE
    public static final byte _1_SET_OPTION      = 0x01;
    public static final byte _2_GET_OPTION      = 0x02;

    public static final byte _1_2_GO_SLEEP          = 0x02;
    public static final byte _1_3_WAKE_UP           = 0x03;
    public static final byte _1_4_SERVER_OPTION     = 0x04;
    public static final byte _1_6_OPTIONS_SCREEN    = 0x06;
    //------------------------------------- -  -   -     -          -                    -                             -

    public static final byte _1_4_1_START_SERVER                = 0x01;
    public static final byte _1_4_2_STOP_SERVER                 = 0x02;
    public static final byte _1_6_1_OPTIONS_SETTINGS_WIDTH      = 0x01;
    public static final byte _1_6_2_OPTIONS_SETTINGS_HEIGHT     = 0x02;
    public static final byte _1_6_3_OPTIONS_SETTINGS_COLORS     = 0x03;
    public static final byte _1_6_4_UPDATE_SCREEN               = 0x04;

    public static final byte _1_6_3_1_SCREEN_COLORS_1BIT_BLACK_WHITE    = 0x01;
    public static final byte _1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS       = 0x02;
    public static final byte _1_6_3_3_SCREEN_COLORS_2BIT_4_COLORS       = 0x03;
    public static final byte _1_6_3_4_SCREEN_COLORS_4BIT_16_COLORS      = 0x04;
    public static final byte _1_6_3_5_SCREEN_COLORS_8BIT_256_COLORS     = 0x05;
    public static final byte _1_6_3_6_SCREEN_COLORS_16BIT__5_6_5        = 0x06;
    public static final byte _1_6_3_7_SCREEN_COLORS_24BIT__8_8_8        = 0x07;
    public static final byte _1_6_3_8_SCREEN_COLORS_8BIT__GRAY          = 0x08;
    public static final byte _1_6_3_9_SCREEN_COLORS_4BIT__GRAY          = 0x09;
    public static final byte _1_6_3_A_SCREEN_COLORS_2BIT__GRAY          = 0x0A;


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Drawing
    //DRAWING MODE
    public static final byte _1_DRAW_PIXEL                                              = 0x01;
    public static final byte _2_DRAW_PIXEL_COORDINATES_LESS_255                         = 0x02;
    public static final byte _3_UPDATE_SCREEN = 0x03;
    public static final byte _4_DRAW_RECTS_ARRAY                                        = 0x04;
    public static final byte _5_DRAW_LINE_VERTICAL_COORDINATES_MORE_255                 = 0x05;
    public static final byte _6_DRAW_LINE_VERTICAL_COORDINATES_LESS_255                 = 0x06;
    public static final byte _7_DRAW_LINE_VERTICAL_COORDINATES_MORE_255_LENGTH_LESS_255 = 0x07;
    public static final byte _8_DRAW_LINE_VERTICAL_COORDINATES_LESS_255_LENGTH_LESS_255 = 0x08;


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // EVENTS
    public static final byte _1_TOUCH_EVENT                     = 0x01;

    public static final byte _1_1_EVENT_TOUCH_TAP               = 0x01;
    public static final byte _1_2_EVENT_TOUCH_UP                = 0x02;
    public static final byte _1_3_EVENT_TOUCH_DOWN              = 0x03;
    public static final byte _1_4_EVENT_TOUCH_MOVE              = 0x04;
    public static final byte _1_5_EVENT_TOUCH_MOVE_FINISHED     = 0x05;
    public static final byte _1_6_EVENT_TOUCH_ZOOM_IN           = 0x06;
    public static final byte _1_7_EVENT_TOUCH_ZOOM_OUT          = 0x07;
    public static final byte _1_8_EVENT_TOUCH_ZOOM_FINISHED     = 0x08;

}
