package com.example.appdoctruyen.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.RecycleViewAdapter;
import com.example.appdoctruyen.adapter.RecycleViewAdapterChapList;
import com.example.appdoctruyen.model.Chap;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements RecycleViewAdapterChapList.ItemListener,RecycleViewAdapter.ItemListener{
    private RecyclerView recyclerView;

    private Button bt;
    RecycleViewAdapterChapList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        recyclerView=findViewById(R.id.recycleChap);

        List<Chap> list=new ArrayList<>();
        adapter=new RecycleViewAdapterChapList();
        Chap c=new Chap("Chuong 1");
        list.add(c);
        adapter.setList(list);

        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        bt=findViewById(R.id.idbt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookActivity.this, ListBookActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override

    public void onItemClick(View view, int position) {
        Chap chap=adapter.getItem(position);
        Intent intent2=new Intent(BookActivity.this, ChapActivity.class);
        startActivity(intent2);

    }



}