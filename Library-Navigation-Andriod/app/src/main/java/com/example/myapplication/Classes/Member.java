package com.example.myapplication.Classes;

public class Member extends User{
    public Member(String username, String password, String roles, String email){
        super(username,password, email,roles);
    }
    public Member(String username, String password, String roles){
        super(username,password, roles);
    }
}
