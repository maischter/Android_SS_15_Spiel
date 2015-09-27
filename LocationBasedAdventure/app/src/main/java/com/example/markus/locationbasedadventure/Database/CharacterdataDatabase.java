package com.example.markus.locationbasedadventure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  CharacterdataDatabase {

    //CharacterDatabase

    private static final String DATABASE_NAME = "Characterdata3.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "Characterdatas";
    private static final String KEY_ID = "_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CHARCTERNAME = "charactername";
    private static final String KEY_STAYANGEMELDET = "stayangemeldet";
    private static final String KEY_SEX = "sex";
    private static final String KEY_TON = "ton";
    private static final String KEY_PUSHUP ="pushup";

    private ToDoDBOpenHelper dbHelper;
    private SQLiteDatabase db;

    public CharacterdataDatabase(Context context) {
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





    //inserts standartvalues in a new row.
    //is called in MainActivity

    public long insertAllmainActivity() {

        ContentValues newValues = new ContentValues();

        newValues.put(KEY_EMAIL, "");
        newValues.put(KEY_CHARCTERNAME, "");
        newValues.put(KEY_STAYANGEMELDET, 0);         // Standartmäßig ist stayangemeldet deaktiviert --> 0
        newValues.put(KEY_SEX, "Männlich");
        newValues.put(KEY_TON, 0);
        newValues.put(KEY_PUSHUP, 0);


        return db.insert(DATABASE_TABLE, null, newValues);
    }

    //update a StayAngemeldet in databaserow 1

    public void updateStayAngemeldet(int stayangemeldet) {
        ContentValues values = new ContentValues();
        values.put(KEY_STAYANGEMELDET, stayangemeldet);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }




    public void updateEmail(String email) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    public void updateTon(boolean checked) {
        ContentValues values = new ContentValues();
        if(checked){
            values.put(KEY_TON, 1);
        }else{
            values.put(KEY_TON, 0);
        }

        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    public void updatePushup(boolean checked) {
        ContentValues values = new ContentValues();
        if(checked){
            values.put(KEY_PUSHUP, 1);
        }else{
            values.put(KEY_PUSHUP, 0);
        }

        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    // update all values of database row 1
    //Email & Stats werden nicht geupdatet, da diese bereits passen

    public void updateCreatecharacter(String charactername, String sex){
        ContentValues values = new ContentValues();

        values.put(KEY_CHARCTERNAME, charactername);
        values.put(KEY_SEX, sex);

        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }


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

    // get a Item from the Database

    public int getStayAngemeldet(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET,
                        KEY_SEX, KEY_TON, KEY_PUSHUP}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
                cursor.moveToFirst();

            return cursor.getInt(3);
    }


    public String getEmail(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET,
                                                                KEY_SEX, KEY_TON, KEY_PUSHUP}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();


        return cursor.getString(1);
    }


    public String getCharactername(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET,
                        KEY_SEX, KEY_TON, KEY_PUSHUP}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(2);
    }


    public String getSex(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET,
                        KEY_SEX, KEY_TON, KEY_PUSHUP}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(4);
    }

    public int getTon(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET,
                        KEY_SEX, KEY_TON, KEY_PUSHUP}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(5);
    }

    public int getPushup(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET,
                        KEY_SEX, KEY_TON, KEY_PUSHUP}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(6);
    }





    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_EMAIL + " text, " + KEY_CHARCTERNAME
                + " text, " + KEY_STAYANGEMELDET + " integer, " + KEY_SEX
                + " text, " + KEY_TON + " integer, " + KEY_PUSHUP
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

