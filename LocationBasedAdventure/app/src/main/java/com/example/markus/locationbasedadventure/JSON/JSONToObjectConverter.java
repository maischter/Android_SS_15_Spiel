package com.example.markus.locationbasedadventure.JSON;

import com.example.markus.locationbasedadventure.Items.AnmeldenItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Markus on 28.07.2015.
 */
public class JSONToObjectConverter {


    private String JSONResponse;
    private ArrayList<AnmeldenItem> data = new ArrayList<>();
    private static final String USERNR = "usernr";
    private static final String EMAIL = "email";
    private static final String PASSWORT = "passwort";

    public JSONToObjectConverter(String JSONResponse) {
        this.JSONResponse = JSONResponse;

    }

    public ArrayList<AnmeldenItem> convertJSONToArray() {
        try {

            JSONArray jsonArray = new JSONArray(JSONResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int usernr = jsonObject.getInt(USERNR);
                String email = jsonObject.getString(EMAIL);
                String passwort = jsonObject.getString(PASSWORT);
                data.add(new AnmeldenItem(usernr,email,passwort));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    //add public (and private) methods if required
}
