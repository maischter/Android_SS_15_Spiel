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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.AsynchronTasks.LoadingBattleTask;
import com.example.markus.locationbasedadventure.AsynchronTasks.UpdateHitpointsbarTask;
import com.example.markus.locationbasedadventure.Items.Entity;
import com.example.markus.locationbasedadventure.Items.Skill;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qcd on 14.08.2015.
 */
public class BattleActivity extends Activity{

    ProgressBar playerHitpoints;
    ProgressBar nonPlayerHitpoints;
    final int players_turn = 0;
    final int nonplayers_turn = 1;
    ImageButton skill_a;
    ImageButton skill_b;
    ImageButton skill_c;
    ImageButton skill_d;
    int level;
    int exp;
    int sp_int;
    int sp_str;
    int sp_dex;
    int sp_sta;
    Entity Player;
    Entity NonPlayer;
    Random rand = new Random();
    int turn;
    boolean turnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        initProgressbars();
        initImageButtons();
        loadBattleData();
        calcBattleStats();

        turn = rand.nextInt(2);
        turnDone = true;
        int FPS = 40;

        //Überprüft wer am Zug ist
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (turn==players_turn && turnDone){
                    playersTurn();
                    turnDone=false;
                } else if (turn==nonplayers_turn && turnDone){
                    nonplayersTurn();
                    turnDone=false;

                }

            }
        },0,1000/FPS);



    }



    private void playersTurn(){
        skill_a.setClickable(true);
        skill_b.setClickable(true);
        skill_c.setClickable(true);
        if (level >= 5) {
            skill_d.setClickable(true);
        } else skill_d.setClickable(false);
    }

    private void nonplayersTurn(){
        skill_a.setClickable(false);
        skill_b.setClickable(false);
        skill_c.setClickable(false);
        skill_d.setClickable(false);
    }

    private void initImageButtons(){
        skill_a = (ImageButton) findViewById(R.id.imageButton_skill1);
        skill_b = (ImageButton) findViewById(R.id.imageButton_skill2);
        skill_c = (ImageButton) findViewById(R.id.imageButton_skill3);
        skill_d = (ImageButton) findViewById(R.id.imageButton_skill4);
        skill_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProgressbar();
                nextTurn();
            }
        });

        skill_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg = Player.Skill1.damage;

                updateProgressbar();
                nextTurn();
            }
        });
        skill_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProgressbar();
                nextTurn();
            }
        });
        skill_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateProgressbar();
                nextTurn();
            }
        });
    }

    private void initProgressbars() {
        playerHitpoints = (ProgressBar) findViewById(R.id.progressBar);
        playerHitpoints.setMax(100);
        nonPlayerHitpoints = (ProgressBar) findViewById(R.id.progressBarNP);
        nonPlayerHitpoints.setMax(100);

    }

    public void loadBattleData(){
        //Laden aller relevanten Kampfdaten
        level = 1;
        exp = 0;
        sp_dex = 15;
        sp_int = 15;
        sp_sta = 15;
        sp_str = 15;
        loadingSkills();
    }

    private void loadingSkills(){
        loadSkillPNG(R.drawable.normal_attk, skill_a);
        loadSkillPNG(R.drawable.def, skill_b);
        loadSkillPNG(R.drawable.power_up, skill_c);
        loadSkillPNG(R.drawable.special_attk, skill_d);

    }

    private void calcBattleStats() {
        Player = new Entity(sp_str,sp_sta,sp_dex,sp_int);

        NonPlayer = new Entity(sp_str,sp_sta,sp_dex,sp_int);

    }

    private void nextTurn(){
        if (turn==players_turn){
            turn=nonplayers_turn;
        }
        if (turn==nonplayers_turn){
            turn=players_turn;
        }
        turnDone=true;
    }

    private void updateProgressbar() {

    }



    public void loadSkillPNG(int resID , ImageButton imageButton) {
        LoadingBattleTask task = new LoadingBattleTask(imageButton);
        task.execute(resID);
    }




}
