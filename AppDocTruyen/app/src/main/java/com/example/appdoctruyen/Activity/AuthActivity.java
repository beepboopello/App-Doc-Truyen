package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.appdoctruyen.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView btn;

    Button loginBtn;

    EditText username,password;

    Map<String, Object> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);

        //Dang nhap bang tai khoan mat khau
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerInfo serverInfo = new ServerInfo(AuthActivity.this);

                RequestQueue queue = Volley.newRequestQueue(AuthActivity.this);

                String url = serverInfo.getUserServiceUrl()+"api/login/";

                Map<String,String> body = new HashMap<>();
                body.put("username",username.getText().toString());
                body.put("password",password.getText().toString());
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
                            CurrentUser currentUser = new CurrentUser(AuthActivity.this, user);
                            if(currentUser.getCurrentUser().getAdmin()==1){
                                System.out.println("La admin dang nhap");
                            }
                            else{
                                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
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
                            Toast.makeText(AuthActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                            System.out.println("Server is offline....");
                            Toast.makeText(AuthActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
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

        btn = findViewById(R.id.google_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            ServerInfo serverInfo = new ServerInfo(AuthActivity.this);
            RequestQueue queue = Volley.newRequestQueue(AuthActivity.this);

            String url = serverInfo.getUserServiceUrl()+"api/googleLogin/";

            Map<String,String> body = new HashMap<>();
            body.put("email",account.getEmail());
            body.put("accountID",account.getId());

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
                        CurrentUser currentUser = new CurrentUser(AuthActivity.this, user);
                        if(currentUser.getCurrentUser().getAdmin()==1){
                            System.out.println("La admin dang nhap");
                        }
                        else{
                            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
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
                        Toast.makeText(AuthActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        throw new RuntimeException(e);
                    } catch (NullPointerException e){
                    System.out.println("Server is offline....");
                    Toast.makeText(AuthActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();

            // goi api kiem tra user bang email, co ton tai thi dang nhap luon, khong thi sang activity dang ky nhu duoi

            ServerInfo serverInfo = new ServerInfo(AuthActivity.this);
            RequestQueue queue = Volley.newRequestQueue(AuthActivity.this);

            String url = serverInfo.getUserServiceUrl()+"api/googleLogin/";

            Map<String,String> body = new HashMap<>();
            body.put("email",account.getEmail());
            body.put("accountID",account.getId());

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
                        CurrentUser currentUser = new CurrentUser(AuthActivity.this, user);
                        if(currentUser.getCurrentUser().getAdmin()==1){
                            System.out.println("La admin dang nhap");
                        }
                        else{
                            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
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
                        Toast.makeText(AuthActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                        Intent intent = new Intent(AuthActivity.this,Register.class);
                        intent.putExtra("email",account.getEmail());
                        intent.putExtra("accountID",account.getId());
                        startActivity(intent);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        throw new RuntimeException(e);
                    } catch (NullPointerException e){
                        System.out.println("Server is offline....");
                        Toast.makeText(AuthActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
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




        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_SHORT).show();


        }
    }

}