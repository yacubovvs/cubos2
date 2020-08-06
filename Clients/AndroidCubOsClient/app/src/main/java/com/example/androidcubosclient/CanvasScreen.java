package com.example.androidcubosclient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.androidcubosclient.helpers.binaryImages.BinaryImage;
import com.example.androidcubosclient.helpers.binaryImages.BinaryImage_24bit;

public class CanvasScreen extends View {

    BinaryImage binaryImage;
    Paint paint = new Paint();

    public CanvasScreen(Context context) {
        super(context);
        binaryImage = new BinaryImage_24bit(512,512);
    }

    public BinaryImage getBinaryImage(){
        return binaryImage;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        updateByBinaryImage(canvas);
    }

    public void updateByBinaryImage(Canvas canvas){
        for (int x=0; x<binaryImage.getWidth(); x++){
            for (int y=0; y<binaryImage.getHeight(); y++){
                byte color_pixel[] = binaryImage.getColorPixel(x,y);
                int color = Color.rgb(color_pixel[0] + 128, color_pixel[1] + 128, color_pixel[2] + 128);
                paint.setColor(color);
                canvas.drawPoint(x,y,paint);
            }
        }

    }
}
