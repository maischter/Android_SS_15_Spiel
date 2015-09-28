package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;

/**
 * Created by Markus on 31.08.2015.
 */
public class EinstellungenActivity extends Activity {

    private TextView einstellungen;
    private CheckBox angemeldetBleiben;
    private Button hilfe;
    private Button back;
    private CharacterdataDatabase characterdataDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        initDB();
        initViews();

    }

    //initialises Database
    //open Database

    private void initDB() {
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
    }

    private void initViews() {

        initTextView();
        initCheckBox();
        initButtons();
    }


    //initialises Button
    //buttonListeners

    private void initButtons() {

        hilfe =(Button) findViewById(R.id.buttonHilfe);
        hilfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HilfeActivity.class);
                startActivity(intent);
            }
        });
        back = (Button) findViewById(R.id.buttonBackEinstellungen);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    //initialises checkBox
    //checkBoxListener

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

    //initialises textView

    private void initTextView() {
        einstellungen = (TextView) findViewById(R.id.textViewEinstellungen);
    }
}
