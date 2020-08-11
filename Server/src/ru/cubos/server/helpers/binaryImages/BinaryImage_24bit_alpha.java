package ru.cubos.server.helpers.binaryImages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BinaryImage_24bit_alpha extends BinaryImage {
    public BinaryImage_24bit_alpha(int width, int height) {
        super(width, height);
        type = Type.COLOR_24BIT_ALPHA;

        data = new byte[width*height*4];

        for(int i=0; i<data.length; i++){
            data[i] = -128;
        }

    }

    public BinaryImage_24bit_alpha(String imagePath) throws IOException {
        super(imagePath);
        type = Type.COLOR_24BIT_ALPHA;
    }

    @Override
    public void setColorPixel(int x, int y, byte r, byte g, byte b){
        int position = (x + (getWidth())*y)*4;
        data[position]      = r;
        data[position + 1]  = g;
        data[position + 2]  = b;
        data[position + 3]  = 127;
    }

    @Override
    public void setColorPixel(int x, int y, byte r, byte g, byte b, byte a){
        int position = (x + (getWidth())*y)*4;
        data[position]      = r;
        data[position + 1]  = g;
        data[position + 2]  = b;
        data[position + 3]  = a;
    }

    @Override
    public byte[] getColorPixel(int x, int y){
        int position = (x + (getWidth())*y)*4;
        byte pixel[] = {
                data[position],
                data[position + 1],
                data[position + 2],
                data[position + 3]
        };
        return pixel;
    }

    @Override
    public byte[] getColorPixel_alpha(int x, int y){
        int position = (x + (getWidth())*y)*4;
        byte pixel[] = {
                data[position],
                data[position + 1],
                data[position + 2],
                data[position + 3]
        };
        return pixel;
    }

    @Override
    public void setImage(BufferedImage image){
        data = new byte[image.getHeight() * image.getWidth() * 4];
        this.setHeight((char)image.getHeight());
        this.setWidth((char)image.getWidth());

        for(char y=0; y<image.getHeight(); y++){
            for(char x=0; x<image.getWidth(); x++){
                int color = image.getRGB(x, y);

                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                int alpha = (color & 0xff000000) >> 24;

                this.setColorPixel(x,y, (byte)(red-128), (byte)(green-128), (byte)(blue-128), (byte)(alpha-128));

                continue;
            }
        }
    }

    @Override
    public BufferedImage getBufferedImageImage(){
        BufferedImage bufferedImage = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int y=0; y<getHeight(); y++) {
            for(int x=0; x<getWidth(); x++){

                byte pixel[] = getColorPixel_alpha(x, y);
                int red = pixel[0] + 128;
                int green = pixel[1] + 128;
                int blue = pixel[2] + 128;
                int alpha = pixel[3] + 128;
                Color color = new Color(red, green, blue, alpha);
                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }

        return bufferedImage;
    }

    @Override
    public void setColorPixel(int x, int y, byte rgb[]){setColorPixel(x, y, rgb[0], rgb[1], rgb[2]);}
}
