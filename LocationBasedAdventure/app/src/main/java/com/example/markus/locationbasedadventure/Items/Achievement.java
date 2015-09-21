package com.example.markus.locationbasedadventure.Items;

/**
 * Created by Markus on 16.09.2015.
 */
public class Achievement {


    private int achievementID;
    private int achievementTyp;

    public Achievement(int achievementID, int achievementTyp){
        this.achievementID = achievementID;
        this.achievementTyp = achievementTyp;
    }


    public int getAchievementID() {
        return achievementID;
    }
    public int getAchievementTyp() {
        return achievementTyp;
    }

}
