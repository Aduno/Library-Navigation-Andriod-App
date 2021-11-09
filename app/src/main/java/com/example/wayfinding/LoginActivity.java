package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wayfinding.classes.UserSettings;


public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private LinearLayout loginFrame;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginFrame = findViewById(R.id.loginFrame);

        Intent intent = getIntent();

        UserSettings settings = (UserSettings) intent.getSerializableExtra("key");

        if(settings.getDarkMode()){
            //Set background of login background to gray or whatever color
            loginFrame.setBackgroundColor(Color.rgb(100, 100, 100));

        }
        else{
            loginFrame.setBackgroundColor(Color.WHITE);
        }
    }
    public void login(View v){
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(username.equals("admin")&&password.equals("admin")){
                //Lets the user know that login was successful
                Toast.makeText(getApplicationContext(),"Logged in as admin",Toast.LENGTH_SHORT).show();
                //Sends the user to the main page
                //Also creates a admin object which is used to define the user
                Intent intent = new Intent(LoginActivity.this, AdminAnnouncement.class);
                startActivity(intent);
            }
            else{
                //Failed login message
                Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_SHORT).show();
            }
        }
    }