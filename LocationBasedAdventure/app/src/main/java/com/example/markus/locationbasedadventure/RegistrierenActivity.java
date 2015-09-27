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

    private Button anmelden;
    private Button registrieren;
    private TextView alreadyRegistered;
    private EditText email;
    private EditText passwort;
    private TextView diskurs;

    private CharacterdataDatabase characterdataDb;
    private String TAG_USERNR = "USERNR";
    private String emailToRemember;
    private String address = "http://sruball.de/game/insertIntoAnmeldedaten.php";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);

        initDB();
        initViews();
        initButton();
    }


    //closes Database if activtiy is destroyed

    @Override
    protected void onDestroy() {
        characterdataDb.close();
        super.onDestroy();    }

    //initialises Database
    // Open Database

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
    }

    //initialises Button
    //buttonListener

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

    //read Data from EditText
    //Saves Data in Server by using BackgroundTask

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
        new RegistrierenTask(this,this).execute(emailText, passwort, address);
    }

    //checks if email is valid
    // --> no : EmailWrong Diskurs, set Email & Password empty
    // --> ja : checkes password --> no: password wrong diskurs
    //                           --> yes: calls saveInput()

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

    //initialises TextViews and EditTexts


    private void initViews() {
        alreadyRegistered = (TextView) findViewById(R.id.textViewAlreadyRegistered);
        email = (EditText) findViewById(R.id.editTextEmailAdressReg);
        passwort = (EditText) findViewById(R.id.editTextPasswordReg);
        diskurs = (TextView) findViewById(R.id.textViewDiskurs);
    }

    // set edittext empty

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

    // checkes password --> must be longer then 6


    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }


    //interface method to get usernr
    //  registered == 0 --> aleready registered diskurs
    //  registered != 0 -->starts createCharacterActivty
    //                  -->putsExtra to intent
    //                  -->update Database
    //                  -->finish this activity

    @Override
    public void UserNrRetrieved(String data,int registered) {
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
