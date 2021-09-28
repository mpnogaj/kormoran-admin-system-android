package com.mpnogaj.kormoranadminsystem.helpers;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class Storage {

    private String _username;
    private String _password;

    private Storage(){}

    @Nullable
    private static Storage _instance;
    public static Storage getInstance(){
        if(_instance == null){
            _instance = new Storage();
        }
        return _instance;
    }

    public JSONObject getLoginData(){
        JSONObject out = new JSONObject();
        try {
            out.put("username", _username);
            out.put("password", _password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String getUsername(){
        return _username;
    }

    public String getPassword(){
        return _password;
    }

    public void setLoginData(String username, String password){
        _username = username;
        _password = password;
    }
}
