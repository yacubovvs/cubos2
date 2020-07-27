package ru.cubos.server.helpers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BinaryImage {

    protected byte data[];

    private int width;
    private int height;

    private String imagePath;

    public BinaryImage(int width, int height){
        setWidth(width);
        setHeight(height);

        data = new byte[width*height*3];

        for(int i=0; i<data.length; i++){
            data[i] = -128;
        }
    }

    public BinaryImage(String imagePath) throws IOException {
        this.imagePath = imagePath;
        BufferedImage image = ImageIO.read(new File(imagePath));
        setImage(image);
    }

    public void setColorPixel(int x, int y, byte r, byte g, byte b){
        int position = (x + (getWidth())*y)*3;
        data[position]      = r;
        data[position + 1]  = g;
        data[position + 2]  = b;
    }

    public byte[] getColorPixel(int x, int y){
        int position = (x + (getWidth())*y)*3;
        byte pixel[] = {
                data[position],
                data[position + 1],
                data[position + 2]
        };
        return pixel;
    }

    public void setColorPixel(int x, int y, byte rgb[]){setColorPixel(x, y, rgb[0], rgb[1], rgb[2]);}

    public int getWidth() { return width; }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) { this.height = height; }

    public void setImage(BufferedImage image){
        data = new byte[image.getHeight() * image.getWidth() * 3];
        this.setHeight((char)image.getHeight());
        this.setWidth((char)image.getWidth());

        for(char y=0; y<image.getHeight(); y++){
            for(char x=0; x<image.getWidth(); x++){
                int color = image.getRGB(x, y);

                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;

                this.setColorPixel(x,y, (byte)(red-128), (byte)(green-128), (byte)(blue-128));

                continue;
            }
        }
    }

    public BufferedImage getBufferedImageImage(){
        BufferedImage bufferedImage = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int y=0; y<getHeight(); y++) {
            for(int x=0; x<getWidth(); x++){

                byte pixel[] = getColorPixel(x, y);
                int red = pixel[0] + 128;
                int green = pixel[1] + 128;
                int blue = pixel[2] + 128;
                Color color = new Color(red, green, blue);
                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }

        return bufferedImage;
    }


    /*
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * # #                                                                                             # #
    * # #                                           HELPERS                                           # #
    * # #                                                                                             # #
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * */

    byte check_byte(int val){return check_byte((short) val);}
    byte check_byte(short val){
        if(val<-128) val = -128;
        if(val>127) val = 127;
        return (byte)val;
    }

    /*
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * # #                                                                                             # #
    * # #                                           DRAWING                                           # #
    * # #                                                                                             # #
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    * */

    static final char font_cubos[] = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x3E, 0x5B, 0x4F, 0x5B, 0x3E, 0x3E, 0x6B, 0x4F, 0x6B, 0x3E, 0x1C, 0x3E, 0x7C, 0x3E, 0x1C, 0x18, 0x3C, 0x7E, 0x3C, 0x18, 0x1C, 0x57, 0x7D, 0x57, 0x1C, 0x1C, 0x5E, 0x7F, 0x5E, 0x1C, 0x00, 0x18, 0x3C, 0x18, 0x00,
        0xFF, 0xE7, 0xC3, 0xE7, 0xFF, 0x00, 0x18, 0x24, 0x18, 0x00, 0xFF, 0xE7, 0xDB, 0xE7, 0xFF, 0x30, 0x48, 0x3A, 0x06, 0x0E, 0x26, 0x29, 0x79, 0x29, 0x26, 0x40, 0x7F, 0x05, 0x05, 0x07, 0x40, 0x7F, 0x05, 0x25, 0x3F, 0x5A, 0x3C, 0xE7, 0x3C, 0x5A,
        0x7F, 0x3E, 0x1C, 0x1C, 0x08, 0x08, 0x1C, 0x1C, 0x3E, 0x7F, 0x14, 0x22, 0x7F, 0x22, 0x14, 0x5F, 0x5F, 0x00, 0x5F, 0x5F, 0x06, 0x09, 0x7F, 0x01, 0x7F, 0x00, 0x66, 0x89, 0x95, 0x6A, 0x60, 0x60, 0x60, 0x60, 0x60, 0x94, 0xA2, 0xFF, 0xA2, 0x94,
        0x08, 0x04, 0x7E, 0x04, 0x08, 0x10, 0x20, 0x7E, 0x20, 0x10, 0x08, 0x08, 0x2A, 0x1C, 0x08, 0x08, 0x1C, 0x2A, 0x08, 0x08, 0x1E, 0x10, 0x10, 0x10, 0x10, 0x0C, 0x1E, 0x0C, 0x1E, 0x0C, 0x30, 0x38, 0x3E, 0x38, 0x30, 0x06, 0x0E, 0x3E, 0x0E, 0x06,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5F, 0x00, 0x00, 0x00, 0x07, 0x00, 0x07, 0x00, 0x14, 0x7F, 0x14, 0x7F, 0x14, 0x24, 0x2A, 0x7F, 0x2A, 0x12, 0x23, 0x13, 0x08, 0x64, 0x62, 0x36, 0x49, 0x56, 0x20, 0x50, 0x00, 0x08, 0x07, 0x03, 0x00,
        0x00, 0x1C, 0x22, 0x41, 0x00, 0x00, 0x41, 0x22, 0x1C, 0x00, 0x2A, 0x1C, 0x7F, 0x1C, 0x2A, 0x08, 0x08, 0x3E, 0x08, 0x08, 0x00, 0x80, 0x70, 0x30, 0x00, 0x08, 0x08, 0x08, 0x08, 0x08, 0x00, 0x00, 0x60, 0x60, 0x00, 0x20, 0x10, 0x08, 0x04, 0x02,
        0x3E, 0x51, 0x49, 0x45, 0x3E, 0x00, 0x42, 0x7F, 0x40, 0x00, 0x72, 0x49, 0x49, 0x49, 0x46, 0x21, 0x41, 0x49, 0x4D, 0x33, 0x18, 0x14, 0x12, 0x7F, 0x10, 0x27, 0x45, 0x45, 0x45, 0x39, 0x3C, 0x4A, 0x49, 0x49, 0x31, 0x41, 0x21, 0x11, 0x09, 0x07,
        0x36, 0x49, 0x49, 0x49, 0x36, 0x46, 0x49, 0x49, 0x29, 0x1E, 0x00, 0x00, 0x14, 0x00, 0x00, 0x00, 0x40, 0x34, 0x00, 0x00, 0x00, 0x08, 0x14, 0x22, 0x41, 0x14, 0x14, 0x14, 0x14, 0x14, 0x00, 0x41, 0x22, 0x14, 0x08, 0x02, 0x01, 0x59, 0x09, 0x06,
        0x3E, 0x41, 0x5D, 0x59, 0x4E, 0x7C, 0x12, 0x11, 0x12, 0x7C, 0x7F, 0x49, 0x49, 0x49, 0x36, 0x3E, 0x41, 0x41, 0x41, 0x22, 0x7F, 0x41, 0x41, 0x41, 0x3E, 0x7F, 0x49, 0x49, 0x49, 0x41, 0x7F, 0x09, 0x09, 0x09, 0x01, 0x3E, 0x41, 0x41, 0x51, 0x73,
        0x7F, 0x08, 0x08, 0x08, 0x7F, 0x00, 0x41, 0x7F, 0x41, 0x00, 0x20, 0x40, 0x41, 0x3F, 0x01, 0x7F, 0x08, 0x14, 0x22, 0x41, 0x7F, 0x40, 0x40, 0x40, 0x40, 0x7F, 0x02, 0x1C, 0x02, 0x7F, 0x7F, 0x04, 0x08, 0x10, 0x7F, 0x3E, 0x41, 0x41, 0x41, 0x3E,
        0x7F, 0x09, 0x09, 0x09, 0x06, 0x3E, 0x41, 0x51, 0x21, 0x5E, 0x7F, 0x09, 0x19, 0x29, 0x46, 0x26, 0x49, 0x49, 0x49, 0x32, 0x03, 0x01, 0x7F, 0x01, 0x03, 0x3F, 0x40, 0x40, 0x40, 0x3F, 0x1F, 0x20, 0x40, 0x20, 0x1F, 0x3F, 0x40, 0x38, 0x40, 0x3F,
        0x63, 0x14, 0x08, 0x14, 0x63, 0x03, 0x04, 0x78, 0x04, 0x03, 0x61, 0x59, 0x49, 0x4D, 0x43, 0x00, 0x7F, 0x41, 0x41, 0x41, 0x02, 0x04, 0x08, 0x10, 0x20, 0x00, 0x41, 0x41, 0x41, 0x7F, 0x04, 0x02, 0x01, 0x02, 0x04, 0x40, 0x40, 0x40, 0x40, 0x40,
        0x00, 0x03, 0x07, 0x08, 0x00, 0x20, 0x54, 0x54, 0x78, 0x40, 0x7F, 0x28, 0x44, 0x44, 0x38, 0x38, 0x44, 0x44, 0x44, 0x28, 0x38, 0x44, 0x44, 0x28, 0x7F, 0x38, 0x54, 0x54, 0x54, 0x18, 0x00, 0x08, 0x7E, 0x09, 0x02, 0x18, 0xA4, 0xA4, 0x9C, 0x78,
        0x7F, 0x08, 0x04, 0x04, 0x78, 0x00, 0x44, 0x7D, 0x40, 0x00, 0x20, 0x40, 0x40, 0x3D, 0x00, 0x7F, 0x10, 0x28, 0x44, 0x00, 0x00, 0x41, 0x7F, 0x40, 0x00, 0x7C, 0x04, 0x78, 0x04, 0x78, 0x7C, 0x08, 0x04, 0x04, 0x78, 0x38, 0x44, 0x44, 0x44, 0x38,
        0xFC, 0x18, 0x24, 0x24, 0x18, 0x18, 0x24, 0x24, 0x18, 0xFC, 0x7C, 0x08, 0x04, 0x04, 0x08, 0x48, 0x54, 0x54, 0x54, 0x24, 0x04, 0x04, 0x3F, 0x44, 0x24, 0x3C, 0x40, 0x40, 0x20, 0x7C, 0x1C, 0x20, 0x40, 0x20, 0x1C, 0x3C, 0x40, 0x30, 0x40, 0x3C,
        0x44, 0x28, 0x10, 0x28, 0x44, 0x4C, 0x90, 0x90, 0x90, 0x7C, 0x44, 0x64, 0x54, 0x4C, 0x44, 0x00, 0x08, 0x36, 0x41, 0x00, 0x00, 0x00, 0x77, 0x00, 0x00, 0x00, 0x41, 0x36, 0x08, 0x00, 0x02, 0x01, 0x02, 0x04, 0x02, 0x3C, 0x26, 0x23, 0x26, 0x3C,
        0x1E, 0xA1, 0xA1, 0x61, 0x12, 0x3A, 0x40, 0x40, 0x20, 0x7A, 0x38, 0x54, 0x54, 0x55, 0x59, 0x21, 0x55, 0x55, 0x79, 0x41, 0x22, 0x54, 0x54, 0x78, 0x42, 0x21, 0x55, 0x54, 0x78, 0x40, 0x20, 0x54, 0x55, 0x79, 0x40, 0x0C, 0x1E, 0x52, 0x72, 0x12,
        0x39, 0x55, 0x55, 0x55, 0x59, 0x39, 0x54, 0x54, 0x54, 0x59, 0x39, 0x55, 0x54, 0x54, 0x58, 0x00, 0x00, 0x45, 0x7C, 0x41, 0x00, 0x02, 0x45, 0x7D, 0x42, 0x00, 0x01, 0x45, 0x7C, 0x40, 0x7D, 0x12, 0x11, 0x12, 0x7D, 0xF0, 0x28, 0x25, 0x28, 0xF0,
        0x7C, 0x54, 0x55, 0x45, 0x00, 0x20, 0x54, 0x54, 0x7C, 0x54, 0x7C, 0x0A, 0x09, 0x7F, 0x49, 0x32, 0x49, 0x49, 0x49, 0x32, 0x3A, 0x44, 0x44, 0x44, 0x3A, 0x32, 0x4A, 0x48, 0x48, 0x30, 0x3A, 0x41, 0x41, 0x21, 0x7A, 0x3A, 0x42, 0x40, 0x20, 0x78,
        0x00, 0x9D, 0xA0, 0xA0, 0x7D, 0x3D, 0x42, 0x42, 0x42, 0x3D, 0x3D, 0x40, 0x40, 0x40, 0x3D, 0x3C, 0x24, 0xFF, 0x24, 0x24, 0x48, 0x7E, 0x49, 0x43, 0x66, 0x2B, 0x2F, 0xFC, 0x2F, 0x2B, 0xFF, 0x09, 0x29, 0xF6, 0x20, 0xC0, 0x88, 0x7E, 0x09, 0x03,
        0x20, 0x54, 0x54, 0x79, 0x41, 0x00, 0x00, 0x44, 0x7D, 0x41, 0x30, 0x48, 0x48, 0x4A, 0x32, 0x38, 0x40, 0x40, 0x22, 0x7A, 0x00, 0x7A, 0x0A, 0x0A, 0x72, 0x7D, 0x0D, 0x19, 0x31, 0x7D, 0x26, 0x29, 0x29, 0x2F, 0x28, 0x26, 0x29, 0x29, 0x29, 0x26,
        0x30, 0x48, 0x4D, 0x40, 0x20, 0x38, 0x08, 0x08, 0x08, 0x08, 0x08, 0x08, 0x08, 0x08, 0x38, 0x2F, 0x10, 0xC8, 0xAC, 0xBA, 0x2F, 0x10, 0x28, 0x34, 0xFA, 0x00, 0x00, 0x7B, 0x00, 0x00, 0x08, 0x14, 0x2A, 0x14, 0x22, 0x22, 0x14, 0x2A, 0x14, 0x08,
        0x55, 0x00, 0x55, 0x00, 0x55, 0xAA, 0x55, 0xAA, 0x55, 0xAA, 0xFF, 0x55, 0xFF, 0x55, 0xFF, 0x00, 0x00, 0x00, 0xFF, 0x00, 0x10, 0x10, 0x10, 0xFF, 0x00, 0x14, 0x14, 0x14, 0xFF, 0x00, 0x10, 0x10, 0xFF, 0x00, 0xFF, 0x10, 0x10, 0xF0, 0x10, 0xF0,
        0x14, 0x14, 0x14, 0xFC, 0x00, 0x14, 0x14, 0xF7, 0x00, 0xFF, 0x00, 0x00, 0xFF, 0x00, 0xFF, 0x14, 0x14, 0xF4, 0x04, 0xFC, 0x14, 0x14, 0x17, 0x10, 0x1F, 0x10, 0x10, 0x1F, 0x10, 0x1F, 0x14, 0x14, 0x14, 0x1F, 0x00, 0x10, 0x10, 0x10, 0xF0, 0x00,
        0x00, 0x00, 0x00, 0x1F, 0x10, 0x10, 0x10, 0x10, 0x1F, 0x10, 0x10, 0x10, 0x10, 0xF0, 0x10, 0x00, 0x00, 0x00, 0xFF, 0x10, 0x10, 0x10, 0x10, 0x10, 0x10, 0x10, 0x10, 0x10, 0xFF, 0x10, 0x00, 0x00, 0x00, 0xFF, 0x14, 0x00, 0x00, 0xFF, 0x00, 0xFF,
        0x00, 0x00, 0x1F, 0x10, 0x17, 0x00, 0x00, 0xFC, 0x04, 0xF4, 0x14, 0x14, 0x17, 0x10, 0x17, 0x14, 0x14, 0xF4, 0x04, 0xF4, 0x00, 0x00, 0xFF, 0x00, 0xF7, 0x14, 0x14, 0x14, 0x14, 0x14, 0x14, 0x14, 0xF7, 0x00, 0xF7, 0x14, 0x14, 0x14, 0x17, 0x14,
        0x10, 0x10, 0x1F, 0x10, 0x1F, 0x14, 0x14, 0x14, 0xF4, 0x14, 0x10, 0x10, 0xF0, 0x10, 0xF0, 0x00, 0x00, 0x1F, 0x10, 0x1F, 0x00, 0x00, 0x00, 0x1F, 0x14, 0x00, 0x00, 0x00, 0xFC, 0x14, 0x00, 0x00, 0xF0, 0x10, 0xF0, 0x10, 0x10, 0xFF, 0x10, 0xFF,
        0x14, 0x14, 0x14, 0xFF, 0x14, 0x10, 0x10, 0x10, 0x1F, 0x00, 0x00, 0x00, 0x00, 0xF0, 0x10, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xF0, 0xF0, 0xF0, 0xF0, 0xF0, 0xFF, 0xFF, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x0F, 0x0F, 0x0F, 0x0F, 0x0F,
        0x38, 0x44, 0x44, 0x38, 0x44, 0xFC, 0x4A, 0x4A, 0x4A, 0x34, 0x7E, 0x02, 0x02, 0x06, 0x06, 0x02, 0x7E, 0x02, 0x7E, 0x02, 0x63, 0x55, 0x49, 0x41, 0x63, 0x38, 0x44, 0x44, 0x3C, 0x04, 0x40, 0x7E, 0x20, 0x1E, 0x20, 0x06, 0x02, 0x7E, 0x02, 0x02,
        0x99, 0xA5, 0xE7, 0xA5, 0x99, 0x1C, 0x2A, 0x49, 0x2A, 0x1C, 0x4C, 0x72, 0x01, 0x72, 0x4C, 0x30, 0x4A, 0x4D, 0x4D, 0x30, 0x30, 0x48, 0x78, 0x48, 0x30, 0xBC, 0x62, 0x5A, 0x46, 0x3D, 0x3E, 0x49, 0x49, 0x49, 0x00, 0x7E, 0x01, 0x01, 0x01, 0x7E,
        0x2A, 0x2A, 0x2A, 0x2A, 0x2A, 0x44, 0x44, 0x5F, 0x44, 0x44, 0x40, 0x51, 0x4A, 0x44, 0x40, 0x40, 0x44, 0x4A, 0x51, 0x40, 0x00, 0x00, 0xFF, 0x01, 0x03, 0xE0, 0x80, 0xFF, 0x00, 0x00, 0x08, 0x08, 0x6B, 0x6B, 0x08, 0x36, 0x12, 0x36, 0x24, 0x36,
        0x06, 0x0F, 0x09, 0x0F, 0x06, 0x00, 0x00, 0x18, 0x18, 0x00, 0x00, 0x00, 0x10, 0x10, 0x00, 0x30, 0x40, 0xFF, 0x01, 0x01, 0x00, 0x1F, 0x01, 0x01, 0x1E, 0x00, 0x19, 0x1D, 0x17, 0x12, 0x00, 0x3C, 0x3C, 0x3C, 0x3C, 0x00, 0x00, 0x00, 0x00, 0x00
    };


    static final char FONT_CHAR_WIDTH   = 6;
    static final char FONT_CHAR_HEIGHT  = 8;

    public void drawString(int x, int y, String string, byte[] color, int fontSize){

        byte[] stringBytes = string.getBytes();
        for (int i=0; i<string.length(); i++){

            for (char char_part=0; char_part<5; char_part++){
                byte char_part_element = (byte)(font_cubos[stringBytes[i] *5 + char_part] - 128);
                for (char bit=0; bit<8; bit++){

                    if (((char_part_element >> (bit)) & 1) == (bit!=(char)7?1:0) ){
                        if(fontSize>1){
                            for(int j=0; j<fontSize; j++){
                                for(int jj=0; jj<fontSize; jj++){
                                    setColorPixel((char)(x + char_part*fontSize + i*FONT_CHAR_WIDTH*fontSize + jj), (char)(y + bit*fontSize - j + (fontSize-1)), color);
                                }
                            }
                        }else{
                            setColorPixel((char)(x + char_part + i*FONT_CHAR_WIDTH), (char)(y + bit), color);
                        }
                    }
                }
            }
        }
    }


    public void drawLine(int x0, int y0, int x1, int y1, byte[] color){

        if(x0<0) x0=0;
        if(x1<0) x1=0;
        if(y1<0) y1=0;
        if(y0<0) y0=0;
        if(x0>=getWidth()) x0=getWidth()-1;
        if(x1>=getWidth()) x1=getWidth()-1;
        if(y0>=getHeight()) y0=getHeight()-1;
        if(y1>=getHeight()) y1=getHeight()-1;

        int dy = y1 - y0; // Difference between y0 and y1
        int dx = x1 - x0; // Difference between x0 and x1
        int stepx, stepy;

        if (dy < 0)
        {
            dy = -dy;
            stepy = -1;
        }
        else
            stepy = 1;

        if (dx < 0)
        {
            dx = -dx;
            stepx = -1;
        }
        else
            stepx = 1;

        dy <<= 1; // dy is now 2*dy
        dx <<= 1; // dx is now 2*dx
        setColorPixel(x0, y0, color); // Draw the first pixel.

        if (dx > dy)
        {
            int fraction = dy - (dx >> 1);
            while (x0 != x1)
            {
                if (fraction >= 0)
                {
                    y0 += stepy;
                    fraction -= dx;
                }
                x0 += stepx;
                fraction += dy;
                setColorPixel(x0, y0, color);
            }
        }
        else
        {
            int fraction = dx - (dy >> 1);
            while (y0 != y1)
            {
                if (fraction >= 0)
                {
                    x0 += stepx;
                    fraction -= dy;
                }
                y0 += stepy;
                fraction += dx;
                setColorPixel(x0, y0, color);
            }
        }
    }

    public void drawRect(int x0, int y0, int x1, int y1, byte[] color, boolean fill){

        // check if the rectangle is to be filled
        if (fill)
        {
            int xDiff;

            if(x0 > x1)
                xDiff = x0 - x1+1; //Find the difference between the x vars
            else
                xDiff = x1 - x0+1;

            while(xDiff > 0)
            {
                drawLine(x0, y0, x0, y1, color);

                if(x0 > x1)
                    x0--;
                else
                    x0++;

                xDiff--;
            }
        }
        else
        {

            drawLine(x0, y0, x1, y0, color);
            drawLine(x0, y1, x1, y1, color);
            drawLine(x0, y0, x0, y1, color);
            drawLine(x1, y0, x1, y1, color);
        }
    }

    public void drawRect(int x0, int y0, int x1, int y1, byte[] color){
        drawRect(x0, y0, x1, y1, color, false);
    }

    int treangle_area(int x0, int y0, int x1, int y1, int x2, int y2){
        return Math.abs((x0 - x2)*(y1 - y2) + (x1-x2)*(y2-y0));
    }

    public void drawRect_custom(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3, byte[] color, boolean fill){
        if (fill){
            // all angles should be less thаn 180 degrees
            char min_x = (char)Math.min(Math.min(x0, x1), Math.min(x2, x3));
            char max_x = (char)Math.max(Math.max(x0, x1), Math.max(x2, x3));
            char min_y = (char)Math.min(Math.min(y0, y1), Math.min(y2, y3));
            char max_y = (char)Math.max(Math.max(y0, y1), Math.max(y2, y3));

            for (char i_x=min_x; i_x<max_x; i_x++){
                for (char i_y=min_y; i_y<max_y; i_y++){

                    if (
                            treangle_area(x0, y0, x1, y1, x2, y2) ==
                                    treangle_area(x0, y0, x1, y1, i_x, i_y) +
                                            treangle_area(x0, y0, x2, y2, i_x, i_y) +
                                            treangle_area(x2, y2, x1, y1, i_x, i_y)
                                    ||
                                    treangle_area(x0, y0, x3, y3, x2, y2) ==
                                            treangle_area(x0, y0, x3, y3, i_x, i_y) +
                                                    treangle_area(x0, y0, x2, y2, i_x, i_y) +
                                                    treangle_area(x2, y2, x3, y3, i_x, i_y)
                    ){
                        setColorPixel(i_x, i_y, color);
                    }
                }
            }
        }else{
            drawLine(x0, y0, x1, y1, color);
            drawLine(x1, y1, x2, y2, color);
            drawLine(x2, y2, x3, y3, color);
            drawLine(x3, y3, x0, y0, color);
        }
    }

    public BinaryImage filter_crop(char x0, char y0, char x1, char y1) {

        if(x0<0) x0=0;
        if(x1<0) x1=0;
        if(y0<0) y0=0;
        if(y1<0) y1=0;

        if(x0>x1){
            char x = x0;
            x0 = x1;
            x1 = x;
        }

        if(y0>y1){
            char y = y0;
            y0 = y1;
            y1 = y;
        }

        if(x1>=getWidth()) x1 = (char)(getWidth() - 1);
        if(y1>=getHeight()) y1 = (char)(getHeight() - 1);

        BinaryImage binaryImage = new BinaryImage((char)(x1-x0), (char)(y1-y0));

        for(char x=0; x<x1-x0; x++){
            for(char y=0; y<y1-y0; y++) {
                binaryImage.setColorPixel(x, y, getColorPixel((char)(x0+x), (char)(y0+y)));
            }
        }

        return binaryImage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public BinaryImage filter_crop(int x0, int y0, int x1, int y1){
        if(x0<0) x0=0;
        if(x1<0) x1=0;
        if(y0<0) y0=0;
        if(y1<0) y1=0;
        return filter_crop((char) x0, (char) y0, (char) x1, (char) y1);
    }

    public int[] drawImage(int x0, int y0, BinaryImage binaryImage){
        return drawImage(x0, y0, binaryImage, null);
    }

    public int[] drawImage(int x0, int y0, BinaryImage binaryImage, byte[] alfaColor){
        return drawImage(x0, y0, binaryImage.getWidth(), binaryImage.getHeight(), binaryImage, alfaColor);
    }

    public int[] drawImage(int x0, int y0, int x1, int y1, BinaryImage binaryImage){
        return drawImage(x0, y0, x1, y1, binaryImage, null);
    }

    public int[] drawImage(int x0, int y0, int x1, int y1, BinaryImage binaryImage, byte[] alfaColor){

        // x1 and y1 - maximum of image drawing (limits)

        byte[] imagepixel;
        int x_limit = Math.min(x1, Math.min(binaryImage.getWidth(), getWidth() - x0));
        int y_limit = Math.min(y1, Math.min(binaryImage.getHeight(), getHeight() - y0));

        for (int x=Math.max(-x0, 0); x<x_limit; x++){
            for (int y=Math.max(-y0, 0); y<y_limit; y++) {
                imagepixel = binaryImage.getColorPixel(x,y);
                if(
                        alfaColor == null ||
                        (
                            imagepixel[0]!=alfaColor[0] ||
                            imagepixel[1]!=alfaColor[1] ||
                            imagepixel[2]!=alfaColor[2]
                        )
                )
                setColorPixel(x + x0,y + y0, imagepixel);
            }
        }

        return new int[]{x_limit, y_limit};
    }


}
