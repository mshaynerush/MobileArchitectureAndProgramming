package com.zybooks.weighttracker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ProgressActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_entry);

        Button btnProgressWeight = (Button) findViewById(R.id.btnProgressWeight);
        btnProgressWeight.setOnClickListener(v -> addProgress(v));


    }


    public void addProgress(View v) {
        SharedPreferences namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String userName = namedSharedPref.getString("name", null);

        EditText inputProgressWeight = (EditText) findViewById(R.id.progressWeight);
        String progressWeight = inputProgressWeight.getText().toString();

        int pw = Integer.parseInt(progressWeight);

        User userProgress = new User(userName, pw);

        StartingWeightDatabase progressDb = new StartingWeightDatabase(this);
        boolean progressWeightAdded = progressDb.addStartingWeight(userProgress);

        if(progressWeightAdded){

            int gw = getGoalWeight(userName);
            if(pw <= gw) {

                // get phone number to text if goal weight is reached.
                LoginDatabase logDb = new LoginDatabase(this);
                String userPhone = logDb.getPhone(userName);

                sendSMSMessage(userPhone);

            } else {
                Intent newActivityIntent = new Intent(this, WeightGridActivity.class);
                startActivity(newActivityIntent);
            }
        }
    }

// Method to send text message notification when goal is reached if permissions are granted.

    protected void sendSMSMessage(String userPhone) {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            String msg = "Congratulations on reaching your goal weight!! We knew you could do it! Set a new goal weight and keep going!";
            String phoneNo = userPhone;
            Intent intent=new Intent(getApplicationContext(),UpdateGoalWeightActivity.class);
            PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(phoneNo, null, msg, pi,null);

            goUpdateGoal();
        } else {
            Intent newActivityIntent = new Intent(this, WeightGridActivity.class);
            startActivity(newActivityIntent);
        }
    }

    private int getGoalWeight(String userName){
        GoalWeightDatabase db = new GoalWeightDatabase(this);
        return db.returnGoalWeight(userName);
    }

    private void goUpdateGoal(){
        Intent newActivityIntent = new Intent(this, UpdateGoalWeightActivity.class);
        startActivity(newActivityIntent);
    }
}
