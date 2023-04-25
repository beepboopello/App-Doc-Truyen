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
import com.example.appdoctruyen.SQLite.CurrentUser;
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
    Book book1 = new Book();
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
        String url;
        Map<String,String> body = new HashMap<>();
        if(id!=0){
         serverInfo = new ServerInfo(ListBookActivity.this);

         queue = Volley.newRequestQueue(ListBookActivity.this);

         url = serverInfo.getUserServiceUrl()+"api/genre/title/";



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
                                jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                jsonObject.getInt("views"),
                                jsonObject.getInt("like")
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
        queue.add(stringRequest);}
        else {
            int mode = intent.getIntExtra("mode", 0);
            if (mode == 1) {
                url = serverInfo.getUserServiceUrl() + "api/mostlike/";
                StringRequest  stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            System.out.println("day"+jsonArray);
                    JSONArray jsonArray = new JSONArray(new JSONObject(response).getString("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Book b = new Book(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                        jsonObject.getInt("views"),
                                        jsonObject.getInt("like")
                                );
                                list.add(b);
                            }
//                            System.out.println(list);
                            adapter.setList(list);
                            adapter.notifyDataSetChanged();
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
                            Toast.makeText(ListBookActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        return body;
                    }

                };
                queue.add(stringRequest);
            }
            else if (mode == 2) {
                url = serverInfo.getUserServiceUrl() + "api/mostread/";
                StringRequest  stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(new JSONObject(response).getString("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Book b = new Book(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                        jsonObject.getInt("views"),
                                        jsonObject.getInt("like")
                                );
                                list.add(b);
                            }
                            adapter.setList(list);
                            adapter.notifyDataSetChanged();
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
                            Toast.makeText(ListBookActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        return body;
                    }

                };
                queue.add(stringRequest);
            }
            else if (mode == 3) {
                url = serverInfo.getUserServiceUrl() + "api/getfreetitle/";
                StringRequest  stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                    JSONArray jsonArray = new JSONArray(new JSONObject(response).get("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Book b = new Book(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                        jsonObject.getInt("views"),
                                        jsonObject.getInt("like")
                                );
                                list.add(b);
                            }
                            adapter.setList(list);
                            adapter.notifyDataSetChanged();
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
                            Toast.makeText(ListBookActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        return body;
                    }

                };
                queue.add(stringRequest);
            }
            else if (mode == 4) {
                url = serverInfo.getUserServiceUrl() + "api/getnofreetitle/";
                StringRequest  stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

//                    JSONArray jsonArray = new JSONArray(new JSONObject(response).get("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Book b = new Book(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                        jsonObject.getInt("views"),
                                        jsonObject.getInt("like")
                                );
                                list.add(b);
                            }
                            adapter.setList(list);
                            adapter.notifyDataSetChanged();
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
                            Toast.makeText(ListBookActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        return body;
                    }

                };
                queue.add(stringRequest);
            } else if (mode == 5) {
                ServerInfo serverInfo5 = new ServerInfo(ListBookActivity.this);
                CurrentUser currentUser5 = new CurrentUser(ListBookActivity.this);



                String url5 = serverInfo5.getUserServiceUrl()+"api/lovestory/";
                url5 += "?userid=" +currentUser5.getCurrentUser().getId();

                Map<String,String> body5 = new HashMap<>();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Book> list = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println(jsonObject.getString("data"));
                            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                            for(int i=0; i<jsonArray.length();i++){
                                jsonObject = jsonArray.getJSONObject(i);
                                Book book = new Book(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                        jsonObject.getInt("views"),
                                        jsonObject.getInt("like")
                                );
                                list.add(book);

                            }
                            adapter.setList(list);
                            adapter.notifyDataSetChanged();
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
                            Toast.makeText(ListBookActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                            System.out.println("Server is offline....");
                            Toast.makeText(ListBookActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
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