package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Classes.Member;
import com.example.myapplication.Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginRegisterPage extends AppCompatActivity{

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private Context mContext;
    private EditText userName, password;
    private Button login, register;
    private Spinner role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_page);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);

        //Sets up dropdown bar
        role = findViewById(R.id.roles);
        String[] items = new String[]{"User", "Employee"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        role.setAdapter(adapter);


        //Checks user input
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(userName.getText().toString().contains(" ")){
                    userName.setError("No spaces allowed");
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
                if(password.getText().length()<5){
                    password.setError("Password must be <5 characters");
                    login.setEnabled(false);
                }
                else if(password.getText().toString().contains(" ")){
                    password.setError("No spaces allowed");
                    login.setEnabled(false);
                }
                else{
                    login.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                //Do nothing
            }
        });
    }





    //----------------------------Login --------------------------------------//
    public void loginClick(View v){
        userName = findViewById(R.id.username);
        password=  findViewById(R.id.password);
        role = findViewById(R.id.roles);
        //Check the database

        String usernameString = userName.getText().toString();
        String passwordString = password.getText().toString();
        String roleString = role.getSelectedItem().toString();
//        User user = new User; Havent set up user class tyet
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Checks if login is by admin
                if(usernameString.equals("admin")){
                    if(snapshot.child("users").child("admin").child("password").getValue().toString().equals(passwordString)){
                        //Lets the user know that login was successful
                        Toast.makeText(getApplicationContext(),"Logged in as admin",Toast.LENGTH_LONG).show();

                        //Sends the user to the main page
                        Intent intent = new Intent(LoginRegisterPage.this,AdminActivity.class);
                        startActivity(intent);
                    }
                    else{
                        //Failed login message
                        Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_LONG).show();
                    }
                }
                else if(checkLogin(usernameString,passwordString,roleString,snapshot)){
                    //Lets the user know that login was successful
                    Toast.makeText(getApplicationContext(),"Login Sucessful!",Toast.LENGTH_LONG).show();

                    //Sends the user to the main page
                    Intent intent = new Intent(LoginRegisterPage.this,MainActivity.class);
                    startActivity(intent);

                }else{
                    //Failed login message
                    Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Checks if user info matches database
    public boolean checkLogin(String username,String password, String role, DataSnapshot snapshot){
        DataSnapshot snippet = snapshot.child("users");
        if(snippet.hasChild(username)){
            try {
                for(DataSnapshot snap: snippet.getChildren()) {
                    if (snap.child("password").getValue().toString().equals(password) &&snap.child("role").getValue().toString().equals(role)) {
                        Log.d("report", "success");
                        return true;
                    }
                }
            }catch(NullPointerException e){
            }
        }
        return false;
    }
}