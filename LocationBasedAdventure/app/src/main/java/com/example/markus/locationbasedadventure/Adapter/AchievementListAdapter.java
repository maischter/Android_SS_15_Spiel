package com.example.markus.locationbasedadventure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.BitmapWorkerTask;
import com.example.markus.locationbasedadventure.Items.Achievement;
import com.example.markus.locationbasedadventure.Items.Item;
import com.example.markus.locationbasedadventure.R;

import java.util.ArrayList;

/**
 * Created by Markus on 16.09.2015.
 */
public class AchievementListAdapter extends ArrayAdapter<Achievement> {

    private ArrayList<Achievement> achievementItem;
    private Context context;
    private AchievementListener achievementListener;
    private ImageView achievementImage;

    public AchievementListAdapter(Context context, ArrayList<Achievement> achievementItem, AchievementListener achievementListener) {
        super(context, R.layout.achievement_item, achievementItem);

        this.context = context;
        this.achievementItem = achievementItem;
        this.achievementListener = achievementListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.achievement_item, null);
        }


        Achievement achievement = achievementItem.get(position);

        final int achievementTyp = achievement.getAchievementTyp();


        if (achievement != null) {

            achievementImage = (ImageView) v.findViewById(R.id.imageViewAchievementItem);


            selectImage(achievementTyp);


            achievementImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    achievementListener.showAchievementInfo(achievementTyp);
                }
            });


        }

        return v;
    }

    private void selectImage(int achievementTyp) {
        switch (achievementTyp) {
            case 1:
                loadBitmap(R.drawable.achievement_level_4, achievementImage);
                break;
            case 2:
                loadBitmap(R.drawable.achievement_level_8, achievementImage);
                break;
            case 3:
                loadBitmap(R.drawable.achievement_level_12, achievementImage);
                break;
            case 4:
                loadBitmap(R.drawable.achievement_5_wins, achievementImage);
                break;
            case 5:
                loadBitmap(R.drawable.achievement_50_wins, achievementImage);
                break;
            case 6:
                loadBitmap(R.drawable.achievement_250_wins, achievementImage);
                break;
        }
    }

    //loads Bitmap into ImageView by Using backgroundTask

    private void loadBitmap(int resID , ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resID);
    }


    public interface AchievementListener{
        public void showAchievementInfo(int achievementTyp);
    }
}
