package com.example.markus.locationbasedadventure.Items;

/**
 * Created by Markus on 28.07.2015.
 */
public class RankingItem {
    private int usernr;
    private int level;
    private int exp;
    private int rank;
    private String charactername;

    public RankingItem(int usernr, int level, int exp, int rank, String charactername){
        this.usernr = usernr;
        this.level = level;
        this.exp = exp;
        this.rank = rank;
        this.charactername = charactername;
    }


    public int getUsernr() {
        return usernr;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getRank() {
        return rank;
    }

    public String getCharactername(){return charactername;}
}
