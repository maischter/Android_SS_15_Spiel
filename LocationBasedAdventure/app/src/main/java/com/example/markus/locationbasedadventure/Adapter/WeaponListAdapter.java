package com.example.markus.locationbasedadventure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.R;

import java.util.ArrayList;

/**
 * Created by Markus on 08.09.2015.
 */
public class WeaponListAdapter extends ArrayAdapter<Equip> {

    private ArrayList<Equip> weaponItem;
    private Context context;

    public WeaponListAdapter(Context context, ArrayList<Equip> weaponItem) {
        super(context, R.layout.weapon_item, weaponItem);

        this.context = context;
        this.weaponItem = weaponItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.weapon_item, null);
        }

        Equip weapon = weaponItem.get(position);

        if (weapon != null) {

            ImageView weaponImage = (ImageView) v.findViewById(R.id.imageViewWeaponItem);

            TextView stamina = (TextView) v.findViewById(R.id.textViewStaminaWeaponItem);
            TextView strength = (TextView) v.findViewById(R.id.textViewStrengthWeaponItem);
            TextView dexterity = (TextView) v.findViewById(R.id.textViewDexterityWeaponItem);
            TextView intelligence = (TextView) v.findViewById(R.id.textViewIntelligenceWeaponItem);
            TextView damage = (TextView) v.findViewById(R.id.textViewDamageWeaponItem);
            TextView hitchance = (TextView) v.findViewById(R.id.textViewHitchanceWeaponItem);
            TextView kritchance = (TextView) v.findViewById(R.id.textViewKritchanceWeaponItem);
            TextView extra = (TextView) v.findViewById(R.id.textViewExtraWeaponItem);


            TextView staminaValue = (TextView) v.findViewById(R.id.textViewStaminaValueWeaponItem);
            TextView strengthValue = (TextView) v.findViewById(R.id.textViewStrengthValueWeaponItem);
            TextView dexterityValue = (TextView) v.findViewById(R.id.textViewDexterityValueWeaponItem);
            TextView intelligenceValue = (TextView) v.findViewById(R.id.textViewIntelligenceValueWeaponItem);
            TextView damageValue = (TextView) v.findViewById(R.id.textViewDamageValueWeaponItem);
            TextView hitchanceValue = (TextView) v.findViewById(R.id.textViewHitchanceValueWeaponItem);
            TextView kritchanceValue = (TextView) v.findViewById(R.id.textViewKritchanceValueWeaponItem);
            TextView extraValue = (TextView) v.findViewById(R.id.textViewExtraValueWeaponItem);


            int[] weaponStats = weapon.getWeaponStats();

            staminaValue.setText(""+weaponStats[0]);
            strengthValue.setText(""+weaponStats[1]);
            dexterityValue.setText(""+weaponStats[2]);
            intelligenceValue.setText(""+weaponStats[3]);
            damageValue.setText(""+weapon.getWeaponDmg());
            hitchanceValue.setText(""+weapon.getWeaponHitrate());
            kritchanceValue.setText(""+weapon.getWeaponCritrate());
            extraValue.setText(""+weapon.getWeaponExtra());

        }

        return v;
    }
}
