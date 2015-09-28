package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.AsynchronTasks.CreateCharacterTask;
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;

/**
 * Created by Markus on 20.07.2015.
 */
public class CreateCharacterActivity extends Activity implements CreateCharacterTask.CreateCharakterTaskListener{

    private Button characterErstellen;
    private ImageView characterImage;
    private CheckBox schild;
    private EditText name;
    private Spinner sex;
    private Spinner typ;
    private String weaponTyp= "Bogen";
    private int weaponNr;
    private CharacterdataDatabase characterdataDb;
    private WeaponDatabase weaponDb;
    private StatsDatabase statsDb;
    private String sexTyp = "Maennlich";
    private String address = "http://sruball.de/game/updateCreateCharacter.php";
    private String usernr ="";
    private String TAG_USERNR = "USERNR";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcharacter);
        getIntentData();
        initDB();
        initViews();
        initButtons();
        initSpinners();
    }

    //closes opened Database if activity is destroyed

    @Override
    protected void onDestroy() {
        characterdataDb.close();
        weaponDb.close();
        statsDb.close();
        super.onDestroy();
    }

    //gets Intent


    private void getIntentData(){
        Intent i = getIntent();
        usernr = i.getStringExtra(TAG_USERNR);
    }


    //Initialises the Databases
    // Opening the Databases

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
    }



    //Initialises Spinners
    //SpinnerListener

    private void initSpinners() {

        sex =(Spinner) findViewById(R.id.spinnerSex);
        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(this, R.array.sex_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sex.setAdapter(adapterSex);
        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexTyp = parent.getItemAtPosition(position).toString();
                selectImage();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       typ = (Spinner) findViewById(R.id.spinnerCharacterTyp);
        ArrayAdapter<CharSequence> adapterTyp = ArrayAdapter.createFromResource(this, R.array.typ_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterTyp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typ.setAdapter(adapterTyp);
        typ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weaponTyp = parent.getItemAtPosition(position).toString();
                weaponNr = getWeaponNr(weaponTyp);
                selectShowSchild();
                selectImage();
                schild.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    // Set checkbox visible

    private void selectShowSchild() {
            switch(weaponTyp){
                case "Einhandschwert":checkBoxVisible(); break;
                case "Einhandaxt":checkBoxVisible(); break;
                default: checkBoxInvisible();
            }
    }





    //Initialises button
    //ButtonListener
    //initialises Checkbox
    //set Checkbox invisible

    private void initButtons() {
        characterErstellen = (Button)findViewById(R.id.createCharakter);
        characterErstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInput();
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i);
                finish();
            }
        });
        schild = (CheckBox) findViewById(R.id.checkBoxSchild);
        checkBoxInvisible();
    }

    //Read data
    //Save data on server
    //update Database

    private void saveInput() {

        String characterName = name.getText().toString();
        new CreateCharacterTask(this,this).execute(address, characterName, sexTyp, weaponTyp, usernr);
        characterdataDb.updateCreatecharacter(characterName, sexTyp);
    }



    //Initialises EditText and ImageView

    private void initViews() {
        name = (EditText) findViewById(R.id.characterName);
        characterImage = (ImageView) findViewById(R.id.characterImage);
    }


    //Set checkBox invisible,  not enabled

    private void checkBoxInvisible(){
        schild.setChecked(false);
        schild.setEnabled(false);
        schild.setVisibility(View.INVISIBLE);
    }


    //Set checkBox visible, enabled

    private void checkBoxVisible(){
        schild.setEnabled(true);
        schild.setVisibility(View.VISIBLE);
        schild.setChecked(false);
    }

    //get String weaponName
    //return int weapon


    public int getWeaponNr(String weapon){
        switch(weapon){
            case "Bogen":
                return 8;
            case "Einhandschwert":
                return 1;
            case "Einhandaxt":
                return 2;
            case "Armbrust":
                return 9;
            case "Zauberstab":
                return 7;
            case "Zwei-Hand-Axt":
                return 6;
            case "Zwei-Hand-Schwert":
                return 5;
            case "Einhandschwert mit Schild":
                return 3;
            case "Einhandaxt mit Schild":
                return 4;
        }
        return 0;
    }

    //selectsImage
    //diffsBetween String sexTyp ( "Männlich" and "Weiblich")
    //diffs between String weaponTyp
    //calls loadBitmap()

    private void selectImage() {


            switch (weaponTyp) {
                case "Bogen":
                    loadBitmap(R.drawable.einhandschwert, characterImage);
                    break;
                case "Einhandschwert":
                    if(schild.isChecked()){
                        weaponTyp = "Einhandschwert mit Schild";
                        loadBitmap(R.drawable.einhandschwertschildweiblich, characterImage);
                    }else{
                        loadBitmap(R.drawable.einhandschwert, characterImage);
                    }
                    break;
                case "Einhandaxt":
                    if(schild.isChecked()){
                        weaponTyp = "Einhandaxt mit Schild";
                        loadBitmap(R.drawable.einhandaxtschildweiblich, characterImage);
                    }else{
                        loadBitmap(R.drawable.einhandaxt, characterImage);
                    }
                    break;
                case "Armbrust":
                    loadBitmap(R.drawable.gewehrweiblich, characterImage);
                    break;
                case "Zauberstab":
                    loadBitmap(R.drawable.zauberstabweiblich, characterImage);
                    break;
                case "Zwei-Hand-Axt":
                    loadBitmap(R.drawable.zweihandaxt, characterImage);
                    break;
                case "Zwei-Hand-Schwert":
                    loadBitmap(R.drawable.zweihandschwert, characterImage);
                    break;
            }

    }



    //loads an Bitmap into ImageView by using BackgroundTask

    public void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }


    //updates weaponDatabase

    @Override
    public void weaponDataRetrieved(int[] weaponArray) {
        weaponDb.updateAll(weaponArray, 1);
        statsDb.updateAll(1,0,15,15,15,15);

    }
}



