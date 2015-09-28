package com.example.markus.locationbasedadventure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.markus.locationbasedadventure.Items.Equip;

import java.util.ArrayList;

/**
 * Created by Markus on 01.09.2015.
 */
public class ArmorDatabase {


    private static final String DATABASE_NAME = "Armor6.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "Armors";

    private static final String KEY_ID = "_id";

    private  static final String KEY_ARMOR = "armor";
    private  static final String KEY_ARMORSTAMINA = "armorstamina";
    private  static final String KEY_ARMORSTRENGTH = "armorstrength";
    private  static final String KEY_ARMORDEXTERITY = "armordexterity";
    private  static final String KEY_ARMORINTELLIGENCE = "armorintelligence";

    private ToDoDBOpenHelper dbHelper;
    private SQLiteDatabase db;

    public ArmorDatabase(Context context) {
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


    //inserts standard values into the Database
    //called in MainActivity

    public long insertAllmainActivity() {

        ContentValues newValues = new ContentValues();

        newValues.put(KEY_ARMOR,1);
        newValues.put(KEY_ARMORSTAMINA, 1);
        newValues.put(KEY_ARMORSTRENGTH, 1);
        newValues.put(KEY_ARMORDEXTERITY, 1);
        newValues.put(KEY_ARMORINTELLIGENCE, 1);

        return db.insert(DATABASE_TABLE, null, newValues);
    }


    //insert a new Armor
    //gets five int Values
    //if you have 10 Armor, nothing happens

    public long insertNewArmor(int armor, int armorstamina, int armorstrength, int armordexterity, int armorintelligence) {

        if(getAllArmorItems().size()==10){

        }else{

            ContentValues newValues = new ContentValues();

            newValues.put(KEY_ARMOR,armor);
            newValues.put(KEY_ARMORSTAMINA, armorstamina);
            newValues.put(KEY_ARMORSTRENGTH, armorstrength);
            newValues.put(KEY_ARMORDEXTERITY, armordexterity);
            newValues.put(KEY_ARMORINTELLIGENCE, armorintelligence);

            return db.insert(DATABASE_TABLE, null, newValues);

        }
        return 0;
    }

    //update weapon and weaponstats of databaserow 1

    public void updateAll(int armor,int armorstamina, int armorstrength, int armordexterity, int armorintelligence) {
        ContentValues values = new ContentValues();
        values.put(KEY_ARMOR, armor);
        values.put(KEY_ARMORSTAMINA, armorstamina);
        values.put(KEY_ARMORSTRENGTH, armorstrength);
        values.put(KEY_ARMORDEXTERITY, armordexterity);
        values.put(KEY_ARMORINTELLIGENCE, armorintelligence);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(1)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }



    //updatesAll values of the database
    //gets int[] armor as updateValues
    //gets int id as row number which has to be updated

    public void updateAll(int[] armor,int id) {
        ContentValues values = new ContentValues();
        values.put(KEY_ARMOR, armor[0]);
        values.put(KEY_ARMORSTAMINA, armor[1]);
        values.put(KEY_ARMORSTRENGTH, armor[2]);
        values.put(KEY_ARMORDEXTERITY, armor[3]);
        values.put(KEY_ARMORINTELLIGENCE, armor[4]);
        String where_clause = KEY_ID + "=?";
        String[] where_args = new String[]{String.valueOf(id)};   // Immer Zeile 1, weil nur eine Zeile vorhanden
        db.update(DATABASE_TABLE, values, where_clause, where_args);
    }


    //return the Used Armor Values as int[]  (= first row)

    public int[] getArmor(){
        int[] armorArray = new int[5];

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_ARMOR,
                        KEY_ARMORSTAMINA, KEY_ARMORSTRENGTH, KEY_ARMORDEXTERITY, KEY_ARMORINTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();


        armorArray[0] = cursor.getInt(1);
        armorArray[1] = cursor.getInt(2);
        armorArray[2] = cursor.getInt(3);
        armorArray[3] = cursor.getInt(4);
        armorArray[4] = cursor.getInt(5);

        return armorArray;
    }

    // return the String of the UsedArmor ( = first row)

    public String getArmorString(){

        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,KEY_ARMOR,
                        KEY_ARMORSTAMINA, KEY_ARMORSTRENGTH, KEY_ARMORDEXTERITY, KEY_ARMORINTELLIGENCE}, KEY_ID + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);            // Immer Zeile 1, weil nur eine Zeile vorhanden

        if (cursor != null)
            cursor.moveToFirst();

        return selectString(cursor.getInt(1));
    }



    //returns an ArrayList with all ArmorItem except the Used Armor

    public ArrayList<Equip> getAllArmorItems(){
        ArrayList<Equip> items = new ArrayList<Equip>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID,KEY_ARMOR,
                KEY_ARMORSTAMINA, KEY_ARMORSTRENGTH, KEY_ARMORDEXTERITY, KEY_ARMORINTELLIGENCE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            if(!cursor.moveToNext()){
                return items;
            }
            do {

                int[] armor = new int[9];
                armor[0] = cursor.getInt(1);
                armor[1] = cursor.getInt(2);
                armor[2] = cursor.getInt(3);
                armor[3] = cursor.getInt(4);
                armor[4] = cursor.getInt(5);
                armor[5] = cursor.getInt(0);
                items.add(new Equip(armor,false));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;

    }


    //changes an Armor to Used Armor ( = to first row)

    public void changeToUsedArmor(Equip armorItem) {
        updateAll(getArmor(),armorItem.getArmorID());
        int[] newNumberOne = new int[5];
        newNumberOne[0] = armorItem.getArmorTyp();
        newNumberOne[1] = armorItem.getArmorStats()[0];
        newNumberOne[2] = armorItem.getArmorStats()[1];
        newNumberOne[3] = armorItem.getArmorStats()[2];
        newNumberOne[4] = armorItem.getArmorStats()[3];
        updateAll(newNumberOne, 1);


    }


    // selects Armor String
    //gets int armorTyp to diff
    //returns String


    private String selectString(int armorTyp) {
        switch(armorTyp){
            case 1: return "Standardrüstung";
            case 2: return "Verstärke Rüstung";
        }
        return "Leer";
    }


    // checks if Database is empty
    //returns true if Dtabase is empty

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

    //deletes an Armor from Database
    //gets int armorID to diff between rows to delete

    public int deleteArmor(int armorID){

        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE " + KEY_ID + "=" +armorID);

        return 0;
    }


    //deletes All Row except  the given one
    //gets int armorID to diff

    public int deleteAllExceptRow(int armorID){

        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE " + KEY_ID + "!=" +armorID);

        return 0;
    }





    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_ARMOR + " integer, " +  KEY_ARMORSTAMINA
                + " integer, " + KEY_ARMORSTRENGTH + " integer, " + KEY_ARMORDEXTERITY + " integer, " + KEY_ARMORINTELLIGENCE
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
