package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.appdoctruyen.model.Title;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TitleUpdateActivity extends AppCompatActivity {

    EditText userid, titleid, name, description, author;
    CheckBox ck;
    Button btnupdate, btndelete;

    private Title title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_update);
        userid=findViewById(R.id.edtTitleIDUser);
        titleid=findViewById(R.id.edt_title_id);
        name=findViewById(R.id.edtTitleName);
        description=findViewById(R.id.edtTitleDes);
        author=findViewById(R.id.edtTitleAuthor);
        ck=findViewById(R.id.updateTitleCK);
        btndelete=findViewById(R.id.TitleDelete);
        btnupdate=findViewById(R.id.TitleUpdate);
        Intent intent=getIntent();
        title = (Title) intent.getSerializableExtra("item");

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleUpdateActivity.this, "Xoa truyen thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
//        btnupdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String gid=genreid.getText().toString();
//                String n=name.getText().toString();
//                String d=description.getText().toString();
//                //update genre
//                ServerInfo serverInfo = new ServerInfo(GenreUpdateActivity.this);
//
//                RequestQueue queue = Volley.newRequestQueue(GenreUpdateActivity.this);
//
//                String url = serverInfo.getUserServiceUrl()+"api/update_genre/";
//
//                Map<String,String> body = new HashMap<>();
//
//                body.put("genreid", gid);
//                body.put("name",n);
//                body.put("description",d);
//                //url += "?titleid="+String.valueOf(id) ;
//
//                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(GenreUpdateActivity.this, "Update thanh cong", Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
//                            System.out.println(jsonObject.getString("error"));
//                            Toast.makeText(GenreUpdateActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
//                        } catch (UnsupportedEncodingException | JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }){
//                    @Override
//                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                        return super.parseNetworkResponse(response);
//                    }
//
//                    @Override
//                    protected Map<String,String> getParams(){
//                        return body;
//                    }
//
//                };
//                queue.add(stringRequest);
//                finish();
//            }
//        });
    }
}