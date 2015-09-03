package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Database.WeaponDatabase;

/**
 * Created by Markus on 03.09.2015.
 */
public class InventarActivity extends Activity {


    Button back;
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





    WeaponDatabase weaponDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventar);

        initDB();
        initButton();
        initTextViews();
        initTextViewsValue();
        loadTextViewValue();
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






    private void initButton() {
        back = (Button) findViewById(R.id.buttonBackInventar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTextViews(){

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

    private void initTextViewsValue(){


        damageValue = (TextView) findViewById(R.id.textViewDamageValue);
        hitchanceValue = (TextView) findViewById(R.id.textViewHitchanceValue);
        kritchanceValue = (TextView) findViewById(R.id.textViewKritchanceValue);
        extraValue = (TextView) findViewById(R.id.textViewExtraValue);
        staminaValue = (TextView) findViewById(R.id.textViewStaminaInvValue);
        strengthValue = (TextView) findViewById(R.id.textViewStrengthInvValue);
        dexterityValue = (TextView) findViewById(R.id.textViewDexterityInvValue);
        intelligenceValue = (TextView) findViewById(R.id.textViewIntelligenceInvValue);
    }

    private void loadTextViewValue(){

        waffen.setText(weaponDb.getWeaponString());
        damageValue.setText(""+weaponDb.getWeapon()[1]);
        hitchanceValue.setText(""+weaponDb.getWeapon()[2]);
        kritchanceValue.setText(""+weaponDb.getWeapon()[3]);
        extraValue.setText("" + weaponDb.getWeapon()[4]);
        staminaValue.setText(""+weaponDb.getWeapon()[5]);
        strengthValue.setText(""+weaponDb.getWeapon()[6]);
        dexterityValue.setText(""+weaponDb.getWeapon()[7]);
        intelligenceValue.setText(""+weaponDb.getWeapon()[8]);
    }


}
