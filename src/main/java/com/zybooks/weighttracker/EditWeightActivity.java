package com.zybooks.weighttracker;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditWeightActivity extends AppCompatActivity {

    TextView wDate;
    EditText wWeight;
    Button wDelete;
    Button wEdit;
    int weightVal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        wDate = (TextView) findViewById(R.id.entryDate);
        wWeight = (EditText) findViewById(R.id.entryWeight);
        wDelete = (Button) findViewById(R.id.btnDeleteWeight);
        wEdit = (Button) findViewById(R.id.btnEditWeight);

        Intent intent = getIntent();
        Long entryId = intent.getLongExtra("index", 0);

        StartingWeightDatabase db = new StartingWeightDatabase(this);
        String sql = "SELECT date, starting_weight FROM starting_weight WHERE _id =" + entryId;

        Cursor c = db.getSingleEntry(sql);
        if (c.moveToFirst()) {
            weightVal = c.getInt(c.getColumnIndexOrThrow("starting_weight"));
            String tempDate = c.getString(c.getColumnIndexOrThrow("date"));

            wDate.setText(tempDate);
            wWeight.setText(String.valueOf(weightVal));
        }

        wDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String deleteEntrySql = "DELETE FROM starting_weight WHERE _id = " + entryId;
                boolean deleted = db.deleteEntry(deleteEntrySql);
                if (deleted) {

                    Intent newActivityIntent = new Intent(EditWeightActivity.this, WeightGridActivity.class);
                    startActivity(newActivityIntent);

                }
            }
        });

        wEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText updatedWeight = (EditText) findViewById(R.id.entryWeight);
                int uWeight = Integer.parseInt(updatedWeight.getText().toString());
                String updateSQL = "UPDATE starting_weight SET starting_weight = " + uWeight + " WHERE _id = " + entryId;
                boolean updated = db.updateEntry(updateSQL);
                if (updated) {

                    Intent newActivityIntent = new Intent(EditWeightActivity.this, WeightGridActivity.class);
                    startActivity(newActivityIntent);

                }
            }
        });
    }
}
