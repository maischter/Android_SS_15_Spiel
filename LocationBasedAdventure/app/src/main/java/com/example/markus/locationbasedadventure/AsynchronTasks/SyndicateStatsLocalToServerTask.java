package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.markus.locationbasedadventure.AnmeldenActivity;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.JSON.JSONParser;
import com.example.markus.locationbasedadventure.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Markus on 29.08.2015.
 */
public class SyndicateStatsLocalToServerTask extends AsyncTask<String, Integer, Integer> {


    private JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog dialog;
    CharacterdataDatabase db;

    public SyndicateStatsLocalToServerTask(AnmeldenActivity activity){
        dialog = new ProgressDialog(activity);
        db = new CharacterdataDatabase(activity);
    }


    public SyndicateStatsLocalToServerTask(MainActivity activity){
        dialog = new ProgressDialog(activity);
        db = new CharacterdataDatabase(activity);
    }



    @Override
    protected Integer doInBackground(String... params) {


        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("email", params[1]));
        param.add(new BasicNameValuePair("level", params[2]));
        param.add(new BasicNameValuePair("exp", params[3]));
        param.add(new BasicNameValuePair("stamina", params[4]));
        param.add(new BasicNameValuePair("strength", params[5]));
        param.add(new BasicNameValuePair("dexterity", params[6]));
        param.add(new BasicNameValuePair("intelligence", params[7]));


        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(params[0],
                "POST", param);

        // check log cat fro response
        Log.d("Create Response", json.toString());
        // check for success tag
        int success = 0;
        try {
            success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
            } else {
                // failed to create product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return success;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading.");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
