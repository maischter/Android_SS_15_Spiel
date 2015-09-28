package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Adapter.ItemListAdapter;
import com.example.markus.locationbasedadventure.Adapter.WeaponListAdapter;
import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Database.AchievementDatabase;
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.ItemDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;
import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.Items.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Markus on 03.09.2015.
 */
public class InventarActivity extends Activity implements ItemListAdapter.ItemListener {


    private Button back;
    private Button changeWeapon;
    private Button changeArmor;

    private  ImageView weaponImage;
    private  TextView waffen;
    private  TextView kindofweapon;
    private  TextView damage;
    private  TextView hitchance;
    private  TextView kritchance;
    private  TextView extra;
    private  TextView stamina;
    private  TextView strength;
    private TextView dexterity;
    private TextView intelligence;
    private TextView damageValue;
    private TextView hitchanceValue;
    private TextView kritchanceValue;
    private TextView extraValue;
    private TextView staminaValue;
    private TextView strengthValue;
    private TextView dexterityValue;
    private TextView intelligenceValue;


    private TableLayout weaponLayout;
    private TableLayout armorLayout;


    private ImageView armorImage;
    private TextView armor;
    private TextView kindofarmor;
    private TextView staminaarmor;
    private TextView strengtharmor;
    private TextView dexterityarmor;
    private TextView intelligencearmor;
    private TextView staminaarmorValue;
    private TextView strengtharmorValue;
    private TextView dexterityarmorValue;
    private TextView intelligencearmorValue;


    private GridView itemGrid;
    private TextView itemInfo;


    private ArrayList<Item> itemList = new ArrayList<Item>();
    private ItemListAdapter itemListAdapter;

    private ItemDatabase itemDb;
    private WeaponDatabase weaponDb;
    private ArmorDatabase armorDb;
    private StatsDatabase statsDb;
    private AchievementDatabase achievementDb;

    private boolean getNewWeapon = false;
    private boolean levelUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventar);

        initDB();

        initButton();
        initTextViews();
        initTextViewsValue();
        initListAdapter();
        initGridView();
        initTextViewItem();
        loadTextViewWeaponValue();
        loadTextViewsArmorValue();
        updateList();
    }

    //initialises TextView

    private void initTextViewItem() {
        itemInfo = (TextView) findViewById(R.id.textViewItemInfo);
    }


    //updates List
    //clearList
    //adds items again to List
    //informs adapter about changes

    private void updateList() {
        itemList.clear();
        itemList.addAll(itemDb.getAllItemItems());
        itemListAdapter.notifyDataSetChanged();
    }


    //initialies ListAdapter

    private void initListAdapter() {
        itemGrid = (GridView) findViewById(R.id.gridViewItem);
        itemListAdapter = new ItemListAdapter(this, itemList, this);
        itemGrid.setAdapter(itemListAdapter);
    }


    //initialies GridView

    private void initGridView() {

        itemGrid = (GridView) findViewById(R.id.gridViewItem);
    }


    //closes opened Database when activity is destroyed

    @Override
    protected void onDestroy() {
        weaponDb.close();
        armorDb.close();
        itemDb.close();
        statsDb.close();
        achievementDb.close();
        super.onDestroy();

    }

    //initialises Databases
    //opens Databases

    private void initDB() {
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        armorDb = new ArmorDatabase(this);
        armorDb.open();
        itemDb = new ItemDatabase(this);
        itemDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
        achievementDb = new AchievementDatabase(this);
        achievementDb.open();
    }


    //initialises buttons
    //buttonListeners


    private void initButton() {
        back = (Button) findViewById(R.id.buttonBackInventar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeWeapon = (Button) findViewById(R.id.buttonChangeToWeaponChangeActivity);
        changeWeapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), WeaponChangeActivity.class);
                startActivity(i);
            }
        });

        changeArmor = (Button) findViewById(R.id.buttonChangeToArmorChangeActivity);
        changeArmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ArmorChangeActivity.class);
                startActivity(i);
            }
        });

    }

    //initTextViews

    private void initTextViews() {
        initTextViewsWeapon();
        initTextViewsArmor();
    }

    //initTextViews to show Armor

    private void initTextViewsArmor() {
        armor = (TextView) findViewById(R.id.textViewArmor);
        staminaarmor = (TextView) findViewById(R.id.textViewStaminaInvArmor);
        strengtharmor = (TextView) findViewById(R.id.textViewStrengthInvArmor);
        dexterityarmor = (TextView) findViewById(R.id.textViewDexterityInvArmor);
        intelligencearmor = (TextView) findViewById(R.id.textViewIntelligenceInvArmor);
    }

    //initTextViews to show Weapon

    private void initTextViewsWeapon() {
        waffen = (TextView) findViewById(R.id.textViewWaffen);
        damage = (TextView) findViewById(R.id.textViewDamage);
        hitchance = (TextView) findViewById(R.id.textViewHitchance);
        kritchance = (TextView) findViewById(R.id.textViewKritchance);
        extra = (TextView) findViewById(R.id.textViewExtra);
        stamina = (TextView) findViewById(R.id.textViewStaminaInv);
        strength = (TextView) findViewById(R.id.textViewStrengthInv);
        dexterity = (TextView) findViewById(R.id.textViewDexterityInv);
        intelligence = (TextView) findViewById(R.id.textViewIntelligenceInv);

    }

    private void initTextViewsValue() {
        initTextViewsWeaponValue();
        initTextViewsArmorValue();
    }

    //initTextViews to show UsedArmor values

    private void initTextViewsArmorValue() {
        armorImage = (ImageView) findViewById(R.id.imageViewArmor);
        kindofarmor = (TextView) findViewById(R.id.textViewArmorText);
        staminaarmorValue = (TextView) findViewById(R.id.textViewStaminaInvValueArmor);
        strengtharmorValue = (TextView) findViewById(R.id.textViewStrengthInvValueArmor);
        dexterityarmorValue = (TextView) findViewById(R.id.textViewDexterityInvValueArmor);
        intelligencearmorValue = (TextView) findViewById(R.id.textViewIntelligenceInvValueArmor);
    }

    //initTextViews to show UsedWeapon Values

    private void initTextViewsWeaponValue() {
        weaponImage = (ImageView) findViewById(R.id.imageViewWeapon);
        kindofweapon = (TextView) findViewById(R.id.textViewWeaponText);
        damageValue = (TextView) findViewById(R.id.textViewDamageValue);
        hitchanceValue = (TextView) findViewById(R.id.textViewHitchanceValue);
        kritchanceValue = (TextView) findViewById(R.id.textViewKritchanceValue);
        extraValue = (TextView) findViewById(R.id.textViewExtraValue);
        staminaValue = (TextView) findViewById(R.id.textViewStaminaInvValue);
        strengthValue = (TextView) findViewById(R.id.textViewStrengthInvValue);
        dexterityValue = (TextView) findViewById(R.id.textViewDexterityInvValue);
        intelligenceValue = (TextView) findViewById(R.id.textViewIntelligenceInvValue);
    }


    //Loads usedWeapon values and sets textViews

    private void loadTextViewWeaponValue() {
        selectWeaponImage(weaponDb.getWeaponTyp());
        kindofweapon.setText(weaponDb.getWeaponString());
        damageValue.setText("" + weaponDb.getWeapon()[1]);
        hitchanceValue.setText("" + weaponDb.getWeapon()[2]);
        kritchanceValue.setText("" + weaponDb.getWeapon()[3]);
        extraValue.setText("" + weaponDb.getWeapon()[4]);
        staminaValue.setText("" + weaponDb.getWeapon()[5]);
        strengthValue.setText("" + weaponDb.getWeapon()[6]);
        dexterityValue.setText("" + weaponDb.getWeapon()[7]);
        intelligenceValue.setText("" + weaponDb.getWeapon()[8]);
    }



    //Loads usedArmor values and sets textViews


    private void loadTextViewsArmorValue() {
        selectArmorImage(armorDb.getArmor()[0]);
        kindofarmor.setText(armorDb.getArmorString());
        staminaarmorValue.setText("" + armorDb.getArmor()[1]);
        strengtharmorValue.setText("" + armorDb.getArmor()[2]);
        dexterityarmorValue.setText("" + armorDb.getArmor()[3]);
        intelligencearmorValue.setText("" + armorDb.getArmor()[4]);
    }

    //select an Image
    //Calls loadBitmap
    //diffs by int armorTyp


    private void selectArmorImage(int armorTyp){
        switch(armorTyp){
            case 1:loadBitmap(R.drawable.standard_ruestung,armorImage);break;
            case 2:loadBitmap(R.drawable.verbesserte_ruestung,armorImage);break;
        }

    }

    private void selectWeaponImage(int weaponTyp){
        switch (weaponTyp) {

            //Bogen
            case 8:
                loadBitmap(R.drawable.bogen, weaponImage);
                break;
            //Einhandschwert
            case 1:
                loadBitmap(R.drawable.einhandschwert, weaponImage);
                break;
            //EinhandschwertMitSchild
            case 3:
                loadBitmap(R.drawable.schwert_schild, weaponImage);
                break;
            //Einhandaxt
            case 2:
                loadBitmap(R.drawable.einhandaxt, weaponImage);
                break;
            //EinhandaxtMitSchild
            case 4:
                loadBitmap(R.drawable.axt_schild, weaponImage);
                break;
            //Armbrust
            case 9:
                loadBitmap(R.drawable.armbrust, weaponImage);
                break;
            //Zauberstab
            case 7:
                loadBitmap(R.drawable.zauberstab, weaponImage);
                break;
            //Zweihandaxt
            case 6:
                loadBitmap(R.drawable.zweihandaxt, weaponImage);
                break;
            //Zweihandschwert
            case 5:
                loadBitmap(R.drawable.zweihandschwert, weaponImage);
                break;
        }
    }


    //loads Bitmap into ImageView by Using backgroundTask

    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }

    //setsText to show info about clicked Item

    @Override
    public void showItemInfo(int itemTyp,int itemID) {
        itemInfo.setText(selectItem(itemTyp,itemID));
    }

    @Override
    public void itemUsed(int itemTyp, int itemID) {
        statsDb.updateExp(selectItemErfahrung(itemTyp)+statsDb.getExp());
        itemDb.updateQuantity(itemTyp,itemDb.getQuantity(itemID)-1,itemID);
        checkLevelUp();
        if(levelUp){
            showAlert();
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Du hast Level "+statsDb.getLevel()+" erreicht!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }

    //checks if a new Level is reached and updates Stats DB
    //handles receiving a new Weapon in some LevelUps
    //handles recieving achievements by levelUps

    private void checkLevelUp() {
        levelUp = true;
        if(statsDb.getLevel() == 1 &&statsDb.getExp()>=100 && statsDb.getExp() <= 175){
            //Level 1 zu 2
            statsDb.updateAllExceptExp(2, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);

        }else{
            if(statsDb.getLevel() == 2 &&statsDb.getExp()>=175 && statsDb.getExp() <= 306){
                //Level 2 zu 3
                getNewWeapon();
                statsDb.updateAllExceptExp(3, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
            }else{
                if(statsDb.getLevel() == 3 && statsDb.getExp()>=306 && statsDb.getExp() <= 536){
                    //Level 3 zu 4
                    statsDb.updateAllExceptExp(4, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);
                    addAchievementLevel(4);
                }else{
                    if(statsDb.getExp()>=536 && statsDb.getExp() <= 938){
                        //Level 4 zu 5
                        getNewWeapon();
                        statsDb.updateAllExceptExp(5, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                    }else{
                        if(statsDb.getExp()>=938 && statsDb.getExp() <= 1548){
                            //Level 5 zu 6
                            statsDb.updateAllExceptExp(6, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                        }else{
                            if(statsDb.getExp()>=1548 && statsDb.getExp() <= 2553){
                                //Level 6 zu 7
                                getNewWeapon();
                                statsDb.updateAllExceptExp(7, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                            }else{
                                if(statsDb.getExp()>=2553 && statsDb.getExp() <= 4213){
                                    //Level 7 zu 8
                                    statsDb.updateAllExceptExp(8, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                                }else{
                                    if(statsDb.getExp()>=4213 && statsDb.getExp() <= 6912){
                                        //Level 8 zu 9
                                        getNewWeapon();
                                        statsDb.updateAllExceptExp(9, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);
                                        addAchievementLevel(8);
                                    }else{
                                        if(statsDb.getExp()>=6912 && statsDb.getExp() <= 11470){
                                            //Level 9 zu 10
                                            statsDb.updateAllExceptExp(10, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                                        }else{
                                            if(statsDb.getExp()>=11470 && statsDb.getExp() <= 17205){
                                                //Level 10 zu 11
                                                statsDb.updateAllExceptExp(11, statsDb.getStamina() + 3, statsDb.getStrength()+3,statsDb.getDexterity()+3,statsDb.getIntelligence()+3);
                                            }else{
                                                if(statsDb.getExp()>=17205 && statsDb.getExp() <= 25808){
                                                    //Level 11 zu 12
                                                    getNewWeapon();
                                                    statsDb.updateAllExceptExp(12, statsDb.getStamina() + 3, statsDb.getStrength() + 3, statsDb.getDexterity() + 3, statsDb.getIntelligence() + 3);
                                                    addAchievementLevel(12);
                                                }else{
                                                    levelUp = false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    private void getNewWeapon() {
        getNewWeapon = false;
        Random random = new Random();
        int newWeapon;
        do {
            newWeapon = random.nextInt(9)+1;
            ArrayList<Equip> weapons = weaponDb.getAllWeaponItemsFromStart();
            for(int i = 0;i<weapons.size();i++){
                if(weapons.get(i).getWeaponTyp()==newWeapon){
                    getNewWeapon =false;
                }else {
                    getNewWeapon = true;
                }
            }
        }while(!getNewWeapon);

        addNewWeapon(newWeapon);


    }


    //dds the new Weapon to the Database

    private void addNewWeapon(int newWeapon) {
        switch(newWeapon){

            case 1:weaponDb.insertNewWeapon(newWeapon,2,70,25,0,0,0,0,0);break;
            case 2:weaponDb.insertNewWeapon(newWeapon,4,55,25,0,0,0,0,0);break;
            case 3:weaponDb.insertNewWeapon(newWeapon,2,85,25,10,1,1,1,1);break;
            case 4:weaponDb.insertNewWeapon(newWeapon,2,85,25,10,1,1,1,1);break;
            case 5:weaponDb.insertNewWeapon(newWeapon,4,70,10,0,0,0,0,0);break;
            case 6:weaponDb.insertNewWeapon(newWeapon,6,55,10,0,0,0,0,0);break;
            case 7:weaponDb.insertNewWeapon(newWeapon,4,100,10,0,0,0,0,0);break;
            case 8:weaponDb.insertNewWeapon(newWeapon,1,85,60,0,0,0,0,0);break;
            case 9:weaponDb.insertNewWeapon(newWeapon,2,70,60,0,0,0,0,0);break;
        }
    }
    //selects Item
    //gets int itemTyp
    //return String

    //inserts an Achievment by Level up

    private void addAchievementLevel(int level) {
        switch(level){
            case 4:achievementDb.insertNewAchievement(1);itemDb.insertNewItem(1, 1);break;
            case 8:achievementDb.insertNewAchievement(2);itemDb.insertNewItem(2, 1);break;
            case 12:achievementDb.insertNewAchievement(3);itemDb.insertNewItem(3,1);break;
        }
    }

    private int selectItemErfahrung(int itemTyp) {
        switch (itemTyp) {
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 40;
        }
        return 0;
    }

    //selects Item
    //gets int itemTyp
    //return String

    private String selectItem(int itemTyp,int itemID) {
        switch (itemTyp) {
            case 1:
                return itemDb.getQuantity(itemID)+"x Pot 1: Erhöht die Erfahrungspunkte um 10.";
            case 2:
                return itemDb.getQuantity(itemID)+"x Pot 2: Erhöht die Erfahrungspunkte um 20.";
            case 3:
                return itemDb.getQuantity(itemID)+"x Pot 3: Erhöht die Erfahrungspunkte um 40.";
            default: return "Leer";
        }
    }

}
