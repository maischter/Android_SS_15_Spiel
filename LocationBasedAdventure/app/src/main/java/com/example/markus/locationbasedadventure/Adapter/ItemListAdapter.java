package com.example.markus.locationbasedadventure.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Items.Equip;
import com.example.markus.locationbasedadventure.Items.Item;
import com.example.markus.locationbasedadventure.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Markus on 16.09.2015.
 */
public class ItemListAdapter extends ArrayAdapter<Item> {


    private ArrayList<Item> itemItem;
    private Context context;
    private ItemListener itemListener;
    private ImageView itemImage;

    public ItemListAdapter(Context context, ArrayList<Item> itemItem, ItemListener itemListener) {
        super(context, R.layout.item_item, itemItem);

        this.context = context;
        this.itemItem = itemItem;
        this.itemListener = itemListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_item, null);
        }


        Item item = itemItem.get(position);


        if (item != null) {

            itemImage = (ImageView) v.findViewById(R.id.imageViewInventarItem);
            TextView itemString = (TextView) v.findViewById(R.id.textViewInventarItem);


            selectImage(item.getItemTyp());

            final int itemTyp =item.getItemTyp();

            itemString.setText("" + item.getItemQuantity() + "x " + selectItemTypString(itemTyp));


            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.showItemInfo(itemTyp);
                }
            });

        }

        return v;
    }

    private void selectImage(int itemTyp){
        switch (itemTyp) {
            case 1:loadBitmap(R.drawable.power_up,itemImage);break;
            case 2:loadBitmap(R.drawable.power_up,itemImage);break;
            case 3: loadBitmap(R.drawable.power_up,itemImage);break;
            case 4: loadBitmap(R.drawable.power_up,itemImage);break;
            case 5: loadBitmap(R.drawable.power_up,itemImage);break;
            case 6: loadBitmap(R.drawable.power_up,itemImage);break;
        }
    }


    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }

    private String selectItemTypString(int itemTyp) {
        switch (itemTyp) {
            case 1:
                return "Trank";
            case 2:
                return "Gegengift";

            case 3: return "StaminaUP";
            case 4: return "StrengthUP";
            case 5: return "DexterityUP";
            case 6: return "IntelligenceUP";
            default:return "Leer";
        }

    }


    public interface ItemListener{
        public void showItemInfo(int itemTyp);
    }

}
