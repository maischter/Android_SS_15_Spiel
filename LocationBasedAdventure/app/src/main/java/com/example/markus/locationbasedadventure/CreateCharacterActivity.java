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
import com.example.markus.locationbasedadventure.Database.MySqlDatabase;

/**
 * Created by Markus on 20.07.2015.
 */
public class CreateCharacterActivity extends Activity{

    private Button characterErstellen;
    private ImageView characterImage;
    private CheckBox schild;
    private EditText name;
    private Spinner sex;
    private Spinner typ;
    private String weaponTyp= "Bogen";
    MySqlDatabase db;

    private String sexTyp = "Maennlich";
    private String address = "http://sruball.de/game/updateCreateCharacter.php";
    private String usernr ="";
    private String TAG_USERNR = "USERNR";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_createcharacter);
        getIntentData();
        initDB();
        initViews();
        initButtons();
        initSpinners();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();    }


    private void getIntentData(){
        Intent i = getIntent();
        usernr = i.getStringExtra(TAG_USERNR);
    }
    // Opening the Database

    private void initDB(){
        db = new MySqlDatabase(this);
        db.open();
    }



    // Initialisieren der Spinner + zugehoerige Listener;
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


    // Setzt CheckBox Sichtbar, um das Schild auszuwaehlen

    private void selectShowSchild() {
            switch(weaponTyp){
                case "Einhandschwert":checkBoxVisible(); break;
                case "Einhandaxt":checkBoxVisible(); break;
                default: checkBoxInvisible();
            }



    }





    //Initialisieren des Button + Listener
    //Und der Checkbox -->Unsichtbar

    private void initButtons() {
        characterErstellen = (Button)findViewById(R.id.createCharakter);
        characterErstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInput();
                Intent i = new Intent(getApplicationContext(),GifActivity.class);
                startActivity(i);
            }
        });
        schild = (CheckBox) findViewById(R.id.checkBoxSchild);
        checkBoxInvisible();
    }


    //Auslesen der Daten
    //Speichern der Daten in den Server

    private void saveInput() {

        String characterName = name.getText().toString();
        new CreateCharacterTask().execute(address, characterName, sexTyp, weaponTyp, usernr);
        db.updateAll(characterName,weaponTyp,"","","");

    }

    //Initialisieren des Edit Texts und des Image Views

    private void initViews() {
        name = (EditText) findViewById(R.id.characterName);
        characterImage = (ImageView) findViewById(R.id.characterImage);
    }


    //Set checkBox unsichtbar,  not enabled

    private void checkBoxInvisible(){
        schild.setChecked(false);
        schild.setEnabled(false);
        schild.setVisibility(View.INVISIBLE);
    }


    //Set checkBox sichtbar, enabled

    private void checkBoxVisible(){
        schild.setEnabled(true);
        schild.setVisibility(View.VISIBLE);
        schild.setChecked(false);
    }



    private void selectImage() {

        if(sexTyp.equals("Weiblich")) {

            switch (weaponTyp) {
                case ("Bogen"):
                    //icon = getResources().getDrawable(R.drawable.bogenweiblich);
                    loadBitmap(R.drawable.bogenweiblich, characterImage);
                    break;
                case "Einhandschwert":
                    if(schild.isChecked()){
                        weaponTyp = "Einhandschwert mit Schild";
                        //icon = getResources().getDrawable(R.drawable.einhandschwertschildweiblich);
                        loadBitmap(R.drawable.einhandschwertschildweiblich, characterImage);
                    }else{
                        //icon = getResources().getDrawable(R.drawable.einhandschwertweiblich);
                        loadBitmap(R.drawable.einhandschwertweiblich, characterImage);
                    }
                    break;
                case "Einhandaxt":
                    if(schild.isChecked()){
                        weaponTyp = "Einhandaxt mit Schild";
                        //icon = getResources().getDrawable(R.drawable.einhandaxtschildweiblich);
                        loadBitmap(R.drawable.einhandaxtschildweiblich, characterImage);
                    }else{
                        //icon = getResources().getDrawable(R.drawable.einhandaxtweiblich);
                        loadBitmap(R.drawable.einhandaxtweiblich, characterImage);
                    }
                    break;
                case "Gewehr":
                    //icon = getResources().getDrawable(R.drawable.gewehrweiblich);
                    loadBitmap(R.drawable.gewehrweiblich, characterImage);
                    break;
                case "Zauberstab":
                    // icon = getResources().getDrawable(R.drawable.zauberstabweiblich);
                    loadBitmap(R.drawable.zauberstabweiblich, characterImage);
                    break;
                case "Zwei-Hand-Axt":
                    //icon = getResources().getDrawable(R.drawable.zweihandaxtweiblich);
                    loadBitmap(R.drawable.zweihandaxtweiblich, characterImage);
                    break;
                case "Zwei-Hand-Schwert":
                    //icon = getResources().getDrawable(R.drawable.zweihandschwertweiblich);
                    loadBitmap(R.drawable.zweihandschwertweiblich, characterImage);
                    break;
            }
        }
        else{
            switch (weaponTyp) {
                case ("Bogen"):
                    //icon = getResources().getDrawable(R.drawable.bogenweiblich);
                    loadBitmap(R.drawable.bogenmannlich, characterImage);
                    break;
                case "Einhandschwert":
                    if(schild.isChecked()){
                        weaponTyp = "Einhandschwert mit Schild";
                        //icon = getResources().getDrawable(R.drawable.einhandschwertschildweiblich);
                        loadBitmap(R.drawable.einhandschwertschildmannlich, characterImage);
                    }else{
                        //icon = getResources().getDrawable(R.drawable.einhandschwertweiblich);
                        loadBitmap(R.drawable.einhandschwertmannlich, characterImage);
                    }
                    break;
                case "Einhandaxt":
                    if(schild.isChecked()){
                        weaponTyp = "Einhandaxt mit Schild";
                        //icon = getResources().getDrawable(R.drawable.einhandaxtschildweiblich);
                        loadBitmap(R.drawable.einhandaxtschildmannlich, characterImage);
                    }else{
                        //icon = getResources().getDrawable(R.drawable.einhandaxtweiblich);
                        loadBitmap(R.drawable.einhandaxtmannlich, characterImage);
                    }
                    break;
                case "Gewehr":
                    //icon = getResources().getDrawable(R.drawable.gewehrweiblich);
                    loadBitmap(R.drawable.gewehrmannlich, characterImage);
                    break;
                case "Zauberstab":
                    // icon = getResources().getDrawable(R.drawable.zauberstabweiblich);
                    loadBitmap(R.drawable.zauberstabmannlich, characterImage);
                    break;
                case "Zwei-Hand-Axt":
                    //icon = getResources().getDrawable(R.drawable.zweihandaxtweiblich);
                    loadBitmap(R.drawable.zweihandaxtmannlich, characterImage);
                    break;
                case "Zwei-Hand-Schwert":
                    //icon = getResources().getDrawable(R.drawable.zweihandschwertweiblich);
                    loadBitmap(R.drawable.zweihandschwertmannlich, characterImage);
                    break;
            }

        }
    }

    public void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }

}



