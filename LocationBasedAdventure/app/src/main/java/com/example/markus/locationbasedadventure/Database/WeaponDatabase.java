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
public class WeaponDatabase {

    private static final String DATABASE_NAME = "Weapon4.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "Weapons";

    public static final String KEY_ID = "_id";

    public static final String KEY_WEAPON = "weapon";
    public static final String KEY_WEAPONDAMAGE = "weapondamage";
    public static final String KEY_WEAPONHITCHANCE = "weaponhitchance";
    public static final String KEY_WEAPONKRITCHANCE = "weaponkritchance";
    public static final String KEY_WEAPONEXTRA = "weaponextra";
    public static final String KEY_WEAPONSTAMINA = "weaponStamina";
    public static final String KEY_WEAPONSTRENGTH = "weaponstrength";
    public static final String KEY_WEAPONDEXTERITY = "weapondexterity";
    public static final String KEY_WEAPONINTELLIGENCE = "weaponintelligence";

    private ToDoDBOpenHelper dbHelper;
    private SQLiteDatabase db;

    public WeaponDatabase(Context context) {
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


    public long insertAllmainActivity() {

        ContentValues newValues = new ContentValues();

        newValues.put(KEY_WEAPON,8);
        newValues.put(KEY_WEAPONDAMAGE, 1);
        newValues.put(KEY_WEAPONHITCHANCE, 85);
        newValues.put(KEY_WEAPONKRITCHANCE, 60);
        newValues.put(KEY_WEAPONEXTRA, 0);
        newValues.put(KEY_WEAPONSTAMINA, 0);
        newValues.put(KEY_WEAPONSTRENGTH, 0);
        newValues.put(KEY_WEAPONDEXTERITY, 0);
        newValues.put(KEY_WEAPONINTELLIGENCE, 0);

        return db.insert(DATABASE_TABLE, null, newValues);
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

    //update weapon and weaponstats of databaserow 1

    public void updateAll(int weapon, int weapondamage, int weaponhitchance, int weaponkritchance,int weaponextra, int weaponstamina, int weaponstrength, int weapondexterity, int weaponintelligence) {
        ContentValues values = new ContentValues();
        values.put(KEY_WEAPON, weapon);
        values.put(KEY_WEAPONDAMAGE, weapondamage);
        values.put(KEY_WEAPONHITCHANCE, weaponhitchance);
        values.put(KEY_WEAPONKRITCHANCE, weaponkritchance);
        values.put(KEY_WEAPONEXTRA, weaponextra);
        values.put(KEY_WEAPONSTAMINA, weaponstamina);
        values.put(KEY_WEAPONSTRENGTH, weaponstrength);
        values.put(KEY_WEAPONDEXTERITY, weapondexterity);
        values.put(KEY_WEAPONINTELLIGENCE, weaponintelligence);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    public void updateAll(int[] weapon) {
        ContentValues values = new ContentValues();
        values.put(KEY_WEAPON, weapon[0]);
        values.put(KEY_WEAPONDAMAGE, weapon[1]);
        values.put(KEY_WEAPONHITCHANCE, weapon[2]);
        values.put(KEY_WEAPONKRITCHANCE, weapon[3]);
        values.put(KEY_WEAPONEXTRA,weapon[4]);
        values.put(KEY_WEAPONSTAMINA, weapon[5]);
        values.put(KEY_WEAPONSTRENGTH, weapon[6]);
        values.put(KEY_WEAPONDEXTERITY, weapon[7]);
        values.put(KEY_WEAPONINTELLIGENCE, weapon[8]);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }

    public int[] getWeapon(){
        int[] weaponArray = new int[8];

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
                        KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        weaponArray[0] = cursor.getInt(1);
        weaponArray[1] = cursor.getInt(2);
        weaponArray[2] = cursor.getInt(3);
        weaponArray[3] = cursor.getInt(4);
        weaponArray[4] = cursor.getInt(5);
        weaponArray[5] = cursor.getInt(6);
        weaponArray[6] = cursor.getInt(7);
        weaponArray[7] = cursor.getInt(8);
        weaponArray[8] = cursor.getInt(9);




        return weaponArray;
    }


    public int getWeaponTyp() {

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
                        KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE}, KEY_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(1);
    }





    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_WEAPON + " integer, " + KEY_WEAPONDAMAGE
                + " integer, " + KEY_WEAPONHITCHANCE + " integer, " + KEY_WEAPONKRITCHANCE + " integer, " + KEY_WEAPONEXTRA + " integer, " + KEY_WEAPONSTAMINA
                + " integer, " + KEY_WEAPONSTRENGTH + " integer, " + KEY_WEAPONDEXTERITY + " integer, " + KEY_WEAPONINTELLIGENCE
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
