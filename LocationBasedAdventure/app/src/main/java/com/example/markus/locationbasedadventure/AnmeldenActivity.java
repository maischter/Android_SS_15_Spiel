package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.AnmeldenTask;
import com.example.markus.locationbasedadventure.AsynchronTasks.SyndicateStatsLocalToServerTask;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Hashing.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Markus on 20.07.2015.
 */
public class AnmeldenActivity extends Activity implements AnmeldenTask.AnmeldenTaskListener {


    private Button anmelden;
    private Button registrieren;
    private CheckBox stayAngemeldet;
    private TextView notRegistered;
    private EditText email;
    private EditText passwort;
    private TextView diskurs;
    private CharacterdataDatabase characterdataDb;
    private StatsDatabase statsDb;
    private String address = "http://sruball.de/game/checkAnmelden.php";
    private String address2 = "http://sruball.de/game/syndicateData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anmelden);
        initDB();
        initViews();
        initButtons();
    }

    @Override
    protected void onDestroy() {
        characterdataDb.close();
        statsDb.close();
        super.onDestroy();
    }

    //Initialises  EditTexts and TextViews

    private void initViews() {
        email = (EditText) findViewById(R.id.editTextEmailAdresse);
        passwort = (EditText) findViewById(R.id.editTextPassword);
        notRegistered = (TextView) findViewById(R.id.textViewNotRegistered);
        diskurs = (TextView) findViewById(R.id.textViewDiskurs);
    }

    //Initialises  Buttons
    //ButtonListeners
    //Initialises CheckBox

    private void initButtons() {
        anmelden = (Button) findViewById(R.id.buttonAnmelden);
        anmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputOnServer();
                diskurs.setText("");
            }
        });
        registrieren = (Button)findViewById(R.id.buttonRegistrierenAkt);
        registrieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyEditTexts();
                Intent i = new Intent(getApplicationContext(),RegistrierenActivity.class);
                startActivity(i);
            }
        });
        stayAngemeldet = (CheckBox) findViewById(R.id.checkBoxAngemeldetBleiben);
    }


    //Read CheckBox --> true save StayAngemeldet ind Database
    //Syndycate ServerData with Databases
    //Starts MapActivity


    private void dataCorrect() {
            if(stayAngemeldet.isChecked()){
                saveStayAngemeldetIntoDBOnPhone();
            }
            new SyndicateStatsLocalToServerTask(this).execute(address2,characterdataDb.getEmail(),""+statsDb.getLevel(),""+statsDb.getExp(),""+statsDb.getStamina(),""+statsDb.getStrength(),""+statsDb.getDexterity(),""+statsDb.getIntelligence());
            Intent i = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(i);
        }


    //Saves stayAngemeldet as true into Database


    private void saveStayAngemeldetIntoDBOnPhone() {
        characterdataDb.updateStayAngemeldet(1);
    }

    //Gets Data from Server by using BackgroundTask

    private void checkInputOnServer() {
        String email = this.email.getText().toString();
        String passwort = this.passwort.getText().toString();
        new AnmeldenTask(this,this).execute(address, email, passwort);
    }

    // initialises Databases
    // open Databases

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
    }



    //Wrong Email --> calls emailNotKnownDiskurs();
    //Wrong Password --> calls passwordWrongDiskurs();
    //calls dataCorrect() if email and password are correct

    @Override
    public void registrationDataRetrieved(Integer usernr,String passworthash) {

        try {
            if(usernr==0){
                emailNotKnownDiskurs();
            }else{
                if(PasswordHash.validatePassword(passwort.getText().toString(),passworthash)){
                    dataCorrect();
                }else{
                    passwortWrongDiskurs();
                }
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }


    //informs user about wrong password


    private void passwortWrongDiskurs() {
        passwort.setText("");
        diskurs.setText(R.string.passwortWrong);
    }


    //informs user about wrong email


    private void emailNotKnownDiskurs() {
        emptyEditTexts();
        diskurs.setText(R.string.emailNotRegistered);
    }

    // sets EditText empty

    private void emptyEditTexts() {
        email.setText("");
        passwort.setText("");
    }

}
