package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.admin.adapter.RviewStatisticAdapter;
import com.example.appdoctruyen.admin.model.ItemStatisticRview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class StatisticHistoryDetailActivity extends AppCompatActivity {
    private Button btn_back;

    private ItemStatisticRview item;

    private RecyclerView recyclerView;
    private RviewStatisticAdapter adapter;

    private TextView tv_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_history_detail);

        initView();
//        btnCancelAction();
//        btnDeleteAction();
//        btnUpdateAction();
//        History_payment_by_month
    }

    private void getDataIntent() {
        Intent intent=getIntent();
        item = (ItemStatisticRview) intent.getSerializableExtra("item");
        tv_date.setText(item.getDate());
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        tv_date = findViewById(R.id.tv_date);
        recyclerView = findViewById(R.id.recycleView);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDataIntent();

//        set data
        adapter= new RviewStatisticAdapter();
        // gọi api
        ArrayList<ItemStatisticRview> list = new ArrayList<>();

        ServerInfo serverInfo = new ServerInfo(StatisticHistoryDetailActivity.this);
        RequestQueue queue = Volley.newRequestQueue(StatisticHistoryDetailActivity.this);
        String url = serverInfo.getContentServiceUrl()+"api/History_payment_by_month/";
        url +="?month="+item.getMonth()+"&year="+item.getYear();
        Toast.makeText(this, ""+item.getYear(), Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray paymentPackages = jsonObject.getJSONArray("data");
                    for (int i=0;i<paymentPackages.length();i++){
                        JSONObject paymentPackage = paymentPackages.getJSONObject(i);
                        String userid = String.valueOf(paymentPackage.getInt("userid"));
                        String income = String.valueOf(paymentPackage.getInt("income"));
                        String month = String.valueOf(paymentPackage.getInt("month"));
                        String year = String.valueOf(paymentPackage.getInt("year"));
                        String day = String.valueOf(paymentPackage.getInt("day"));
                        String date = "Ngày "+day+" tháng "+month+" năm "+year;
                        ItemStatisticRview item = new ItemStatisticRview(userid,date,income);
                        list.add(item);
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
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
                    Toast.makeText(StatisticHistoryDetailActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e){
                    System.out.println("Server is offline....");
                    Toast.makeText(StatisticHistoryDetailActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
                }

                Toast.makeText(StatisticHistoryDetailActivity.this,"Failed",Toast.LENGTH_SHORT);
            }
        });

        queue.add(stringRequest);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        // set layout cho r_view
        LinearLayoutManager manager = new LinearLayoutManager(StatisticHistoryDetailActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        // set listener event
    }
}