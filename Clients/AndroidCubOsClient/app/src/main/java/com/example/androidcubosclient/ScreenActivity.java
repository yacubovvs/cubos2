package com.example.androidcubosclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.example.androidcubosclient.connectors.ClientSocket;
import com.example.androidcubosclient.helpers.binaryImages.BinaryImage;

public class ScreenActivity extends AppCompatActivity {

    private CanvasScreen mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = new CanvasScreen(this);
        setContentView(mContentView);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentView.invalidate();
            }
        });

        ClientSocket clientSocket = new ClientSocket("10.0.0.153" , 8000, mContentView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
    }

    private void fullScreen() { mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE); }


}
