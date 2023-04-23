package com.example.appdoctruyen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.Activity.ListBookActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.adapter.RecycleViewAdapterGenreList;
import com.example.appdoctruyen.model.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentGenre extends Fragment implements RecycleViewAdapterGenreList.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapterGenreList adapter;
    Button btlove,btread,btfree,btnofree;
    int mode;
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
//        Genre b=new Genre("Trinh tham");
//        list.add(b);
        adapter.setList(list);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        Intent intent=new Intent(getActivity(),ListBookActivity.class);

        ServerInfo serverInfo = new ServerInfo( getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = serverInfo.getUserServiceUrl()+"api/genre/";

        Map<String,String> body = new HashMap<>();
//        body.put("username",username.getText().toString());
//        body.put("password",password.getText().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Genre g = new Genre(jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description")
                                );
                        list.add(g);
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
//                    System.out.println(jsonObject.get("name"));
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
                    Toast.makeText(getActivity(),jsonObject.getString("error"),Toast.LENGTH_LONG);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
            protected Map<String,String> getParams(){
                return body;
            }
        };
        queue.add(stringRequest);


        btlove=view.findViewById(R.id.btToplove);
        btlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode=1;
                intent.putExtra("mode",mode);
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

                mode=2;
                intent.putExtra("mode",mode);
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
        int idgenre =Integer.parseInt(genre.getId());
        System.out.println(idgenre);
        Intent intent=new Intent(getActivity(), ListBookActivity.class);
        intent.putExtra("idgenre",idgenre);
        startActivity(intent);
    }

}
