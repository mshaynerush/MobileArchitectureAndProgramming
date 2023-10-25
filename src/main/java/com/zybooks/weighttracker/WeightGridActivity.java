package com.zybooks.weighttracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WeightGridActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_grid);

        // get user name of currently logged in user
        SharedPreferences namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String userName = namedSharedPref.getString("name", null);

        // Set the user name logged in as for the weight tracker
        TextView loggedInAs = (TextView)findViewById(R.id.loggedInAs);
        loggedInAs.setText(new StringBuilder().append("You are logged in as: ").append(userName).toString());

        // Get goal weight to display above weight data
        GoalWeightDatabase db = new GoalWeightDatabase(this);
        int userGoalWeight = db.returnGoalWeight(userName);
        TextView goalWeightBanner = (TextView) findViewById(R.id.goalWeightBanner);
        goalWeightBanner.setText(new StringBuilder().append("Goal Weight: ").append(userGoalWeight).toString());

        // Set the weight data
        GridView weightData = (GridView) findViewById(R.id.weightGrid);

        // Set menu button
        Button btnMenu = (Button) findViewById(R.id.btnMenu);

        // Get all the data entered by user for progress weights
        StartingWeightDatabase startDb = new StartingWeightDatabase(this);
        Cursor c = startDb.populateWeights(userName);

        // Sets the data into the grid dynamically
        WeightDataAdapter weightDataAdapter = new WeightDataAdapter(this, c);

        // Sets the menu button function which opens the menu activity
        btnMenu.setOnClickListener(v -> openMenu(v));

        // Attach cursor adapter to the ListView
        weightData.setAdapter(weightDataAdapter);
        weightData.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // lets user click on a tile and edit or delete the entry
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), EditWeightActivity.class);
                intent.putExtra("index", l);
                startActivity(intent);

            }
        });
    }

    // opens the menu so user can add a weight, udpate goal weight, see their data or log out
    public void openMenu(View v){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}