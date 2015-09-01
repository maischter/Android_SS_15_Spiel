package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Database.MySqlDatabase;

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
    MySqlDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        initDB();
        initViews();

    }

    private void initDB() {
        db = new MySqlDatabase(this);
        db.open();
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
        if(db.getTon()==1){
            ton.setChecked(true);
        }else{
            ton.setChecked(false);
        }
        ton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.updateTon(isChecked);
            }
        });
        pushup = (Switch) findViewById(R.id.switchPushup);
        if(db.getPushup()==1){
            pushup.setChecked(true);
        }else{
            pushup.setChecked(false);
        }
        pushup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.updatePushup(isChecked);
            }
        });
    }

    private void initCheckBox() {
        angemeldetBleiben = (CheckBox) findViewById(R.id.checkBoxAngemeldetBleibenEinstellungen);
        if(db.getStayAngemeldet()==1){
            angemeldetBleiben.setChecked(true);
        }else{
            angemeldetBleiben.setChecked(false);
        }

        angemeldetBleiben.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStayAngemeldet(1);
                }else{
                    db.updateStayAngemeldet(0);
                }
            }
        });
    }

    private void initTextView() {
        einstellungen = (TextView) findViewById(R.id.textViewEinstellungen);
    }
}
