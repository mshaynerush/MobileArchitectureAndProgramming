package com.zybooks.weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;


public class LoginDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "login.db";
    private static final int VERSION = 3;

    public LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }



    private static final class LoginTable {
        private static final String TABLE = "login";
        private static final String COL_ID = "_id";
        private static final String COL_USER_NAME = "user_name";
        private static final String COL_PASSWORD = "password";
        private static final String COL_NAME = "name";
        private static final String COL_PHONE = "phone";


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LoginTable.TABLE + " (" +
                LoginTable.COL_ID + " integer primary key autoincrement, " +
                LoginTable.COL_USER_NAME + " text, " +
                LoginTable.COL_PASSWORD + " text, " +
                LoginTable.COL_NAME + " text, " +
                LoginTable.COL_PHONE + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + LoginTable.TABLE);
        onCreate(db);
    }


    public boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(LoginTable.COL_USER_NAME, user.user);
        userValues.put(LoginTable.COL_PASSWORD, user.password);
        userValues.put(LoginTable.COL_NAME, user.custName);
        userValues.put(LoginTable.COL_PHONE, user.phoneNum);

        long result = db.insert(LoginTable.TABLE, null, userValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean userLogin(User user) {
        SQLiteDatabase db = getReadableDatabase();
        String sqlAuth = "SELECT * FROM login WHERE user_name = '" + user.user + "' AND password = '" + user.password + "'";
        Cursor c = db.rawQuery(sqlAuth, null);

        if (c.moveToFirst()) {
           return true;
        } else {
            return false;
        }
    }

    public String getPhone(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT phone FROM login WHERE user_name = '" + userName + "'";
        Cursor c = db.rawQuery(sql, null);

        if(c.moveToFirst()){
            return c.getString(0);
        } else {
            return "no phone";
        }
    }
}




