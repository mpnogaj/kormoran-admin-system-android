package com.mpnogaj.kormoranadminsystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mpnogaj.kormoranadminsystem.App;
import com.mpnogaj.kormoranadminsystem.R;
import com.mpnogaj.kormoranadminsystem.helpers.Endpoints;
import com.mpnogaj.kormoranadminsystem.helpers.Requester;
import com.mpnogaj.kormoranadminsystem.models.Tournament;
import com.mpnogaj.kormoranadminsystem.models.adapters.TournamentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TournamentsActivity extends AppCompatActivity {

    private List<Tournament> _tournaments;
    private SwipeRefreshLayout _swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);
        _swipeRefreshLayout = this.findViewById(R.id.tournamentsActivityRefresh);
        _swipeRefreshLayout.setOnRefreshListener(
                // on refresh
                this::updateList
        );
        updateList();
    }

    private void updateList(){
        _tournaments = new ArrayList<>();
        Requester.getInstance().sendGETRequest(Endpoints.TOURNAMENTS, response -> {
            _swipeRefreshLayout.setRefreshing(false);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = (JSONArray) object.get("tournaments");
                for(int i = 0; i < array.length(); i++){
                    JSONObject tournamentJSON = array.getJSONObject(i);
                    Tournament tournament = Tournament.constructObject(tournamentJSON);
                    if(tournament != null) {
                        _tournaments.add(tournament);
                    }
                }
                ListView listView = findViewById(R.id.tournamentsActivityList);
                listView.setAdapter(
                        new TournamentAdapter(
                                this,
                                android.R.layout.simple_list_item_1,
                                _tournaments)
                );

                listView.setOnItemClickListener((adapterView, view, position, l) -> {
                    Tournament tappedTournament = _tournaments.get(position);
                    Intent intent = new Intent(App.getAppContext(), MatchesActivity.class);
                    intent.putExtra("tournamentName", tappedTournament.getName());
                    intent.putExtra("tournamentReadableName", tappedTournament.getReadableName());
                    startActivity(intent);
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            _swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(
                    App.getAppContext(),
                    "Nie udało się pobrać listy turniejów",
                    Toast.LENGTH_LONG).show();
        });
    }
}