package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.markus.locationbasedadventure.AnmeldenActivity;
import com.example.markus.locationbasedadventure.JSON.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Markus on 28.07.2015.
 */
public class AnmeldenTask extends AsyncTask<String, Integer, Integer> {

    private JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private AnmeldenTaskListener anmeldenTaskListener;
    private String passwort = "";
    private ProgressDialog dialog;

    public AnmeldenTask(AnmeldenActivity activity, AnmeldenTaskListener anmeldenTaskListener){
        this.anmeldenTaskListener = anmeldenTaskListener;
        dialog = new ProgressDialog(activity);
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
        anmeldenTaskListener.registrationDataRetrieved(result,passwort);
    }


    public interface AnmeldenTaskListener {
        public void registrationDataRetrieved(Integer data,String passworthash);
    }
}
