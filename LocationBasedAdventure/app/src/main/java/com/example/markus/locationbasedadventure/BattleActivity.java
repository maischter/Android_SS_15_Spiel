package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.markus.locationbasedadventure.AsynchronTasks.LoadingBattleTask;
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;
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

    int sp_sta;
    int sp_str;
    int sp_dex;
    int sp_int;


    Random rand = new Random();
    int turn;
    boolean turnDone;

    ArmorDatabase armorDb;
    WeaponDatabase weaponDb;
    StatsDatabase statsDb;

    Entity Player;
    Entity NonPlayer;

    int [] weaponData;
    int [] armorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        initDB();

        initProgressbars();
        initImageButtons();
        loadBattleData();
        calcBattleStats();

        turn = rand.nextInt(2);
        turnDone = true;
        int FPS = 20;

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

    @Override
    protected void onDestroy() {
        weaponDb.close();
        armorDb.close();
        statsDb.close();
        super.onDestroy();
    }

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        armorDb = new ArmorDatabase(this);
        armorDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
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

                updateProgressbar(Player.Skill1,NonPlayer);
                nextTurn();
            }
        });

        skill_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProgressbar(Player.Skill2,NonPlayer);
                nextTurn();
            }
        });
        skill_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProgressbar(Player.Skill3,NonPlayer);
                nextTurn();
            }
        });
        skill_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateProgressbar(Player.Skill4,NonPlayer);
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
        int [] battleData = statsDb.getStats(); // KEY_ID,KEY_LEVEL,
        // KEY_EXP,KEY_STAMINA, KEY_STRENGTH, KEY_DEXTERITY, KEY_INTELLIGENCE
        level = battleData[0];
        exp = battleData[1];
        sp_sta = battleData[2];
        sp_str = battleData[3];
        sp_dex = battleData[4];
        sp_int = battleData[5];
        armorData = armorDb.getArmor(); // KEY_ID,KEY_ARMOR,
        // KEY_ARMORSTAMINA, KEY_ARMORSTRENGTH, KEY_ARMORDEXTERITY, KEY_ARMORINTELLIGENCE
        weaponData = weaponDb.getWeapon(); // KEY_ID,KEY_WEAPON,
        // KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
        // KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE

        loadingSkills();
    }

    private void loadingSkills(){
        loadSkillPNG(R.drawable.normal_attk, skill_a);
        loadSkillPNG(R.drawable.def, skill_b);
        loadSkillPNG(R.drawable.power_up, skill_c);
        loadSkillPNG(R.drawable.special_attk, skill_d);

    }

    private void calcBattleStats() {
        Player = new Entity(sp_sta,sp_str,sp_dex,sp_int,level,false);
        NonPlayer = new Entity(sp_str,sp_sta,sp_dex,sp_int,level,true);
        Player.setEntityEQ(weaponData, armorData);

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

    private void updateProgressbar(Skill skill,Entity target) {

    }

    public void loadSkillPNG(int resID , ImageButton imageButton) {
        LoadingBattleTask task = new LoadingBattleTask(imageButton);
        task.execute(resID);
    }


}
