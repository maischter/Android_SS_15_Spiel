package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.markus.locationbasedadventure.CreateCharacterActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Markus on 03.08.2015.
 */
public class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;


    public BitmapWorkerTask(ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        return null;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView imageView = imageViewReference.get();
        imageView.setImageResource(data);

    }




}
