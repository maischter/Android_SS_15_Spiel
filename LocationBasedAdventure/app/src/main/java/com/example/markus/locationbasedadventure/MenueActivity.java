package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Database.MySqlDatabase;

/**
 * Created by Markus on 19.08.2015.
 */
public class MenueActivity extends Activity {

    String sexTyp = "";
    String weaponTyp = "";
    ImageView characterImage;
    TextView characterName;
    Button stats;
    Button inventar;
    Button ranking;
    Button einstellungen;
    Button backToMap;
    MySqlDatabase db;


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
        db.close();
    }

    private void initDB(){
        db = new MySqlDatabase(getApplication());
        db.open();
    }

    private void loadCharacterData(){
        characterName.setText(db.getCharactername());
        sexTyp = db.getSex();
        weaponTyp = db.getWeapon();
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
                case ("Bogen"):
                    loadBitmap(R.drawable.bogenweiblich, characterImage);
                    break;
                case "Einhandschwert":
                    loadBitmap(R.drawable.einhandschwertweiblich, characterImage);
                    break;
                case "Einhandschwert mit Schild":
                    loadBitmap(R.drawable.einhandschwertschildweiblich, characterImage);
                    break;
                case "Einhandaxt":
                    loadBitmap(R.drawable.einhandaxtweiblich, characterImage);
                    break;
                case "Einhandaxt mit Schild":
                    loadBitmap(R.drawable.einhandaxtschildweiblich, characterImage);
                    break;
                case "Gewehr":
                    loadBitmap(R.drawable.gewehrweiblich, characterImage);
                    break;
                case "Zauberstab":
                    loadBitmap(R.drawable.zauberstabweiblich, characterImage);
                    break;
                case "Zwei-Hand-Axt":
                    loadBitmap(R.drawable.zweihandaxtweiblich, characterImage);
                    break;
                case "Zwei-Hand-Schwert":
                    loadBitmap(R.drawable.zweihandschwertweiblich, characterImage);
                    break;
            }
        } else {
            switch (weaponTyp) {
                case ("Bogen"):
                    loadBitmap(R.drawable.bogenmannlich, characterImage);
                    break;
                case "Einhandschwert":
                    loadBitmap(R.drawable.einhandschwertmannlich, characterImage);
                    break;
                case "Einhandschwert mit Schild":
                    loadBitmap(R.drawable.einhandschwertschildmannlich, characterImage);
                    break;
                case "Einhandaxt":
                    loadBitmap(R.drawable.einhandaxtmannlich, characterImage);
                    break;
                case "Einhandaxt mit Schild":
                    loadBitmap(R.drawable.einhandaxtschildmannlich, characterImage);
                    break;
                case "Gewehr":
                    loadBitmap(R.drawable.gewehrmannlich, characterImage);
                    break;
                case "Zauberstab":
                    loadBitmap(R.drawable.zauberstabmannlich, characterImage);
                    break;
                case "Zwei-Hand-Axt":
                    loadBitmap(R.drawable.zweihandaxtmannlich, characterImage);
                    break;
                case "Zwei-Hand-Schwert":
                    loadBitmap(R.drawable.zweihandschwertmannlich, characterImage);
                    break;
            }

        }
    }


    public void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }


}
