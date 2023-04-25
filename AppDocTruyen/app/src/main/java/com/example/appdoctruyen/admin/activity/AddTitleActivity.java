package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddTitleActivity extends AppCompatActivity {
    EditText genreid;
    EditText userid, name, des, author;
    CheckBox cbFree;
    Button btnAddTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);
        genreid=findViewById(R.id.add_title_genreid);
        name=findViewById(R.id.add_title_name);
        des=findViewById(R.id.add_title_des);
        author=findViewById(R.id.add_title_author);
        cbFree=findViewById(R.id.ckFeeAddTitle);
        btnAddTitle=findViewById(R.id.btnAddTitle);
//        Intent intent=getIntent();
//        genreid.setText(intent.getSerializableExtra("id").toString());
        btnAddTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gid=genreid.getText().toString();
                String n=name.getText().toString();
                String a=author.getText().toString();
                String d=des.getText().toString();
                Boolean f=cbFree.isChecked();
                //Toast.makeText(getApplicationContext(), "Them truyen thanh cong", Toast.LENGTH_SHORT).show();
                // start
                ServerInfo serverInfo = new ServerInfo(AddTitleActivity.this);

                RequestQueue queue = Volley.newRequestQueue(AddTitleActivity.this);

                String url = serverInfo.getUserServiceUrl()+"api/add_title/";

                Map<String,String> body = new HashMap<>();

                body.put("name", n);
                body.put("author", a);
                body.put("description", d);
                body.put("genreid", gid);
                if(f){
                    body.put("fee", "True");
                }else{
                    body.put("fee", "False");
                }

                //url += "?titleid="+String.valueOf(id) ;

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
                            Toast.makeText(AddTitleActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
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
                //end
                Toast.makeText(AddTitleActivity.this, "Them oke genre!", Toast.LENGTH_SHORT).show();
                //Intent t=new Intent(AddTitleActivity.this, AddGenreListActivity.class);
                finish();

                //end
            }
        });

    }
}