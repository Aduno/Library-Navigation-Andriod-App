package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Classes.InputVerification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {
    private EditText username, email, password;
    private Button register;
    private Spinner role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //Sets up dropdown bar
        role = findViewById(R.id.roles);
        String[] items = new String[]{"User", "Employee"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        role.setAdapter(adapter);
    }


    public void registerClick(View v) {
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String emailString = email.getText().toString();

        //Checks if user input is valid
        if (InputVerification.checkEmail(emailString) && InputVerification.checkUsername(usernameString) && InputVerification.checkPassword(passwordString)) {
            //Need to check if already in database
            checkIfUserExistsAndAdd();
        }
        Log.d("register","checked");
    }

    private void checkIfUserExistsAndAdd() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot snippet = snapshot.child("users");
                if (!snippet.hasChild(username.getText().toString())) {
                    DatabaseReference newUserRole = db.getReference("users/" + username.getText().toString() + "/role");
                    DatabaseReference newUserEmail = db.getReference("users/" + username.getText().toString() + "/email");
                    DatabaseReference newUserPassword = db.getReference("users/" + username.getText().toString() + "/password");

                    newUserRole.setValue(role.getSelectedItem().toString());
                    newUserEmail.setValue(email.getText().toString());
                    newUserPassword.setValue(password.getText().toString());
                    Log.d("register", "registered a user");
                } else {
                    Log.d("register", "failed registration");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}