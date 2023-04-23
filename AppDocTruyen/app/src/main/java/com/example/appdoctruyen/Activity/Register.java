package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.appdoctruyen.SQLite.CurrentUser;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.admin.activity.AdminActivity;
import com.example.appdoctruyen.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    Button btn;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = findViewById(R.id.signUpBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Intent t = getIntent();
                String email = t.getStringExtra("email");
                String accountID = t.getStringExtra("accountID");
                ServerInfo serverInfo = new ServerInfo(Register.this);
                RequestQueue queue = Volley.newRequestQueue(Register.this);

                String url = serverInfo.getUserServiceUrl()+"api/user/";

                Map<String,String> body = new HashMap<>();
                body.put("username",user);
                body.put("password",pass);
                body.put("email",email);
                body.put("accountID",accountID
                );
                body.put("admin","false");

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            User user = new User(
                                    jsonObject.getInt("id"),
                                    jsonObject.getBoolean("admin") ? 1 : 0,
                                    jsonObject.getString("username"),
                                    jsonObject.getString("email"),
                                    jsonObject.getString("token")
                            );
                            CurrentUser currentUser = new CurrentUser(Register.this, user);
                            if(currentUser.getCurrentUser().getAdmin()==1){
                                Intent intent = new Intent(Register.this, AdminActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            }
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
                            Toast.makeText(Register.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                        System.out.println("Server is offline....");
                        Toast.makeText(Register.this,"Server is offline....",Toast.LENGTH_SHORT);
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
        });
    }
}