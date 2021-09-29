package com.mpnogaj.kormoranadminsystem.models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Tournament {

    private final String _name, _repName;

    public Tournament(String name, String repName){
            _name = name;
            _repName = repName;
    }

    public static Tournament constructObject(JSONObject jsonObject){
        try{
            return new Tournament(
                jsonObject.getString("name"),
                jsonObject.getString("rep_name")
            );
        } catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return _name;
    }

    @NonNull
    @Override
    public String toString() {
        return _repName + " - " + _name;
    }
}
