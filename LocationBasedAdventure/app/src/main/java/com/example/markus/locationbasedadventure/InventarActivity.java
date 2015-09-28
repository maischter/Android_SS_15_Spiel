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
import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
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
            case 1:loadBitmap(R.drawable.power_up,armorImage);break;
            case 2:loadBitmap(R.drawable.power_up,armorImage);break;
        }

    }

    private void selectWeaponImage(int weaponTyp){
        switch (weaponTyp) {

            //Bogen
            case 8:
                loadBitmap(R.drawable.einhandschwert, weaponImage);
                break;
            //Einhandschwert
            case 1:
                loadBitmap(R.drawable.einhandschwert, weaponImage);
                break;
            //EinhandschwertMitSchild
            case 3:
                loadBitmap(R.drawable.einhandschwertschildweiblich, weaponImage);
                break;
            //Einhandaxt
            case 2:
                loadBitmap(R.drawable.einhandaxt, weaponImage);
                break;
            //EinhandaxtMitSchild
            case 4:
                loadBitmap(R.drawable.einhandaxtschildweiblich, weaponImage);
                break;
            //Armbrust
            case 9:
                loadBitmap(R.drawable.gewehrweiblich, weaponImage);
                break;
            //Zauberstab
            case 7:
                loadBitmap(R.drawable.zauberstabweiblich, weaponImage);
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
    public void showItemInfo(int itemTyp) {
        itemInfo.setText(selectItem(itemTyp));
    }



    //selects Item
    //gets int itemTyp
    //return String

    private String selectItem(int itemTyp) {
        switch (itemTyp) {
            case 1:
                return " Schwacher Trank: Stellt eine geringe Anzahl an LP wieder her.";
            case 2:
                return "Trank: Stellt eine Anzahl an LP wieder her.";
            case 3:
                return "Starker Trank: Stellt eine hohe Anzahl an LP wieder her.";
            default: return "Leer";
        }
    }

}
