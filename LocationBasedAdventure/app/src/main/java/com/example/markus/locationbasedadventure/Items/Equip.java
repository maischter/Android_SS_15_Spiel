package com.example.markus.locationbasedadventure.Items;

/**
 * Created by qcd on 31.08.2015.
 */
public class Equip {

    public int weaponTyp;
    public int weaponDmg;
    public int weaponHitrate;
    public int weaponCritrate;
    public int weaponExtra;
    public int armorTyp;
    public int [] weaponStats = new int[4];
    public int [] armorStats = new int[4];// Reihenfolge sta,str,dex,int

    public Equip(int[] weaponstats, int[] armorstats){
        //loading weapon data, differenzieren zwischen magischen schaden (int), pfeil/gewehr schaden (dex), und restliche waffen (str)
        this.weaponTyp = weaponstats[0];
        this.weaponDmg = weaponstats[1];
        this.weaponHitrate = weaponstats[2];
        this.weaponCritrate = weaponstats[3];
        this.weaponExtra = weaponstats[4];
        this.weaponStats[0] = weaponstats[5];
        this.weaponStats[1] = weaponstats[6];
        this.weaponStats[2] = weaponstats[7];
        this.weaponStats[3] = weaponstats[8];


        this.armorTyp = armorstats[0];
        this.armorStats[0] = armorstats[1];
        this.armorStats[1] = armorstats[2];
        this.armorStats[2] = armorstats[3];
        this.armorStats[3] = armorstats[4];
    }

    public Equip(int[] stats, boolean kind){
        if(kind){
            this.weaponTyp = stats[0];
            this.weaponDmg = stats[1];
            this.weaponHitrate = stats[2];
            this.weaponCritrate = stats[3];
            this.weaponExtra = stats[4];
            this.weaponStats[0] = stats[5];
            this.weaponStats[1] = stats[6];
            this.weaponStats[2] = stats[7];
            this.weaponStats[3] = stats[8];
        }else{
            this.armorTyp = stats[0];
            this.armorStats[0] = stats[1];
            this.armorStats[1] = stats[2];
            this.armorStats[2] = stats[3];
            this.armorStats[3] = stats[4];
        }


    }

    public int getWeaponCritrate() {
        return weaponCritrate;
    }

    public int getWeaponDmg() {
        return weaponDmg;
    }

    public int getWeaponTyp() {
        return weaponTyp;
    }

    public int getWeaponHitrate() {
        return weaponHitrate;
    }

    public int getWeaponExtra() {
        return weaponExtra;
    }

    public int[] getWeaponStats() {
        return weaponStats;
    }

    public int[] getArmorStats() {
        return armorStats;
    }

}



/*
        armorData = armorDb.getArmor(); // KEY_ID,KEY_ARMOR,
        // KEY_ARMORSTAMINA, KEY_ARMORSTRENGTH, KEY_ARMORDEXTERITY, KEY_ARMORINTELLIGENCE
        weaponData = weaponDb.getWeapon(); // KEY_ID,KEY_WEAPON,
        // KEY_WEAPONDAMAGE, KEY_WEAPONHITCHANCE, KEY_WEAPONKRITCHANCE, KEY_WEAPONEXTRA,
        // KEY_WEAPONSTAMINA, KEY_WEAPONSTRENGTH, KEY_WEAPONDEXTERITY, KEY_WEAPONINTELLIGENCE
 */