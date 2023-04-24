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
import com.example.appdoctruyen.admin.model.ItemSubcriptionRview;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UpDelSubcriptionActivity extends AppCompatActivity {

    private EditText tv_price,tv_month;
    private Button btn_update,btn_cancel,btn_delete;
    private ItemSubcriptionRview item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_del_subcription);
        initView();
        getDataIntent();
        btnCancelAction();
        btnDeleteAction();
        btnUpdateAction();
    }

    private void getDataIntent() {
        Intent intent=getIntent();
        item = (ItemSubcriptionRview) intent.getSerializableExtra("item");
        tv_price.setText(item.getPrice());
        tv_month.setText(item.getMonth());
    }


    private void btnDeleteAction() {

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               int id = item.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Bạn có chắc muốn xóa gói thanh toán thời hạn '" + item.getMonth() + "' tháng này không?");
                builder.setIcon(R.drawable.baseline_delete_24);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        call api
                        Toast.makeText(UpDelSubcriptionActivity.this,item.getId(),Toast.LENGTH_SHORT).show();

                        ServerInfo serverInfo = new ServerInfo(UpDelSubcriptionActivity.this);

                        RequestQueue queue = Volley.newRequestQueue(UpDelSubcriptionActivity.this);

                        String url = serverInfo.getUserServiceUrl()+"api/deletePaymentPakage/";

                        Map<String,String> body = new HashMap<>();
                        body.put("id",item.getId());
                        body.put("month",tv_month.getText().toString());
                        body.put("price",tv_price.getText().toString());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Toast.makeText(UpDelSubcriptionActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    finish();

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UpDelSubcriptionActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                                    Toast.makeText(UpDelSubcriptionActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (NullPointerException e){
                                    Toast.makeText(UpDelSubcriptionActivity.this,"Server is offline....",Toast.LENGTH_SHORT).show();
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
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    private void btnUpdateAction() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = tv_price.getText().toString();
                String month = tv_month.getText().toString();
                if(month.isEmpty() || price.isEmpty()){
                    Toast.makeText(UpDelSubcriptionActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(month.matches("\\d+") && price.matches("\\d+") ){
                    //gọi api
                    ServerInfo serverInfo = new ServerInfo(UpDelSubcriptionActivity.this);

                    RequestQueue queue = Volley.newRequestQueue(UpDelSubcriptionActivity.this);

                    String url = serverInfo.getUserServiceUrl()+"api/paymentPakage/";

                    Map<String,String> body = new HashMap<>();
                    body.put("id",item.getId());
                    body.put("month",tv_month.getText().toString());
                    body.put("price",tv_price.getText().toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(UpDelSubcriptionActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                finish();

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UpDelSubcriptionActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                                Toast.makeText(UpDelSubcriptionActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException | JSONException e) {
                                throw new RuntimeException(e);
                            } catch (NullPointerException e){
                                System.out.println("Server is offline....");
                                Toast.makeText(UpDelSubcriptionActivity.this,"Server is offline....",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UpDelSubcriptionActivity.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
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
        btn_update = findViewById(R.id.btn_update);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_delete =findViewById(R.id.btn_delete);
    }
}