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
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.R;

import java.util.ArrayList;

/**
 * Created by Markus on 08.09.2015.
 */
public class WeaponListAdapter extends ArrayAdapter<Equip> {

    private ArrayList<Equip> weaponItem;
    private Context context;
    private WeaponListener weaponListener;
    private ImageView weaponImage;

    public WeaponListAdapter(Context context, ArrayList<Equip> weaponItem, WeaponListener weaponListener) {
        super(context, R.layout.weapon_item, weaponItem);

        this.context = context;
        this.weaponItem = weaponItem;
        this.weaponListener = weaponListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final int aposition = position;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.weapon_item, null);
        }

        Equip weapon = weaponItem.get(position);

        if (weapon != null) {


            TableLayout tableLayoutWeapon = (TableLayout) v.findViewById(R.id.tableLayoutWeaponItem);

            TextView weaponText = (TextView) v.findViewById(R.id.textViewWeaponItemText);

            weaponImage = (ImageView) v.findViewById(R.id.imageViewWeaponItem);

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

            Button deleteWeapon = (Button) v.findViewById(R.id.buttonWeaponDelete);
            Button changeWeapon = (Button) v.findViewById(R.id.buttonChangeWeapon);

            deleteWeapon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set title
                      //  alertDialogBuilder.setTitle("Your Title");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Möchtest du diese Waffe tatsächlich entfernen?")
                                .setCancelable(false)
                                .setPositiveButton("Ja",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        weaponListener.deleteWeapon(aposition);
                                    }
                                })
                                .setNegativeButton("Nein, Waffe behalten",new DialogInterface.OnClickListener() {
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



            changeWeapon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set title
                 //  alertDialogBuilder.setTitle("Your Title");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Möchtest du diese Waffe als Primärwaffe benutzen?")
                            .setCancelable(false)
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    weaponListener.changeWeapon(aposition);
                                }
                            })
                            .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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

            tableLayoutWeapon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weaponListener.showDiff(aposition);
                }
            });


            selectImage(weapon.getWeaponTyp());

            int[] weaponStats = weapon.getWeaponStats();


            weaponText.setText(selectWeaponTypString(weapon.getWeaponTyp()));
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

    private void selectImage(int weaponTyp) {

        switch (weaponTyp) {
            //Bogen
            case 8:
                loadBitmap(R.drawable.bogenweiblich, weaponImage);
                break;
            //Einhandschwert
            case 1:
                loadBitmap(R.drawable.einhandschwertweiblich, weaponImage);
                break;
            //EinhandschwertMitSchild
            case 3:
                loadBitmap(R.drawable.einhandschwertschildweiblich, weaponImage);
                break;
            //Einhandaxt
            case 2:
                loadBitmap(R.drawable.einhandaxtweiblich, weaponImage);
                break;
            //EinhandaxtMitSchild
            case 4:
                loadBitmap(R.drawable.einhandaxtschildweiblich, weaponImage);
                break;
            //Gewehr
            case 9:
                loadBitmap(R.drawable.gewehrweiblich, weaponImage);
                break;
            //Zauberstab
            case 7:
                loadBitmap(R.drawable.zauberstabweiblich, weaponImage);
                break;
            //Zweihandaxt
            case 6:
                loadBitmap(R.drawable.zweihandaxtweiblich, weaponImage);
                break;
            //Zweihandschwert
            case 5:
                loadBitmap(R.drawable.zweihandschwertweiblich, weaponImage);
                break;
        }

    }

    //loads Bitmap into ImageView by Using backgroundTask

    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }


    private String selectWeaponTypString(int weaponTyp) {
        switch (weaponTyp) {
            case 8:
                return "Bogen";
            case 1:
                return "Einhandschwert";
            case 3:
                return "Einhandschwert mit Schild";
            case 2:
                return "Einhandaxt";
            case 4:
                return "Einhandaxt mit Schild";
            case 9:
                return "Gewehr";
            case 7:
                return "Zauberstab";
            case 6:
                return "Zweihandaxt";
            case 5:
                return "Zweihandschwert";

        }
        return null;

    }

    public interface WeaponListener{
        public void deleteWeapon(int position);
        public void changeWeapon(int position);
        public void showDiff(int position);
    }


}
