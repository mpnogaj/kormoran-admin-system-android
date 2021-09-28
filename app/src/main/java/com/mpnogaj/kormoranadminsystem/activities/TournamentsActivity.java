package com.mpnogaj.kormoranadminsystem.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.mpnogaj.kormoranadminsystem.App;
import com.mpnogaj.kormoranadminsystem.R;
import com.mpnogaj.kormoranadminsystem.helpers.Endpoints;
import com.mpnogaj.kormoranadminsystem.helpers.Requester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TournamentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);

        Requester.getInstance().sendDataRequest(Request.Method.GET, Endpoints.TOURNAMENTS,null, response -> {
            try {
                JSONArray jsonObject = new JSONArray(new JSONObject(response).getJSONArray("tournaments"));
                //JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("tournaments"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(App.getAppContext(), "Nie udało się pobrać listy turniejów", Toast.LENGTH_LONG).show();
        });
    }
}