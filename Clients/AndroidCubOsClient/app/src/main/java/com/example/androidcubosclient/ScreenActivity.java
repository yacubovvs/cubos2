package com.example.androidcubosclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;

import com.example.androidcubosclient.connectors.ClientSocket;

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
                ActionBar actionBar = getActionBar();
                if(actionBar!=null) actionBar.hide();
            }
        });

        ClientSocket clientSocket = new ClientSocket("192.168.1.38" , 8000, mContentView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
    }

    private void fullScreen() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        ActionBar actionBar = getActionBar();
        if(actionBar!=null) actionBar.hide();

    }


}
