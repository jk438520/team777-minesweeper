package com.io.minesweeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_HARD_SCORE = "USER_HARD_SCORE";
    public static final String COLUMN_USER_MEDIUM_SCORE = "USER_MEDIUM_SCORE";
    public static final String COLUMN_USER_EASY_SCORE = "USER_EASY_SCORE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "scoreboard.db", null, 1);
    }

    // This is called the first time a database is accessed.
    // There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_HARD_SCORE + " INTEGER, "
                + COLUMN_USER_MEDIUM_SCORE + " INTEGER, "
                + COLUMN_USER_EASY_SCORE + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    // This is called if the database version number changes.
    // It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(UserModel userModel) {

        if (nameAlreadyInTheDatabase(userModel.getName()) || Objects.equals(userModel.getName(), "None")) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, userModel.getName());
        cv.put(COLUMN_USER_HARD_SCORE, userModel.getHardScore());
        cv.put(COLUMN_USER_MEDIUM_SCORE, userModel.getMediumScore());
        cv.put(COLUMN_USER_EASY_SCORE, userModel.getEasyScore());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteOne(int id) {
        boolean returnValue = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            returnValue = true;
        }
        else {
            returnValue = false;
        }
        cursor.close();
        db.close();
        return returnValue;
    }

    public boolean nameAlreadyInTheDatabase(String name) {
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + "='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        boolean return_value;

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return_value = true;
        }
        else {
            return_value = false;
        }
        cursor.close();
        db.close();
        return return_value;
    }

    public UserModel getOneByName(String name) {
        UserModel returnObject = null;
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + "='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            int userID = cursor.getInt(0);
            String userName = cursor.getString(1);
            long hardScore = cursor.getLong(2);
            long mediumScore = cursor.getLong(3);
            long easyScore = cursor.getLong(4);

            returnObject = new UserModel(userID, userName, hardScore, mediumScore, easyScore);
        }
        cursor.close();
        db.close();
        return returnObject;
    }

    public UserModel getOneById(int id) {
        UserModel returnObject = null;
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            int userID = cursor.getInt(0);
            String userName = cursor.getString(1);
            long hardScore = cursor.getLong(2);
            long mediumScore = cursor.getLong(3);
            long easyScore = cursor.getLong(4);

            returnObject = new UserModel(userID, userName, hardScore, mediumScore, easyScore);
        }
        cursor.close();
        db.close();
        return returnObject;
    }

    public List<UserModel> getEveryone() {
        List<UserModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                long hardScore = cursor.getLong(2);
                long mediumScore = cursor.getLong(3);
                long easyScore = cursor.getLong(4);

                UserModel newUser = new UserModel(userID, userName, hardScore, mediumScore, easyScore);
                returnList.add(newUser);
            } while (cursor.moveToNext());
        }

        // Close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public List<UserModel> getEveryoneSortedByTime(String category) {
        List<UserModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE + " ORDER BY " + category + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                long hardScore = cursor.getLong(2);
                long mediumScore = cursor.getLong(3);
                long easyScore = cursor.getLong(4);

                UserModel newUser = new UserModel(userID, userName, hardScore, mediumScore, easyScore);
                returnList.add(newUser);
            } while (cursor.moveToNext());
        }

        // Close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean updateTime(int id, long timeInMilliseconds, String whichScore) {
        UserModel userModel = this.getOneById(id);
        boolean result = false;
        if (Objects.equals(whichScore, COLUMN_USER_HARD_SCORE)) {
            if (timeInMilliseconds < userModel.getHardScore()) {
                userModel.setHardScore(timeInMilliseconds);
                result = true;
            }
        }
        else if (Objects.equals(whichScore, COLUMN_USER_MEDIUM_SCORE)) {
            if (timeInMilliseconds < userModel.getMediumScore()) {
                userModel.setMediumScore(timeInMilliseconds);
                result = true;
            }
        }
        else if (Objects.equals(whichScore, COLUMN_USER_EASY_SCORE)) {
            if (timeInMilliseconds < userModel.getEasyScore()) {
                userModel.setEasyScore(timeInMilliseconds);
                result = true;
            }
        }

        if (result) {
            Log.d("Upadteowanie", "Jest update");
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_HARD_SCORE, userModel.getHardScore());
            values.put(COLUMN_USER_MEDIUM_SCORE, userModel.getMediumScore());
            values.put(COLUMN_USER_EASY_SCORE, userModel.getEasyScore());

            db.update(USER_TABLE, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
            db.close();
        }
        return result;
    }
}