package ru.cubos.server.helpers;

public class Protocol {

    //Drawind
    public static final byte DRWING_PIXEL           = 0x01;
    public static final byte DRWING_RECT            = 0x02;
    public static final byte DRWING_PIXELS_ARRAY    = 0x03;
    public static final byte DRWING_RECTS_ARRAY     = 0x04;

    // Parameters
    public static final byte SET_PARAM = 0x20;
    public static final byte GET_PARAM = 0x21;

    // Power settings
    public static final byte UPDATE_SCREEN = 0x30;
    public static final byte GO_SLEEP = 0x31;
    public static final byte WAKE_UP = 0x32;


}
