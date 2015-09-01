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
import com.example.markus.locationbasedadventure.AsynchronTasks.syndciateStatsLocalToServerTask;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Hashing.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Markus on 20.07.2015.
 */
public class AnmeldenActivity extends Activity implements AnmeldenTask.AnmeldenTaskListener {


    Button anmelden;
    Button registrieren;
    CheckBox stayAngemeldet;
    TextView notRegistered;
    EditText email;
    EditText passwort;
    TextView diskurs;
    CharacterdataDatabase characterdataDb;
    StatsDatabase statsDb;
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
        super.onDestroy();    }


    //Initialisieren der EditText und TextView

    private void initViews() {
        email = (EditText) findViewById(R.id.editTextEmailAdress);
        passwort = (EditText) findViewById(R.id.editTextPassword);
        notRegistered = (TextView) findViewById(R.id.textViewNotRegistered);
        diskurs = (TextView) findViewById(R.id.textViewDiskurs);
    }

    //Initialisieren der Button und die zugehoerigen Listener
    //Initialisieren der CheckBox

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




    //Zustand der CheckBox auslesen - bei true, speichern auf dem Smartphone
    //Wechsel zu neuer Activity, falls Daten korrekt

    //Momentan noch Wechsel zu MainActivity!!


    private void dataCorrect() {
            if(stayAngemeldet.isChecked()){
                saveStayAngemeldetIntoDBOnPhone();
            }
            new syndciateStatsLocalToServerTask(this).execute(address2,characterdataDb.getEmail(),""+statsDb.getLevel(),""+statsDb.getExp(),""+statsDb.getStamina(),""+statsDb.getStrength(),""+statsDb.getDexterity(),""+statsDb.getIntelligence());
            Intent i = new Intent(getApplicationContext(),MenueActivity.class); // eigentlich zu MAPActivity
            startActivity(i);
        }




    //Speichern des Angemeldet Bleiben Status in lokaler Datenbank, um bei Neustart der App direkt zur Map zu gelangen
    //noch leer

    private void saveStayAngemeldetIntoDBOnPhone() {
        characterdataDb.updateStayAngemeldet(1);
    }

    //Ruft AsynchronousTask auf, um Daten aus der Datenbank zu bekommen

    private void checkInputOnServer() {
        String email = this.email.getText().toString();
        String passwort = this.passwort.getText().toString();
        new AnmeldenTask(this,this).execute(address, email, passwort);
    }

    // Opening the Database

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
    }



    //Methode des Interface um die gefundenen Email-Adressen + PW mit eingegeben Daten zuvergleichen
    // Bekommt usernr. usernr = 0 --> diskurs, da email oder passwort nicht korrekt
    //usernr != 0 --> dataCorrect()

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


    //Setzt den Diskurs Text im Falle eines falschen Passworts ( EMAIL-RIchtig!)


    private void passwortWrongDiskurs() {
        passwort.setText("");
        diskurs.setText(R.string.passwortWrong);
    }


    //Setzt den Diskurs Text im Falle einer falschen Email


    private void emailNotKnownDiskurs() {
        emptyEditTexts();
        diskurs.setText(R.string.emailNotRegistered);
    }

    // setzt edittext auf leer zurueck
    private void emptyEditTexts() {
        email.setText("");
        passwort.setText("");
    }

}
