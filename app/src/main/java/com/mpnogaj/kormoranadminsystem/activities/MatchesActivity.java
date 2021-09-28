package com.mpnogaj.kormoranadminsystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.mpnogaj.kormoranadminsystem.App;
import com.mpnogaj.kormoranadminsystem.R;
import com.mpnogaj.kormoranadminsystem.helpers.Endpoints;
import com.mpnogaj.kormoranadminsystem.helpers.Requester;
import com.mpnogaj.kormoranadminsystem.models.Match;
import com.mpnogaj.kormoranadminsystem.models.Tournament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    private String _tournamentName;

    List<Match> _matches;
    List<String> _matchesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        Intent intent = getIntent();
        _tournamentName = intent.getStringExtra("tournamentName");
        _matches = new ArrayList<>();
        _matchesString = new ArrayList<>();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("tournament", _tournamentName);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        Requester.getInstance().sendRequest(Request.Method.GET, Endpoints.MATCHES + "?tournament=" + _tournamentName, null, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = (JSONArray) object.get("matches");
                for(int i = 0; i < array.length(); i++){
                    JSONObject matchJSON = array.getJSONObject(i);
                    Match match = Match.constructObject(matchJSON);
                    if(match != null) {
                        _matches.add(match);
                        _matchesString.add(match.toString());
                    }
                }
                ListView listView = findViewById(R.id.matchesActivityListView);
                listView.setAdapter(
                        new ArrayAdapter<String>(
                                this,
                                android.R.layout.simple_list_item_1,
                                _matchesString));

                listView.setOnItemClickListener((adapterView, view, position, l) -> {

                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(App.getAppContext(), "Nie udało się pobrać listy turniejów", Toast.LENGTH_LONG).show();
        });
    }
}