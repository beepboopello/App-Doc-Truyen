package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.appdoctruyen.admin.adapter.TitleRecycleViewAdapter;
import com.example.appdoctruyen.model.Genre;
import com.example.appdoctruyen.model.Title;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleListActivity extends AppCompatActivity implements TitleRecycleViewAdapter.ItemListener{

    RecyclerView recycleView;
    TitleRecycleViewAdapter adapter;
    FloatingActionButton fab;
    Button btnSearch;
    Spinner spinner;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        intiview();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleListActivity.this, "Add title thanh cong", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), AddGenreActivity.class);
                startActivity(intent);
            }
        });
        List<Title> mList=new ArrayList<>();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Genre tmpGenre=new Genre();
                ServerInfo serverInfo = new ServerInfo(TitleListActivity.this);

                RequestQueue queue = Volley.newRequestQueue(TitleListActivity.this);

                String url = serverInfo.getUserServiceUrl()+"api/getid_genre_by_name/";
                String ngenre = (String) spinner.getSelectedItem();
                System.out.println("PPPPPPPPPPPP"+ ngenre);

                Map<String,String> body = new HashMap<>();
                body.put("genreName", ngenre.trim());


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(" DEN DAY OK 3");
                            JSONObject object=new JSONObject(response);
                            System.out.println(" DEN DAY OK1");
                            id=object.getString("id");
                            System.out.println(" DEN DAY OK2");

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
                            Toast.makeText(TitleListActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String,String> getParams(){
                        return body;
                    }

                };
                queue.add(stringRequest);
                System.out.println("HHHHHHHHHHHHHHHHHH"+id);
                // recycleview data


                //lay ds tu api
//
                ServerInfo serverInfo1 = new ServerInfo(TitleListActivity.this);

                RequestQueue queue1 = Volley.newRequestQueue(TitleListActivity.this);

                String url1 = serverInfo1.getUserServiceUrl()+"api/genre/title/";

                Map<String,String> body1 = new HashMap<>();

                url1 += "?genreID=" +  String.valueOf(id + "&page=1");

                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                //int id, String name, String author, String des, String created_at, String updated_at, boolean fee, int totalView
                                Title b = new Title(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee"));
                                mList.add(b);
                            }
                            adapter.setList(mList);

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
                            Toast.makeText(TitleListActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String,String> getParams(){
                        return body1;
                    }

                };
                queue1.add(stringRequest1);
            }
        });


//        String[] listGenre=new String[l.size()];
//        for(int i=0;i<l.size();i++){
//            listGenre[i]= String.valueOf(l.get(i).getName());
//            System.out.println(l.get(i).getName());
//        }
//        SpinnerAdapter spinnerAdapter=new SpinnerAdapter(this);
//        spinner.setAdapter(spinnerAdapter);
        // get title

        //end
        String[] listSchool=getResources().getStringArray(R.array.listGenre);
        ArrayAdapter<String> adapterSchool=new ArrayAdapter<>(this, R.layout.item_spinner,listSchool);
        spinner.setAdapter(adapterSchool);

        adapter.setList(mList);
        adapter.setItemListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
    }

    private void intiview() {
        recycleView=findViewById(R.id.title_recycleView);
        fab=findViewById(R.id.addtitle);
        spinner=findViewById(R.id.spinner);
        btnSearch=findViewById(R.id.btnSearchtitleByGenre);
        adapter=new TitleRecycleViewAdapter();
    }


    @Override
    public void onItemClick(View view, int position) {
        Title title=adapter.getTitle(position);
        Intent intent=new Intent(getApplicationContext(), TitleUpdateActivity.class);
        intent.putExtra("item", title);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}