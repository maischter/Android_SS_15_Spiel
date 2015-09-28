package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Adapter.AchievementListAdapter;
import com.example.markus.locationbasedadventure.Adapter.ItemListAdapter;
import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;

/**
 * Created by Markus on 19.08.2015.
 */
public class MenueActivity extends Activity {

    private String sexTyp = "";
    private int weaponTyp;

    private ImageView characterImage;
    private TextView characterName;
    private Button inventar;
    private Button ranking;
    private Button einstellungen;
    private Button backToMap;
    private TextView stamina;
    private TextView staminaValue;
    private TextView strength;
    private TextView strengthValue;
    private TextView dexterity;
    private TextView dexterityValue;
    private TextView intelligence;
    private TextView intelligenceValue;

    private CharacterdataDatabase characterdataDb;
    private WeaponDatabase weaponDb;
    private StatsDatabase statsDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menue);

        initDB();
        initViews();
        initButtons();
        loadCharacterData();
        loadStatsData();
    }


    //closes opened Database if Activity is destroyed

    @Override
    protected void onDestroy(){

        weaponDb.close();
        characterdataDb.close();
        statsDb.close();
        super.onDestroy();
    }


    //initialises Databases
    //opens Databases

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();

    }

    //loads CharacterData from Database
    //calls selectImage()

    private void loadCharacterData(){
        characterName.setText(characterdataDb.getCharactername());
        sexTyp = characterdataDb.getSex();
        weaponTyp = weaponDb.getWeaponTyp();
        selectImage();
    }

    //loads Stats from Database

    private void loadStatsData(){
        staminaValue.setText(""+statsDb.getStamina());
        strengthValue.setText(""+statsDb.getStrength());
        dexterityValue.setText(""+statsDb.getDexterity());
        intelligenceValue.setText(""+statsDb.getIntelligence());
    }


    //calls methods to initButtons

    private void initButtons() {
        initInventarButton();
        initRankingButton();
        initEinstellungenButton();
        initBackToMapButton();

    }

    //initialises InventarButton
    //buttonListener

    private void initInventarButton(){
        inventar = (Button) findViewById(R.id.ButtonInventar);
        inventar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),InventarActivity.class);
                startActivity(i);
            }
        });
    }


    //initialises RankingButton
    //buttonListener

    private void initRankingButton(){
        ranking = (Button) findViewById(R.id.ButtonRanking);
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RankingActivity.class);
                startActivity(i);
            }
        });
    }

    //initialises EinstellungenButtons
    //buttonListener

    private void initEinstellungenButton(){
        einstellungen = (Button) findViewById(R.id.ButtonEinstellungen);
        einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),EinstellungenActivity.class);
                startActivity(i);
            }
        });
    }


    //initialises backButton
    //buttonListener

    private void initBackToMapButton(){
        backToMap = (Button) findViewById(R.id.ButtonBackToMap);
        backToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //initialises Views

    private void initViews() {
        characterImage = (ImageView) findViewById(R.id.characterImageMenue);
        characterName =(TextView) findViewById(R.id.characterNameMenue);
        stamina = (TextView)findViewById(R.id.TextViewStamina);
        staminaValue = (TextView)findViewById(R.id.TextViewStaminaValue);
        strength = (TextView)findViewById(R.id.TextViewStrength);
        strengthValue = (TextView)findViewById(R.id.TextViewStrengthValue);
        dexterity = (TextView)findViewById(R.id.TextViewDexterity);
        dexterityValue = (TextView)findViewById(R.id.TextViewDexterityValue);
        intelligence = (TextView)findViewById(R.id.TextViewIntelligence);
        intelligenceValue = (TextView)findViewById(R.id.TextViewIntelligenceValue);
    }


    //selectsImage
    // diffs String sexTyp
    //diffs String weaponTyp --->Calls loadBitmap

    private void selectImage() {


            switch (weaponTyp) {

                //Bogen
                case 8:
                    loadBitmap(R.drawable.bogen, characterImage);
                    break;
                //Einhandschwert
                case 1:
                    loadBitmap(R.drawable.einhandschwert, characterImage);
                    break;
                //EinhandschwertMitSchild
                case 3:
                    loadBitmap(R.drawable.schwert_schild, characterImage);
                    break;
                //Einhandaxt
                case 2:
                    loadBitmap(R.drawable.einhandaxt, characterImage);
                    break;
                //EinhandaxtMitSchild
                case 4:
                    loadBitmap(R.drawable.axt_schild, characterImage);
                    break;
                //Armbrust
                case 9:
                    loadBitmap(R.drawable.armbrust, characterImage);
                    break;
                //Zauberstab
                case 7:
                    loadBitmap(R.drawable.zauberstab, characterImage);
                    break;
                //Zweihandaxt
                case 6:
                    loadBitmap(R.drawable.zweihandaxt, characterImage);
                    break;
                //Zweihandschwert
                case 5:
                    loadBitmap(R.drawable.zweihandschwert, characterImage);
                    break;
            }

        }



    //loads Bitmap into ImageView by Using backgroundTask

    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }


}
