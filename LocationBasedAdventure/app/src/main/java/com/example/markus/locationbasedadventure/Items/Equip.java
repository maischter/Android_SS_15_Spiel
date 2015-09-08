package com.example.markus.locationbasedadventure.Items;

/**
 * Created by qcd on 31.08.2015.
 */
public class Equip {

    public int weaponTyp;
    public int weaponDmg;
    public int weaponHitrate;
    public int weaponCritrate;
    public int [] weaponStats;
    public int [] armorStats;// Reihenfolge sta,str,dex,int

    public Equip(int[] weaponstats, int[] armorstats){
        //loading weapon data, differenzieren zwischen magischen schaden (int), pfeil/gewehr schaden (dex), und restliche waffen (str)
        this.weaponTyp = weaponstats[1];
        this.weaponDmg = weaponstats[2];
        this.weaponHitrate = weaponstats[3];
        this.weaponCritrate = weaponstats[4];
        this.weaponStats[0] = weaponstats[5];
        this.weaponStats[1] = weaponstats[6];
        this.weaponStats[2] = weaponstats[7];
        this.weaponStats[3] = weaponstats[8];

        this.armorStats[0] = armorstats[0];
        this.armorStats[2] = armorstats[1];
        this.armorStats[3] = armorstats[2];
        this.armorStats[4] = armorstats[3];
    }

}



/*
        armorData = armorDb.getArmor(); // KEY_ID,KEY_ARMOR,
        // KEY_ARMORSTAMINA, KEY_ARMORSTRENGTH, KEY_ARMORDEXTERITY, KEY_ARMORINTELLIGENCE
        weaponData = weaponDb.getWeapon(); // KEY_ID,KEY_WEAPON,
        // KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
        // KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE
 */