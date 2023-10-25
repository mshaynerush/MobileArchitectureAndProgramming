package com.zybooks.weighttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartingWeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_weight);

        Button btnAddWeights = (Button) findViewById(R.id.userBtnStartWeight);
        btnAddWeights.setOnClickListener(v -> addWeights(v));
    }

    public void addWeights(View v) {

        // Get logged in user name
        SharedPreferences namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String userName = namedSharedPref.getString("name", null);

        // Get values from weight fields
        EditText inputStartWeight = (EditText) findViewById(R.id.userStartingWeight);
        EditText inputGoalWeight = (EditText) findViewById(R.id.userTargetWeight);

        // Case EditText values as integers
        String userStartWeight = inputStartWeight.getText().toString();
        String userGoalWeight = inputGoalWeight.getText().toString();

        int sw = Integer.parseInt(userStartWeight);
        int gw = Integer.parseInt(userGoalWeight);
        // Create users with a start and goal weight respectively
        User userStart = new User(userName, sw);
        User userGoal = new User(userName, gw);

        StartingWeightDatabase startDb = new StartingWeightDatabase(this);
        boolean startWeightAdd = startDb.addStartingWeight(userStart);

        GoalWeightDatabase goalDb = new GoalWeightDatabase(this);
        boolean goalWeightAdd = goalDb.addGoalWeight(userGoal);

        if(startWeightAdd && goalWeightAdd){

            Intent newActivityIntent = new Intent(this, WeightGridActivity.class);
            startActivity(newActivityIntent);
        }

    }

}
