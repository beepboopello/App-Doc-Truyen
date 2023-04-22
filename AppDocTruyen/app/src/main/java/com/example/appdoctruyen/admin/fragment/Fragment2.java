package com.example.appdoctruyen.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.activity.AddSubcriptionActivity;
import com.example.appdoctruyen.admin.activity.UpDelSubcriptionActivity;
import com.example.appdoctruyen.admin.adapter.RViewSubcriptionAdapter;
import com.example.appdoctruyen.admin.model.ItemSubcriptionRview;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment implements RViewSubcriptionAdapter.ItemListener {

    private RecyclerView recyclerView;
    private RViewSubcriptionAdapter adapter;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_2,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initRview(view);
    }

    private void initRview(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        adapter= new RViewSubcriptionAdapter();
        List<ItemSubcriptionRview> list = new ArrayList<>();
        list.add(new ItemSubcriptionRview("6","50000"));
        list.add(new ItemSubcriptionRview("3","70000"));
        adapter.setList(list);
        // set layout cho r_view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        // set listener event
        adapter.setItemListener(this);
    }

    private void initView(View view) {
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AddSubcriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

        ItemSubcriptionRview item = adapter.getItem(position);
        // //pass item ->  UpDelActivity
         Intent intent = new Intent(getActivity(), UpDelSubcriptionActivity.class);
         intent.putExtra("item", item);
         startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        // xu ly cap nhat adapter
    }
}
