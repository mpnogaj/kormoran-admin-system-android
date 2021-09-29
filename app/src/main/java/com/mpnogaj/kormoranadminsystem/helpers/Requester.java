package com.mpnogaj.kormoranadminsystem.helpers;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.mpnogaj.kormoranadminsystem.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Requester {
    private final RequestQueue requestQueue;

    private Requester(){
        Cache cache = new DiskBasedCache(App.getAppContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    @Nullable
    private static Requester _instance = null;
    public static Requester getInstance(){
        if(_instance == null){
            _instance = new Requester();
        }
        return _instance;
    }

    public void sendPOSTRequest(String url, JSONObject requestBody, Response.Listener<String> onResponse, Response.ErrorListener onError){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, String.format(Endpoints.BASE, url), onResponse, onError){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString;
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    if(response.data.length > 0){
                        Log.d("Data: ", Arrays.toString(response.data));
                        JSONObject object;
                        try {
                            object = new JSONObject(new String(response.data));
                            responseString = object.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
                return Response.success("", null);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void sendGETRequest(String url, Response.Listener<String> onResponse, Response.ErrorListener onError){
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, String.format(Endpoints.BASE, url), onResponse, onError);
        requestQueue.add(jsonRequest);
    }
}
