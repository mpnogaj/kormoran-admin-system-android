package com.mpnogaj.kormoranadminsystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mpnogaj.kormoranadminsystem.App;
import com.mpnogaj.kormoranadminsystem.R;
import com.mpnogaj.kormoranadminsystem.helpers.Endpoints;
import com.mpnogaj.kormoranadminsystem.helpers.Requester;
import com.mpnogaj.kormoranadminsystem.helpers.Storage;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = this.findViewById(R.id.loginActivityLoginBtn);
        EditText usernameBox = this.findViewById(R.id.loginActivityUsernameBox);
        EditText passwordBox = this.findViewById(R.id.loginActivityPasswordBox);

        loginBtn.setOnClickListener(view -> {
            String username = usernameBox.getText().toString();
            String password = passwordBox.getText().toString();

            JSONObject requestBody = new JSONObject();
            try {
                requestBody.put("username", username);
                requestBody.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            Requester.getInstance().sendPOSTRequest(Endpoints.LOGIN, requestBody, response -> {
                Storage.getInstance().setLoginData(username, password);
                Intent intent = new Intent(this, TournamentsActivity.class);
                startActivity(intent);
            }, error -> Toast.makeText(
                    App.getAppContext(),
                    "Nieprawidłowy login lub hasło",
                    Toast.LENGTH_LONG).show());
        });
    }
}