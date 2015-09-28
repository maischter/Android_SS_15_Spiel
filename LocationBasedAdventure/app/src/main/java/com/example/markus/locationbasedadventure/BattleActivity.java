package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.AsynchronTasks.LoadingBattleTask;
import com.example.markus.locationbasedadventure.Database.AchievementDatabase;
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.ItemDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;
import com.example.markus.locationbasedadventure.Items.Entity;
import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.Items.Skill;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by qcd on 14.08.2015.
 */
public class BattleActivity extends Activity{

    private ProgressBar playerHitpoints;
    private ProgressBar nonPlayerHitpoints;
    private ImageButton skill_a;
    private ImageButton skill_b;
    private ImageButton skill_c;
    private ImageButton skill_d;
    public Skill [] pSkill;

    private int level;
    private int exp;
    private int sp_sta;
    private int sp_str;
    private int sp_dex;
    private int sp_int;
    private ArmorDatabase armorDb;
    private WeaponDatabase weaponDb;
    private StatsDatabase statsDb;
    private AchievementDatabase achievementDb;
    private CharacterdataDatabase characterdataDb;
    private ItemDatabase itemDb;

    private int [] weaponData;
    private int [] armorData;

    private final int players_turn = 0;
    private final int nonplayers_turn = 1;
    private int turn;
    private Random rand = new Random();

    private boolean def;
    private boolean defSuspend;
    private boolean powerUp;
    private boolean powerUpSuspend;
    private boolean special;
    private boolean specialSuspend;
    private int suspend;

    private Entity Player;
    private Entity NonPlayer;

    Handler handler = new Handler();

    private boolean gameOver = false;
    private boolean levelUp = false;
    private boolean firstRound = true;
    boolean getNewWeapon = false;

    private int receiveExperience = 0;
    private double lastDmg;




    private TextView playerLevel, enemyLevel;
    private ImageView playerImage, enemyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        initDB();
        initProgressbars();
        initImageButtons();
        initLevels();
        initImages();

        loadBattleData();
        setBattleStats();
        calcBattleStats();

        initSkills();

        //turn = rand.nextInt(2);
        System.out.println("Zug:" + turn);

        if (turn==players_turn){
            showTurnMsg();
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

    //initialises the Image of player and enemy

    private void initImages() {
        playerImage = (ImageView) findViewById(R.id.player_image);
        enemyImage = (ImageView) findViewById(R.id.enemy_image);
        selectPlayerImage();
        selectEnemyImage();
    }

    //selects Player Image by weaponTyp from Database
    //uses background task to load the Image

    private void selectPlayerImage(){
        switch(weaponDb.getWeaponTyp()){
            //Einhandschwert
            case 1:loadBitmap(R.drawable.einhandschwert,playerImage);break;
            //Einhandaxt
            case 2:loadBitmap(R.drawable.einhandaxt,playerImage);break;
            //Einhandschwert mit Schild
            case 3:loadBitmap(R.drawable.einhandschwert,playerImage);break;
            //Einhandaxt mit Schild
            case 4:loadBitmap(R.drawable.einhandschwert,playerImage);break;
            //Zweihandschwert
            case 5:loadBitmap(R.drawable.zweihandschwert,playerImage);break;
            //Zweihandaxt
            case 6:loadBitmap(R.drawable.zweihandaxt,playerImage);break;
            //Zauberstab
            case 7:loadBitmap(R.drawable.einhandschwert,playerImage);break;
            //Bogen
            case 8:loadBitmap(R.drawable.einhandschwert,playerImage);break;
            //Armbrust
            case 9:loadBitmap(R.drawable.einhandschwert,playerImage);break;
        }
    }

    //selects the image of the enemy

    public void selectEnemyImage(){
        loadBitmap(R.drawable.special_attk,enemyImage);
    }

    //loads Bitmap into ImageView by Using backgroundTask

    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }




    private void initLevels() {
        playerLevel = (TextView) findViewById(R.id.player_level);
        enemyLevel = (TextView) findViewById(R.id.enemy_level);

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


    //init and open Databases

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        armorDb = new ArmorDatabase(this);
        armorDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
        achievementDb = new AchievementDatabase(this);
        achievementDb.open();
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        itemDb = new ItemDatabase(this);
        itemDb.open();
    }

    //initImageButtons
    //load Images into Buttons
    //button Listeners

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
                execNextTurn();
            }
        });

        skill_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                def = true;
                updateProgressbar(pSkill[1], NonPlayer, Player);
                System.out.println(pSkill[1].skillName);
                execNextTurn();


            }
        });
        skill_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                powerUp = true;
                updateProgressbar(pSkill[2],NonPlayer, Player);
                System.out.println(pSkill[2].skillName);
                execNextTurn();
            }
        });
        skill_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgressbar(pSkill[3], NonPlayer, Player);
                System.out.println(pSkill[3].skillName);
                execNextTurn();
            }
        });
    }



    private void execNextTurn() {
        handler.postDelayed(new Runnable() {
            public void run() {
                if(!gameOver) {
                    nextTurn();
                }
            }
        }, 1000);
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
        achievementDb.close();
        characterdataDb.close();
        itemDb.close();
        super.onDestroy();
    }



    // KI wählt per Random aus welchen Skill er einsetzt
    private void actionKI(){

        int action = rand.nextInt(4);

        switch (action){
            case 0:
                enemyAttack(pSkill[action], Player, NonPlayer);
                break;
            case 1:
                def = true;
                enemyAttack(pSkill[action], Player, NonPlayer);
                break;
            case 2:
                powerUp = true;
                enemyAttack(pSkill[action], Player, NonPlayer);
                break;
            case 3:
                enemyAttack(pSkill[action], Player, NonPlayer);
                break;
        }

    }

    private void enemyAttack(Skill skill, Entity player, Entity nonPlayer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Der Gegner hat " + Math.floor(lastDmg) + " erhalten und führt nun einen Gegenangriff aus!")
                .setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        handler.postDelayed(new Runnable() {
            public void run() {
                updateProgressbar(pSkill[0], Player, NonPlayer);
                nextTurn();
                alert.dismiss();
            }
        }, 2000);
    }

    // Setzt GUI Element clickbar/unclickbar
    private void playersTurn(){
        skill_a.setClickable(true);
        skill_b.setClickable(true);
        skill_c.setClickable(true);
        if (level >= 5) {
            skill_d.setClickable(true);
            TextView specialAttack = (TextView) findViewById(R.id.textViewSpecialAttack);
            specialAttack.setVisibility(View.VISIBLE);
        } else {
            skill_d.setClickable(false);
            skill_d.setVisibility(View.INVISIBLE);
            TextView specialAttack = (TextView) findViewById(R.id.textViewSpecialAttack);
            specialAttack.setVisibility(View.INVISIBLE);
        }
    }

    //sets Buttons not Clickable because enemy is on turn

    private void nonplayersTurn(){
        skill_a.setClickable(false);
        skill_b.setClickable(false);
        skill_c.setClickable(false);
        skill_d.setClickable(false);
    }

    //checks who is on turn next

    private void nextTurn(){
        if (turn==players_turn){
            System.out.println("NonPlayer ist am Zug");
            turn=nonplayers_turn;
            nonplayersTurn();
            actionKI();
        }else if (turn==nonplayers_turn){
            System.out.println("Player ist am Zug");
            turn=players_turn;
            showTurnMsg();
            playersTurn();
        }


    }


    //shows the Messages after a turn
    //uses Alerts

    private void showTurnMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(firstRound) {
            builder.setMessage("WAHH! Ein Monster! Greif es an!")
                    .setCancelable(false);
            firstRound = false;

            playerLevel.setText("Level " + Player.lvl);
            enemyLevel.setText("Level " + NonPlayer.lvl);
        } else {
            builder.setMessage("Du hast " + Math.floor(lastDmg) + " Schaden erhalten... Nun bist du wieder am Zug!")
                    .setCancelable(false);
        }
        final AlertDialog alert = builder.create();
        if(!gameOver) {
            alert.show();
        }
        handler.postDelayed(new Runnable() {
            public void run() {
                alert.dismiss();
            }
        }, 3000);
    }


    // loads the battle Data

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

    //sets the Battle Stats

    private void setBattleStats() {
        Player = new Entity(sp_sta,sp_str,sp_dex,sp_int,level);
        NonPlayer = new Entity(sp_str,sp_sta,sp_dex,sp_int,level);
        Player.setEntityEQ(weaponData, armorData);
        NonPlayer.setEntityEQ(weaponData, armorData);

    }

    //calculates the Battle Stats

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



    //updates the Progress bar
    //calculates Hit oder no Hit
    //handles end of the Game(== hp smaller 0)

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
                lastDmg = skill.getDamage() * source.crit_dmg * nullDMG;
                target.curHitpoints = target.curHitpoints - skill.getDamage() * source.crit_dmg * nullDMG;
                defSuspend = false;
            }else if (powerUpSuspend && suspend == 1){
                lastDmg = skill.getDamage() * source.crit_dmg * powerUpDmg;
                target.curHitpoints = target.curHitpoints - skill.getDamage() * source.crit_dmg * powerUpDmg;
                powerUpSuspend=false;
            } else {
                lastDmg = skill.getDamage() * source.crit_dmg;
                target.curHitpoints = target.curHitpoints - skill.getDamage() * source.crit_dmg;
                System.out.println("Kritischer Hit: " + skill.getDamage() * source.crit_dmg );
            }
        } else

        //normal getroffen
        if (hit <= source.getHitrate() && crit > source.getCritrate() && dodge > source.getDodge()){
            if (defSuspend){
                lastDmg = skill.getDamage()  * nullDMG;
                target.curHitpoints = target.curHitpoints - skill.getDamage() * nullDMG;
                defSuspend = false;
            } else if (powerUpSuspend && suspend == 1){
                lastDmg = skill.getDamage() * powerUpDmg;
                target.curHitpoints = target.curHitpoints - skill.getDamage() * powerUpDmg ;
                powerUpSuspend=false;
            } else {
                lastDmg = skill.getDamage();
                target.curHitpoints = target.curHitpoints - skill.getDamage();
                System.out.println("Normaler Hit:" + skill.getDamage());
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
            lastDmg = 0;
            System.out.println("Ausgewichen");
        } else if (hit > source.getHitrate()){ //nicht getroffen
            //nothing to do here
            if (defSuspend){
                defSuspend = false;
            } else if (powerUpSuspend && suspend ==1){
                powerUpSuspend = false;
            }
            lastDmg = 0;
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
            nonPlayerHitpoints.setProgress((int) ((target.curHitpoints / target.maxHitpoints) * 100));
            System.out.println("Ziel HP: " + target.curHitpoints);
            if(target.curHitpoints <= 0) {
                updateExperience();
                gameOver = true;
                checkLevelUp();
                countFights();
                if(levelUp){
                    if(getNewWeapon){
                        playerWinsLevelUpAndNewWeapon();
                        getNewWeapon=false;
                    }else{
                        playerWinsLevelUp();
                    }

                }else{
                    playerWins();
                }


            }

        }
        if (turn==nonplayers_turn) {
            playerHitpoints.setProgress((int) ((target.curHitpoints / target.maxHitpoints) * 100));
            System.out.println("Eigene HP: " + target.curHitpoints);
            if(target.curHitpoints <= 0) {
                System.out.println(""+Player.lvl+" vs."+ NonPlayer.lvl);
                countFights();
                gameOver = true;
                enemyWins();

            }
        }
    }


    //counts the number of fights
    //changes it in Database

    private void countFights() {
        characterdataDb.updateFights(characterdataDb.getFights() + 1);
        switch(characterdataDb.getFights()){
            case 5: achievementDb.insertNewAchievement(4);itemDb.insertNewItem(1,1);break;
            case 50:achievementDb.insertNewAchievement(5);itemDb.insertNewItem(2, 1);break;
            case 250:achievementDb.insertNewAchievement(6);itemDb.insertNewItem(3, 1);break;
        }
    }



    //checks if a new Level is reached and updates Stats DB
    //handles receiving a new Weapon in some LevelUps
    //handles recieving achievments by levelUps

    private void checkLevelUp() {

        levelUp = true;
        if(statsDb.getLevel() == 1 &&statsDb.getExp()>=100 && statsDb.getExp() <= 175){
            //Level 1 zu 2
            statsDb.updateAllExceptExp(2, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);

        }else{
            if(statsDb.getLevel() == 2 &&statsDb.getExp()>=175 && statsDb.getExp() <= 306){
                //Level 2 zu 3
                getNewWeapon();
                statsDb.updateAllExceptExp(3, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
            }else{
                if(statsDb.getLevel() == 3 && statsDb.getExp()>=306 && statsDb.getExp() <= 536){
                    //Level 3 zu 4
                    statsDb.updateAllExceptExp(4, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);
                    addAchievementLevel(4);
                }else{
                    if(statsDb.getExp()>=536 && statsDb.getExp() <= 938){
                        //Level 4 zu 5
                        getNewWeapon();
                        statsDb.updateAllExceptExp(5, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                    }else{
                        if(statsDb.getExp()>=938 && statsDb.getExp() <= 1548){
                            //Level 5 zu 6
                            statsDb.updateAllExceptExp(6, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                        }else{
                            if(statsDb.getExp()>=1548 && statsDb.getExp() <= 2553){
                                //Level 6 zu 7
                                getNewWeapon();
                                statsDb.updateAllExceptExp(7, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                            }else{
                                if(statsDb.getExp()>=2553 && statsDb.getExp() <= 4213){
                                    //Level 7 zu 8
                                    statsDb.updateAllExceptExp(8, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                                }else{
                                    if(statsDb.getExp()>=4213 && statsDb.getExp() <= 6912){
                                        //Level 8 zu 9
                                        getNewWeapon();
                                        statsDb.updateAllExceptExp(9, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);
                                        addAchievementLevel(8);
                                    }else{
                                        if(statsDb.getExp()>=6912 && statsDb.getExp() <= 11470){
                                            //Level 9 zu 10
                                            statsDb.updateAllExceptExp(10, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                                        }else{
                                            if(statsDb.getExp()>=11470 && statsDb.getExp() <= 17205){
                                                //Level 10 zu 11
                                                statsDb.updateAllExceptExp(11, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                                            }else{
                                                if(statsDb.getExp()>=17205 && statsDb.getExp() <= 25808){
                                                    //Level 11 zu 12
                                                    getNewWeapon();
                                                    statsDb.updateAllExceptExp(12, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);
                                                    addAchievementLevel(12);
                                                }else{
                                                    levelUp = false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //inserts an Achievment by Level up

    private void addAchievementLevel(int level) {
        switch(level){
            case 4:achievementDb.insertNewAchievement(1);itemDb.insertNewItem(1, 1);break;
            case 8:achievementDb.insertNewAchievement(2);itemDb.insertNewItem(2, 1);break;
            case 12:achievementDb.insertNewAchievement(3);itemDb.insertNewItem(3,1);break;
        }
    }

    //handles random receiving of a new Weapon

    private void getNewWeapon() {
        getNewWeapon = false;
        Random random = new Random();
        int newWeapon;
        do {
            newWeapon = random.nextInt(9)+1;
            ArrayList<Equip> weapons = weaponDb.getAllWeaponItemsFromStart();
            for(int i = 0;i<weapons.size();i++){
                if(weapons.get(i).getWeaponTyp()==newWeapon){
                    getNewWeapon =false;
                }else {
                    getNewWeapon = true;
                }
            }
        }while(!getNewWeapon);

        addNewWeapon(newWeapon);


    }


    //dds the new Weapon to the Database

    private void addNewWeapon(int newWeapon) {
        switch(newWeapon){

            case 1:weaponDb.insertNewWeapon(newWeapon,2,70,25,0,0,0,0,0);break;
            case 2:weaponDb.insertNewWeapon(newWeapon,4,55,25,0,0,0,0,0);break;
            case 3:weaponDb.insertNewWeapon(newWeapon,2,85,25,10,1,1,1,1);break;
            case 4:weaponDb.insertNewWeapon(newWeapon,2,85,25,10,1,1,1,1);break;
            case 5:weaponDb.insertNewWeapon(newWeapon,4,70,10,0,0,0,0,0);break;
            case 6:weaponDb.insertNewWeapon(newWeapon,6,55,10,0,0,0,0,0);break;
            case 7:weaponDb.insertNewWeapon(newWeapon,4,100,10,0,0,0,0,0);break;
            case 8:weaponDb.insertNewWeapon(newWeapon,1,85,60,0,0,0,0,0);break;
            case 9:weaponDb.insertNewWeapon(newWeapon,2,70,60,0,0,0,0,0);break;
        }
    }

    //updates the Experience
    //diffs between level of the enemy

    private void updateExperience() {
        System.out.println(""+Player.lvl+" vs."+ NonPlayer.lvl);
        if(Player.lvl - NonPlayer.lvl == 0){

        //Spieler gleiches Level +20Exp
            statsDb.updateExp(20 + statsDb.getExp());
            receiveExperience = 20;
        }else{
            if(Player.lvl - NonPlayer.lvl < 0){
            //Spieler kleineres Level +40Exp
                statsDb.updateExp(40 + statsDb.getExp());
                receiveExperience = 40;
            }else{
                //Spieler größes Level +10Exp
                statsDb.updateExp(10 + statsDb.getExp());
                receiveExperience = 10;
            }
        }

    }


    //handles end of the fight if enemy wins
    //shows alert and switches to mapActivity

    private void enemyWins() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Du hast den Kampf verloren!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(BattleActivity.this, MapsActivity.class));
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }


    //handles end of the fight if player wins and has a levelUp
    //shows alert and switches to mapActivity

    private void playerWinsLevelUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Du hast gewonnen und "+ receiveExperience +" Erfahrungspunkte verdient! Glückwunsch, du hast Level "+ statsDb.getLevel()+" erreicht. +3 für alle Stats!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(BattleActivity.this, MapsActivity.class));
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }
    //handles end of the fight if player wins and has a level up and recieives a new Weapon
    //shows alert and switches to mapActivity
    private void playerWinsLevelUpAndNewWeapon() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Du hast gewonnen und "+ receiveExperience +" Erfahrungspunkte verdient! Glückwunsch, du hast Level "+ statsDb.getLevel()+" erreicht. +3 für alle Stats!\n\nDu hast eine neue Waffe gefunden! Sie wurde deinem Inventar hinzugefügt.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(BattleActivity.this, MapsActivity.class));
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }

    //handles end of the fight if player wins
    //shows alert and switches to mapActivity
    private void playerWins() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Du hast gewonnen und "+ receiveExperience +" Erfahrungspunkte verdient!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(BattleActivity.this, MapsActivity.class));
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }
}
