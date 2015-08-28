package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.markus.locationbasedadventure.CreateCharacterActivity;

import java.lang.ref.WeakReference;

public class LoadingBattleTask extends AsyncTask<Integer,Void,Bitmap> {
    private final WeakReference<ImageButton> imageButtonReference;
    private int data = 0;

public LoadingBattleTask(ImageButton imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageButtonReference = new WeakReference<ImageButton>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageButton imageButton = imageButtonReference.get();
        imageButton.setImageResource(data);

    }




}