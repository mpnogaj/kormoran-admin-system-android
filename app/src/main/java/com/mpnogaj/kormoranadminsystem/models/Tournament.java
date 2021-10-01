package com.mpnogaj.kormoranadminsystem.models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Tournament {

    private final String _name, _repName, _state;

    public Tournament(String name, String repName, String state){
        _name = name;
        _repName = repName;
        _state = state;
    }

    public static Tournament constructObject(JSONObject jsonObject){
        try{
            return new Tournament(
                jsonObject.getString("name"),
                jsonObject.getString("rep_name"),
                jsonObject.getString("state")
            );
        } catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return _name;
    }

    public String getReadableName(){
        return _repName;
    }

    public String getState(){
        return _state;
    }

    @NonNull
    @Override
    public String toString() {
        return _repName;
    }
}
