package com.example.appdoctruyen.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.RecycleViewAdapterHistory;
import com.example.appdoctruyen.model.Viewed;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements RecycleViewAdapterHistory.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapterHistory adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView=findViewById(R.id.recycleView);

        List<Viewed> list=new ArrayList<>();
        adapter=new RecycleViewAdapterHistory();
        Viewed v=new Viewed("Chuong 1","22/2/2022");
        list.add(v);
        adapter.setList(list);

        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent2=new Intent(HistoryActivity.this, ChapActivity.class);
        startActivity(intent2);
    }
}