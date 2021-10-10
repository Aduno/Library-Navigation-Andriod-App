package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private ToggleButton lightButton;
    private ConstraintLayout main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = findViewById(R.id.mainFrame);
        lightButton = findViewById(R.id.toggleLight);

        lightButton.setText(null);
        lightButton.setTextOn(null);
        lightButton.setTextOff(null);

        lightButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_lightmode,null));
                    main.setBackgroundColor(Color.rgb(100,100,100));
                }else{
                    lightButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_brightness_high_24,null));
                    main.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }



    //Sends the user to navigation/home page
    public void goToHome(View v){
        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    //Sends the user to login page
    public void goToSignin(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void toggleSound(View v){
        ImageButton sound = findViewById(R.id.soundIcon);
    }

}