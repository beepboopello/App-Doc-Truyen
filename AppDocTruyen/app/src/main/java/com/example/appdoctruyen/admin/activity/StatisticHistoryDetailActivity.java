package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.adapter.RviewStatisticAdapter;
import com.example.appdoctruyen.admin.model.ItemStatisticRview;

import java.util.ArrayList;
import java.util.List;

public class StatisticHistoryDetailActivity extends AppCompatActivity {

    private ItemStatisticRview item;

    private RecyclerView recyclerView;
    private RviewStatisticAdapter adapter;

    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_history_detail);
        initView();
        getDataIntent();
//        btnCancelAction();
//        btnDeleteAction();
//        btnUpdateAction();
    }

    private void getDataIntent() {
        Intent intent=getIntent();
        item = (ItemStatisticRview) intent.getSerializableExtra("item");
        tv_date.setText(item.getDate());
    }

    private void initView() {
        tv_date = findViewById(R.id.tv_date);
        recyclerView = findViewById(R.id.recycleView);
        adapter= new RviewStatisticAdapter();
        List<ItemStatisticRview> list = new ArrayList<>();
        list.add(new ItemStatisticRview("Ngày 15 tháng 11","50000"));
        list.add(new ItemStatisticRview("Ngày 16 tháng 11","60000"));
        adapter.setList(list);
        // set layout cho r_view
        LinearLayoutManager manager = new LinearLayoutManager(StatisticHistoryDetailActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        // set listener event
    }
}