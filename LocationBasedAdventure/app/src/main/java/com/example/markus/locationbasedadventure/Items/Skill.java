package com.example.markus.locationbasedadventure.Items;

import com.example.markus.locationbasedadventure.R;

/**
 * Created by qcd on 24.08.2015.
 */
public class Skill {

    public int skillName;
    public int damage;

    public Skill (int sName, int dmg){
        this.skillName = sName;
        this.damage = dmg;

        switch (sName){
            case R.string.skillDef:
                //nächster skill macht keinen schaden
            case R.string.skillPower:
                //Skills machen 1,5 mehr dmg
            case R.string.skillSpecA:
                //Skill macht 2 mehr dmg,aber man muss eine Runde aussetzen

        }
    }

}
