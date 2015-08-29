package com.example.markus.locationbasedadventure.Items;

import com.example.markus.locationbasedadventure.R;

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

    public Entity (int strength, int stamina, int dexterity, int intelligence){

        physical_dmg = strength * 20;
        magical_dmg = intelligence * 10;
        magical_wand_dmg = intelligence * 10;
        hitpoints = stamina * 50;
        physical_res = strength * 5 + stamina * 10;
        magical_res = strength * 5 + stamina * 10;
        crit_chance = dexterity * 0.1 ;
        crit_dmg = 1.5;
        hitrate = 0.5 + dexterity * 0.1;
        dodge = dexterity * 0.1;
        physical_bow_dmg = dexterity * 10;

        Skill1 = new Skill(R.string.skillA,30);
        Skill2 = new Skill(R.string.skillB,0);
        Skill3 = new Skill(R.string.skillC,80);
        Skill4 = new Skill(R.string.skillD,0);
    }
}
