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


    @NonNull
    @Override
    public String toString() {
        String out = _team1 + " vs " + _team2;
        if(_pointsTeam2 != null && _pointsTeam1 != null){
            out += " - " + _pointsTeam1 + ":" + _pointsTeam2;
        }
        return out;
    }
}
