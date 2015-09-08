package com.example.markus.locationbasedadventure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.R;

import java.util.ArrayList;

/**
 * Created by Markus on 08.09.2015.
 */
public class ArmorListAdapter extends ArrayAdapter<Equip> {

    private ArrayList<Equip> armorItem;
    private Context context;

    public ArmorListAdapter(Context context, ArrayList<Equip> armorItem) {
        super(context, R.layout.armor_item, armorItem);

        this.context = context;
        this.armorItem = armorItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.armor_item, null);
        }

        Equip armor = armorItem.get(position);

        if (armor != null) {

            ImageView armorImage = (ImageView) v.findViewById(R.id.imageViewArmorItem);

            TextView stamina = (TextView) v.findViewById(R.id.textViewStaminaArmorItem);
            TextView strength = (TextView) v.findViewById(R.id.textViewStrengthArmorItem);
            TextView dexterity = (TextView) v.findViewById(R.id.textViewDexterityArmorItem);
            TextView intelligence = (TextView) v.findViewById(R.id.textViewIntelligenceArmorItem);


            TextView staminaValue = (TextView) v.findViewById(R.id.textViewStaminaValueArmorItem);
            TextView strengthValue = (TextView) v.findViewById(R.id.textViewStrengthValueArmorItem);
            TextView dexterityValue = (TextView) v.findViewById(R.id.textViewDexterityValueArmorItem);
            TextView intelligenceValue = (TextView) v.findViewById(R.id.textViewIntelligenceValueArmorItem);


            armorImage.setImageResource(R.drawable.power_up);

            int[] armorStats = armor.getArmorStats();
            staminaValue.setText(""+armorStats[0]);
            strengthValue.setText(""+armorStats[1]);
            dexterityValue.setText(""+armorStats[2]);
            intelligenceValue.setText(""+armorStats[3]);

        }

        return v;
    }
}
