package com.example.markus.locationbasedadventure.Items;

import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.R;

import java.util.Random;

/**
 * Created by qcd on 28.08.2015.
 */
public class Entity {

    public double physical_dmg;
    public double magical_dmg;
    public double magical_wand_dmg;
    public double maxHitpoints;
    public double curHitpoints;
    public double physical_res;
    public double magical_res;
    public double crit_chance;
    public double crit_dmg;
    public double dodge;
    public double physical_bow_dmg;
    public double hitrate;

    CharacterdataDatabase db;


    Random rand;
    public int sta;
    public int str;
    public int dex;
    public int intell;
    public int lvl;
    public Equip entityEQ;

    public Entity (int stamina, int strength, int dexterity, int intelligence,int level, boolean npc){

        //initStats
        this.lvl = level;
        this.sta = stamina;
        this.str = strength;
        this.dex = dexterity;
        this.intell = intelligence;


        if (npc){
            randomizeStats();
        }

        calcDetailStats();


    }

    public void setEntityEQ(int [] weaponData, int [] armorData){
        this.entityEQ = new Equip (weaponData, armorData);
    }

    public void calcEqStats(){
        this.sta = this.sta + entityEQ.armorStats[0] + entityEQ.weaponStats[0];
        this.str = this.str + entityEQ.armorStats[1] + entityEQ.weaponStats[1];
        this.dex = this.dex + entityEQ.armorStats[2] + entityEQ.weaponStats[2];
        this.intell = this.intell + entityEQ.armorStats[3] + entityEQ.weaponStats[3];
    }

    public double getDodge(){
        return dodge;
    }

    public double getHitrate(){
        return hitrate;
    }

    public double getCritrate(){
        return crit_chance;
    }

    public double getWeaponDmg(){
        if (entityEQ.weaponTyp==1 || entityEQ.weaponTyp==2 || entityEQ.weaponTyp==3 || entityEQ.weaponTyp==4 || entityEQ.weaponTyp==5 || entityEQ.weaponTyp==6
                || entityEQ.weaponTyp==10 || entityEQ.weaponTyp==11 || entityEQ.weaponTyp==12) { // Physical DMG
            return physical_dmg;
        }
        if (entityEQ.weaponTyp==7) { // Magical DMG
            return magical_dmg;
        }
        if (entityEQ.weaponTyp==8 || entityEQ.weaponTyp==9){ // Physical Bow DMG
            return physical_bow_dmg;
        }
        return -1;
    }

    public void calcDetailStats (){
        if (entityEQ.weaponTyp==1 || entityEQ.weaponTyp==2 || entityEQ.weaponTyp==3 || entityEQ.weaponTyp==4 || entityEQ.weaponTyp==5 || entityEQ.weaponTyp==6
                || entityEQ.weaponTyp==10 || entityEQ.weaponTyp==11 || entityEQ.weaponTyp==12) { // Physical DMG
            magical_dmg = intell * 0.1;
            physical_dmg = str * 20 + entityEQ.weaponDmg + magical_dmg;
        }
        if (entityEQ.weaponTyp==7) { // Magical DMG
            magical_wand_dmg = intell * 10 + entityEQ.weaponDmg;
            physical_dmg = str * 0.1;
            magical_dmg = intell * 10 + magical_wand_dmg + physical_dmg;
        }
        if (entityEQ.weaponTyp==8 || entityEQ.weaponTyp==9){ // Physical Bow DMG
            physical_dmg = str * 0.2;
            physical_bow_dmg = dex * 10 + physical_dmg;
        }
        maxHitpoints = sta * 50;
        curHitpoints = maxHitpoints;
        physical_res = str * 5 + sta * 10;
        magical_res = str * 5 + sta * 10;
        crit_chance = entityEQ.weaponCritrate*0.01 + dex * 0.1 ;
        crit_dmg = 1.5;
        hitrate = entityEQ.weaponHitrate*0.01 + dex * 0.1;
        dodge = dex * 0.1;


    }

    public void randomizeStats(){
        int [] newStats = new int [4];
        newStats[0] = this.sta;
        newStats[1] = this.str;
        newStats[2] = this.dex;
        newStats[3] = this.intell;
        int newStatsCombined = 0;
        int statsCombined = this.sta + this.str + this.dex + this.intell;

        for (int i = 0; i <4; i++){
           newStats[i] = newStats[i] + rand.nextInt(10) - 5;
            newStatsCombined = newStatsCombined + newStats[i];
        }

        this.sta = newStats[0];
        this.str = newStats[1];
        this.dex = newStats[2];
        this.intell = newStats[3];

        int statsDiff = newStatsCombined - statsCombined;

        if (statsDiff>10){
            // größer als 10
            this.lvl = this.lvl +2;
        }

        if (statsDiff<-10){
            //kleiner als -10
            this.lvl = this.lvl -2;
        }

        if (statsDiff<=10 && statsDiff>=2){
            //zwischen 2 und 10
            this.lvl = this.lvl +1;
        }

        if (statsDiff<=1 && statsDiff >=-1){
            //zwischen 1 und -1
        }

        if (statsDiff<=-2 && statsDiff>=-10) {
            //zwischen 2 und - 10
            this.lvl = this.lvl-1;
        }


    }
}
