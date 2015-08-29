package com.example.markus.locationbasedadventure.AsynchronTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.markus.locationbasedadventure.Items.AnmeldenItem;
import com.example.markus.locationbasedadventure.Items.RankingItem;
import com.example.markus.locationbasedadventure.JSON.JSONParser;
import com.example.markus.locationbasedadventure.JSON.JSONToObjectConverter;
import com.example.markus.locationbasedadventure.RankingActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Markus on 27.08.2015.
 */
public class LoadRankingTask extends AsyncTask<String,Integer,Integer> {



    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERNR = "usernr";
    private static final String TAG_EXP = "exp";
    private static final String TAG_LEVEL = "level";
    private static final String TAG_RANK = "rank";
    private static final String TAG_CHARACTERNAME = "charactername";
    private ArrayList<RankingItem> data = new ArrayList<>();
    private ArrayList<RankingItem> rankingData = new ArrayList<>();

    private RankingTaskListener rankingTaskListener;
    private JSONParser jsonParser = new JSONParser();
    private ProgressDialog dialog;



    public LoadRankingTask(RankingActivity activity,RankingTaskListener rankingTaskListener){
        this.rankingTaskListener = rankingTaskListener;
        dialog = new ProgressDialog(activity);
    }


    @Override
    protected Integer doInBackground(String... params) {
        data.clear();
        int persusernr = 0;

        // Building Parameters
        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("email", params[1]));


        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(params[0],
                "POST", param);




        // check log cat fro response
        Log.d("Create Response", json.toString());


        // check for success tag
        try {
           JSONArray jsonArray = (JSONArray) json.get("rank");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int usernr = jsonObject.getInt(TAG_USERNR);
                int level = jsonObject.getInt(TAG_LEVEL);
                int exp = jsonObject.getInt(TAG_EXP);
                int rank = jsonObject.getInt(TAG_RANK);
                String charactername = jsonObject.getString(TAG_CHARACTERNAME);
                data.add(new RankingItem(usernr,level,exp,rank,charactername));
            }
            int success = json.getInt(TAG_SUCCESS);

            if(success == 1){
                persusernr = json.getInt(TAG_USERNR);

            } else {
                // failed to create product
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getRankingData(data,persusernr);

        return persusernr;
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
        rankingTaskListener.rankingDataRetrieved(rankingData,result);

    }

    public void getRankingData(ArrayList<RankingItem> arrayList,int persusernr){
        int persRank = 0;
        for(int i=0;i<arrayList.size();i++){
            if(persusernr == arrayList.get(i).getUsernr()){
                persRank = arrayList.get(i).getRank();
            }
        }
        rankingData.clear();
        if(persRank>3){
            rankingData.add(arrayList.get(0));
            rankingData.add(arrayList.get(1));
            rankingData.add(arrayList.get(2));
            rankingData.add(arrayList.get(persRank-2));
            rankingData.add(arrayList.get(persRank-1));
            System.out.println(arrayList);
            if(persRank != arrayList.size()){
                rankingData.add(arrayList.get(persRank));
            }

            System.out.println(rankingData.get(0).getCharactername());
        }else{
            for(int i=0;i<6;i++){
                rankingData.add(arrayList.get(0));
            }
        }
    }


    public interface RankingTaskListener {
        public void rankingDataRetrieved(ArrayList<RankingItem> rankingList,int persusernr);
    }
}
