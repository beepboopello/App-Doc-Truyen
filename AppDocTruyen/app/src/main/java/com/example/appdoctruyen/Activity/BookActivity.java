package com.example.appdoctruyen.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
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
import com.example.appdoctruyen.adapter.RecycleViewAdapterChapList;
import com.example.appdoctruyen.model.Book;
import com.example.appdoctruyen.model.Chap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookActivity extends AppCompatActivity implements RecycleViewAdapterChapList.ItemListener,RecycleViewAdapter.ItemListener{
    private RecyclerView recyclerView;
    private TextView txtname,txtauthor,txtdes,txtgenre;
    private Button bt;
    RatingBar btlove;
    RecycleViewAdapterChapList adapter;
    String idgenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        txtname=findViewById(R.id.txtbook);
        txtauthor=findViewById(R.id.txtauthor);
        txtdes=findViewById(R.id.txtdescrip);
        txtgenre=findViewById(R.id.txtgenre);
        btlove=findViewById(R.id.btlove);
        recyclerView=findViewById(R.id.recycleChap);


        List<Chap> list=new ArrayList<>();
        adapter=new RecycleViewAdapterChapList();
        Chap c=new Chap("Chuong 1");
        list.add(c);
        adapter.setList(list);

        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        Intent intent = getIntent();
        int id=intent.getIntExtra("idbook",0);
//        int id = Integer.parseInt(intent.getStringExtra("idgenre"));
        ServerInfo serverInfo = new ServerInfo(BookActivity.this);

        RequestQueue queue = Volley.newRequestQueue(BookActivity.this);

        String url = serverInfo.getUserServiceUrl()+"api/title/";

        Map<String,String> body = new HashMap<>();

//        body.put("genreID", String.valueOf(id));
//        body.put("page","1");
        url += "?titleid="+String.valueOf(id) ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String title=jsonObject.getString("name");
                    String author=jsonObject.getString("author");
                    String description=jsonObject.getString("description");
                    String genre=jsonObject.getString("genre");
                    idgenre=jsonObject.getString("genreID");
                    System.out.println(idgenre);
                    txtname.setText(title);
                    txtauthor.setText(author);
                    txtdes.setText(description);
                    txtgenre.setText(genre);

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
                    Toast.makeText(BookActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
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


        bt=findViewById(R.id.idbt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookActivity.this, ListBookActivity.class);
                intent.putExtra("idgenre",Integer.parseInt(idgenre));
                startActivity(intent);
            }
        });
        btlove.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(BookActivity.this, "Bạn đã thích truyện", Toast.LENGTH_SHORT).show();
                ServerInfo serverInfo = new ServerInfo(BookActivity.this);

                RequestQueue queue = Volley.newRequestQueue(BookActivity.this);

                String url = serverInfo.getUserServiceUrl()+"api/like/";
                CurrentUser user=new CurrentUser(BookActivity.this);
                Map<String,String> body = new HashMap<>();
                body.put("userid", String.valueOf(user.getCurrentUser().getId()));
                 body.put("titleid",String.valueOf(id));
//                url += "?userid="+String.valueOf(user.getCurrentUser().getId())+"&titleid="+String.valueOf(id) ;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(BookActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
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
        });
    }

    @Override

    public void onItemClick(View view, int position) {
        Chap chap=adapter.getItem(position);
        Intent intent2=new Intent(BookActivity.this, ChapActivity.class);
        startActivity(intent2);

    }



}