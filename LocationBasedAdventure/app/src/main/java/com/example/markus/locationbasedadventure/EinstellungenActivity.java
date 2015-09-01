package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;

/**
 * Created by Markus on 31.08.2015.
 */
public class EinstellungenActivity extends Activity {

    TextView einstellungen;
    CheckBox angemeldetBleiben;
    Switch ton;
    Switch pushup;
    Button kampferklaerung;
    Button hilfe;
    Button aboutUs;
    CharacterdataDatabase characterdataDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        initDB();
        initViews();

    }

    private void initDB() {
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
    }

    private void initViews() {

        initTextView();
        initCheckBox();
        initSwitchs();
        initButtons();
    }

    private void initButtons() {
        kampferklaerung = (Button) findViewById(R.id.buttonKampferklaerung);
        kampferklaerung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),GifActivity.class);
                startActivity(i);
            }
        });
        hilfe =(Button) findViewById(R.id.buttonHilfe);
        aboutUs = (Button) findViewById(R.id.buttonAboutUs);
    }

    private void initSwitchs() {
        ton = (Switch) findViewById(R.id.switchTon);
        if(characterdataDb.getTon()==1){
            ton.setChecked(true);
        }else{
            ton.setChecked(false);
        }
        ton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                characterdataDb.updateTon(isChecked);
            }
        });
        pushup = (Switch) findViewById(R.id.switchPushup);
        if(characterdataDb.getPushup()==1){
            pushup.setChecked(true);
        }else{
            pushup.setChecked(false);
        }
        pushup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                characterdataDb.updatePushup(isChecked);
            }
        });
    }

    private void initCheckBox() {
        angemeldetBleiben = (CheckBox) findViewById(R.id.checkBoxAngemeldetBleibenEinstellungen);
        if(characterdataDb.getStayAngemeldet()==1){
            angemeldetBleiben.setChecked(true);
        }else{
            angemeldetBleiben.setChecked(false);
        }

        angemeldetBleiben.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    characterdataDb.updateStayAngemeldet(1);
                }else{
                    characterdataDb.updateStayAngemeldet(0);
                }
            }
        });
    }

    private void initTextView() {
        einstellungen = (TextView) findViewById(R.id.textViewEinstellungen);
    }
}
