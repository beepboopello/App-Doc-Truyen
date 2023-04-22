package com.example.appdoctruyen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.BookActivity;
import com.example.appdoctruyen.ListBookActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.RecycleViewAdapter;
import com.example.appdoctruyen.adapter.RecycleViewAdapterGenreList;
import com.example.appdoctruyen.model.Book;
import com.example.appdoctruyen.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class FragmentGenre extends Fragment implements RecycleViewAdapterGenreList.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapterGenreList adapter;
    Button btlove,btread,btfree,btnofree;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genre,container,false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycleView);
        adapter=new RecycleViewAdapterGenreList();
        List<Genre> list=new ArrayList<>();
        Genre b=new Genre("Trinh tham");
        list.add(b);
        adapter.setList(list);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        Intent intent=new Intent(getActivity(),ListBookActivity.class);
        btlove=view.findViewById(R.id.btToplove);
        btlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });
        btread=view.findViewById(R.id.btTopRead);
        btread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ListBookActivity.class);
                startActivity(intent);
            }
        });
        btfree=view.findViewById(R.id.btFree);
        btfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ListBookActivity.class);
                startActivity(intent);
            }
        });
        btnofree=view.findViewById(R.id.btNotFree);
        btnofree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ListBookActivity.class);
                startActivity(intent);
            }
        });

    }
    public void onItemClick(View view, int position) {
        //adapter de click vao dc
        Genre genre=adapter.getItem(position);
        Intent intent=new Intent(getActivity(), ListBookActivity.class);
//        intent.putExtra("book",book);
        startActivity(intent);
    }
}
