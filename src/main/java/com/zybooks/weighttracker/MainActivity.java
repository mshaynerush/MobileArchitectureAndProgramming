package com.zybooks.weighttracker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String[] SMS_PERMISSIONS = {Manifest.permission.SEND_SMS};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyPermissions();

        try {
            SharedPreferences namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
            boolean loggedIn = namedSharedPref.getBoolean("isLoggedIn", false);

            if(loggedIn){
                Intent newActivityIntent = new Intent(this, WeightGridActivity.class);
                startActivity(newActivityIntent);
            }

        } catch (Exception e){
            Log.e("Exception: ", e.getMessage());
        }

        Button btnLogin = (Button) findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(v -> onLoginAttempt(v));


    }

    public void onRegisterClick(View v) {
        // Create the intent which will start your new activity.
        Intent newActivityIntent = new Intent(this, RegisterActivity.class);
        startActivity(newActivityIntent);
    }


    public void onLoginAttempt(View v) {
        EditText inputUser = (EditText) findViewById(R.id.userName);
        EditText inputPassword = (EditText) findViewById(R.id.userPassword);

        String userName = inputUser.getText().toString();
        String userPass = inputPassword.getText().toString();


        LoginDatabase db = new LoginDatabase(this);
        // check user inputs for values - no value  no login
        if(userName == null){
            Toast.makeText(this, "User Name is required", Toast.LENGTH_LONG).show();
        } else if(userPass == null){
            Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
        } else {


            User currUser = new User(userName, userPass);
            boolean loggedIn = db.userLogin(currUser);

            if (loggedIn) {
                Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show();

                SharedPreferences namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = namedSharedPref.edit();
                editor.putString("name", userName);
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                GoalWeightDatabase gwDb = new GoalWeightDatabase(this);
                boolean initialLogin = gwDb.checkGoalWeight(userName);

                Intent newActivityIntent;
                if (initialLogin) {
                    newActivityIntent = new Intent(this, WeightGridActivity.class);

                } else {
                    newActivityIntent = new Intent(this, StartingWeightActivity.class);
                }
                startActivity(newActivityIntent);

            } else {
                Toast.makeText(this, "The user name or password does not match", Toast.LENGTH_LONG).show();


                // Check for existing goal weight and send to correct page


            }
        }
    }

    // Thank you to Mitch Tabain for the inspiration for this portion of the code.
    private void verifyPermissions(){


        Log.d("MainActivity", "verifyPermissions: Checking Permissions.");

        int permissionSendMSG = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS);



        if (permissionSendMSG != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    SMS_PERMISSIONS,
                    1
            );
        }
    }
}