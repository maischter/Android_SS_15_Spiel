package com.example.markus.locationbasedadventure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.markus.locationbasedadventure.Items.Achievement;
import com.example.markus.locationbasedadventure.Items.Item;

import java.util.ArrayList;

/**
 * Created by Markus on 16.09.2015.
 */
public class AchievementDatabase {

    private static final String DATABASE_NAME = "AchievementDatenbank.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "Achievements";
    private  static final String KEY_ID = "_id";
    private  static final String KEY_TYP = "typ";

    private ToDoDBOpenHelper dbHelper;
    private SQLiteDatabase db;

    public AchievementDatabase(Context context) {
        dbHelper = new ToDoDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    //insert a new achievement
    //gets in achievementTyp

    public long insertNewAchievement(int typ) {

        ContentValues newValues = new ContentValues();

        newValues.put(KEY_TYP, typ);

        return db.insert(DATABASE_TABLE, null, newValues);
    }

    //update weapon and weaponstats of databaserow 1

    public void updateAll(int typ) {
        ContentValues values = new ContentValues();
        values.put(KEY_TYP, typ);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    //deletes AllAchievements

    public void deleteAchievements(){
        db.execSQL("DELETE FROM " + DATABASE_TABLE);
    }


    //gives all achievementItems back

    public ArrayList<Achievement> getAllAchievementItems(){
        ArrayList<Achievement> items = new ArrayList<Achievement>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_TYP
                }, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                items.add(new Achievement(cursor.getInt(0),cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }


    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TYP
                + " integer);";

        public ToDoDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
