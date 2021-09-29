package com.mpnogaj.kormoranadminsystem.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mpnogaj.kormoranadminsystem.App;
import com.mpnogaj.kormoranadminsystem.R;
import com.mpnogaj.kormoranadminsystem.helpers.Endpoints;
import com.mpnogaj.kormoranadminsystem.helpers.Requester;
import com.mpnogaj.kormoranadminsystem.helpers.Storage;
import com.mpnogaj.kormoranadminsystem.models.Match;
import com.mpnogaj.kormoranadminsystem.models.adapters.MatchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    private String _tournamentName;

    List<Match> _matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        Intent intent = getIntent();
        _tournamentName = intent.getStringExtra("tournamentName");
        //getActionBar().setTitle(_tournamentName);

        updateList();
    }

    void showDialog(Match match){
        final Dialog dialog = new Dialog(MatchesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_set_score);

        final EditText team1ScoreBox = dialog.findViewById(R.id.setScoreDialogTeam1Box);
        final EditText team2ScoreBox = dialog.findViewById(R.id.setScoreDialogTeam2Box);

        if(!match.getTeam1Points().equals("null")){
            team1ScoreBox.setText(match.getTeam1Points());
        }
        if(!match.getTeam2Points().equals("null")){
            team2ScoreBox.setText(match.getTeam2Points());
        }

        final Button cancelBtn = dialog.findViewById(R.id.setScoreDialogCancelBtn);
        final Button setBtn = dialog.findViewById(R.id.setScoreDialogSetBtn);

        setBtn.setOnClickListener(view -> {
            final String team1ScoreText, team2ScoreText;
            final int team1Score, team2Score;
            team1ScoreText = team1ScoreBox.getText().toString();
            team2ScoreText = team2ScoreBox.getText().toString();
            team1Score = Integer.parseInt(team1ScoreText.equals("") ? "0" : team1ScoreText);
            team2Score = Integer.parseInt(team2ScoreText.equals("") ? "0" : team2ScoreText);
            final String winner;
            if (team1Score > team2Score){
                winner = match.getTeam1();
            } else if (team2Score > team1Score){
                winner = match.getTeam2();
            } else {
                winner = "Ex aequo";
            }

            boolean isBothNull = team2Score == 0 && team1Score == 0;
            JSONObject requestBody = new JSONObject();
            try {
                requestBody.put("username", Storage.getInstance().getUsername());
                requestBody.put("password", Storage.getInstance().getPassword());
                requestBody.put("tournament", _tournamentName);
                requestBody.put("id", match.getId());
                requestBody.put("state",
                        isBothNull
                            ? "ready-to-play"
                            : "finished");
                requestBody.put("points_team_1",
                        isBothNull
                            ? JSONObject.NULL
                            : team1Score);
                requestBody.put("points_team_2",
                        isBothNull
                            ? JSONObject.NULL
                            : team2Score);
                requestBody.put("winner",
                        isBothNull
                            ? JSONObject.NULL
                            : winner);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            Requester.getInstance().sendPOSTRequest(Endpoints.MATCHES, requestBody, response -> {
                Toast.makeText(dialog.getContext(), "Wynik ustawiony", Toast.LENGTH_LONG).show();
                updateList();
            }, error ->
                    Toast.makeText(dialog.getContext(), "Coś poszło nie tak!", Toast.LENGTH_LONG).show());

            dialog.dismiss();
        });

        cancelBtn.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    void updateList(){
        _matches = new ArrayList<>();

        Requester.getInstance().sendGETRequest(Endpoints.MATCHES + "?tournament=" + _tournamentName, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = (JSONArray) object.get("matches");
                for(int i = 0; i < array.length(); i++){
                    JSONObject matchJSON = array.getJSONObject(i);
                    Match match = Match.constructObject(matchJSON);
                    if(match != null) {
                        _matches.add(match);
                    }
                }
                ListView listView = findViewById(R.id.matchesActivityListView);
                listView.setAdapter(
                        new MatchAdapter(
                                this,
                                android.R.layout.simple_list_item_1,
                                _matches));
                listView.setOnItemClickListener((adapterView, view, position, l) ->
                        showDialog(_matches.get(position)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(
                App.getAppContext(),
                "Nie udało się pobrać listy turniejów",
                Toast.LENGTH_LONG).show());
    }
}