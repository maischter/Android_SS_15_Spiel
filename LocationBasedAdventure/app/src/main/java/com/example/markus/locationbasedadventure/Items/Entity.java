package com.example.markus.locationbasedadventure.Items;

import com.example.markus.locationbasedadventure.Database.MySqlDatabase;
import com.example.markus.locationbasedadventure.R;

import java.util.Random;

/**
 * Created by qcd on 28.08.2015.
 */
public class Entity {

    public double physical_dmg;
    public double magical_dmg;
    public double magical_wand_dmg;
    public double hitpoints;
    public double physical_res;
    public double magical_res;
    public double crit_chance;
    public double crit_dmg;
    public double dodge;
    public double physical_bow_dmg;
    public double hitrate;
    public Skill Skill1;
    public Skill Skill2;
    public Skill Skill3;
    public Skill Skill4;
    MySqlDatabase db;
    Random rand;
    int str;
    int sta;
    int dex;
    int intell;
    int lvl;

    public Entity (int strength, int stamina, int dexterity, int intelligence,int level, boolean npc){

        //initStats
        this.lvl = level;
        this.str = strength;
        this.sta = stamina;
        this.dex = dexterity;
        this.intell = intelligence;

        if (npc){
            randomizeStats();
        }

        calcDetailStats();


        Skill1 = new Skill(R.string.skillA,30);
        Skill2 = new Skill(R.string.skillB,0);
        Skill3 = new Skill(R.string.skillC,80);
        Skill4 = new Skill(R.string.skillD,0);
    }

    public void calcDetailStats (){
        physical_dmg = str * 20;
        magical_dmg = intell * 10;
        magical_wand_dmg = intell * 10;
        hitpoints = sta * 50;
        physical_res = str * 5 + sta * 10;
        magical_res = str * 5 + sta * 10;
        crit_chance = dex * 0.1 ;
        crit_dmg = 1.5;
        hitrate = 0.5 + dex * 0.1;
        dodge = dex * 0.1;
        physical_bow_dmg = dex * 10;
    }

    public void randomizeStats(){
        int [] newStats = new int [4];
        newStats[0] = this.str;
        newStats[1] = this.sta;
        newStats[2] = this.dex;
        newStats[3] = this.intell;
        int newStatsCombined = 0;
        int statsCombined = this.str + this.sta + this.dex + this.intell;

        for (int i = 0; i <4; i++){
           newStats[i] = newStats[i] + rand.nextInt(10) - 5;
            newStatsCombined = newStatsCombined + newStats[i];
        }

        this.str = newStats[0];
        this.sta = newStats[1];
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
