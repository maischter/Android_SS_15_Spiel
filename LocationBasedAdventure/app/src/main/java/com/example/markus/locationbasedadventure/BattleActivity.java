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

    public Skill [] pSkill;

    int level;
    int exp;

    int sp_sta;
    int sp_str;
    int sp_dex;
    int sp_int;


    Random rand = new Random();
    int turn;
    boolean turnDone;
    boolean def;
    boolean defSuspend;
    boolean powerUp;
    boolean powerUpSuspend;
    boolean special;
    boolean specialSuspend;
    int suspend;

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
        def = false;
        defSuspend = false;
        powerUp = false;
        powerUpSuspend = false;
        special = false;
        specialSuspend = false;
        int FPS = 20;

        //Überprüft wer am Zug ist
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (turn==players_turn && turnDone){

                        playersTurn();
                        turnDone = false;

                } else if (turn==nonplayers_turn && turnDone){
                    nonplayersTurn();
                    actionKI();
                    turnDone=false;
                }
            }
        },0,1000/FPS);

        pSkill = new Skill[4];
        pSkill[0] = new Skill(R.string.skillA,Player.getWeaponDmg());
        pSkill[1] = new Skill(R.string.skillDef,0);
        pSkill[2] = new Skill(R.string.skillPower,0);
        pSkill[3] = new Skill(R.string.skillSpecA,Player.getWeaponDmg());

    }

    @Override
    protected void onDestroy() {
        weaponDb.close();
        armorDb.close();
        statsDb.close();
        super.onDestroy();
    }

    private void actionKI(){
        int action = rand.nextInt(4);

        switch (action){
            case 0:{
                updateProgressbar(pSkill[0],Player, NonPlayer);
                nextTurn();
            }
            case 1:{
                def = true;
                updateProgressbar(pSkill[1],Player, NonPlayer);
                nextTurn();
            }
            case 2:{
                powerUp = true;
                updateProgressbar(pSkill[2],Player, NonPlayer);
                nextTurn();
            }
            case 3: {
                updateProgressbar(pSkill[3], Player, NonPlayer);
                nextTurn();
            }
        }



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
                updateProgressbar(pSkill[0],NonPlayer, Player);
                nextTurn();
            }
        });

        skill_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def = true;
                updateProgressbar(pSkill[1],NonPlayer, Player);
                nextTurn();
            }
        });
        skill_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                powerUp = true;
                updateProgressbar(pSkill[2],NonPlayer, Player);
                nextTurn();
            }
        });
        skill_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateProgressbar(pSkill[3],NonPlayer, Player);
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
        NonPlayer.setEntityEQ(weaponData, armorData);

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



    private void updateProgressbar(Skill skill,Entity target, Entity source) {
        // random skill dmg hier
        int hit = rand.nextInt(99);
        int crit = rand.nextInt(99);
        int dodge = rand.nextInt(99);
        int nextDmg = 0;
        double powerUpDmg = 1.5;

        //ausweichen
        if (dodge <= target.getDodge()){
            //Nothing to do here
            if (defSuspend){
                defSuspend = false;
            }
            if (powerUpSuspend && suspend ==1){
                powerUpSuspend=false;
            }
        }
        //nicht getroffen
        if (hit > source.getHitrate()){
            //nothing to do here
            if (defSuspend){
                defSuspend = false;
            }
            if (powerUpSuspend && suspend ==1){
                powerUpSuspend = false;
            }
        }

        //normal getroffen
        if (hit <= source.getHitrate() && crit > source.getCritrate() && dodge > source.getDodge()){
            if (defSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * 0;
                defSuspend = false;
            } else if (powerUpSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * powerUpDmg ;
                powerUpSuspend=false;
            } else target.curHitpoints = target.curHitpoints - skill.damage;
        }

        //kritischer treffer
        if (hit <= source.getHitrate() && crit <= source.getCritrate() && dodge > source.getDodge()){
            if (defSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * source.crit_dmg * 0;
                defSuspend = false;
            }else if (powerUpSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * source.crit_dmg * powerUpDmg;
                powerUpSuspend=false;
            } else target.curHitpoints = target.curHitpoints - skill.damage * source.crit_dmg;
        }

        if (powerUpSuspend && suspend == 2){
            suspend = suspend - 1;
        }

        if (specialSuspend){

        }

        if (def){
            defSuspend = true;
            def = false;
        }
        if (powerUp){
            //todo
            if (powerUpSuspend = false) {
                suspend = 2;
            }
            powerUpSuspend = true;
            powerUp = false;
        }
        if (special){
            //todo
            if (specialSuspend = false) {
                suspend = 2;
            }
            specialSuspend = true;
            special = false;
        }

        if (turn==players_turn){
            nonPlayerHitpoints.setProgress((int)(target.curHitpoints/target.maxHitpoints));
        }
        if (turn==nonplayers_turn){
            playerHitpoints.setProgress((int)(target.curHitpoints/target.maxHitpoints));
        }

    }

    public void loadSkillPNG(int resID , ImageButton imageButton) {
        LoadingBattleTask task = new LoadingBattleTask(imageButton);
        task.execute(resID);
    }


}
