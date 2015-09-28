package com.example.markus.locationbasedadventure.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.R;

import java.util.ArrayList;

/**
 * Created by Markus on 08.09.2015.
 */
public class ArmorListAdapter extends ArrayAdapter<Equip> {

    private ArrayList<Equip> armorItem;
    private Context context;
    private ArmorListener armorListener;
    private ImageView armorImage;

    public ArmorListAdapter(Context context, ArrayList<Equip> armorItem,ArmorListener armorListener) {
        super(context, R.layout.armor_item, armorItem);

        this.context = context;
        this.armorItem = armorItem;
        this.armorListener = armorListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final int aposition = position;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.armor_item, null);
        }

        Equip armor = armorItem.get(position);

        if (armor != null) {

            TableLayout tableLayoutArmor = (TableLayout) v.findViewById(R.id.tableLayoutArmorItem);

            TextView armorText = (TextView) v.findViewById(R.id.textViewArmorItemText);

            armorImage = (ImageView) v.findViewById(R.id.imageViewArmorItem);

            TextView stamina = (TextView) v.findViewById(R.id.textViewStaminaArmorItem);
            TextView strength = (TextView) v.findViewById(R.id.textViewStrengthArmorItem);
            TextView dexterity = (TextView) v.findViewById(R.id.textViewDexterityArmorItem);
            TextView intelligence = (TextView) v.findViewById(R.id.textViewIntelligenceArmorItem);


            TextView staminaValue = (TextView) v.findViewById(R.id.textViewStaminaValueArmorItem);
            TextView strengthValue = (TextView) v.findViewById(R.id.textViewStrengthValueArmorItem);
            TextView dexterityValue = (TextView) v.findViewById(R.id.textViewDexterityValueArmorItem);
            TextView intelligenceValue = (TextView) v.findViewById(R.id.textViewIntelligenceValueArmorItem);


            Button deleteArmor = (Button) v.findViewById(R.id.buttonArmorDelete);
            Button changeArmor = (Button) v.findViewById(R.id.buttonChangeArmor);



            deleteArmor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set title
                    //alertDialogBuilder.setTitle("Your Title");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Möchtest du diese Rüstung tatsächlich entfernen?")
                            .setCancelable(false)
                            .setPositiveButton("Ja",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    armorListener.deleteArmor(aposition);
                                }
                            })
                            .setNegativeButton("Nein, Rüstung behalten",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            });

            changeArmor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set title
                    //alertDialogBuilder.setTitle("Your Title");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Möchtest du diese Rüstung benutzen?")
                            .setCancelable(false)
                            .setPositiveButton("Ja",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                   armorListener.changeArmor(aposition);
                                }
                            })
                            .setNegativeButton("Nein",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            });


            tableLayoutArmor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    armorListener.showDiff(aposition);
                }
            });


            selectImage(armor.getArmorTyp());


            armorText.setText(selectArmorTyp(armor.getArmorTyp()));
            int[] armorStats = armor.getArmorStats();
            staminaValue.setText(""+armorStats[0]);
            strengthValue.setText(""+armorStats[1]);
            dexterityValue.setText(""+armorStats[2]);
            intelligenceValue.setText(""+armorStats[3]);

        }

        return v;
    }

    //loads Image by calling loadBitmap
    //gets int armorTyp to diff

    private void selectImage(int armorTyp){
        switch(armorTyp){
            case 1: loadBitmap(R.drawable.power_up,armorImage);break;
            case 2: loadBitmap(R.drawable.power_up,armorImage);break;
        }
    }


    //selects Armor typ
    //gets int armorTyp
    //returns String

    private String selectArmorTyp(int armorTyp) {
        switch(armorTyp){
            case 1: return "Standardrüstung";
            case 2: return "Verstärkte Rüstung";
        }
        return "Leer";
    }

    //loads Bitmap into ImageView by Using backgroundTask

    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }



    public interface ArmorListener{
        public void deleteArmor(int position);
        public void changeArmor(int position);
        public void showDiff(int position);
    }
}
