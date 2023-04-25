package com.example.appdoctruyen_chapteractivity3.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen_chapteractivity3.R;

import org.json.JSONException;
import org.json.JSONObject;

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
        String title = intent.getStringExtra("title");

        textViewTitle.setText(title.toUpperCase());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getChapter(chapterid);

    }

    public void getChapter(int chapterid){
        String url = "http://192.168.1.5:8001/api/chapter/get_chapter_info/";

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
    }
}