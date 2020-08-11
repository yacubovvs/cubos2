package ru.cubos.connectors;

public class Protocol {

    /*
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * *                                                                                                             * *
     * *                                               SERVER -> SCREEN                                              * *
     * *                                                                                                             * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    // Drawing
    public static final byte DRAWING_PIXEL          = 0x01;
    public static final byte DRAWING_RECT           = 0x02;
    public static final byte DRAWING_PIXELS_ARRAY   = 0x03;
    public static final byte DRAWING_RECTS_ARRAY    = 0x04;

    // Parameters
    public static final byte SET_PARAM = 0x20;
    public static final byte GET_PARAM = 0x21;

    // Power settings
    public static final byte UPDATE_SCREEN = 0x30;
    public static final byte GO_SLEEP = 0x31;
    public static final byte WAKE_UP = 0x32;

    // Commands
    public static final String Command_CLOSE_CONNECTION = "cc"; // close connection

    /*
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * *                                                                                                             * *
     * *                                               SCREEN -> SERVER                                              * *
     * *                                                                                                             * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // EVENTS
    public static final byte _1_DATATYPE_EVENT          = 0x01;

    public static final byte _1_1_EVENT_TOUCH_TAP               = 0x01;
    public static final byte _1_2_EVENT_TOUCH_UP                = 0x02;
    public static final byte _1_3_EVENT_TOUCH_DOWN              = 0x03;
    public static final byte _1_4_EVENT_TOUCH_MOVE              = 0x04;
    public static final byte _1_5_EVENT_TOUCH_MOVE_FINISHED     = 0x05;
    public static final byte _1_6_EVENT_TOUCH_ZOOM_IN           = 0x06;
    public static final byte _1_7_EVENT_TOUCH_ZOOM_OUT          = 0x07;
    public static final byte _1_8_EVENT_TOUCH_ZOOM_FINISHED     = 0x08;

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // SETTINGS
    public static final byte _2_DATATYPE_SETTINGS               = 0x01;

    public static final byte _2_1_SETTINGS_SCREEN               = 0x01;

    public static final byte _2_1_1_SCREEN_SETTINGS_WIDTH       = 0x01;
    public static final byte _2_1_2_SCREEN_SETTINGS_HEIGHT      = 0x02;
    public static final byte _2_1_3_SCREEN_SETTINGS_COLORS      = 0x03;

    public static final byte _2_1_3_1_SCREEN_COLORS_1BIT                = 0x01;
    public static final byte _2_1_3_2_SCREEN_COLORS_2BIT_3_COLORS       = 0x02;
    public static final byte _2_1_3_2_SCREEN_COLORS_2BIT_4_COLORS       = 0x03;
    public static final byte _2_1_3_2_SCREEN_COLORS_4BIT_16_COLORS      = 0x04;
    public static final byte _2_1_3_2_SCREEN_COLORS_8BIT_256_COLORS     = 0x05;
    public static final byte _2_1_3_2_SCREEN_COLORS_16BIT__5_6_5        = 0x06;
    public static final byte _2_1_3_2_SCREEN_COLORS_24BIT__8_8_8        = 0x07;



}
