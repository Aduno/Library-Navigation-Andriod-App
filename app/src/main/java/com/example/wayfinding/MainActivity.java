package com.example.wayfinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.wayfinding.classes.UserSettings;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ToggleButton lightButton;
    private ToggleButton soundButton;
    private LinearLayout main;
    protected UserSettings userSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate the user setting
        //In the future, might be good to save this into local storage so settings dont revert every
        userSettings = new UserSettings(false,false);

        main = findViewById(R.id.mainFrame);
        lightButton = findViewById(R.id.toggleLight);
        soundButton = findViewById(R.id.soundIcon);
        lightButton.setText(null);
        lightButton.setTextOn(null);
        lightButton.setTextOff(null);

        soundButton.setText(null);
        soundButton.setTextOn(null);
        soundButton.setTextOff(null);


        soundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    soundButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_off_24, null));
                    //Play sound enabled audio cue
                    userSettings.setAudioMode(true);
                } else {
                    soundButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_up_24, null));
                    //Play sound disable audio cue
                    userSettings.setAudioMode(false);
                }
            }
        });

        //Initializes the darkmode toggle button
        lightButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness_high_24, null));
                    main.setBackgroundColor(Color.rgb(100, 100, 100));
                    userSettings.setDarkMode(true);

                } else {
                    lightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness_3_24, null));
                    main.setBackgroundColor(Color.WHITE);
                    userSettings.setDarkMode(false);
                }
            }
        });
    }


        //Sends the user to navigation/home page
        public void goToHome (View v){
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            intent.putExtra("key",userSettings);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        //Sends the user to login page
        public void goToSignin (View v){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
