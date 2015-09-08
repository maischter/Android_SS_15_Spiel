package com.example.markus.locationbasedadventure.Items;

import com.example.markus.locationbasedadventure.R;

/**
 * Created by qcd on 24.08.2015.
 */
public class Skill {

    public int skillName;
    public double damage;
    public int multiply = 1;

    public Skill (int sName, double dmg){
        this.skillName = sName;
        this.damage = dmg;

        switch (sName){
            case R.string.skillA:

            case R.string.skillDef: // Blockt den nächsten Schaden
                this.damage = 0;
            case R.string.skillPower: //
                this.damage = 0;
            case R.string.skillSpecA:
                //Skill macht 2 mehr dmg,aber man muss eine Runde aussetzen
                this.damage = dmg;

        }
    }

}
