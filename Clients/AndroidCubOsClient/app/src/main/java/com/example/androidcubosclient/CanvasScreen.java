package com.example.androidcubosclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import static com.example.androidcubosclient.helpers.ClientSessionSettings.screen_height;
import static com.example.androidcubosclient.helpers.ClientSessionSettings.screen_width;

public class CanvasScreen extends androidx.appcompat.widget.AppCompatImageView {

    Bitmap bitmap;
    Paint paint = new Paint();

    public CanvasScreen(Context context) {
        super(context);
        bitmap = Bitmap.createBitmap(screen_width,screen_height, Bitmap.Config.RGB_565);
        setImageBitmap(bitmap);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
