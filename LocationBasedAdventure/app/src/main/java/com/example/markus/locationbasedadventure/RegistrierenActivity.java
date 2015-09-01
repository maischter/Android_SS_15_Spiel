package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.RegistrierenTask;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Hashing.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Markus on 20.07.2015.
 */
public class RegistrierenActivity extends Activity implements RegistrierenTask.RegistrierenTaskListener {

    Button anmelden;
    Button registrieren;
    TextView alreadyRegistered;
    EditText email;
    EditText passwort;
    TextView diskurs;
    CharacterdataDatabase characterdataDb;
    private String address = "http://sruball.de/game/insertIntoAnmeldedaten.php";
    String TAG_USERNR = "USERNR";
    String emailToRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registrieren);
        initDB();
        initViews();
        initButton();
    }


    @Override
    protected void onDestroy() {
        characterdataDb.close();
        super.onDestroy();    }

    // Opening the Database

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
    }

    private void initButton() {
        anmelden = (Button) findViewById(R.id.buttonAnmeldenAkt);
        anmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyEditTexts();
                Intent i = new Intent(getApplicationContext(), AnmeldenActivity.class);
                startActivity(i);
            }
        });
        registrieren = (Button) findViewById(R.id.buttonRegistrieren);
        registrieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });
    }

    //Liest Daten aus den EditViews
    //Speichert Daten ueber die Klasse RegistrierenTask per AsyncTask in Server

    private void saveInput(String emailText,String passwortText) {
        String passwort = "";
        try {
             passwort = PasswordHash.createHash(passwortText);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        emailToRemember = emailText;
        System.out.println(emailToRemember);
        new RegistrierenTask(this,this).execute(emailText, passwort, address);
    }

    //prueft ob gueltiges Email eingegeben wurde --> nein : EmailWrong Diskurs, Email & Passwort leer
    // --> ja : ueberprueft passwort --> nein: passwort falsch diskurs
    //              --> ja: ruft saveInput() auf

    private void checkInput(){
        if(isValidEmail(email.getText().toString())){
            if(isValidPassword(passwort.getText().toString())){
                saveInput(email.getText().toString(),passwort.getText().toString());
            }else{
               diskurs.setText(R.string.passwortShort);
                emptyEditTexts();
            }
        }else{
            diskurs.setText(R.string.emailWrong);
            emptyEditTexts();
        }
    }

    //initialisieren der Views und EditText


    private void initViews() {
        alreadyRegistered = (TextView) findViewById(R.id.textViewAlreadyRegistered);
        email = (EditText) findViewById(R.id.editTextEmailAdressReg);
        passwort = (EditText) findViewById(R.id.editTextPasswordReg);
        diskurs = (TextView) findViewById(R.id.textViewDiskurs);
    }

    // setzt edittext auf leer zurueck

    private void emptyEditTexts() {
        email.setText("");
        passwort.setText("");
    }


    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // prueft Passwort --> muss laenger als 6 Zeichen sein.


    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }


    //Methode des Interfaces, um die UserNummer zurueck zu bekommen
    // Wenn registered == 0 --> bereits registriert diskurs
    // registered != 0 -->Startet die neue Aktivity und uebergibt ihr die Usernr

    @Override
    public void UserNrRetrieved(String data,int registered) {
        System.out.println(registered);
        if(registered == 0){
            diskurs.setText(R.string.alreadyregisteredText);
            emptyEditTexts();
        }else{
            characterdataDb.updateEmail(emailToRemember);
            Intent i = new Intent(getApplicationContext(), CreateCharacterActivity.class);
            i.putExtra(TAG_USERNR, data);
            startActivity(i);
            finish();
        }

    }
}
