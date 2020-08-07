package com.example.androidcubosclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;

import static com.example.androidcubosclient.MainActivity.image_scale;

public class CanvasScreen extends ImageView {

    Bitmap bitmap;
    Paint paint = new Paint();

    public CanvasScreen(Context context) {
        super(context);
        bitmap = Bitmap.createBitmap(1080/image_scale,2340/image_scale, Bitmap.Config.RGB_565);
        setImageBitmap(bitmap);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //long start = System.currentTimeMillis();
        //Log.i("timing", "canvas start");
        super.onDraw(canvas);
        //long finish = System.currentTimeMillis() - start;
        //Log.i("timing", "canvas finish: " + finish + " ms");
        /*
        try {
            Thread.sleep(100);
            this.invalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
