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
    ArmorDatabase armorDb;
    WeaponDatabase weaponDb;
    StatsDatabase statsDb;

    int [] weaponData;
    int [] armorData;

    final int players_turn = 0;
    final int nonplayers_turn = 1;
    int turn;
    Random rand = new Random();

    boolean def;
    boolean defSuspend;
    boolean powerUp;
    boolean powerUpSuspend;
    boolean special;
    boolean specialSuspend;
    int suspend;

    Entity Player;
    Entity NonPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        initDB();
        initProgressbars();
        initImageButtons();

        loadBattleData();
        setBattleStats();
        calcBattleStats();

        initSkills();

        turn = rand.nextInt(2);
        System.out.println("Zug:" + turn);

        if (turn==players_turn){
            playersTurn();
        } else if (turn==nonplayers_turn){
            nonplayersTurn();
            actionKI();
        }

        def = false;
        defSuspend = false;
        powerUp = false;
        powerUpSuspend = false;
        special = false;
        specialSuspend = false;
    }
    //init all GUI Elements
    private void initProgressbars() {
        playerHitpoints = (ProgressBar) findViewById(R.id.progressBar);
        playerHitpoints.setMax(100);
        playerHitpoints.setProgress(100);
        nonPlayerHitpoints = (ProgressBar) findViewById(R.id.progressBarNP);
        nonPlayerHitpoints.setMax(100);
        nonPlayerHitpoints.setProgress(100);
    }

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        armorDb = new ArmorDatabase(this);
        armorDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
    }

    private void initImageButtons(){
        skill_a = (ImageButton) findViewById(R.id.imageButton_skill1);
        skill_b = (ImageButton) findViewById(R.id.imageButton_skill2);
        skill_c = (ImageButton) findViewById(R.id.imageButton_skill3);
        skill_d = (ImageButton) findViewById(R.id.imageButton_skill4);
        loadSkillPNG(R.drawable.normal_attk, skill_a);
        loadSkillPNG(R.drawable.def, skill_b);
        loadSkillPNG(R.drawable.power_up, skill_c);
        loadSkillPNG(R.drawable.special_attk, skill_d);
        skill_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgressbar(pSkill[0],NonPlayer, Player);
                System.out.println("Skill A wurde aktiviert" + pSkill[0].skillName);
                nextTurn();
            }
        });

        skill_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def = true;
                updateProgressbar(pSkill[1],NonPlayer, Player);
                System.out.println(pSkill[1].skillName);
                nextTurn();

            }
        });
        skill_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                powerUp = true;
                updateProgressbar(pSkill[2],NonPlayer, Player);
                System.out.println(pSkill[2].skillName);
                nextTurn();
            }
        });
        skill_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProgressbar(pSkill[3],NonPlayer, Player);
                System.out.println(pSkill[3].skillName);
                nextTurn();
            }
        });
    }

    private void initSkills(){
        pSkill = new Skill[4];
        pSkill[0] = new Skill(R.string.skillA,Player.getWeaponDmg());
        pSkill[1] = new Skill(R.string.skillDef,0);
        pSkill[2] = new Skill(R.string.skillPower,0);
        pSkill[3] = new Skill(R.string.skillSpecA,Player.getWeaponDmg());
    }

    //Tasks zum Bilder laden
    public void loadSkillPNG(int resID , ImageButton imageButton) {
        LoadingBattleTask task = new LoadingBattleTask(imageButton);
        task.execute(resID);
    }

    @Override
    protected void onDestroy() {
        weaponDb.close();
        armorDb.close();
        statsDb.close();
        super.onDestroy();
    }
    // KI wählt per Random aus welchen Skill er einsetzt
    private void actionKI(){

        int action = 0;//rand.nextInt(4);

        switch (action){
            case 0:
                updateProgressbar(pSkill[0], Player, NonPlayer);
                nextTurn();
                break;
            case 1:
                def = true;
                updateProgressbar(pSkill[1],Player, NonPlayer);
                nextTurn();
                break;
            case 2:
                powerUp = true;
                updateProgressbar(pSkill[2],Player, NonPlayer);
                nextTurn();
                break;
            case 3:
                updateProgressbar(pSkill[3], Player, NonPlayer);
                nextTurn();
                break;
        }

    }

    // Setzt GUI Element clickbar/unclickbar
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

    private void nextTurn(){

        try {
            Thread.sleep(10000, 0);
        } catch (InterruptedException e){
            e.printStackTrace();
        }


        if (turn==players_turn){
            System.out.println("NonPlayer ist am Zug");
            turn=nonplayers_turn;
            nonplayersTurn();
            actionKI();
        }else if (turn==nonplayers_turn){
            System.out.println("Player ist am Zug");
            turn=players_turn;
            playersTurn();
        }


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
        System.out.println(battleData[5]);
        System.out.println(battleData[5]);
        System.out.println(battleData[5]);
        System.out.println(battleData[5]);
        armorData = armorDb.getArmor(); // KEY_ID,KEY_ARMOR,
        System.out.println(armorData[0]);
        // KEY_ARMORSTAMINA, KEY_ARMORSTRENGTH, KEY_ARMORDEXTERITY, KEY_ARMORINTELLIGENCE
        weaponData = weaponDb.getWeapon(); // KEY_ID,KEY_WEAPON,
        // KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
        // KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE
    }

    private void setBattleStats() {
        Player = new Entity(sp_sta,sp_str,sp_dex,sp_int,level);
        NonPlayer = new Entity(sp_str,sp_sta,sp_dex,sp_int,level);
        Player.setEntityEQ(weaponData, armorData);
        NonPlayer.setEntityEQ(weaponData, armorData);
    }

    private void calcBattleStats(){
        int [] randomValues = new int [4];
        for (int i=0; i <4; i++){
            randomValues[i]= rand.nextInt(10)-5;
        }
        Player.calcEqStats();
        NonPlayer.calcEqStats();
        NonPlayer.randomizeStats(randomValues);
        Player.calcDetailStats();
        NonPlayer.calcDetailStats();
    }


    private void updateProgressbar(Skill skill,Entity target, Entity source) {
        // random skill dmg hier
        int hit = rand.nextInt(99);
        int crit = rand.nextInt(99);
        int dodge = rand.nextInt(99);
        int nullDMG = 0;
        double powerUpDmg = 1.5;

        //kritischer treffer
        if (hit <= source.getHitrate() && crit <= source.getCritrate() && dodge > source.getDodge()){
            if (defSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * source.crit_dmg * nullDMG;
                defSuspend = false;
            }else if (powerUpSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * source.crit_dmg * powerUpDmg;
                powerUpSuspend=false;
            } else {
                target.curHitpoints = target.curHitpoints - skill.damage * source.crit_dmg;
                System.out.println("Kritischer Hit: " + skill.damage * source.crit_dmg );
            }
        } else

        //normal getroffen
        if (hit <= source.getHitrate() && crit > source.getCritrate() && dodge > source.getDodge()){
            if (defSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * nullDMG;
                defSuspend = false;
            } else if (powerUpSuspend){
                target.curHitpoints = target.curHitpoints - skill.damage * powerUpDmg ;
                powerUpSuspend=false;
            } else {
                target.curHitpoints = target.curHitpoints - skill.damage;
                System.out.println("Normaler Hit:" + skill.damage);
            }
        }

        //ausweichen
        if (dodge <= target.getDodge()){
            //Nothing to do here
            if (defSuspend){
                defSuspend = false;
            } else if (powerUpSuspend && suspend ==1){
                powerUpSuspend=false;
            }
            System.out.println("Ausgewichen");
        } else if (hit > source.getHitrate()){ //nicht getroffen
            //nothing to do here
            if (defSuspend){
                defSuspend = false;
            } else if (powerUpSuspend && suspend ==1){
                powerUpSuspend = false;
            }
            System.out.println("Nicht getroffen");
        }





        if (powerUpSuspend && suspend == 2){
            suspend = suspend - 1;
        } else if (specialSuspend){

        } else if (def){
            defSuspend = true;
            def = false;
        } else if (powerUp){
            //todo
            if (powerUpSuspend = false) {
                suspend = 2;
            } else
            powerUpSuspend = true;
            powerUp = false;

        } else if (special){
            //todo
            if (specialSuspend = false) {
                suspend = 2;
            } else
            specialSuspend = true;
            special = false;

        }

        if (turn==players_turn){
            nonPlayerHitpoints.setProgress((int)((target.curHitpoints/target.maxHitpoints) * 100));
            System.out.println("Ziel HP: " + target.curHitpoints);
        }
        if (turn==nonplayers_turn) {
            playerHitpoints.setProgress((int) ((target.curHitpoints / target.maxHitpoints) * 100));
            System.out.println("Ziel HP: " + target.curHitpoints);
        }

    }




}
