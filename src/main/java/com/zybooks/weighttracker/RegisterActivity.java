package com.zybooks.weighttracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity<addThisUser> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onLoginClick(View v) {
        // Create the intent which will start your new activity.
        Intent newActivityIntent = new Intent(this, MainActivity.class);

        startActivity(newActivityIntent);
    }

    public void newUserRegister(View v) {
        EditText inputUserName = (EditText) findViewById(R.id.userName);
        EditText inputPassword = (EditText) findViewById(R.id.userPassword);
        EditText inputCustName = (EditText) findViewById(R.id.actualName);
        EditText inputPhoneNum = (EditText) findViewById(R.id.userPhone);

        String userName = inputUserName.getText().toString();
        String userPass = inputPassword.getText().toString();
        String custName = inputCustName.getText().toString();
        String custPhone = inputPhoneNum.getText().toString();

        LoginDatabase db = new LoginDatabase(this);
        User newUser = new User(userName, userPass, custName,custPhone);

        boolean addThisUser = db.addUser(newUser);
        if (addThisUser) {
            Toast.makeText(this, "Registration Successful. Login now", Toast.LENGTH_LONG).show();
            Intent homepage = new Intent(this, MainActivity.class);
            startActivity(homepage);
        }
    }
}
