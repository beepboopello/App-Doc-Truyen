package com.example.appdoctruyen.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.activity.StatisticHistoryDetailActivity;
import com.example.appdoctruyen.admin.adapter.RviewStatisticAdapter;
import com.example.appdoctruyen.admin.model.ItemStatisticRview;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment implements RviewStatisticAdapter.ItemListener{
    private RadioGroup radioGroup;

    private RecyclerView recyclerView;
    private RviewStatisticAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_3,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        radioGroup = view.findViewById(R.id.groupradio);
        radioGroup = view.findViewById(R.id.groupradio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                Toast.makeText(getContext(), ""+radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = view.findViewById(R.id.recycleView);
        adapter= new RviewStatisticAdapter();
        List<ItemStatisticRview> list = new ArrayList<>();
        list.add(new ItemStatisticRview("Tháng 10 năm 2022","202200"));
        list.add(new ItemStatisticRview("Tháng 11 năm 2022","150000"));
        adapter.setList(list);
        // set layout cho r_view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        // set listener event
        adapter.setItemListener(this);
    }
    @Override
    public void onItemClick(View view, int position) {

        ItemStatisticRview item = adapter.getItem(position);
        // //pass item ->  UpDelActivity
        Intent intent = new Intent(getActivity(), StatisticHistoryDetailActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
