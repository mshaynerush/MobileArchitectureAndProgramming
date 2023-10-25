package com.zybooks.weighttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView btnAddWeights = (TextView) findViewById(R.id.navAddWeight);
         btnAddWeights.setOnClickListener(v -> loadAddWeight());

        TextView btnUpdateGoal = (TextView) findViewById(R.id.navUpdateGoal);
        btnUpdateGoal.setOnClickListener(v -> loadUpdateGoal());

        TextView btnTracker = (TextView) findViewById(R.id.navWeightTrack);
        btnTracker.setOnClickListener(v -> loadWeightTracker());

        TextView btnLogout = (TextView) findViewById(R.id.navLogOut);
        btnLogout.setOnClickListener(v -> logout());


    }

    public void loadAddWeight(){
        Intent newActivityIntent = new Intent(this, ProgressActivity.class);
        startActivity(newActivityIntent);
    }

    public void loadUpdateGoal(){
        Intent newActivityIntent = new Intent(this, UpdateGoalWeightActivity.class);
        startActivity(newActivityIntent);
    }

    public void loadWeightTracker(){
        Intent newActivityIntent = new Intent(this, WeightGridActivity.class);
        startActivity(newActivityIntent);
    }

    public void logout(){

        SharedPreferences namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = namedSharedPref.edit();

        editor.putString("name", "" );
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        Intent newActivityIntent = new Intent(this, MainActivity.class);
        startActivity(newActivityIntent);
    }
}
