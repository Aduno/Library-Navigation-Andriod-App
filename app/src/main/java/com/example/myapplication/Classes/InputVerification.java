package com.example.myapplication.Classes;

import android.widget.EditText;

import com.example.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputVerification {




    public static boolean checkUsername(String username){
        //Cannot contain spaces
        if(username.contains(" ")||username.length()==0){
            return false;
        }
        return true;
    }
    public static boolean checkPassword(String password){
        //Password must be greater than 5 chars
        if(password.length()<5){
            return false;
        }
        return true;
    }

    public static boolean checkEmail(String email){
        if(email.length()==0||!validate(email)){
            return false;
        }
        return true;
    }

    //***************************************************************
    //Code from https://stackoverflow.com/questions/8204680/java-regex-email
    // By Jason Buberel
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    //********************************************************************
}
