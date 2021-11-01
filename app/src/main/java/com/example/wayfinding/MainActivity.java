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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ToggleButton lightButton;
    private ToggleButton soundButton;
    private ConstraintLayout main;
    private ConstraintLayout login;
    private ConstraintLayout nav;
    private LinearLayout admin;
    private LinearLayout home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = findViewById(R.id.mainFrame);
        lightButton = findViewById(R.id.toggleLight);


        lightButton.setText(null);
        lightButton.setTextOn(null);
        lightButton.setTextOff(null);

        main = findViewById(R.id.mainFrame);
        login = findViewById(R.id.loginFrame);
        admin = findViewById(R.id.adminFrame);
        home = findViewById(R.id.homeFrame);
        nav = findViewById(R.id.navFrame);

        soundButton = findViewById(R.id.soundIcon);

        soundButton.setText(null);
        soundButton.setTextOn(null);
        soundButton.setTextOff(null);



        soundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    soundButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_up_24, null));
                } else {
                    soundButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_off_24, null));
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
                    login.setBackgroundColor(Color.rgb(100, 100, 100));
                    admin.setBackgroundColor(Color.rgb(100, 100, 100));
                    home.setBackgroundColor(Color.rgb(100, 100, 100));
                    nav.setBackgroundColor(Color.rgb(100, 100, 100));
                } else {
                    lightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness_3_24, null));
                    main.setBackgroundColor(Color.WHITE);
                    login.setBackgroundColor(Color.WHITE);
                    admin.setBackgroundColor(Color.rgb(100, 100, 100));
                    home.setBackgroundColor(Color.rgb(100, 100, 100));
                    nav.setBackgroundColor(Color.rgb(100, 100, 100));
                }
            }
        });
    }


        //Sends the user to navigation/home page
        public void goToHome (View v){
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        //Sends the user to login page
        public void goToSignin (View v){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        public void toggleSound (View v){
            ImageButton sound = findViewById(R.id.soundIcon);
        }

    }
