package com.example.androidcubosclient.helpers;


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


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public static final byte _COMMON_MODE           = -128;
    // Parameters
    public static final byte _1_SET_PARAM           = 0x01;
    public static final byte _2_GET_PARAM           = 0x02;

    // Power settings
    public static final byte _1_1_UPDATE_SCREEN     = 0x01;
    public static final byte _1_2_GO_SLEEP          = 0x02;
    public static final byte _1_3_WAKE_UP           = 0x03;

    public static final byte _1_4_START_SERVER      = 0x04;
    public static final byte _1_5_STOP_SERVER       = 0x05;

    public static final byte _1_6_OPTIONS_SCREEN                = 0x06;

    public static final byte _1_6_1_OPTIONS_SETTINGS_WIDTH      = 0x01;
    public static final byte _1_6_2_OPTIONS_SETTINGS_HEIGHT     = 0x02;
    public static final byte _1_6_3_OPTIONS_SETTINGS_COLORS     = 0x03;

    public static final byte _1_6_3_1_SCREEN_COLORS_1BIT                = 0x01;
    public static final byte _1_6_3_2_SCREEN_COLORS_2BIT_3_COLORS       = 0x02;
    public static final byte _1_6_3_2_SCREEN_COLORS_2BIT_4_COLORS       = 0x03;
    public static final byte _1_6_3_2_SCREEN_COLORS_4BIT_16_COLORS      = 0x04;
    public static final byte _1_6_3_2_SCREEN_COLORS_8BIT_256_COLORS     = 0x05;
    public static final byte _1_6_3_2_SCREEN_COLORS_16BIT__5_6_5        = 0x06;
    public static final byte _1_6_3_2_SCREEN_COLORS_24BIT__8_8_8        = 0x07;

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Drawing
    public static final byte _2_DRAWING_MODE            = -127;

    public static final byte _2_1_DRAWING_PIXEL         = 0x01;
    public static final byte _2_2_DRAWING_RECT          = 0x02;
    public static final byte _2_3_DRAWING_PIXELS_ARRAY  = 0x03;
    public static final byte _2_4_DRAWING_RECTS_ARRAY   = 0x04;


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

    public static final byte _1_DATATYPE_EVENT                  = 0x01;

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
    // Parameters
    /*
    public static final byte _1_1_SET_PARAM         = 0x01;
    public static final byte _1_2_GET_PARAM         = 0x02;

    // Power settings
    public static final byte _1_3_UPDATE_SCREEN     = 0x03;
    public static final byte _1_4_GO_SLEEP          = 0x04;
    public static final byte _1_5_WAKE_UP           = 0x05;

    public static final byte _2_1_OPTIONS_SCREEN                = 0x01;

    public static final byte _2_1_1_OPTIONS_SETTINGS_WIDTH      = 0x01;
    public static final byte _2_1_2_OPTIONS_SETTINGS_HEIGHT     = 0x02;
    public static final byte _2_1_3_OPTIONS_SETTINGS_COLORS     = 0x03;

    public static final byte _2_1_3_1_SCREEN_COLORS_1BIT                = 0x01;
    public static final byte _2_1_3_2_SCREEN_COLORS_2BIT_3_COLORS       = 0x02;
    public static final byte _2_1_3_2_SCREEN_COLORS_2BIT_4_COLORS       = 0x03;
    public static final byte _2_1_3_2_SCREEN_COLORS_4BIT_16_COLORS      = 0x04;
    public static final byte _2_1_3_2_SCREEN_COLORS_8BIT_256_COLORS     = 0x05;
    public static final byte _2_1_3_2_SCREEN_COLORS_16BIT__5_6_5        = 0x06;
    public static final byte _2_1_3_2_SCREEN_COLORS_24BIT__8_8_8        = 0x07;
    */

}
