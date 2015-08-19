package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Markus on 21.07.2015.
 */
public class GifActivity extends Activity {

    AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        ImageView animationVid = (ImageView) findViewById(R.id.imageViewAnimation);
        animation = (AnimationDrawable) animationVid.getDrawable();

        animationVid.post(new Runnable() {
            @Override
            public void run() {

               animation.start();
            }
        });

    }
}
