package com.example.markus.locationbasedadventure.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 * Created by Markus on 21.07.2015.
 */
public class MySqlDatabase {

    private String url;
    private Connection Verbindung;
    static Statement SQLStatement;

	/*
	 * default constructor
	 */

    public MySqlDatabase() {

    }

	/*
	 * stellt Verbindung zu Datenbank her
	 */

    public void open() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Verbindung =  DriverManager.getConnection("jdbc:mysql://87.106.18.104:3306/LocationBasedGame","Entwickler","Qu1Ma2Ch3");
            SQLStatement = Verbindung.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.print("Klasse nicht gefunden.");
        } catch (SQLException e) {
            System.err.print("SQL-Ausnahme: ");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.print("Ein-/Ausgabefehler");
        }

    }

    /*
     * Beendet Verbindung zur Datenbank
     */
    public void close() {
        try {
            Verbindung.close();
        } catch (SQLException e) {
            System.err.print("SQL-Ausnahme: ");
            System.err.println(e.getMessage());
        }
    }
    public void selectAllFromAnmeldedaten(String email, String passwort){

        try{
            String emaill = "";
            String passwortt = "";
            String SQLString = new String("SELECT * FROM `Anmeldedaten`") ;
            ResultSet Ergebnis = SQLStatement.executeQuery(SQLString);
            while(Ergebnis.next()){
                emaill = Ergebnis.getString("email");
                passwortt = Ergebnis.getString("passwort");

            }
            email = emaill;
            passwort = passwortt;

        }catch (SQLException e) {
            System.err.print("SQL-Ausnahme: ");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.print("Ein-/Ausgabefehler");
        }
    }

    // SQL Befehle anpassen zu Serverbefehlen!

    //Funktioniert noch nicht! Wieso? SELECT Befehl testen!
    // INSERT INTO `Anmeldedaten`(`usernr`, `email`, `passwort`) VALUES (NULL,'quang@bauchweh.de','Qu1Ma2Ch3')
    public void insertAllInto(String email,String passwort, String CharacterName, String CharacterWeapon, String sex){
        try{
            String SQLStringAnmeldedaten = "INSERT INTO Anmeldedaten(usernr, email, passwort) VALUES (NULL,'quang@bauchweh.de','Qu1Ma2Ch3')";
            SQLStatement.executeUpdate(SQLStringAnmeldedaten);
            /*String SQLStringCharakterdaten = new String("INSERT INTO Charakterdaten VALUES " + "(NULL," + CharacterName
                    + "," + CharacterWeapon +"," + sex + ")");
            SQLStatement.executeUpdate(SQLStringCharakterdaten);*/
        }catch (SQLException e) {
            System.err.print("SQL-Ausnahme: ");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.print("Ein-/Ausgabefehler");
        }

    }

    public void updateCharakterdaten(String characterName, String characterWeapon, String sex) {
        try{
            String SQLStringUpdate = new String(
                    "UPDATE Characterdaten SET charactername='"+ characterName+"', characterweapon='"+ characterWeapon+"', sex='"+sex+"' WHERE characterName=" +"");
            SQLStatement.executeUpdate(SQLStringUpdate);
        }catch (SQLException e) {
            System.err.print("SQL-Ausnahme: ");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.print("Ein-/Ausgabefehler");
        }
    }
}
