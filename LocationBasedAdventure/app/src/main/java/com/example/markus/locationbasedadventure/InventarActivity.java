package com.example.markus.locationbasedadventure;

import android.app.Activity;
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
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.ItemDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;
import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.Items.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Markus on 03.09.2015.
 */
public class InventarActivity extends Activity implements ItemListAdapter.ItemListener {


    Button back;
    Button changeWeapon;
    Button changeArmor;

    ImageView weaponImage;
    TextView waffen;
    TextView kindofweapon;
    TextView damage;
    TextView hitchance;
    TextView kritchance;
    TextView extra;
    TextView stamina;
    TextView strength;
    TextView dexterity;
    TextView intelligence;
    TextView damageValue;
    TextView hitchanceValue;
    TextView kritchanceValue;
    TextView extraValue;
    TextView staminaValue;
    TextView strengthValue;
    TextView dexterityValue;
    TextView intelligenceValue;


    TableLayout weaponLayout;
    TableLayout armorLayout;


    ImageView armorImage;
    TextView armor;
    TextView kindofarmor;
    TextView staminaarmor;
    TextView strengtharmor;
    TextView dexterityarmor;
    TextView intelligencearmor;
    TextView staminaarmorValue;
    TextView strengtharmorValue;
    TextView dexterityarmorValue;
    TextView intelligencearmorValue;


    private GridView itemGrid;
    private TextView itemInfo;


    private ArrayList<Item> itemList = new ArrayList<Item>();
    ItemListAdapter itemListAdapter;

    ItemDatabase itemDb;
    WeaponDatabase weaponDb;
    ArmorDatabase armorDb;

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
        weaponImage.setImageResource(R.drawable.power_up);
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
        armorImage.setImageResource(R.drawable.power_up);
        kindofarmor.setText(armorDb.getArmorString());
        staminaarmorValue.setText("" + armorDb.getArmor()[1]);
        strengtharmorValue.setText("" + armorDb.getArmor()[2]);
        dexterityarmorValue.setText("" + armorDb.getArmor()[3]);
        intelligencearmorValue.setText("" + armorDb.getArmor()[4]);
    }



    //setsText to show info about clicked Item

    @Override
    public void showItemInfo(int itemTyp) {
        itemInfo.setText(selectItem(itemTyp));
    }



    //selects Item
    //gets int itemTyp
    //return String

    private String selectItem(int itemTyp) {
        switch (itemTyp) {
            case 1:
                return "Trank: Stellt eine Anzahl an LP wieder her.";
            case 2:
                return "Gegengift: Heilt eine Vergiftung.";
            case 3:
                return "StaUP: Erhöht Stamina für die Dauer dieses Kampfes.";
            case 4:
                return "StrUP: Erhöht Strength für die Dauer dieses Kampfes.";
            case 5:
                return "DexUP: Erhöht Dexterity für die Dauer dieses Kampfes.";
            case 6:
                return "IntUP: Erhöht Intelligence für die Dauer dieses Kampfes.";
            default: return "Leer";
        }
    }

}
