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
 * Created by Markus on 29.07.2015.
 */
public class CreateCharacterTask extends AsyncTask<String,Integer,Integer>{

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";


    public CreateCharacterTask(){

    }

    @Override
    protected Integer doInBackground(String... params) {
        int waponTyp = diffBetweenWeapons(params[3]);



        // Building Parameters
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("usernr", params[4]));
        param.add(new BasicNameValuePair("charactername", params[1]));
        param.add(new BasicNameValuePair("sex", params[2]));
        param.add(new BasicNameValuePair("usedweapon", ""+waponTyp));


        // sending modified data through http request
        // Notice that update product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(params[0],
                "POST", param);

        Log.d("Create Response", json.toString());

        // check json success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully updated
                return success;
            } else {
                // failed to update product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
    }



    public int diffBetweenWeapons(String weapon){
        switch(weapon){
            case ("Bogen"):
                return 8;
            case "Einhandschwert":
                return 1;
            case "Einhandaxt":
                return 2;
            case "Gewehr":
                return 9;
            case "Zauberstab":
                return 7;
            case "Zwei-Hand-Axt":
                return 6;
            case "Zwei-Hand-Schwert":
                return 5;
        }
        return 0;
    }

}



