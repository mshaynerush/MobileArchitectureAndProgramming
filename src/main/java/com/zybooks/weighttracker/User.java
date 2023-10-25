package com.zybooks.weighttracker;

import android.widget.EditText;

public class User {

    String user;
    String password;
    int weight;
    String custName;
    String phoneNum;

    public User (String userName, String pass, String name, String phone){
        user = userName;
        password = pass;
        custName = name;
        phoneNum = phone;
    }

    public User(String userName, String pass) {
        user = userName;
        password = pass;
    }

    public User(String userName, int userWeight){
        user = userName;
        weight = userWeight;
    }


}