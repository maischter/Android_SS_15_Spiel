package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Markus on 21.07.2015.
 */
public class GifActivity extends Activity {

    AnimationDrawable animation;
    int activity;
   Button buttonWeiter;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        initButtons();
        getValueFromIntent();

        diffButtons(activity);
        buttonListeners();


        ImageView animationVid = (ImageView) findViewById(R.id.imageViewAnimation);
        animation = (AnimationDrawable) animationVid.getDrawable();
        animationVid.post(new Runnable() {
            @Override
            public void run() {

                animation.start();


            }
        });


    }


    private void buttonListeners() {
        buttonWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j = new Intent(getApplicationContext(), MenueActivity.class); //eigentlich MapActivity
                startActivity(j);
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void getValueFromIntent() {
        Intent i = getIntent();
        activity  = i.getIntExtra("activity", 1);
    }

    private void initButtons() {
        buttonWeiter = (Button) findViewById(R.id.buttonWeiter);
        buttonBack = (Button) findViewById(R.id.buttonBack);
    }

    // Unterscheidet von welchen Activity man kommt & zeigt entsprechend einen Button an.


    private void diffButtons(int activity) {
        switch (activity) {

            //kommt von EinstellungenActivity

            case 1:
                buttonWeiter.setClickable(false);
                buttonWeiter.setVisibility(View.INVISIBLE);

                buttonBack.setClickable(true);
                buttonBack.setVisibility(View.VISIBLE);
                break;


            // kommt von CreateCharacterActitivty
            case 2:

                buttonWeiter.setClickable(true);
                buttonWeiter.setVisibility(View.VISIBLE);

                buttonBack.setClickable(false);
                buttonBack.setVisibility(View.INVISIBLE);
                break;
        }

    }
}
