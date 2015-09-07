package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.markus.locationbasedadventure.AsynchronTasks.syndciateStatsLocalToServerTask;
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;


public class MainActivity extends Activity{

    Button anmeldenPage;
    Button registrierenPage;
    CharacterdataDatabase characterdataDb;
    StatsDatabase statsDb;
    WeaponDatabase weaponDb;
    ArmorDatabase armorDb;

    private String address2 = "http://sruball.de/game/syndicateData.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB();
        characterdataDb.updateStayAngemeldet(0);
        checkAnmeldung();
        initButtons();
    }

    @Override
    protected void onDestroy() {
        characterdataDb.close();
        statsDb.close();
        armorDb.close();
        weaponDb.close();
        super.onDestroy();
    }


    //Überprüft ob die Datenbank leer ist ( = Erster Aufruf der APP) und füllt  in diesem Fall eine leere Standartzeile.
    //Überprüft dann ob die Stay angemeldet in der ersten ( =einzige) Zeile 1 ist, falls ja, wechseln in GIF.


    private void checkAnmeldung() {
        if(characterdataDb.isEmpty()) {
            characterdataDb.insertAllmainActivity();
        }
        if(statsDb.isEmpty()) {
            statsDb.insertAllmainActivity();
        }
        if(weaponDb.isEmpty()) {
            weaponDb.insertAllmainActivity();
        }
        if(armorDb.isEmpty()) {
            armorDb.insertAllmainActivity();
        }
        if(characterdataDb.getStayAngemeldet() == 1){
            new syndciateStatsLocalToServerTask(this).execute(address2,characterdataDb.getEmail(),""+statsDb.getLevel(),""+statsDb.getExp(),""+statsDb.getStamina(),""+statsDb.getStrength(),""+statsDb.getDexterity(),""+statsDb.getIntelligence());
            Intent i = new Intent(getApplicationContext(),GifActivity.class); // Hier eigentlich MapActivity
            startActivity(i);
        }
    }


    private void initButtons(){
        anmeldenPage = (Button) findViewById(R.id.button);
        registrierenPage = (Button) findViewById(R.id.button2);
        anmeldenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(),BattleActivity.class);
                Intent i = new Intent(getApplicationContext(),AnmeldenActivity.class);
                startActivity(i);
            }
        });
        registrierenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegistrierenActivity.class);
                startActivity(i);
            }
        });
    }

    // Opening the Database

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        armorDb = new ArmorDatabase(this);
        armorDb.open();
    }

}
