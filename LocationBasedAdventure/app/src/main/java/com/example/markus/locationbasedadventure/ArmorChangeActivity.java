package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Adapter.ArmorListAdapter;
import com.example.markus.locationbasedadventure.Adapter.ArmorListAdapter.ArmorListener;
import com.example.markus.locationbasedadventure.Adapter.WeaponListAdapter;
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;
import com.example.markus.locationbasedadventure.Items.Equip;

import java.util.ArrayList;

/**
 * Created by Markus on 07.09.2015.
 */
public class ArmorChangeActivity extends Activity implements ArmorListener {


    Button back;
    ImageView usedArmor;

    TextView[] tableStatsText = new TextView[4];
    TextView[] tableStatsValue = new TextView[4];
    TextView[] tableStatsDiff = new TextView[4];
    TextView armorChangeText;
    TextView armorTypText;

    private GridView armorGrid;


    private ArrayList<Equip> armorList = new ArrayList<Equip>();
    ArmorListAdapter armorListAdapter;

    ArmorDatabase armorDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armorchange);

        initDB();
        initButtons();
        initStaticViews();
        initValueViews();
        initDiffViews();
        initImageView();
        setValueViews();
        initGridView();
        initListAdapter();
        updateList();
    }

    private void initImageView() {
        usedArmor = (ImageView) findViewById(R.id.imageViewUsedArmor);
        usedArmor.setImageResource(R.drawable.power_up);
    }


    private void updateList() {
        armorList.clear();
        armorList.addAll(armorDb.getAllArmorItems());
        armorListAdapter.notifyDataSetChanged();
    }

    private void initListAdapter() {
        armorGrid = (GridView) findViewById(R.id.gridViewArmor);
        armorListAdapter = new ArmorListAdapter(this,armorList,this);
        armorGrid.setAdapter(armorListAdapter);
    }

    private void initGridView() {
        armorGrid = (GridView) findViewById(R.id.gridViewArmor);
    }

    @Override
    protected void onDestroy(){
        armorDb.close();
        super.onDestroy();

    }

    private void initDB(){
        armorDb = new ArmorDatabase(this);
        armorDb.open();
    }

    private void setDiffViewsEmpty() {
        tableStatsDiff[0].setText("");
        tableStatsDiff[1].setText("");
        tableStatsDiff[2].setText("");
        tableStatsDiff[3].setText("");
    }


    private void setValueViews() {
        armorTypText.setText(armorDb.getArmorString());
        tableStatsValue[0].setText(""+ armorDb.getArmor()[1]);
        tableStatsValue[1].setText("" + armorDb.getArmor()[2]);
        tableStatsValue[2].setText("" + armorDb.getArmor()[3]);
        tableStatsValue[3].setText("" + armorDb.getArmor()[4]);
    }

    private void initButtons() {
        Button back = (Button) findViewById(R.id.buttonBackArmorchange);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDiffViews() {
        tableStatsDiff[0] = (TextView) findViewById(R.id.textViewStaminaArmorChangeDiff);
        tableStatsDiff[1] = (TextView) findViewById(R.id.textViewStrengthArmorChangeDiff);
        tableStatsDiff[2] = (TextView) findViewById(R.id.textViewDexterityArmorChangeDiff);
        tableStatsDiff[3] = (TextView) findViewById(R.id.textViewIntelligenceArmorChangeDiff);
    }

    private void initValueViews() {

        armorTypText = (TextView) findViewById(R.id.textViewWeaponChangeArmorText);
        tableStatsValue[0] = (TextView) findViewById(R.id.textViewStaminaArmorChangeValue);
        tableStatsValue[1] = (TextView) findViewById(R.id.textViewStrengthArmorChangeValue);
        tableStatsValue[2] = (TextView) findViewById(R.id.textViewDexterityArmorChangeValue);
        tableStatsValue[3] = (TextView) findViewById(R.id.textViewIntelligenceArmorChangeValue);
    }

    private void initStaticViews() {
        armorChangeText = (TextView) findViewById(R.id.textViewChangeArmor);
        tableStatsText[0] = (TextView) findViewById(R.id.textViewStaminaArmorChange);
        tableStatsText[1] = (TextView) findViewById(R.id.textViewStrengthArmorChange);
        tableStatsText[2] = (TextView) findViewById(R.id.textViewDexterityArmorChange);
        tableStatsText[3] = (TextView) findViewById(R.id.textViewIntelligenceArmorChange);
    }

    @Override
    public void deleteArmor(int position) {
        Equip armor = armorList.get(position);
        armorDb.deleteArmor(armor.getArmorID());
        updateList();
    }

    @Override
    public void changeArmor(int position) {
        Equip armor = armorList.get(position);
        armorDb.changeToUsedArmor(armor);
        setValueViews();
        setDiffViewsEmpty();
        updateList();
    }

    @Override
    public void showDiff(int position) {
        Equip armor = armorList.get(position);
        if((armor.getArmorStats()[0] - armorDb.getArmor()[1]) >= 0){
            tableStatsDiff[0].setText("+" + (armor.getArmorStats()[0] - armorDb.getArmor()[1]));
            tableStatsDiff[0].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[0].setText("" + (armor.getArmorStats()[0] - armorDb.getArmor()[1]));
            tableStatsDiff[0].setTextColor(0xFFFF0000);
        }
        if((armor.getArmorStats()[1] - armorDb.getArmor()[2]) >= 0){
            tableStatsDiff[1].setText("+" + (armor.getArmorStats()[1] - armorDb.getArmor()[2]));
            tableStatsDiff[1].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[1].setText("" + (armor.getArmorStats()[1] - armorDb.getArmor()[2]));
            tableStatsDiff[1].setTextColor(0xFFFF0000);
        }
        if((armor.getArmorStats()[2] - armorDb.getArmor()[3]) >= 0){
            tableStatsDiff[2].setText("+" + (armor.getArmorStats()[2] - armorDb.getArmor()[3]));
            tableStatsDiff[2].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[2].setText("" + (armor.getArmorStats()[2] - armorDb.getArmor()[3]));
            tableStatsDiff[2].setTextColor(0xFFFF0000);
        }
        if((armor.getArmorStats()[3] - armorDb.getArmor()[4]) >= 0){
            tableStatsDiff[3].setText("+" + (armor.getArmorStats()[3] - armorDb.getArmor()[4]));
            tableStatsDiff[3].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[3].setText("" + (armor.getArmorStats()[3] - armorDb.getArmor()[4]));
            tableStatsDiff[3].setTextColor(0xFFFF0000);
        }


    }
}
