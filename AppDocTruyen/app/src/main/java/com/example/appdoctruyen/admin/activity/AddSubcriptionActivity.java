package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddSubcriptionActivity extends AppCompatActivity {

    private EditText tv_price,tv_month;
    private Button btn_add,btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subcription);
        initView();
        btnCancelAction();
        btnAddAction();
    }

    private void btnAddAction() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = tv_price.getText().toString();
                String month = tv_month.getText().toString();
                if(month.isEmpty() || price.isEmpty()){
                    Toast.makeText(AddSubcriptionActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(month.matches("\\d+") && price.matches("\\d+") ){
                    //gọi api
                    ServerInfo serverInfo = new ServerInfo(AddSubcriptionActivity.this);

                    RequestQueue queue = Volley.newRequestQueue(AddSubcriptionActivity.this);

                    String url = serverInfo.getUserServiceUrl()+"api/paymentPakage/";

                    Map<String,String> body = new HashMap<>();
                    body.put("month",tv_month.getText().toString());
                    body.put("price",tv_price.getText().toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(AddSubcriptionActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish();

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try {
                                JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                                Toast.makeText(AddSubcriptionActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException | JSONException e) {
                                throw new RuntimeException(e);
                            } catch (NullPointerException e){
                                Toast.makeText(AddSubcriptionActivity.this,"Server is offline....",Toast.LENGTH_SHORT).show();
                            }
                        }
                    } ){
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
                else {
                    Toast.makeText(AddSubcriptionActivity.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void btnCancelAction() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tv_price = findViewById(R.id.tv_price);
        tv_month = findViewById(R.id.tv_month);
        btn_add = findViewById(R.id.btn_add);
        btn_cancel = findViewById(R.id.btn_cancel);
    }
}