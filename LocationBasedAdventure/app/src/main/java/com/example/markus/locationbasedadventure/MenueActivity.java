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
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;

/**
 * Created by Markus on 19.08.2015.
 */
public class MenueActivity extends Activity {

    String sexTyp = "";
    int weaponTyp;
    ImageView characterImage;
    TextView characterName;
    Button stats;
    Button inventar;
    Button ranking;
    Button einstellungen;
    Button backToMap;
    CharacterdataDatabase characterdataDb;
    WeaponDatabase weaponDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menue);
        initDB();
        initViews();
        initButtons();
        loadCharacterData();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        weaponDb.close();
        characterdataDb.close();
    }

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
    }

    private void loadCharacterData(){
        characterName.setText(characterdataDb.getCharactername());
        sexTyp = characterdataDb.getSex();
        System.out.println(weaponDb.getWeaponTyp());
        weaponTyp = weaponDb.getWeaponTyp();
        selectImage();
    }

    private void initButtons() {
        initStatsButton();
        initInventarButton();
        initRankingButton();
        initEinstellungenButton();
        initBackToMapButton();

    }

    private void initStatsButton(){
        stats = (Button) findViewById(R.id.ButtonStats);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initInventarButton(){
        inventar = (Button) findViewById(R.id.ButtonInventar);
        inventar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
