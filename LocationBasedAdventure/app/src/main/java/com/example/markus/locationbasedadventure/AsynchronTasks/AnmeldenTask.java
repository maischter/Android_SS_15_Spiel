package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.markus.locationbasedadventure.Items.AnmeldenItem;
import com.example.markus.locationbasedadventure.JSON.JSONParser;
import com.example.markus.locationbasedadventure.JSON.JSONToObjectConverter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Markus on 28.07.2015.
 */
public class AnmeldenTask extends AsyncTask<String, Integer, Integer> {

    private JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private AnmeldenTaskListener anmeldenTaskListener;
    private int diskurs = 0;
    private String passwort = "";

    public AnmeldenTask(AnmeldenTaskListener anmeldenTaskListener){
        this.anmeldenTaskListener = anmeldenTaskListener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        // Building Parameters
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("email", params[1]));
        param.add(new BasicNameValuePair("passwort", params[2]));


        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(params[0],
                "POST", param);

        // check log cat fro response
        Log.d("Create Response", json.toString());
        int usernr =0;
        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);
            usernr = json.getInt("usernr");
            if (usernr != 0) {
                passwort = json.getString("passwort");
            } else {
                // failed to create product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usernr;
    }


    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        anmeldenTaskListener.registrationDataRetrieved(result,passwort);

    }


    public interface AnmeldenTaskListener {
        public void registrationDataRetrieved(Integer data,String passworthash);
    }
}
