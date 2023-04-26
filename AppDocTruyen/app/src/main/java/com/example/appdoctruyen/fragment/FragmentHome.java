package com.example.appdoctruyen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.example.appdoctruyen.Activity.BookActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.adapter.RecycleViewAdapter;
import com.example.appdoctruyen.model.Book;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
//import com.example.appdoctruyen_fragmenthome.Activity.BookActivity;
//import com.example.appdoctruyen_fragmenthome.R;
//import com.example.appdoctruyen_fragmenthome.adapter.RecycleViewAdapter;
//import com.example.appdoctruyen_fragmenthome.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener, SearchView.OnQueryTextListener{
    private RecyclerView recyclerViewTruyenDeXuat;
    private RecycleViewAdapter adapter;
    private SearchView searchView;
    private TextView txt;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewTruyenDeXuat=view.findViewById(R.id.recycleViewTruyenDeXuat);
        searchView=view.findViewById(R.id.searchView);

        txt = view.findViewById(R.id.txt);
        updateListTruyenDeXuat();

        adapter =new RecycleViewAdapter();
        recyclerViewTruyenDeXuat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTruyenDeXuat.setAdapter(adapter);
        adapter.setItemListener(this);

        searchView.setOnQueryTextListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {

        Book book=adapter.getItem(position);
        int idbook =Integer.parseInt(book.getId());
        Intent intent=new Intent(getActivity(), BookActivity.class);
        intent.putExtra("idbook",idbook);
        startActivity(intent);
    }


    public void updateListSearch(){
        ServerInfo serverInfo = new ServerInfo(getActivity());
        String url = serverInfo.getUserServiceUrl()+"api/title/search_title_by_name_or_author/";

        String search = String.valueOf(searchView.getQuery());

        if(search==null||search.equals("")){
            updateListTruyenDeXuat();
        }

        Map<String,String> body = new HashMap<>();
        url +="?name=" + search + "&author=" + search;
//        body.put("name_search",search);
//        body.put("author_search",search);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Book> list = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Book book = new Book(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                        jsonObject.getInt("views"),
                                        jsonObject.getInt("like")
                                );
                                list.add(book);

                                adapter.setList(list);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            System.err.println(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(getActivity(),jsonObject.getString("error"),Toast.LENGTH_SHORT);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                            System.out.println("Server is offline....");
                            Toast.makeText(getActivity(),"Server is offline....",Toast.LENGTH_SHORT);
                        }
                    }
                    protected Map<String,String> getParams(){
                        return body;
                    }
                });
        txt.setText("Kết quả tìm kiếm");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void updateListTruyenDeXuat(){

        ServerInfo serverInfo = new ServerInfo(getActivity());
        String url = serverInfo.getUserServiceUrl()+ "api/title/get_list_favorite_title/";

        Map<String,String> body = new HashMap<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Book> list = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Book book = new Book(jsonObject.getString("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("author"),
                                        jsonObject.getString("description"),
                                        jsonObject.getBoolean("fee") ? "Mien phi" : "Co phi",
                                        jsonObject.getInt("views"),
                                        jsonObject.getInt("like")
                                );
                                list.add(book);

                                adapter.setList(list);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            System.err.println(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(getActivity(),jsonObject.getString("error"),Toast.LENGTH_SHORT);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                            System.out.println("Server is offline....");
                            Toast.makeText(getActivity(),"Server is offline....",Toast.LENGTH_SHORT);
                        }
                    }
                    protected Map<String,String> getParams(){
                        return body;
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.setList(new ArrayList<>());
        updateListSearch();
        adapter.notifyDataSetChanged();
        return false;
    }
}
