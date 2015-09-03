package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;

/**
 * Created by Markus on 19.08.2015.
 */
public class MenueActivity extends Activity {

    String sexTyp = "";
    int weaponTyp;
    ImageView characterImage;
    TextView characterName;
    Button inventar;
    Button ranking;
    Button einstellungen;
    Button backToMap;
    TextView stamina;
    TextView staminaValue;
    TextView strength;
    TextView strengthValue;
    TextView dexterity;
    TextView dexterityValue;
    TextView intelligence;
    TextView intelligenceValue;

    CharacterdataDatabase characterdataDb;
    WeaponDatabase weaponDb;
    StatsDatabase statsDb;


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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        weaponDb.close();
        characterdataDb.close();
        statsDb.close();
    }

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();

    }

    private void loadCharacterData(){
        characterName.setText(characterdataDb.getCharactername());
        sexTyp = characterdataDb.getSex();
        weaponTyp = weaponDb.getWeaponTyp();
        selectImage();
    }

    private void loadStatsData(){
        staminaValue.setText(""+statsDb.getStamina());
        strengthValue.setText(""+statsDb.getStrength());
        dexterityValue.setText(""+statsDb.getDexterity());
        intelligenceValue.setText(""+statsDb.getIntelligence());
    }

    private void initButtons() {
        initInventarButton();
        initRankingButton();
        initEinstellungenButton();
        initBackToMapButton();

    }

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

    private void initBackToMapButton(){
        backToMap = (Button) findViewById(R.id.ButtonBackToMap);
        backToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


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


    private void selectImage() {

        if (sexTyp.equals("Weiblich")) {

            switch (weaponTyp) {
                case 8:
                    loadBitmap(R.drawable.bogenweiblich, characterImage);
                    break;
                case 1:
                    loadBitmap(R.drawable.einhandschwertweiblich, characterImage);
                    break;
                case 3:
                    loadBitmap(R.drawable.einhandschwertschildweiblich, characterImage);
                    break;
                case 2:
                    loadBitmap(R.drawable.einhandaxtweiblich, characterImage);
                    break;
                case 4:
                    loadBitmap(R.drawable.einhandaxtschildweiblich, characterImage);
                    break;
                case 9:
                    loadBitmap(R.drawable.gewehrweiblich, characterImage);
                    break;
                case 7:
                    loadBitmap(R.drawable.zauberstabweiblich, characterImage);
                    break;
                case 6:
                    loadBitmap(R.drawable.zweihandaxtweiblich, characterImage);
                    break;
                case 5:
                    loadBitmap(R.drawable.zweihandschwertweiblich, characterImage);
                    break;
                case 10:
                    loadBitmap(R.drawable.zweieinhandschwertweiblich, characterImage);
                    break;

                case 11:
                    loadBitmap(R.drawable.einhandaxtundeinhandschwertweiblich, characterImage);
                    break;
                case 12:
                    loadBitmap(R.drawable.zweieinhandaxtweiblich, characterImage);
                    break;
            }
        } else {
            switch (weaponTyp) {
                case 8:
                    loadBitmap(R.drawable.bogenmannlich, characterImage);
                    break;
                case 1:
                    loadBitmap(R.drawable.einhandschwertmannlich, characterImage);
                    break;
                case 3:
                    loadBitmap(R.drawable.einhandschwertschildmannlich, characterImage);
                    break;
                case 2:
                    loadBitmap(R.drawable.einhandaxtmannlich, characterImage);
                    break;
                case 4:
                    loadBitmap(R.drawable.einhandaxtschildmannlich, characterImage);
                    break;
                case 9:
                    loadBitmap(R.drawable.gewehrmannlich, characterImage);
                    break;
                case 7:
                    loadBitmap(R.drawable.zauberstabmannlich, characterImage);
                    break;
                case 6:
                    loadBitmap(R.drawable.zweihandaxtmannlich, characterImage);
                    break;
                case 5:
                    loadBitmap(R.drawable.zweihandschwertmannlich, characterImage);
                    break;
                case 10:
                    loadBitmap(R.drawable.zweieinhandschwertmannlich, characterImage);
                    break;
                case 11:
                    loadBitmap(R.drawable.einhandaxtundeinhandschwertmannlich, characterImage);
                    break;
                case 12:
                    loadBitmap(R.drawable.zweieinhandaxtmannlich, characterImage);
                    break;

            }

        }
    }


    public void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }


}
