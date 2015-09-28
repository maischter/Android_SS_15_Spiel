package com.example.markus.locationbasedadventure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Markus on 01.09.2015.
 */
public class StatsDatabase {


    private static final String DATABASE_NAME = "StatDatenbank.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "Stats";

    private static final String KEY_ID = "_id";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_EXP = "exp";
    private static final String KEY_STAMINA = "stamina";
    private static final String KEY_STRENGTH = "strength";
    private static final String KEY_DEXTERITY = "dexterity";
    private static final String KEY_INTELLIGENCE = "intelligence";

    private ToDoDBOpenHelper dbHelper;
    private SQLiteDatabase db;

    public StatsDatabase(Context context) {
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



    //insert standard values to Database
    //is called in MainActivity

    public long insertAllmainActivity() {

        ContentValues newValues = new ContentValues();

        newValues.put(KEY_LEVEL,1);
        newValues.put(KEY_EXP, 0);
        newValues.put(KEY_STAMINA, 15);
        newValues.put(KEY_STRENGTH, 15);
        newValues.put(KEY_DEXTERITY, 15);
        newValues.put(KEY_INTELLIGENCE, 15);

        return db.insert(DATABASE_TABLE, null, newValues);
    }

    //update weapon and weaponstats of databaserow 1

    public void updateAll(int level, int exp,int stamina, int strength, int dexterity, int intelligence) {
        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL, level);
        values.put(KEY_EXP,exp);
        values.put(KEY_STAMINA, stamina);
        values.put(KEY_STRENGTH, strength);
        values.put(KEY_DEXTERITY, dexterity);
        values.put(KEY_INTELLIGENCE, intelligence);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    //updates all Values Except Exp

    public void updateAllExceptExp(int level, int stamina, int strength, int dexterity, int intelligence) {
        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL, level);
        values.put(KEY_STAMINA, stamina);
        values.put(KEY_STRENGTH, strength);
        values.put(KEY_DEXTERITY, dexterity);
        values.put(KEY_INTELLIGENCE, intelligence);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    //updates Exp

    public void updateExp(int exp) {
        ContentValues values = new ContentValues();
        values.put(KEY_EXP, exp);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }




    //checks if Database is empty
    //returns true if Database is empty

    public boolean isEmpty(){
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + DATABASE_TABLE, null);
        if (cur != null){
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {
                // Empty
                return true;
            }

        }
        return false;
    }


    //gets all Values as int[]

    public int[] getStats(){


        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_LEVEL,
                        KEY_EXP, KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null) {
            cursor.moveToFirst();
        }
        int[] statsArray = new int[6];
        statsArray[0]=cursor.getInt(1);
        statsArray[1]=cursor.getInt(2);
        statsArray[2]=cursor.getInt(3);
        statsArray[3]=cursor.getInt(4);
        statsArray[4]=cursor.getInt(5);
        statsArray[5]=cursor.getInt(6);


        return statsArray;
    }


    //gets Level

    public int getLevel(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_LEVEL,
                        KEY_EXP,KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(1);
    }

    //gets Exp

    public int getExp(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_LEVEL,
                        KEY_EXP,KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(2);
    }

    //gets Stamina

    public int getStamina(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_LEVEL,
                        KEY_EXP,KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(3);
    }


    //gets Strength

    public int getStrength(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_LEVEL,
                        KEY_EXP,KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(4);
    }


    //gets Dexterity

    public int getDexterity(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_LEVEL,
                        KEY_EXP,KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(5);
    }

    //gets Intelligence

    public int getIntelligence(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_LEVEL,
                        KEY_EXP,KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(6);
    }




    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_LEVEL + " integer, " + KEY_EXP + " integer, " +  KEY_STAMINA
                + " integer, " + KEY_STRENGTH + " integer, " + KEY_DEXTERITY + " integer, " + KEY_INTELLIGENCE
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
