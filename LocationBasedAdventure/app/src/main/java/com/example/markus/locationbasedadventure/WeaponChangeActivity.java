package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Adapter.WeaponListAdapter;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;
import com.example.markus.locationbasedadventure.Items.Equip;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Markus on 07.09.2015.
 */
public class WeaponChangeActivity extends Activity {

    Button back;
    ImageView usedWeapon;

    TextView[] tableStatsText = new TextView[8];
    TextView[] tableStatsValue = new TextView[8];
    TextView[] tableStatsDiff = new TextView[8];
    TextView weaponChangeText;
    TextView weaponTypText;

    private GridView weaponGrid;


    private ArrayList<Equip> weaponList = new ArrayList<Equip>();
    WeaponListAdapter weaponListAdapter;

    WeaponDatabase  weaponDb;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weaponchange);

        initDB();
        initButtons();
        initStaticViews();
        initValueViews();
        initDiffViews();
        setValueViews();
        initGridView();
        initListAdapter();
        updateList();

    }

    private void updateList() {
        weaponList.clear();
        weaponList.addAll(weaponDb.getAllFoodieItems());
        weaponListAdapter.notifyDataSetChanged();
    }

    private void initListAdapter() {
        weaponGrid = (GridView) findViewById(R.id.gridViewWeapon);
        weaponListAdapter = new WeaponListAdapter(this,weaponList);
        weaponGrid.setAdapter(weaponListAdapter);
    }

    private void initGridView() {
        weaponGrid = (GridView) findViewById(R.id.gridViewWeapon);
    }

    @Override
    protected void onDestroy(){
        weaponDb.close();
        super.onDestroy();

    }

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
    }



    private void setValueViews() {
        weaponTypText.setText(weaponDb.getWeaponString());
        tableStatsValue[0].setText(""+ weaponDb.getWeapon()[5]);
        tableStatsValue[1].setText("" + weaponDb.getWeapon()[6]);
        tableStatsValue[2].setText("" + weaponDb.getWeapon()[7]);
        tableStatsValue[3].setText("" + weaponDb.getWeapon()[8]);
        tableStatsValue[4].setText("" + weaponDb.getWeapon()[1]);
        tableStatsValue[5].setText("" + weaponDb.getWeapon()[2]);
        tableStatsValue[6].setText("" + weaponDb.getWeapon()[3]);
        tableStatsValue[7].setText("" + weaponDb.getWeapon()[4]);

    }

    private void initButtons() {
        Button back = (Button) findViewById(R.id.buttonBackWeaponchange);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDiffViews() {
        tableStatsDiff[0] = (TextView) findViewById(R.id.textViewStaminaWeaponChangeDiff);
        tableStatsDiff[1] = (TextView) findViewById(R.id.textViewStrengthWeaponChangeDiff);
        tableStatsDiff[2] = (TextView) findViewById(R.id.textViewDexterityWeaponChangeDiff);
        tableStatsDiff[3] = (TextView) findViewById(R.id.textViewIntelligenceWeaponChangeDiff);
        tableStatsDiff[4] = (TextView) findViewById(R.id.textViewDamageWeaponChangeDiff);
        tableStatsDiff[5] = (TextView) findViewById(R.id.textViewHitchanceWeaponChangeDiff);
        tableStatsDiff[6] = (TextView) findViewById(R.id.textViewKritchanceWeaponChangeDiff);
        tableStatsDiff[7] = (TextView) findViewById(R.id.textViewExtraWeaponChangeDiff);
    }

    private void initValueViews() {

        weaponTypText = (TextView) findViewById(R.id.textViewWeaponChangeWeaponText);
        tableStatsValue[0] = (TextView) findViewById(R.id.textViewStaminaWeaponChangeValue);
        tableStatsValue[1] = (TextView) findViewById(R.id.textViewStrengthWeaponChangeValue);
        tableStatsValue[2] = (TextView) findViewById(R.id.textViewDexterityWeaponChangeValue);
        tableStatsValue[3] = (TextView) findViewById(R.id.textViewIntelligenceWeaponChangeValue);
        tableStatsValue[4] = (TextView) findViewById(R.id.textViewDamageWeaponChangeValue);
        tableStatsValue[5] = (TextView) findViewById(R.id.textViewHitchanceWeaponChangeValue);
        tableStatsValue[6] = (TextView) findViewById(R.id.textViewKritchanceWeaponChangeValue);
        tableStatsValue[7] = (TextView) findViewById(R.id.textViewExtraWeaponChangeValue);
    }

    private void initStaticViews() {
        weaponChangeText = (TextView) findViewById(R.id.textViewChangeWaffen);
        tableStatsText[0] = (TextView) findViewById(R.id.textViewStaminaWeaponChange);
        tableStatsText[1] = (TextView) findViewById(R.id.textViewStrengthWeaponChange);
        tableStatsText[2] = (TextView) findViewById(R.id.textViewDexterityWeaponChange);
        tableStatsText[3] = (TextView) findViewById(R.id.textViewIntelligenceWeaponChange);
        tableStatsText[4] = (TextView) findViewById(R.id.textViewDamageWeaponChange);
        tableStatsText[5] = (TextView) findViewById(R.id.textViewHitchanceWeaponChange);
        tableStatsText[6] = (TextView) findViewById(R.id.textViewKritchanceWeaponChange);
        tableStatsText[7] = (TextView) findViewById(R.id.textViewExtraWeaponChange);
    }
}
