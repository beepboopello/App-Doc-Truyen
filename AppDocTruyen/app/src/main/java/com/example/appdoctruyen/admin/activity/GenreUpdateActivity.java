package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.appdoctruyen.model.Genre;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GenreUpdateActivity extends AppCompatActivity {
    EditText userid, genreid, name, description;
    Button btnupdate, btndelete;
    Genre genre;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_genre_update);
        userid=findViewById(R.id.edtGenreIDUser);
        genreid=findViewById(R.id.edt_genre_id);
        name=findViewById(R.id.edtGenreName);
        description=findViewById(R.id.edtGenreDes);
        btndelete=findViewById(R.id.GenreDelete);
        btnupdate=findViewById(R.id.GenreUpdate);
        intent=getIntent();
        genre = (Genre) intent.getSerializableExtra("item");

//        userid.setText(genre.);
        genreid.setText(genre.getId());
        name.setText(genre.getName());
        description.setText(genre.getDescription());

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = genre.getId().trim();
                System.out.println(id);
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac muoon xoa '"+ genre.getName()+"' khong?");
                builder.setIcon(R.drawable.baseline_delete_24);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ServerInfo serverInfo = new ServerInfo(GenreUpdateActivity.this);

                        RequestQueue queue = Volley.newRequestQueue(GenreUpdateActivity.this);

                        String url = serverInfo.getUserServiceUrl()+"api/delete_genre/";

                        Map<String,String> body = new HashMap<>();

                        body.put("genreid", id);
//        body.put("page","1");
                        //url += "?titleid="+String.valueOf(id) ;

                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {
                                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                                    System.out.println(jsonObject.getString("error"));
                                    Toast.makeText(GenreUpdateActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
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
                        protected Map<String, String> getParams(){
                            return body;
                        }

                        };
                        queue.add(stringRequest);
                        finish();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gid=genreid.getText().toString();
                String n=name.getText().toString();
                String d=description.getText().toString();
                //update genre
                ServerInfo serverInfo = new ServerInfo(GenreUpdateActivity.this);

                RequestQueue queue = Volley.newRequestQueue(GenreUpdateActivity.this);

                String url = serverInfo.getUserServiceUrl()+"api/update_genre/";

                Map<String,String> body = new HashMap<>();

                body.put("genreid", gid);
                body.put("name",n);
                body.put("description",d);
                //url += "?titleid="+String.valueOf(id) ;

                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(GenreUpdateActivity.this, "Update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(GenreUpdateActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
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
                finish();
            }
        });
    }
}