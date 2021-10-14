package com.example.wayfinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
    }
    public void login(View v){
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(username.equals("admin")&&password.equals("libraryadmin")){
                //Lets the user know that login was successful
                Toast.makeText(getApplicationContext(),"Logged in as admin",Toast.LENGTH_LONG).show();
                //Sends the user to the main page
                //Also creates a admin object which is used to define the user
                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
            else{
                //Failed login message
                Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_LONG).show();
            }
        }
    }