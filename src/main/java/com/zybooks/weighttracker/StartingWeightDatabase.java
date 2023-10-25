package com.zybooks.weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;


public class StartingWeightDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "starting_weight.db";
    private static final int VERSION = 2    ;

    public StartingWeightDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class StartingWeightTable {
        private static final String TABLE = "starting_weight";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_USER_NAME = "user_name";
        private static final String COL_STARTING_WEIGHT = "starting_weight";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + StartingWeightTable.TABLE + " (" +
                StartingWeightTable.COL_ID + " integer primary key autoincrement, " +
                StartingWeightTable.COL_DATE + " text, " +
                StartingWeightTable.COL_USER_NAME + " text, " +
                StartingWeightTable.COL_STARTING_WEIGHT + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + StartingWeightTable.TABLE);
        onCreate(db);
    }


    public boolean addStartingWeight(User user) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(StartingWeightTable.COL_DATE, getDate());
        userValues.put(StartingWeightTable.COL_USER_NAME, user.user);
        userValues.put(StartingWeightTable.COL_STARTING_WEIGHT, user.weight);

        long result = db.insert(StartingWeightTable.TABLE, null, userValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = formatter.format(date);
        return dateString;
    }

    public Cursor populateWeights(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM starting_weight WHERE user_name = '" + userName + "' ORDER BY date DESC";

        Cursor c = db.rawQuery(sql, null);

        return c;
    }

    public Cursor getSingleEntry(String sql) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Boolean deleteEntry(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean updateEntry(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
                return true;
        } catch (Exception e) {
            return false;
        }
    }
}




