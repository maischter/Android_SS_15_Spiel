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

/**
 * Created by qcd on 14.08.2015.
 */
public class BattleActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);



    }

    public void loadBattleData(){
        //Laden aller relevanten Kampfdaten
    }

    public void randomizeEnemy(){
        // Gegner an die Stats des Spieler anpassen
    }




}
