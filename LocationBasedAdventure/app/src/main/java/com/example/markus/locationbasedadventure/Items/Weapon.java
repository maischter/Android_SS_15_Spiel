package com.example.markus.locationbasedadventure.Items;

/**
 * Created by qcd on 31.08.2015.
 */
public class Weapon {

    public int weapontyp;
    public int weaponDmg;
    public int weaponHitrate;
    public int weaponCritrate;
    public int [] armorstats;// Reihenfolge str,sta,dex,int

    public Weapon(int wTyp, int wDmg, int wHit, int wCrit, int [] armorstats){
        //loading weapon data, differenzieren zwischen magischen schaden (int), pfeil/gewehr schaden (dex), und restliche waffen (str)

    }

}
