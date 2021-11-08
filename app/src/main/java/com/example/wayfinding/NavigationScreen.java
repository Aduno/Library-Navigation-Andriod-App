package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wayfinding.classes.UserSettings;

public class NavigationScreen extends AppCompatActivity {

    private ConstraintLayout navFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_screen);

        navFrame = findViewById(R.id.navFrame);


        Intent intent = getIntent();

        UserSettings settings = (UserSettings) intent.getSerializableExtra("key");

        if(settings.getDarkMode()){
            //Set background of homeframe background to gray or whatever color
            navFrame.setBackgroundColor(Color.rgb(100, 100, 100));

        }
        else{
            navFrame.setBackgroundColor(Color.WHITE);
        }

    }

}