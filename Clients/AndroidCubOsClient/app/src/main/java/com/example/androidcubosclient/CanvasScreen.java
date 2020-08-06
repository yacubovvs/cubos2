package com.example.androidcubosclient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.example.androidcubosclient.helpers.binaryImages.BinaryImage;
import com.example.androidcubosclient.helpers.binaryImages.BinaryImage_24bit;

import static com.example.androidcubosclient.MainActivity.image_scale;

public class CanvasScreen extends View {

    BinaryImage binaryImage;
    Paint paint = new Paint();



    public CanvasScreen(Context context) {
        super(context);
        binaryImage = new BinaryImage_24bit(1080/image_scale,2340/image_scale);
    }

    public BinaryImage getBinaryImage(){
        return binaryImage;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        updateByBinaryImage(canvas);
    }

    public void updateByBinaryImage(Canvas canvas){
        long start = System.currentTimeMillis();
        Log.i("timing", "canvas start");

        for (int x=0; x<binaryImage.getWidth(); x++){
            for (int y=0; y<binaryImage.getHeight(); y++){
                byte color_pixel[] = binaryImage.getColorPixel(x,y);
                int color = Color.rgb(color_pixel[0] + 128, color_pixel[1] + 128, color_pixel[2] + 128);
                paint.setColor(color);
                //canvas.drawPoint(x,y,paint);

                if(image_scale==1){
                    canvas.drawPoint(x,y,paint);
                } else{
                    for (int xi=0; xi<image_scale; xi++){
                        for (int yi=0; yi<image_scale; yi++){
                            canvas.drawPoint(x*image_scale + xi,y*image_scale + yi,paint);
                        }
                    }
                }

            }
        }

        long finish = System.currentTimeMillis() - start;
        Log.i("timing", "canvas finish: " + finish + " ms");

    }
}
