package com.example.appdoctruyen.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.admin.activity.AddSubcriptionActivity;
import com.example.appdoctruyen.admin.activity.UpDelSubcriptionActivity;
import com.example.appdoctruyen.admin.adapter.RViewSubcriptionAdapter;
import com.example.appdoctruyen.admin.model.ItemSubcriptionRview;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


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

        // set item cho r_view
        adapter= new RViewSubcriptionAdapter();
        ArrayList<ItemSubcriptionRview> list = new ArrayList<>();

        ServerInfo serverInfo = new ServerInfo(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = serverInfo.getUserServiceUrl()+"api/listPaymentPacket/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray paymentPackages = jsonObject.getJSONArray("data");
                    for (int i=0;i<paymentPackages.length();i++){
                        JSONObject paymentPackage = paymentPackages.getJSONObject(i);
                        String id = String.valueOf(paymentPackage.getInt("id"));
                        String price = String.valueOf(paymentPackage.getInt("price"));
                        String months = String.valueOf(paymentPackage.getInt("months"));
                        ItemSubcriptionRview item = new ItemSubcriptionRview(id,months,price);
                        list.add(item);
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
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
                    Toast.makeText(getActivity(),jsonObject.getString("error"),Toast.LENGTH_SHORT);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e){
                    System.out.println("Server is offline....");
                    Toast.makeText(getActivity(),"Server is offline....",Toast.LENGTH_SHORT);
                }

                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT);
            }
        });
        queue.add(stringRequest);

        // set layout cho r_view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        // set listener event
        adapter.setItemListener(this);
    }

    private void initView(View view) {
//        Fragment2.lst = new ArrayList<>();

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
        ArrayList<ItemSubcriptionRview> list = new ArrayList<>();

        ServerInfo serverInfo = new ServerInfo(getActivity());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = serverInfo.getContentServiceUrl()+"api/listPaymentPacket/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray paymentPackages = jsonObject.getJSONArray("data");
                    for (int i=0;i<paymentPackages.length();i++){
                        JSONObject paymentPackage = paymentPackages.getJSONObject(i);
                        String id = String.valueOf(paymentPackage.getInt("id"));
                        String price = String.valueOf(paymentPackage.getInt("price"));
                        String months = String.valueOf(paymentPackage.getInt("months"));
                        ItemSubcriptionRview item = new ItemSubcriptionRview(id,months,price);
                        list.add(item);
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
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
                    Toast.makeText(getActivity(),jsonObject.getString("error"),Toast.LENGTH_SHORT);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e){
                    System.out.println("Server is offline....");
                    Toast.makeText(getActivity(),"Server is offline....",Toast.LENGTH_SHORT);
                }

                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT);
            }
        });
        queue.add(stringRequest);
    }
}
