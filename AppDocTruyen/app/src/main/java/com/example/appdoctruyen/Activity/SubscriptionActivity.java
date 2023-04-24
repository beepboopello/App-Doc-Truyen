package com.example.appdoctruyen.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.adapter.RecyclerViewAdapterSubscription;
import com.example.appdoctruyen.model.Subscription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapterSubscription adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcription);

        recyclerView = findViewById(R.id.recycleView);
        adapter = new RecyclerViewAdapterSubscription(this, SubscriptionActivity.this);
        LinearLayoutManager manager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        ServerInfo serverInfo = new ServerInfo(SubscriptionActivity.this);

        RequestQueue queue = Volley.newRequestQueue(SubscriptionActivity.this);

        String url = serverInfo.getUserServiceUrl()+"api/listPaymentPacket/";

        Map<String,String> body = new HashMap<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    List<Subscription> list = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.get(i);
                        list.add(new Subscription(jsonObject.getInt("id"),
                                jsonObject.getInt("price"),
                                jsonObject.getInt("months")));
                        adapter.setList(list);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                    System.out.println(jsonObject.getString("error"));
                    Toast.makeText(SubscriptionActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e){
                    System.out.println("Server is offline....");
                    Toast.makeText(SubscriptionActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
                }
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
            protected Map<String,String> getParams(){
                return body;
            }
        };
        queue.add(stringRequest);
    }
}