package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.markus.locationbasedadventure.CreateCharacterActivity;
import com.example.markus.locationbasedadventure.Items.RankingItem;
import com.example.markus.locationbasedadventure.JSON.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Markus on 29.07.2015.
 */
public class CreateCharacterTask extends AsyncTask<String,Integer,Integer>{

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_WEAPONNR = "weaponnr";
    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_HITCHANCE = "hitchance";
    private static final String TAG_KRITCHANCE = "kritchance";
    private static final String TAG_EXTRA = "extra";
    private static final String TAG_STAMINA = "stamina";
    private static final String TAG_STRENGTH = "strength";
    private static final String TAG_DEXTERITY = "dexterity";
    private static final String TAG_INTELLIGENCE ="intelligence";



    private static final int[] weaponArray = new int[9];
    private CreateCharakterTaskListener charakterTaskListener;
    private ProgressDialog dialog;

    public CreateCharacterTask(CreateCharacterActivity activity, CreateCharakterTaskListener charakterTaskListener){
            this.charakterTaskListener = charakterTaskListener;
            dialog = new ProgressDialog(activity);
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
            JSONArray jsonArray = (JSONArray) json.get("waffen");

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                weaponArray[0] = jsonObject.getInt(TAG_WEAPONNR);
                weaponArray[1] = jsonObject.getInt(TAG_DAMAGE);
                weaponArray[2] = jsonObject.getInt(TAG_HITCHANCE);
                weaponArray[3] = jsonObject.getInt(TAG_KRITCHANCE);
                weaponArray[4] = jsonObject.getInt(TAG_EXTRA);
                weaponArray[5] = jsonObject.getInt(TAG_STAMINA);
                weaponArray[6] = jsonObject.getInt(TAG_STRENGTH);
                weaponArray[7] = jsonObject.getInt(TAG_DEXTERITY);
                weaponArray[8] = jsonObject.getInt(TAG_INTELLIGENCE);



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
        charakterTaskListener.weaponDataRetrieved(weaponArray);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading.");
        dialog.setCancelable(false);
        dialog.show();
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
            case "Einhandschwert mit Schild":
                return 3;
            case "Einhandaxt mit Schild":
                return 4;
        }
        return 0;
    }


    public interface CreateCharakterTaskListener {
        public void weaponDataRetrieved(int[] weaponArray);
    }

}



