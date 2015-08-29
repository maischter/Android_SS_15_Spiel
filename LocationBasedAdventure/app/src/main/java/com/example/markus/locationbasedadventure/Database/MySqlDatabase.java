package com.example.markus.locationbasedadventure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlDatabase {
    private static final String DATABASE_NAME = "Game4.db";
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
    public static final String KEY_LEVEL = "level";
    public static final String KEY_EXP = "exp";
    public static final String KEY_STAMINA = "stamina";
    public static final String KEY_STRENGTH = "strength";
    public static final String KEY_DEXTERITY = "dexterity";
    public static final String KEY_INTELLIGENCE = "intelligence";



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

       // newFoodieValues.put(KEY_EMAIL, email);
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

        ContentValues newValues = new ContentValues();

        newValues.put(KEY_EMAIL, "");
        newValues.put(KEY_CHARCTERNAME, "Louie");
        newValues.put(KEY_STAYANGEMELDET, 0);         // Standartmäßig ist stayangemeldet deaktiviert --> 0
        newValues.put(KEY_WEAPON,"Bogen");
        newValues.put(KEY_WEAPONDAMAGE, "");
        newValues.put(KEY_WEAPONHITCHANCE, "");
        newValues.put(KEY_WEAPONKRITCHANCE, "");
        newValues.put(KEY_SEX, "Männlich");
        newValues.put(KEY_LEVEL, 1);
        newValues.put(KEY_EXP, 1);
        newValues.put(KEY_STAMINA, 15);
        newValues.put(KEY_STRENGTH, 15);
        newValues.put(KEY_DEXTERITY, 15);
        newValues.put(KEY_INTELLIGENCE, 15);


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

    public void updateEmail(String email) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    // update all values of database row 1
    //Email & Stats werden nicht geupdatet, da diese bereits passen

    public void updateAllWithoutEmail(String charactername, String weapon, String weapondamage, String weaponhitchance, String weaponkritchance,String sex){
        ContentValues values = new ContentValues();

        //values.put(KEY_EMAIL, email);
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
                              KEY_WEAPON, KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_SEX, KEY_LEVEL, KEY_EXP,
                              KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                    new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

            if (cursor != null)
                cursor.moveToFirst();

            return cursor.getInt(3);
    }


    public String getEmail(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE,KEY_SEX, KEY_LEVEL, KEY_EXP,
                        KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();


        System.out.println(cursor.getString(1));
        return cursor.getString(1);
    }



    public String getCharactername(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE,KEY_SEX, KEY_LEVEL, KEY_EXP,
                        KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(2);
    }

    public String getWeapon(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE,KEY_SEX, KEY_LEVEL, KEY_EXP,
                        KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getString(4);
    }


    public String getSex(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_EMAIL, KEY_CHARCTERNAME, KEY_STAYANGEMELDET, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE,KEY_SEX, KEY_LEVEL, KEY_EXP,
                        KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE}, KEY_ID + "=?",
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

    //public void rememberEmail(String email) {this.email = email;}


    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_EMAIL + " text, " + KEY_CHARCTERNAME
                + " text, " + KEY_STAYANGEMELDET + " integer, "+ KEY_WEAPON
                + " text, " + KEY_WEAPONDAMAGE + " text, " + KEY_WEAPONHITCHANCE
                + " text, " + KEY_WEAPONKRITCHANCE + " text, " + KEY_SEX + " text, " + KEY_LEVEL + " integer, " + KEY_EXP
                + " integer, " + KEY_STAMINA + " integer, " + KEY_STRENGTH + " integer, " + KEY_DEXTERITY + " integer, " + KEY_INTELLIGENCE
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

