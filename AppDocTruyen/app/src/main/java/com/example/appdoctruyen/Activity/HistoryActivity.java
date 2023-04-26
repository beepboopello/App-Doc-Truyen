package com.example.appdoctruyen.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.CurrentUser;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.adapter.RecycleViewAdapterHistory;
import com.example.appdoctruyen.model.Viewed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements RecycleViewAdapterHistory.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapterHistory adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView=findViewById(R.id.recycleView);

        List<Viewed> list=new ArrayList<>();
        adapter=new RecycleViewAdapterHistory();
        adapter.setList(list);

        ServerInfo serverInfo = new ServerInfo(this);
        String url = serverInfo.getUserServiceUrl() + "api/read/get_read/";

        CurrentUser currentUser = new CurrentUser(this);
        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userid",currentUser.getCurrentUser().getId());
//            jsonObject.put("token", currentUser.getCurrentUser().getToken());
//        } catch (JSONException e) {
//            System.err.println(e);
//        }
        url+="?userid=" + String.valueOf(currentUser.getCurrentUser().getId()) + "&token=" + currentUser.getCurrentUser().getToken();

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject;
                            JSONArray jsonArray = new JSONArray(response);
                            List<Viewed> list = new ArrayList<>();
                            for(int i = 0; i<jsonArray.length();i++){
                                jsonObject = jsonArray.getJSONObject(i);
                                Viewed viewed = new Viewed(jsonObject.getString("chapterID"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("view_at").substring(0,10));
                                System.out.println(viewed.getChapterID());
                                list.add(viewed);
                                adapter.setList(list);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error: "+error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent2=new Intent(HistoryActivity.this, ChapterActivity.class);
        Viewed viewed = adapter.getItem(position);
        intent2.putExtra("chapterid", viewed.getChapterID());
        intent2.putExtra("title",viewed.getName());
        startActivity(intent2);
    }
}