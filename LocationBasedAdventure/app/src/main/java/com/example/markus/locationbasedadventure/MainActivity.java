package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.markus.locationbasedadventure.Database.MySqlDatabase;


public class MainActivity extends Activity{

    Button anmeldenPage;
    Button registrierenPage;
    MySqlDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB();
        db.updateStayAngemeldet(0);
        checkAnmeldung();
        initButtons();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }


    //Überprüft ob die Datenbank leer ist ( = Erster Aufruf der APP) und füllt  in diesem Fall eine leere Standartzeile.
    //Überprüft dann ob die Stay angemeldet in der ersten ( =einzige) Zeile 1 ist, falls ja, wechseln in GIF.


    private void checkAnmeldung() {
        if(db.isEmpty()){
            db.insertAllmainActivity();
        }
        if(db.getStayAngemeldet() == 1){
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
        db = new MySqlDatabase(this);
        db.open();
    }

}
