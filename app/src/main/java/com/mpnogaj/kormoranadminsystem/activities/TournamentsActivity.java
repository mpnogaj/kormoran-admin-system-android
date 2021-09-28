package com.mpnogaj.kormoranadminsystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.mpnogaj.kormoranadminsystem.App;
import com.mpnogaj.kormoranadminsystem.R;
import com.mpnogaj.kormoranadminsystem.helpers.Endpoints;
import com.mpnogaj.kormoranadminsystem.helpers.Requester;
import com.mpnogaj.kormoranadminsystem.models.Tournament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TournamentsActivity extends AppCompatActivity {

    List<Tournament> _tournaments;
    List<JSONObject> _jsonTournaments;
    List<String> _tournamentsStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);

        _tournaments = new ArrayList<>();
        _jsonTournaments = new ArrayList<>();
        _tournamentsStrings = new ArrayList<>();
        Requester.getInstance().sendDataRequest(Request.Method.GET, Endpoints.TOURNAMENTS,null, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = (JSONArray) object.get("tournaments");
                for(int i = 0; i < array.length(); i++){
                    JSONObject tournamentJSON = array.getJSONObject(i);
                    Tournament tournament = Tournament.constructObject(tournamentJSON);
                    if(tournament != null) {
                        _tournaments.add(tournament);
                        _jsonTournaments.add(tournamentJSON);
                        _tournamentsStrings.add(tournament.toString());
                    }
                }
                ListView listView = findViewById(R.id.tournamentsActivityList);
                listView.setAdapter(
                        new ArrayAdapter<String>(
                                this,
                                android.R.layout.simple_list_item_1,
                                _tournamentsStrings));

                listView.setOnItemClickListener((adapterView, view, position, l) -> {
                    Tournament tappedTournament = _tournaments.get(position);
                    Intent intent = new Intent(App.getAppContext(), MatchesActivity.class);
                    intent.putExtra("tournamentName", tappedTournament.getName());
                    startActivity(intent);
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(App.getAppContext(), "Nie udało się pobrać listy turniejów", Toast.LENGTH_LONG).show();
        });
    }
}