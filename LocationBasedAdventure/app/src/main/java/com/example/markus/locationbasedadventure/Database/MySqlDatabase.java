package com.example.markus.locationbasedadventure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlDatabase {
    private static final String DATABASE_NAME = "Game3.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "Games";

    public static final String KEY_ID = "_id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_CHARCTERNAME = "charactername";
    public static final String KEY_STAYANGEMELDET = "stayangemeldet";
    public static final String KEY_WEAPON = "weapon";
    public static final String KEY_WEAPONDAMAGE = "weapondamage";
    public static final String KEY_WEAPONHITCHANCE = "weaponhitchance";
    public static final String KEY_WEAPONKRITCHANCE = "weaponkritchance";
    public static final String KEY_SEX = "sex";

    public String email = "";


    private ToDoDBOpenHelper dbHelper;
    private SQLiteDatabase db;

    public MySqlDatabase(Context context) {
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


    //add Data into the Database

    public long insertAll(String charactername, String weapon,
                              String weapondamage, String weaponhitchance, String weaponkritchance,String sex) {

        ContentValues newFoodieValues = new ContentValues();

        newFoodieValues.put(KEY_EMAIL, email);
        newFoodieValues.put(KEY_CHARCTERNAME, charactername);
        newFoodieValues.put(KEY_STAYANGEMELDET, 0);         // Standartmäßig ist stayangemeldet deaktiviert --> 0
        newFoodieValues.put(KEY_WEAPON, weapon);
        newFoodieValues.put(KEY_WEAPONDAMAGE, weapondamage);
        newFoodieValues.put(KEY_WEAPONHITCHANCE, weaponhitchance);
        newFoodieValues.put(KEY_WEAPONKRITCHANCE, weaponkritchance);
        newFoodieValues.put(KEY_SEX, sex);

        return db.insert(DATABASE_TABLE, null, newFoodieValues);
    }


    //inserts standartvalues in a new row.
    //is called in MainActivity

    public long insertAllmainActivity() {

        ContentValues newFoodieValues = new ContentValues();

        newFoodieValues.put(KEY_EMAIL, "");
        newFoodieValues.put(KEY_CHARCTERNAME, "Louie");
        newFoodieValues.put(KEY_STAYANGEMELDET, 0);         // Standartmäßig ist stayangemeldet deaktiviert --> 0
        newFoodieValues.put(KEY_WEAPON,"Bogen");
        newFoodieValues.put(KEY_WEAPONDAMAGE, "");
        newFoodieValues.put(KEY_WEAPONHITCHANCE, "");
        newFoodieValues.put(KEY_WEAPONKRITCHANCE, "");
        newFoodieValues.put(KEY_SEX, "Männlich");

        return db.insert(DATABASE_TABLE, null, newFoodieValues);
    }

    //update a StayAngemeldet in databaserow 1

    public void updateStayAngemeldet(int stayangemeldet) {
        ContentValues values = new ContentValues();
        values.put(KEY_STAYANGEMELDET, stayangemeldet);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }


    //update weapon and weaponstats of databaserow 1

    public void updateWeapon(String weapon, String weapondamage, String weaponhitchance, String weaponkritchance) {
        ContentValues values = new ContentValues();
        values.put(KEY_WEAPON, weapon);
        values.put(KEY_WEAPONDAMAGE, weapondamage);
        values.put(KEY_WEAPONHITCHANCE, weaponhitchance);
        values.put(KEY_WEAPONKRITCHANCE, weaponkritchance);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    // update all values of database row 1

    public void updateAll(String charactername, String weapon, String weapondamage, String weaponhitchance, String weaponkritchance,String sex){
        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL, email);
        values.put(KEY_CHARCTERNAME, charactername);
        values.put(KEY_STAYANGEMELDET, 0);
        values.put(KEY_WEAPON, weapon);
        values.put(KEY_WEAPONDAMAGE, weapondamage);
        values.put(KEY_WEAPONHITCHANCE, weaponhitchance);
        values.put(KEY_WEAPONKRITCHANCE, weaponkritchance);
        values.put(KEY_SEX, sex);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }


    public boolean isEmpty(){
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+ DATABASE_TABLE, null);
        if (cur != null){
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {
                // Empty
                return true;
            }

        }
        return false;
    }

    // get a FoodieItem from the Database

    public int getStayAngemeldet(){

              Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                            KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_SEX}, KEY_ID + "=?",
                    new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

            if (cursor != null)
                cursor.moveToFirst();

            return cursor.getInt(3);
    }

    public String getCharactername(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE,KEY_SEX}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(2);
    }

    public String getWeapon(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE,KEY_SEX}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(4);
    }


    public String getSex(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE,KEY_SEX}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(8);
    }

    public int countEntries(){
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    public void removeItemFromDataBase(long id) {
        String delete_clause = KEY_ID + "=?";
        String[] delete_args = new String[]{String.valueOf(id)};
        db.delete(DATABASE_TABLE, delete_clause, delete_args);
    }

    public void removeAllItemsFromDatabase() {
        db.delete(DATABASE_TABLE, null, null);
    }

    public void rememberEmail(String email) {
        this.email = email;
    }


    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_EMAIL + " text, " + KEY_CHARCTERNAME
                + " text, " + KEY_STAYANGEMELDET + " integer, "+ KEY_WEAPON
                + " text, " + KEY_WEAPONDAMAGE + " text, " + KEY_WEAPONHITCHANCE
                + " text, " + KEY_WEAPONKRITCHANCE + " text, " + KEY_SEX
                + " text);";

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

