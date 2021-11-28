package com.example.wayfinding;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.wayfinding.classes.UserSettings;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class MainActivity extends AppCompatActivity {



    private ToggleButton lightButton;
    private ToggleButton soundButton;
    private LinearLayout main;
    protected UserSettings userSettings;
    private SwitchCompat languageToggle;
    MediaPlayer player;
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
        languageToggle = findViewById(R.id.languageSwitch);



        languageToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userSettings.setFrench(b);
                settingsUpdate();
            }
        });

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
                    if(userSettings.getFrench()){
                        play2(soundButton);

                    }
                   else{
                       play(soundButton);
                    }
                } else {
                    soundButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_up_24, null));
                    //Play sound disable audio cue
                    userSettings.setAudioMode(false);
                    stopPlayer();
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

    public void play(View buttonView ){
        if (player==null){
            player= MediaPlayer.create(MainActivity.this, R.raw.english_soundb);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }
    public void play2(View buttonView ){
        if (player==null){
            player= MediaPlayer.create(MainActivity.this, R.raw.french_soundb);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }
    private void stopPlayer(){
        if(player!=null){
            player.release();
            player=null;
            Toast.makeText(this, "Mediaplayer released",Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Function for swipe to next activity
     * Tutorial from https://www.youtube.com/watch?v=Gsuz2j11qgc
     * @param motionEvent
     * @return
     */
    float x1,y1,x2,y2;
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                if(x1>x2+175){
                    Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                    intent.putExtra("key",userSettings);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
        }
        return false;
    }
        //Sends the user to navigation/home page
        public void goToHome (View v){
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            intent.putExtra("key",userSettings);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        //Sends the user to login page
        public void goToSignin (View v) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("key",userSettings);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        public void goToNav (View v){
            Intent intent = new Intent(MainActivity.this, NavigationScreen.class);
            intent.putExtra("key",userSettings);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        }
        public void settingsUpdate(){
            TextView textView;
            TextView textView1;
            TextView textView2;
            TextView textView3;
            Button sign;
            //Could have done this in one line
            textView = findViewById(R.id.welcome_1);
            textView2 = findViewById(R.id.welcome_3);
            textView1 = findViewById(R.id.welcome_2);
            textView3 = findViewById(R.id.welcome_4);
            sign = findViewById(R.id.signin);

            if(userSettings.getFrench()){
                textView.setText(R.string.welcome_text_1_fr);
                textView1.setText(R.string.welcome_text_2_fr);
                textView2.setText(R.string.welcome_text_3_fr);
                textView3.setText(R.string.welcome_text_4_fr);
                sign.setText(R.string.sign_in_fr);
            }
            else{
                textView.setText(R.string.welcome_text_1);
                textView1.setText(R.string.welcome_text_2);
                textView2.setText(R.string.welcome_text_3);
                textView3.setText(R.string.welcome_text_4);
                sign.setText(R.string.sign_in);
            }
        }
    }
