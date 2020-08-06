package com.example.androidcubosclient.helpers.binaryImages;


import java.io.IOException;

public class BinaryImage_24bit extends BinaryImage {
    public BinaryImage_24bit(int width, int height) {
        super(width, height);
        type = Type.COLOR_24BIT;

        data = new byte[width*height*3];

        for(int i=0; i<data.length; i++){
            data[i] = -128;
        }
    }

    /*
    public BinaryImage_24bit(String imagePath) throws IOException {
        super(imagePath);
        type = Type.COLOR_24BIT;
    }*/

    @Override
    public void setColorPixel(int x, int y, byte r, byte g, byte b){
        int position = (x + (getWidth())*y)*3;
        data[position]      = r;
        data[position + 1]  = g;
        data[position + 2]  = b;
    }

    @Override
    public void setColorPixel(int x, int y, byte r, byte g, byte b, byte a){
        int position = (x + (getWidth())*y)*3;
        data[position]      = r;
        data[position + 1]  = g;
        data[position + 2]  = b;
    }

    @Override
    public byte[] getColorPixel(int x, int y){
        int position = (x + (getWidth())*y)*3;
        byte pixel[] = {
                data[position],
                data[position + 1],
                data[position + 2]
        };
        return pixel;
    }

    /*
    @Override
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
    }*/

    @Override
    public byte[] getColorPixel_alpha(int x, int y){
        int position = (x + (getWidth())*y)*3;
        byte pixel[] = {
                data[position],
                data[position + 1],
                data[position + 2],
                127
        };
        return pixel;
    }

    /*
    @Override
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
    */
}
