package ru.cubos.server.helpers;

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

    //Drawind
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


    /*
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * *                                                                                                             * *
     * *                                               SCREEN -> SERVER                                              * *
     * *                                                                                                             * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    // Touch screen or mouse click
    public static final byte EVENT_TOUCH_TAP            = 0x01;
    public static final byte EVENT_TOUCH_UP             = 0x02;
    public static final byte EVENT_TOUCH_DOWN           = 0x03;
    public static final byte EVENT_TOUCH_MOVE           = 0x04;
    public static final byte EVENT_TOUCH_MOVE_FINISHED  = 0x05;
    public static final byte EVENT_TOUCH_ZOOM_IN        = 0x06;
    public static final byte EVENT_TOUCH_ZOOM_OUT       = 0x07;
    public static final byte EVENT_TOUCH_ZOOM_FINISHED  = 0x08;

}
