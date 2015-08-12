package com.example.markus.locationbasedadventure.Items;

/**
 * Created by Markus on 28.07.2015.
 */
public class AnmeldenItem {
    private int usernr;
    private String email;
    private String passwort;

    public AnmeldenItem(int usernr, String email, String passwort){
        this.usernr = usernr;
        this.email = email;
        this.passwort = passwort;
    }


    public int getUsernr() {
        return usernr;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }
}
