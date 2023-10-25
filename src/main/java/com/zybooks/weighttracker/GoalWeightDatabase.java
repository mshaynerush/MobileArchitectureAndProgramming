package com.zybooks.weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


public class GoalWeightDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "goal_weight.db";
    private static final int VERSION = 2;

    public GoalWeightDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class GoalWeightTable {
        private static final String TABLE = "goal_weight";
        private static final String COL_ID = "_id";
        private static final String COL_USER_NAME = "user_name";
        private static final String COL_GOAL_WEIGHT = "goal_weight";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + GoalWeightTable.TABLE + " (" +
                GoalWeightTable.COL_ID + " integer primary key autoincrement, " +
                GoalWeightTable.COL_USER_NAME + " text, " +
                GoalWeightTable.COL_GOAL_WEIGHT + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + GoalWeightTable.TABLE);
        onCreate(db);
    }


    public boolean addGoalWeight(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(GoalWeightTable.COL_USER_NAME, user.user);
        userValues.put(GoalWeightTable.COL_GOAL_WEIGHT, user.weight);

        long result = db.insert(GoalWeightTable.TABLE, null, userValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateGoalWeight(String sql){
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.execSQL(sql);
            return true;
        } catch(Exception e){
            Log.e("Exception", e.getMessage());
            return false;
        }
    }

    public boolean checkGoalWeight(String userName){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM goal_weight WHERE user_name = '" + userName + "'";

        Cursor c = db.rawQuery(sql, null);
        if(c.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public int returnGoalWeight(String userName){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT goal_weight FROM goal_weight WHERE user_name = '" + userName + "'";
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()){
            return c.getInt(0);
        } else {
            return 0;
        }
    }
    
}




