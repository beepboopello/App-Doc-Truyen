package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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
import com.example.appdoctruyen.admin.activity.AdminActivity;
import com.example.appdoctruyen.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ChapterActivity extends AppCompatActivity {

    private TextView textViewContent,textViewTitle,textViewNameChapter;
    private Button buttonChapterTruoc,buttonChapterSau,buttonBack;
    private Spinner spinnerListChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        textViewContent=findViewById(R.id.textViewContentChapter);
        textViewNameChapter=findViewById(R.id.textViewNameChapter);
        textViewTitle=findViewById(R.id.textViewNameTitle);
        buttonBack=findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        int chapterid=intent.getIntExtra("chapterid",1);
        System.out.println(chapterid);
        String title = intent.getStringExtra("title");

        textViewTitle.setText(title.toUpperCase());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ServerInfo serverInfo = new ServerInfo(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        CurrentUser currentUser = new CurrentUser(this);

        String url = serverInfo.getUserServiceUrl()+"api/read/add_read/";

        Map<String,String> body = new HashMap<>();
        body.put("userid",String.valueOf(currentUser.getCurrentUser().getId()));
        body.put("token",currentUser.getCurrentUser().getToken());
        body.put("chapterid", String.valueOf(chapterid));
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
                    Toast.makeText(ChapterActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                    finish();
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e){
                    System.out.println("Server is offline....");
                    Toast.makeText(ChapterActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
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
        getChapter(chapterid);

    }

    public void getChapter(int chapterid){
        ServerInfo serverInfo = new ServerInfo(this);
        String url = serverInfo.getUserServiceUrl() + "/api/chapter/get_chapter_info/";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("chapterid",chapterid);
        } catch (JSONException e) {
            System.err.println(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = response.getInt("id");
                            int titleId = response.getInt("titleId");
                            String name = response.getString("name");
                            int number = response.getInt("number");
                            int views = response.getInt("views");
                            String content = response.getString("content");
                            String created_at = response.getString("created_at");
                            String updated_at = response.getString("updated_at");

                            textViewContent.setText(content);
                            textViewNameChapter.setText("Chapter "+number+" : "+name);

                        } catch (JSONException e) {
                            Toast.makeText(ChapterActivity.this, "Loi xu li Response", Toast.LENGTH_SHORT).show();
                            System.err.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChapterActivity.this, "Loi response: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("error: "+error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ChapterActivity.this);
        requestQueue.add(jsonObjectRequest);

//        url = serverInfo.getUserServiceUrl()+"api/read/add_read/";
//        CurrentUser currentUser = new CurrentUser(this);
//
//        Map<String,String> body = new HashMap<>();
//        body.put("userid",String.valueOf(currentUser.getCurrentUser().getId()));
//        body.put("chapterid",String.valueOf(chapterid));
//        body.put("token", currentUser.getCurrentUser().getToken());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
//                    System.out.println(jsonObject.getString("error"));
//                    Toast.makeText(ChapterActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
//                } catch (UnsupportedEncodingException | JSONException e) {
//                    throw new RuntimeException(e);
//                } catch (NullPointerException e){
//                    System.out.println("Server is offline....");
//                    Toast.makeText(ChapterActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
//                }
//            }
//        }){
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                return super.parseNetworkResponse(response);
//            }
//            protected Map<String,String> getParams(){
//                return body;
//            }
//        };
//        requestQueue.add(stringRequest);
    }
}