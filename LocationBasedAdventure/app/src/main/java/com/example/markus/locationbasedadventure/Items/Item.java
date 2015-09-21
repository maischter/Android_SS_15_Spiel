package com.example.markus.locationbasedadventure.Items;

/**
 * Created by Markus on 16.09.2015.
 */
public class Item {

    private int itemID;
    private int itemTyp;
    private int itemQuantity;

    public Item(int itemID, int itemTyp, int itemQuantity){
        this.itemID = itemID;
        this.itemTyp = itemTyp;
        this.itemQuantity = itemQuantity;
    }


    public int getItemID() {
        return itemID;
    }
    public int getItemTyp() {
        return itemTyp;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
}
