package com.example.markus.locationbasedadventure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

            ImageView achievementImage = (ImageView) v.findViewById(R.id.imageViewAchievementItem);


            achievementImage.setImageResource(R.drawable.power_up);


            achievementImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    achievementListener.showAchievementInfo(achievementTyp);
                }
            });


        }

        return v;
    }


    public interface AchievementListener{
        public void showAchievementInfo(int achievementTyp);
    }
}
