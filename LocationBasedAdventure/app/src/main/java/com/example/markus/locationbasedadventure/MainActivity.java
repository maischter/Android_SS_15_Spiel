package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Server.MySqlDatabase;


//Serverport   url = "jdbc:mysql://87.106.18.104:3306/LocationBasedGame?user=Entwickler&password=Qu1Ma2Ch3";

public class MainActivity extends Activity{

    Button anmeldenPage;
    Button registrierenPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initButtons();
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

}
