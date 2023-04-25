package com.example.appdoctruyen_fragmenthome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.appdoctruyen.Activity.BookActivity;
//import com.example.appdoctruyen.R;
//import com.example.appdoctruyen.adapter.RecycleViewAdapter;
//import com.example.appdoctruyen.model.Book;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen_fragmenthome.Activity.BookActivity;
import com.example.appdoctruyen_fragmenthome.R;
import com.example.appdoctruyen_fragmenthome.adapter.RecycleViewAdapter;
import com.example.appdoctruyen_fragmenthome.model.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener, SearchView.OnQueryTextListener{
    private RecyclerView recyclerViewTruyenDeXuat,recyclerViewSearch;
    private RecycleViewAdapter adapterTruyenDeXuat,adapterSearch;
    private List<Book> listTruyenDeXuat,listSearch;
    private SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewTruyenDeXuat=view.findViewById(R.id.recycleViewTruyenDeXuat);
        recyclerViewSearch=view.findViewById(R.id.recycleViewSearch);
        searchView=view.findViewById(R.id.searchView);

        listTruyenDeXuat=new ArrayList<>();
        listSearch=new ArrayList<>();

        updateListTruyenDeXuat();

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSearch=new RecycleViewAdapter(listSearch);
        adapterSearch.setItemListener(this);
        recyclerViewSearch.setAdapter(adapterSearch);

        adapterTruyenDeXuat=new RecycleViewAdapter(listTruyenDeXuat);
        recyclerViewTruyenDeXuat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTruyenDeXuat.setAdapter(adapterTruyenDeXuat);
        adapterTruyenDeXuat.setItemListener(this);

        searchView.setOnQueryTextListener(this);
    }

    public void onItemClick(View view, int position) {
        //adapter de click vao dc
        //Book book=adapter.getItem(position);
        Intent intent=new Intent(getActivity(), BookActivity.class);
        //intent.putExtra("book",book);
        startActivity(intent);
    }

    public void updateListSearch(){
        listSearch.clear();

        String url = "http://192.168.1.5:8001/api/title/search_title_by_name_or_author/";

        String search = String.valueOf(searchView.getQuery());

        if(search==null||search.equals("")){
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name_search",search);
            jsonObject.put("author_search",search);

        } catch (JSONException e) {
            System.err.println(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for(int i=0;i<response.getInt("size");i++){
                                String name = response.getString("name"+i);
                                boolean free = response.getBoolean("free"+i);
                                int totalViews = response.getInt("views"+i);
                                int totalLikes = response.getInt("likes"+i);

                                listSearch.add(new Book(0,0,name,"","",free,"","",totalViews,totalLikes));

                                adapterSearch.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            System.err.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void updateListTruyenDeXuat(){
        listTruyenDeXuat.clear();

        String url = "http://192.168.1.5:8001/api/title/get_list_favorite_title/";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number",2);
        } catch (JSONException e) {
            System.err.println(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for(int i=0;i<response.getInt("size");i++){
                                String name = response.getString("name"+i);
                                boolean free = response.getBoolean("free"+i);
                                int totalViews = response.getInt("views"+i);
                                int totalLikes = response.getInt("likes"+i);

                                listTruyenDeXuat.add(new Book(0,0,name,"","",free,"","",totalViews,totalLikes));

                                adapterTruyenDeXuat.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            System.err.println(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        updateListSearch();
        adapterSearch.notifyDataSetChanged();
        return false;
    }
}
