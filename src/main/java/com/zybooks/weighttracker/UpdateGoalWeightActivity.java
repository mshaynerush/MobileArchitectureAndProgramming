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

public class UpdateGoalWeightActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_weight_update);

        Button btnGoalWeight = (Button) findViewById(R.id.btnUpdateGoalWeight);
        btnGoalWeight.setOnClickListener(v -> updateGoal(v));

    }


    public void updateGoal(View v) {
        SharedPreferences namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String userName = namedSharedPref.getString("name", null);

        EditText inputGoalWeight = (EditText) findViewById(R.id.updateGoalWeight);
        String goalWeight = inputGoalWeight.getText().toString();

        int gw = Integer.parseInt(goalWeight);

        GoalWeightDatabase goalDb = new GoalWeightDatabase(this);
        String sql = "UPDATE goal_weight SET goal_weight = " + gw + " WHERE user_name = '" + userName + "'";

        Toast.makeText(this, sql, Toast.LENGTH_SHORT).show();
        boolean goalWeightUpdated = goalDb.updateGoalWeight(sql);
        if(goalWeightUpdated){

            Intent newActivityIntent = new Intent(this, WeightGridActivity.class);
            startActivity(newActivityIntent);
        }

    }
}
