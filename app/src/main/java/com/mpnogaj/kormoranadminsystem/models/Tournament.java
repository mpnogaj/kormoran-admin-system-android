package com.mpnogaj.kormoranadminsystem.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Tournament {

    private final String _name, _game, _state, _repName, _tournamentType;
    private final int _id;

    public Tournament(String name, String game, String state, int id, String repName, String tournamentType){
            _name = name;
            _game = game;
            _state = state;
            _id = id;
            _repName = repName;
            _tournamentType = tournamentType;
    }

    public static Tournament constructObject(JSONObject jsonObject){
        try{
            return new Tournament(
                jsonObject.getString("name"),
                jsonObject.getString("game"),
                jsonObject.getString("state"),
                jsonObject.getInt("id"),
                jsonObject.getString("rep_name"),
                jsonObject.getString("tournament_type")
            );
        } catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return _name;
    }

    public String getGame() {
        return _game;
    }

    public String getState() {
        return _state;
    }

    public String getRepName() {
        return _repName;
    }

    public String getTournamentType() {
        return _tournamentType;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString() {
        return _repName + " - " + _name;
    }
}
