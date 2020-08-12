package com.example.androidcubosclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidcubosclient.helpers.Preferences;
import com.example.androidcubosclient.helpers.ClientSessionSettings;

public class MainActivity extends AppCompatActivity {

    Button connectButton;
    EditText editTextHostAddress;
    EditText editTextHostPort;
    EditText editTextScreenWidth;
    EditText editTextScreenHeight;
    EditText editTextScreenScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.init(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextHostAddress     = findViewById(R.id.editTextHostAddress);
        editTextHostPort        = findViewById(R.id.editTextHostPort);
        editTextScreenWidth     = findViewById(R.id.editTextScreenWidth);
        editTextScreenHeight    = findViewById(R.id.editTextScreenHeight);
        editTextScreenScale     = findViewById(R.id.editTextScreenScale);

        editTextHostAddress.setText(Preferences.getString("SETTINGS_HOST_ADDRESS", "192.168.1.1"));
        editTextHostPort.setText(Preferences.getString("SETTINGS_HOST_PORT", "8000"));
        editTextScreenScale.setText(Preferences.getString("IMAGE_SCALE", "1"));
        editTextScreenWidth.setText(Preferences.getString("SCREEN_WIDTH", "1080"));
        editTextScreenHeight.setText(Preferences.getString("SCREEN_HEIGHT", "2320"));

        //Intent intent = new Intent(getBaseContext(), ScreenActivity.class);
        //startActivity(intent);

        connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.putString("SETTINGS_HOST_ADDRESS", editTextHostAddress.getEditableText().toString());
                Preferences.putString("SETTINGS_HOST_PORT", editTextHostPort.getEditableText().toString());
                Preferences.putString("IMAGE_SCALE", editTextScreenScale.getEditableText().toString());
                Preferences.putString("SCREEN_WIDTH", editTextScreenWidth.getEditableText().toString());
                Preferences.putString("SCREEN_HEIGHT", editTextScreenHeight.getEditableText().toString());

                ClientSessionSettings.host_address = Preferences.getString("SETTINGS_HOST_ADDRESS", "192.168.1.1");
                ClientSessionSettings.host_port = Preferences.getInt("SETTINGS_HOST_PORT", 8000);
                ClientSessionSettings.image_scale = (byte)Preferences.getInt("IMAGE_SCALE", 1);
                ClientSessionSettings.screen_width = Preferences.getInt("SCREEN_WIDTH", 1080);
                ClientSessionSettings.screen_height = Preferences.getInt("SCREEN_HEIGHT", 2320);

                Intent intent = new Intent(getBaseContext(), ScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
