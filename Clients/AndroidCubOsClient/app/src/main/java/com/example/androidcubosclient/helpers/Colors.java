package com.example.androidcubosclient.helpers;

public class Colors {
    public static final byte COLOR_PINK[]               =  {    127,    0,      0       };
    public static final byte COLOR_DARK_RED[]           =  {    0,     -128,   -128     };

    public static final byte COLOR_RED[]                =  {    127,   -128,   -128     };
    public static final byte COLOR_GREEN[]              =  {   -128,    127,   -128     };
    public static final byte COLOR_BLUE[]               =  {   -128,   -128,    127     };
    public static final byte COLOR_DARK_BLUE[]          =  {   -128,   -128,    0       };
    public static final byte COLOR_BLACK[]              =  {   -128,   -128,   -128     };
    public static final byte COLOR_WHITE[]              =  {    127,    127,    127     };
    public static final byte COLOR_GRAY[]               =  {    0,      0,      0       };
    public static final byte COLOR_YELLOW[]             =  {    127,    127,   -128     };

    public static final byte COLOR_ALFA[]               =  {    126,   -128,    126     };
    public static final byte COLOR_LIGHT_GRAY[]         =  {    64,     64,     64      };
    public static final byte COLOR_DARK_GRAY[]          =  {   -63,    -63,    -63      };

    public static byte[] color_256_to_rgb(byte color){
        byte rgb[] = new byte[3];
        rgb[0] = (byte)(((color+128)>>5)*255/7 - 128);
        rgb[1] = (byte)((((color+128)>>2) & 0b00000111) * 255/7 - 128);
        rgb[2] = (byte)(((color+128) & 0b00000011) * 255/3 - 128);

        return rgb;
    }

    public static byte rgb_to_color_256(byte rgb[]){ return rgb_to_color_256(rgb[0], rgb[1], rgb[2]);}
    public static byte rgb_to_color_256(byte r, byte g, byte b){
        int ir = (r+128)*7/255;
        int ig = (g+128)*7/255;
        int ib = (b+128)*3/255;
        return (byte)(((ir)<<5) + ((ig)<<2) + (ib)-128);
    }
}
