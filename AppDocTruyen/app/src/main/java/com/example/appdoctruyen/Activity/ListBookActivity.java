package com.example.appdoctruyen.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.appdoctruyen.adapter.RecycleViewAdapter;
import com.example.appdoctruyen.model.Book;
import com.example.appdoctruyen.model.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBookActivity extends AppCompatActivity implements RecycleViewAdapter.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);
        recyclerView=findViewById(R.id.recycleView);

        List<Book> list=new ArrayList<>();
        adapter=new RecycleViewAdapter();
        Book b=new Book("Doremon");
        list.add(b);
        adapter.setList(list);

        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        Intent intent = getIntent();
        int id=intent.getIntExtra("idgenre",0);
//        int id = Integer.parseInt(intent.getStringExtra("idgenre"));
        ServerInfo serverInfo = new ServerInfo(ListBookActivity.this);

        RequestQueue queue = Volley.newRequestQueue(ListBookActivity.this);

        String url = serverInfo.getUserServiceUrl()+"api/genre/title/";

        Map<String,String> body = new HashMap<>();

//        body.put("genreID", String.valueOf(id));
//        body.put("page","1");
        url += "?genreID=" +  String.valueOf(id) + "&page=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

//                    JSONArray jsonArray = new JSONArray(new JSONObject(response).get("data"));
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Book b = new Book(jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("author"),
                                jsonObject.getString("description"),
                                jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi"
                        );
                        list.add(b);
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
//                    System.out.println(jsonObject.get("name"));
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
                    Toast.makeText(ListBookActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
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
    }

    @Override
    public void onItemClick(View view, int position) {
        Book book=adapter.getItem(position);
        int idbook =Integer.parseInt(book.getId());
        Intent intent=new Intent(ListBookActivity.this, BookActivity.class);
        intent.putExtra("idbook",idbook);
        startActivity(intent);
    }
}