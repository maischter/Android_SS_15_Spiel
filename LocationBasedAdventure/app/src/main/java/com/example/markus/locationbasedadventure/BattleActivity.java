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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.UpdateHitpointsbarTask;

/**
 * Created by qcd on 14.08.2015.
 */
public class BattleActivity extends Activity{

    ProgressBar playerHitpoints;
    final int payers_turn = 0;
    final int nonplayers_turn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        initProgressbars();



    }

    private void initProgressbars() {
        playerHitpoints = (ProgressBar) findViewById(R.id.progressBar);
        playerHitpoints.setMax(100);
        playerHitpoints.setProgress(80);

    }

    private void updateProgressbar() {

       // new UpdateHitpointsbarTask(this).execute(0);
    }


    public void loadBattleData(){
        //Laden aller relevanten Kampfdaten
    }

    public void randomizeEnemy(){
        // Gegner an die Stats des Spieler anpassen
    }




}
