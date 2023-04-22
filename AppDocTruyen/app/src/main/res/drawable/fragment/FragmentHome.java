package com.example.appdoctruyen.fragment;

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

import com.example.appdoctruyen.Activity.BookActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.adapter.RecycleViewAdapter;
import com.example.appdoctruyen.model.Book;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycleView);
        adapter=new RecycleViewAdapter();
        List<Book> list=new ArrayList<>();
        Book b=new Book("Sach 1");
        list.add(b);
        adapter.setList(list);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }
    public void onItemClick(View view, int position) {
        //adapter de click vao dc
        Book book=adapter.getItem(position);
        Intent intent=new Intent(getActivity(), BookActivity.class);
//        intent.putExtra("book",book);
        startActivity(intent);
    }
}
