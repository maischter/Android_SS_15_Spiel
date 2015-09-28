package com.example.markus.locationbasedadventure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.R;

import java.util.ArrayList;

/**
 * Created by Markus on 01.09.2015.
 */

public class WeaponDatabase {

    private static final String DATABASE_NAME = "WeaponDatenbank.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "Weapons";

    private static final String KEY_ID = "_id";
    private static final String KEY_WEAPON = "weapon";
    private static final String KEY_WEAPONDAMAGE = "weapondamage";
    private static final String KEY_WEAPONHITCHANCE = "weaponhitchance";
    private static final String KEY_WEAPONKRITCHANCE = "weaponkritchance";
    private static final String KEY_WEAPONEXTRA = "weaponextra";
    private static final String KEY_WEAPONSTAMINA = "weaponStamina";
    private static final String KEY_WEAPONSTRENGTH = "weaponstrength";
    private static final String KEY_WEAPONDEXTERITY = "weapondexterity";
    private static final String KEY_WEAPONINTELLIGENCE = "weaponintelligence";

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


    //inserts standart values into Database
    //is called in MainActivity

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



    //inserts a new Weapon
    //gets nine int Values to insert
    //if aleready ten Item are in Datbase, nothing habens

    public long insertNewWeapon(int weapon, int weapondamage, int weaponhitchance, int weaponkritchance,int weaponextra, int weaponstamina, int weaponstrength, int weapondexterity, int weaponintelligence) {

        if(getAllWeaponItems().size()==10){

        }else{



        ContentValues newValues = new ContentValues();

        newValues.put(KEY_WEAPON,weapon);
        newValues.put(KEY_WEAPONDAMAGE, weapondamage);
        newValues.put(KEY_WEAPONHITCHANCE, weaponhitchance);
        newValues.put(KEY_WEAPONKRITCHANCE, weaponkritchance);
        newValues.put(KEY_WEAPONEXTRA, weaponextra);
        newValues.put(KEY_WEAPONSTAMINA, weaponstamina);
        newValues.put(KEY_WEAPONSTRENGTH, weaponstrength);
        newValues.put(KEY_WEAPONDEXTERITY, weapondexterity);
        newValues.put(KEY_WEAPONINTELLIGENCE, weaponintelligence);

        return db.insert(DATABASE_TABLE, null, newValues);

        }
        return 0;
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

    //updates all Values
    //get int[], gets int id to diff between row

    public void updateAll(int[] weapon,int id) {
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
        String[] where_args = new String[]{String.valueOf(id)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }


    //inserts a new Weapon
    //gets int[] Values to insert
    //if aleready ten Item are in Datbase, nothing habens

    public long insertNewWeapon(int[] weapon) {

        if(getAllWeaponItems().size()==10){

        }else{

            ContentValues newValues = new ContentValues();


            newValues.put(KEY_WEAPON, weapon[0]);
            newValues.put(KEY_WEAPONDAMAGE, weapon[1]);
            newValues.put(KEY_WEAPONHITCHANCE, weapon[2]);
            newValues.put(KEY_WEAPONKRITCHANCE, weapon[3]);
            newValues.put(KEY_WEAPONEXTRA,weapon[4]);
            newValues.put(KEY_WEAPONSTAMINA, weapon[5]);
            newValues.put(KEY_WEAPONSTRENGTH, weapon[6]);
            newValues.put(KEY_WEAPONDEXTERITY, weapon[7]);
            newValues.put(KEY_WEAPONINTELLIGENCE, weapon[8]);

            return db.insert(DATABASE_TABLE, null, newValues);

        }
        return 0;
    }

    // gets int[] with all weapon values

    public int[] getWeapon(){
        int[] weaponArray = new int[9];

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


    //gets int weaponTyp of Used Weapon ( = row one)

    public int getWeaponTyp() {

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
                        KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE}, KEY_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(1);
    }

    //gets String of Used Weapon ( row one)

    public String getWeaponString() {

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_WEAPON,
                        KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
                        KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE}, KEY_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();



        return selectImage(cursor.getInt(1));
    }


    //gets List with all Items back

    public ArrayList<Equip> getAllWeaponItemsFromStart(){
        ArrayList<Equip> items = new ArrayList<Equip>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_WEAPON,
                KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
                KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                int[] weapon = new int[10];
                weapon[0] = cursor.getInt(1);
                weapon[1] = cursor.getInt(2);
                weapon[2] = cursor.getInt(3);
                weapon[3] = cursor.getInt(4);
                weapon[4] = cursor.getInt(5);
                weapon[5] = cursor.getInt(6);
                weapon[6] = cursor.getInt(7);
                weapon[7] = cursor.getInt(8);
                weapon[8] = cursor.getInt(9);
                weapon[9] = cursor.getInt(0);
                items.add(new Equip(weapon,true));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;

    }

    //gets List with all Items except the first one back

    public ArrayList<Equip> getAllWeaponItems(){
        ArrayList<Equip> items = new ArrayList<Equip>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_WEAPON,
                KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
                KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            if(!cursor.moveToNext()){
                return items;
            }
            do {

                int[] weapon = new int[10];
                weapon[0] = cursor.getInt(1);
                weapon[1] = cursor.getInt(2);
                weapon[2] = cursor.getInt(3);
                weapon[3] = cursor.getInt(4);
                weapon[4] = cursor.getInt(5);
                weapon[5] = cursor.getInt(6);
                weapon[6] = cursor.getInt(7);
                weapon[7] = cursor.getInt(8);
                weapon[8] = cursor.getInt(9);
                weapon[9] = cursor.getInt(0);
                items.add(new Equip(weapon,true));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;

    }

    //slects an String
    //gets int weaponTyp
    //return String

    private String selectImage(int weaponTyp) {
        switch (weaponTyp) {
            case 8:
                return "Bogen";
            case 1:
                return "Einhandschwert";
            case 3:
                return "Einhandschwert mit Schild";
            case 2:
                return "Einhandaxt";
            case 4:
                return "Einhandaxt mit Schild";
            case 9:
                return "Armbrust";
            case 7:
                return "Zauberstab";
            case 6:
                return "Zweihandaxt";
            case 5:
                return "Zweihandschwert";

        }
        return null;

    }

    //changes a weapon to used Weapon (row one)

    public void changeToUsedWeapon(Equip weaponItem) {
        updateAll(getWeapon(),weaponItem.getWeaponID());
        int[] newNumberOne = new int[9];
        newNumberOne[0] = weaponItem.getWeaponTyp();
        newNumberOne[1] = weaponItem.getWeaponDmg();
        newNumberOne[2] = weaponItem.getWeaponHitrate();
        newNumberOne[3] = weaponItem.getWeaponCritrate();
        newNumberOne[4] = weaponItem.getWeaponExtra();
        newNumberOne[5] = weaponItem.getWeaponStats()[0];
        newNumberOne[6] = weaponItem.getWeaponStats()[1];
        newNumberOne[7] = weaponItem.getWeaponStats()[2];
        newNumberOne[8] = weaponItem.getWeaponStats()[3];
        updateAll(newNumberOne,1);


    }


    //deletes a Weapon

    public int deleteWeapon(int weaponID){

        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE " + KEY_ID + "=" + weaponID);

        return 0;
    }


    //deletes all weapons ExceptRow1

    public int deleteAllExceptRow(int weaponID){

        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE " + KEY_ID + "!=" + weaponID);

        return 0;
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
