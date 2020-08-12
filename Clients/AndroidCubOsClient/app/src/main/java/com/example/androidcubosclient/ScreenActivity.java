package com.example.androidcubosclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.androidcubosclient.connectors.ClientSocket;
import com.example.androidcubosclient.helpers.ByteConverter;
import com.example.androidcubosclient.helpers.Protocol;

import static com.example.androidcubosclient.helpers.ClientSessionSettings.image_scale;

public class ScreenActivity extends AppCompatActivity {

    private CanvasScreen mContentView;
    char eventTouchPosition_coords[];
    byte eventStartTouchPosition[];
    char[] eventStartTouchPosition_coords;
    byte eventTouchPositionLast[];
    final char minClickPositionDiff = 15; // If position between touch down and touch up less then N, it is tab, else drag

    ClientSocket clientSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = new CanvasScreen(this);
        setContentView(mContentView);

        /*
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mContentView.invalidate();
                ActionBar actionBar = getActionBar();
                if(actionBar!=null) actionBar.hide();
            }
        });*/

        clientSocket = new ClientSocket("10.0.0.153" , 8000, mContentView);
        mContentView.setOnTouchListener(onScreenTouchListener);

    }

    View.OnTouchListener onScreenTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            char xPosition = (char)((int)event.getX()/image_scale);
            char yPosition = (char)((int)event.getY()/image_scale);

            char xPosition_coords = (char)((int)event.getX());
            char yPosition_coords = (char)((int)event.getY());

            byte x_bytes[], y_bytes[], eventData[];

            x_bytes = ByteConverter.char_to_bytes((char)(xPosition));
            y_bytes = ByteConverter.char_to_bytes((char)(yPosition));

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.d("touch", "On touch DOWN x: " + Math.floor(xPosition) + ", y: " + Math.floor(yPosition));
                    eventData = new byte[5];

                    eventData[0] = Protocol._1_3_EVENT_TOUCH_DOWN;
                    eventData[1] = x_bytes[0];
                    eventData[2] = x_bytes[1];
                    eventData[3] = y_bytes[0];
                    eventData[4] = y_bytes[1];

                    eventTouchPosition_coords = new char[]{xPosition, yPosition};
                    eventStartTouchPosition_coords = new char[]{xPosition_coords, yPosition_coords};

                    eventStartTouchPosition = new byte[4];
                    eventStartTouchPosition[0] = x_bytes[0];
                    eventStartTouchPosition[1] = x_bytes[1];
                    eventStartTouchPosition[2] = y_bytes[0];
                    eventStartTouchPosition[3] = y_bytes[1];

                    clientSocket.addMessage(eventData);

                    break;
                case MotionEvent.ACTION_UP:

                    if(Math.abs(xPosition_coords - eventStartTouchPosition_coords[0])<minClickPositionDiff && Math.abs(yPosition_coords - eventStartTouchPosition_coords[1])<minClickPositionDiff){
                        eventData = new byte[5];
                        eventData[0] = Protocol._1_1_EVENT_TOUCH_TAP;
                        eventData[1] = x_bytes[0];
                        eventData[2] = x_bytes[1];
                        eventData[3] = y_bytes[0];
                        eventData[4] = y_bytes[1];

                        Log.d("touch", "On touch TAP x: " + Math.floor(xPosition) + ", y: " + Math.floor(yPosition));
                        clientSocket.addMessage(eventData);
                    }else{

                        Log.d("touch", "On touch MOVE FINISHED x: " + Math.floor(xPosition) + ", y: " + Math.floor(yPosition));
                        eventData = new byte[9];
                        eventData[0] = Protocol._1_5_EVENT_TOUCH_MOVE_FINISHED;
                        eventData[1] = x_bytes[0];
                        eventData[2] = x_bytes[1];
                        eventData[3] = y_bytes[0];
                        eventData[4] = y_bytes[1];
                        eventData[5] = eventStartTouchPosition[0];
                        eventData[6] = eventStartTouchPosition[1];
                        eventData[7] = eventStartTouchPosition[2];
                        eventData[8] = eventStartTouchPosition[3];

                        clientSocket.addMessage(eventData);
                    }

                    Log.d("touch", "On touch UP x: " + Math.floor(xPosition) + ", y: " + Math.floor(yPosition));

                    eventData = new byte[5];
                    eventData[0] = Protocol._1_2_EVENT_TOUCH_UP;
                    eventData[1] = x_bytes[0];
                    eventData[2] = x_bytes[1];
                    eventData[3] = y_bytes[0];
                    eventData[4] = y_bytes[1];

                    clientSocket.addMessage(eventData);

                    eventStartTouchPosition = null;
                    eventTouchPositionLast = null;
                    eventStartTouchPosition_coords = null;

                    break;

                case MotionEvent.ACTION_MOVE:

                    Log.d("touch", "On touch MOVE MOVE  x: " + Math.floor(xPosition) + ", y: " + Math.floor(yPosition));

                    eventData = new byte[13];

                    if(eventTouchPositionLast ==null) eventTouchPositionLast = eventStartTouchPosition;

                    eventData[0]  = Protocol._1_4_EVENT_TOUCH_MOVE;
                    eventData[1]  = x_bytes[0];
                    eventData[2]  = x_bytes[1];
                    eventData[3]  = y_bytes[0];
                    eventData[4]  = y_bytes[1];
                    eventData[5]  = eventTouchPositionLast[0];
                    eventData[6]  = eventTouchPositionLast[1];
                    eventData[7]  = eventTouchPositionLast[2];
                    eventData[8]  = eventTouchPositionLast[3];
                    eventData[9]  = eventTouchPositionLast[0];
                    eventData[10] = eventTouchPositionLast[1];
                    eventData[11] = eventTouchPositionLast[2];
                    eventData[12] = eventTouchPositionLast[3];

                    eventTouchPositionLast = new byte[4];
                    eventTouchPositionLast[0] = x_bytes[0];
                    eventTouchPositionLast[1] = x_bytes[1];
                    eventTouchPositionLast[2] = y_bytes[0];
                    eventTouchPositionLast[3] = y_bytes[1];

                    clientSocket.addMessage(eventData);

                    break;
            }

            return true;
        }
    };

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
