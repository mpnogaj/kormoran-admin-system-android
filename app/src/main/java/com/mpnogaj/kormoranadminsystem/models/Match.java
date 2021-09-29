package com.mpnogaj.kormoranadminsystem.models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Match {

    int _matchId;
    String _pointsTeam1, _pointsTeam2;
    String _state, _team1, _team2, _winner;

    Match(int matchId, String state, String team1, String team2, String winner, String pointsTeam1, String pointsTeam2){
        _matchId = matchId;
        _state = state;
        _team1 = team1;
        _team2 = team2;
        _winner = winner;
        _pointsTeam1 = pointsTeam1;
        _pointsTeam2 = pointsTeam2;
    }

    public static Match constructObject(JSONObject jsonObject){

        try {
            return new Match(
                    jsonObject.getInt("match_id"),
                    jsonObject.getString("state"),
                    jsonObject.getString("team_1"),
                    jsonObject.getString("team_2"),
                    jsonObject.getString("winner"),
                    jsonObject.getString("points_team_1"),
                    jsonObject.getString("points_team_2")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTeam1(){
        return _team1;
    }

    public String getTeam2(){
        return _team2;
    }

    public int getId(){
        return _matchId;
    }

    public String getTeam1Points(){
        return _pointsTeam1;
    }

    public String getTeam2Points(){
        return _pointsTeam2;
    }

    public String getState(){
        return _state;
    }

    @NonNull
    @Override
    public String toString() {
        String out = _team1 + " vs " + _team2;
        if(!_pointsTeam2.equals("null") && !_pointsTeam1.equals("null")){
            out += " - " + _pointsTeam1 + ":" + _pointsTeam2;
        }
        return out;
    }
}
