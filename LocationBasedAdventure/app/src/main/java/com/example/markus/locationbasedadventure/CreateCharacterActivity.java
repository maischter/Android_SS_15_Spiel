package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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

/**
 * Created by Markus on 20.07.2015.
 */
public class CreateCharacterActivity extends Activity{

    Button characterErstellen;
    ImageView characterImage;
    CheckBox schild;
    EditText name;
    Spinner sex;
    Spinner typ;
    String waponTyp= "Bogen";

    String sexTyp = "Maennlich";
    private String address = "http://sruball.de/game/updateCreateCharacter.php";
    String usernr ="";
    String TAG_USERNR = "USERNR";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_createcharacter);
        Intent i = getIntent();
        usernr = i.getStringExtra(TAG_USERNR);
        System.out.println(usernr);
        initViews();
        initButtons();
        initSpinners();
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
                waponTyp = parent.getItemAtPosition(position).toString();
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
            switch(waponTyp){
                case "Einhandschwert":checkBoxVisible(); break;
                case "Einhandaxt":checkBoxVisible(); break;
                default: checkBoxInvisible();
            }



    }

    //asynchronustask!!




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
        new CreateCharacterTask().execute(address, characterName, sexTyp, waponTyp, usernr);
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

            switch (waponTyp) {
                case ("Bogen"):
                    //icon = getResources().getDrawable(R.drawable.bogenweiblich);
                    loadBitmap(R.drawable.bogenweiblich, characterImage);
                    break;
                case "Einhandschwert":
                    if(schild.isChecked()){
                        waponTyp = "Einhandschwert mit Schild";
                        //icon = getResources().getDrawable(R.drawable.einhandschwertschildweiblich);
                        loadBitmap(R.drawable.einhandschwertschildweiblich, characterImage);
                    }else{
                        //icon = getResources().getDrawable(R.drawable.einhandschwertweiblich);
                        loadBitmap(R.drawable.einhandschwertweiblich, characterImage);;
                    }
                    break;
                case "Einhandaxt":
                    if(schild.isChecked()){
                        waponTyp = "Einhandaxt mit Schild";
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
            switch (waponTyp) {
                case ("Bogen"):
                    //icon = getResources().getDrawable(R.drawable.bogenweiblich);
                    loadBitmap(R.drawable.bogenmannlich, characterImage);
                    break;
                case "Einhandschwert":
                    if(schild.isChecked()){
                        waponTyp = "Einhandschwert mit Schild";
                        //icon = getResources().getDrawable(R.drawable.einhandschwertschildweiblich);
                        loadBitmap(R.drawable.einhandschwertschildmannlich, characterImage);
                    }else{
                        //icon = getResources().getDrawable(R.drawable.einhandschwertweiblich);
                        loadBitmap(R.drawable.einhandschwertmannlich, characterImage);
                    }
                    break;
                case "Einhandaxt":
                    if(schild.isChecked()){
                        waponTyp = "Einhandaxt mit Schild";
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



