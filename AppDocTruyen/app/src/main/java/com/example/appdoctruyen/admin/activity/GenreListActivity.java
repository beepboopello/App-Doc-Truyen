package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.admin.adapter.GenreRecycleViewAdapter;
import com.example.appdoctruyen.model.Genre;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GenreListActivity extends AppCompatActivity implements GenreRecycleViewAdapter.ItemListener{
    RecyclerView recycleView;
    GenreRecycleViewAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
        initview();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GenreListActivity.this, "Add genre thanh cong", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), AddGenreActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initview() {
        recycleView=findViewById(R.id.genre_recycleView);
        fab=findViewById(R.id.addgenre);

        adapter=new GenreRecycleViewAdapter();
        List<Genre> list=new ArrayList<>();
        //lay ds tu api
        ServerInfo serverInfo = new ServerInfo(GenreListActivity.this);

        RequestQueue queue = Volley.newRequestQueue(GenreListActivity.this);

        String url = serverInfo.getUserServiceUrl()+"api/genre/";

        //Map<String,String> body = new HashMap<>();

//        body.put("genreID", String.valueOf(id));
//        body.put("page","1");
        //url += "?titleid="+String.valueOf(id) ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray arr=object.getJSONArray("data");
                    for(int i=0;i<arr.length();i++){
                        JSONObject j= (JSONObject) arr.get(i);
                        //String id, String name, String description, String created_at, String update_at
                        Genre genre=new Genre(j.getString("id"),
                                j.getString("name"),
                                j.getString("description"),
                                j.getString("created_at"),
                                j.getString("updated_at"));
                        list.add(genre);

                    }
                    adapter.setList(list);

                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                    System.out.println(jsonObject.getString("error"));
                    Toast.makeText(GenreListActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

//            @Override
//            protected Map<String,String> getParams(){
//                return body;
//            }

        };
        queue.add(stringRequest);
        //end
//        List<Genre> list=new ArrayList<>();
        //list.add(new Genre("1", "Truyen ngu ngon", "hay hay", "2022", "2022"));


        adapter.setItemListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Genre genre=adapter.getGenre(position);
        Intent intent=new Intent(getApplicationContext(), GenreUpdateActivity.class);
        intent.putExtra("item", genre);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Genre> list=new ArrayList<>();
        //lay ds tu api
        ServerInfo serverInfo = new ServerInfo(GenreListActivity.this);

        RequestQueue queue = Volley.newRequestQueue(GenreListActivity.this);

        String url = serverInfo.getUserServiceUrl()+"api/genre/";

        //Map<String,String> body = new HashMap<>();

//        body.put("genreID", String.valueOf(id));
//        body.put("page","1");
        //url += "?titleid="+String.valueOf(id) ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray arr=object.getJSONArray("data");
                    for(int i=0;i<arr.length();i++){
                        JSONObject j= (JSONObject) arr.get(i);
                        //String id, String name, String description, String created_at, String update_at
                        Genre genre=new Genre(j.getString("id"),
                                j.getString("name"),
                                j.getString("description"),
                                j.getString("created_at"),
                                j.getString("updated_at"));
                        list.add(genre);

                    }
                    adapter.setList(list);

                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                    System.out.println(jsonObject.getString("error"));
                    Toast.makeText(GenreListActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

//            @Override
//            protected Map<String,String> getParams(){
//                return body;
//            }

        };
        queue.add(stringRequest);
        adapter.setList(list);
    }
}