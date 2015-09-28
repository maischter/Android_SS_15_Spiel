package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Adapter.WeaponListAdapter;
import com.example.markus.locationbasedadventure.Adapter.WeaponListAdapter.WeaponListener;
import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;
import com.example.markus.locationbasedadventure.Items.Equip;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Markus on 07.09.2015.
 */
public class WeaponChangeActivity extends Activity implements WeaponListener {

    private Button back;
    private ImageView usedWeapon;

    private TextView[] tableStatsText = new TextView[8];
    private TextView[] tableStatsValue = new TextView[8];
    private TextView[] tableStatsDiff = new TextView[8];
    private TextView weaponChangeText;
    private TextView weaponTypText;

    private GridView weaponGrid;


    private ArrayList<Equip> weaponList = new ArrayList<Equip>();
    private WeaponListAdapter weaponListAdapter;

    private WeaponDatabase weaponDb;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weaponchange);

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

    //initialise ImageView of UsedWeapon

    private void initImageView() {
        usedWeapon = (ImageView)findViewById(R.id.imageViewUsedWeapon);
        selectImage(weaponDb.getWeaponTyp());
        usedWeapon.setImageResource(R.drawable.power_up);
    }

    private void selectImage(int weaponTyp) {


        switch (weaponTyp) {

            //Bogen
            case 8:
                loadBitmap(R.drawable.einhandschwert, usedWeapon);
                break;
            //Einhandschwert
            case 1:
                loadBitmap(R.drawable.einhandschwert, usedWeapon);
                break;
            //EinhandschwertMitSchild
            case 3:
                loadBitmap(R.drawable.einhandschwertschildweiblich, usedWeapon);
                break;
            //Einhandaxt
            case 2:
                loadBitmap(R.drawable.einhandaxt, usedWeapon);
                break;
            //EinhandaxtMitSchild
            case 4:
                loadBitmap(R.drawable.einhandaxtschildweiblich, usedWeapon);
                break;
            //Armbrust
            case 9:
                loadBitmap(R.drawable.gewehrweiblich, usedWeapon);
                break;
            //Zauberstab
            case 7:
                loadBitmap(R.drawable.zauberstabweiblich, usedWeapon);
                break;
            //Zweihandaxt
            case 6:
                loadBitmap(R.drawable.zweihandaxt, usedWeapon);
                break;
            //Zweihandschwert
            case 5:
                loadBitmap(R.drawable.zweihandschwert, usedWeapon);
                break;
        }

    }

    //update List
    //clear List
    //add items to List
    //inform adapter about changes

    private void updateList() {
        weaponList.clear();
        weaponList.addAll(weaponDb.getAllWeaponItems());
        weaponListAdapter.notifyDataSetChanged();
    }


    //loads Bitmap into ImageView by Using backgroundTask

    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }


    //initialises Adapter

    private void initListAdapter() {
        weaponGrid = (GridView) findViewById(R.id.gridViewWeapon);
        weaponListAdapter = new WeaponListAdapter(this,weaponList,this);
        weaponGrid.setAdapter(weaponListAdapter);
    }


    //initialies GrivView

    private void initGridView() {

        weaponGrid = (GridView) findViewById(R.id.gridViewWeapon);
    }


    //sets View to Show Diff between UsedWeapon and clicked weapon to empty

    private void setDiffViewsEmpty() {
        tableStatsDiff[0].setText("");
        tableStatsDiff[1].setText("");
        tableStatsDiff[2].setText("");
        tableStatsDiff[3].setText("");
        tableStatsDiff[4].setText("");
        tableStatsDiff[5].setText("");
        tableStatsDiff[6].setText("");
        tableStatsDiff[7].setText("");
    }


    //close Database if activity is destroyed

    @Override
    protected void onDestroy(){
        weaponDb.close();
        super.onDestroy();

    }


    //initialise Database
    //open Database

    private void initDB(){
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
    }


    //sets View with values of the UsedWeapon

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


    //initialise buttons
    //buttonListener

    private void initButtons() {
        back = (Button) findViewById(R.id.buttonBackWeaponchange);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //init Views to Show Diff between UsedWeapon and clicked weapon to empty

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


    //init View with values of the UsedWeapon

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

    //sets View with TableNames

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

    //delete a weapon
    //call updateList

    @Override
    public void deleteWeapon(int position) {

        Equip weapon = weaponList.get(position);
        weaponDb.deleteWeapon(weapon.getWeaponID());
        updateList();
    }

    //changeWeapon to usedWeapon

    @Override
    public void changeWeapon(int position) {
        Equip weapon = weaponList.get(position);
        weaponDb.changeToUsedWeapon(weapon);
        setValueViews();
        setDiffViewsEmpty();
        updateList();
    }


    //show Diff between UsedWeapon and clickedWeapon
    //setTextColor

    @Override
    public void showDiff(int position) {
        Equip weapon = weaponList.get(position);

        if((weapon.getWeaponStats()[0] - weaponDb.getWeapon()[5]) >= 0) {
            tableStatsDiff[0].setText("+" + (weapon.getWeaponStats()[0] - weaponDb.getWeapon()[5]));
            tableStatsDiff[0].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[0].setText("" + (weapon.getWeaponStats()[0] - weaponDb.getWeapon()[5]));
            tableStatsDiff[0].setTextColor(0xFFFF0000);

        }

        if((weapon.getWeaponStats()[1] - weaponDb.getWeapon()[6]) >= 0) {
            tableStatsDiff[1].setText("+" + (weapon.getWeaponStats()[1] - weaponDb.getWeapon()[6]));
            tableStatsDiff[1].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[1].setText("" + (weapon.getWeaponStats()[1] - weaponDb.getWeapon()[6]));
            tableStatsDiff[1].setTextColor(0xFFFF0000);
        }

        if((weapon.getWeaponStats()[2] - weaponDb.getWeapon()[7]) >= 0) {
            tableStatsDiff[2].setText("+" + (weapon.getWeaponStats()[2] - weaponDb.getWeapon()[7]));
            tableStatsDiff[2].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[2].setText("" + (weapon.getWeaponStats()[2] - weaponDb.getWeapon()[7]));
            tableStatsDiff[2].setTextColor(0xFFFF0000);
        }

        if((weapon.getWeaponStats()[3] - weaponDb.getWeapon()[8]) >= 0) {
            tableStatsDiff[3].setText("+" + (weapon.getWeaponStats()[3] - weaponDb.getWeapon()[8]));
            tableStatsDiff[3].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[3].setText("" + (weapon.getWeaponStats()[3] - weaponDb.getWeapon()[8]));
            tableStatsDiff[3].setTextColor(0xFFFF0000);
        }

        if((weapon.getWeaponDmg() - weaponDb.getWeapon()[1]) >= 0) {
            tableStatsDiff[4].setText("+" + (weapon.getWeaponDmg() - weaponDb.getWeapon()[1]));
            tableStatsDiff[4].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[4].setText("" + (weapon.getWeaponDmg() - weaponDb.getWeapon()[1]));
            tableStatsDiff[4].setTextColor(0xFFFF0000);
        }

        if((weapon.getWeaponHitrate() - weaponDb.getWeapon()[2]) >= 0) {
            tableStatsDiff[5].setText("+" + (weapon.getWeaponHitrate() - weaponDb.getWeapon()[2]));
            tableStatsDiff[5].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[5].setText("" + (weapon.getWeaponHitrate() - weaponDb.getWeapon()[2]));
            tableStatsDiff[5].setTextColor(0xFFFF0000);
        }

        if((weapon.getWeaponCritrate() - weaponDb.getWeapon()[3]) >= 0) {
            tableStatsDiff[6].setText("+" + (weapon.getWeaponCritrate() - weaponDb.getWeapon()[3]));
            tableStatsDiff[6].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[6].setText("" + (weapon.getWeaponCritrate() - weaponDb.getWeapon()[3]));
            tableStatsDiff[6].setTextColor(0xFFFF0000);
        }

        if((weapon.getWeaponExtra() - weaponDb.getWeapon()[4]) >= 0) {
            tableStatsDiff[7].setText("+" + (weapon.getWeaponExtra() - weaponDb.getWeapon()[4]));
            tableStatsDiff[7].setTextColor(0xFF00CD00);
        }else{
            tableStatsDiff[7].setText("" + (weapon.getWeaponExtra() - weaponDb.getWeapon()[4]));
            tableStatsDiff[7].setTextColor(0xFFFF0000);
        }
    }
}
