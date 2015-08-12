package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.os.AsyncTask;
import android.util.Log;


import com.example.markus.locationbasedadventure.JSON.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Markus on 21.07.2015.
 */
public class RegistrierenTask extends AsyncTask<String,Integer,Integer> {

    private JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private int email;

    RegistrierenTaskListener registrierenTaskListener;

    public RegistrierenTask(RegistrierenTaskListener registrierenTaskListener){
        this.registrierenTaskListener = registrierenTaskListener;
    }


   /* @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(NewProductActivity.this);
        pDialog.setMessage("Creating Product..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }*/

    @Override
    protected Integer doInBackground(String... params) {

        // Building Parameters
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("email", params[0]));
        param.add(new BasicNameValuePair("passwort", params[1]));


        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(params[2],
                "POST", param);

        // check log cat fro response
        Log.d("Create Response", json.toString());
        int usernr =0;
        email = 1;
        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if(success == 2){
                email = json.getInt("email");
            }
            if (success == 1) {
                usernr = json.getInt("usernr");
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
        registrierenTaskListener.UserNrRetrieved("" + result, email);


    }


    public interface RegistrierenTaskListener {
        public void UserNrRetrieved(String data,int registered);
    }

}
