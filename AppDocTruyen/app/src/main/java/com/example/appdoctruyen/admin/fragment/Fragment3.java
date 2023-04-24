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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.admin.activity.StatisticHistoryDetailActivity;
import com.example.appdoctruyen.admin.adapter.RviewStatisticAdapter;
import com.example.appdoctruyen.admin.model.ItemStatisticRview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment implements RviewStatisticAdapter.ItemListener{
    private RadioGroup radioGroup;

    private RecyclerView recyclerView;
    private RviewStatisticAdapter adapter;

    private static String selectedRadioButton;

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

    private void setList(){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Fragment3.selectedRadioButton = "Tháng";
        }
        else {
            RadioButton radioButton= (RadioButton)radioGroup.findViewById(selectedId);
            Fragment3.selectedRadioButton = radioButton.getText().toString();
        }
        if(Fragment3.selectedRadioButton.equals("Năm")){
//            get list by month
            ArrayList<ItemStatisticRview> ls = new ArrayList<>();
            ServerInfo serverInfo = new ServerInfo(getActivity());
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            String url = serverInfo.getUserServiceUrl()+"api/PaymentStatisticByYear/";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray paymentPackages = jsonObject.getJSONArray("data");
                        for (int i=0;i<paymentPackages.length();i++){
                            JSONObject paymentPackage = paymentPackages.getJSONObject(i);
                            String year = String.valueOf(paymentPackage.getInt("year"));
                            String income = String.valueOf(paymentPackage.getInt("income"));
                            String date = "Năm "+year;
                            ItemStatisticRview item = new ItemStatisticRview(date,income);
                            ls.add(item);
                        }
                        adapter.setList(ls);
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
        else{
//          get list by month
            ArrayList<ItemStatisticRview> ls = new ArrayList<>();

            ServerInfo serverInfo = new ServerInfo(getActivity());
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            String url = serverInfo.getUserServiceUrl()+"api/PaymentStatisticByMonth/";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray paymentPackages = jsonObject.getJSONArray("data");
                        for (int i=0;i<paymentPackages.length();i++){
                            JSONObject paymentPackage = paymentPackages.getJSONObject(i);
                            String year = String.valueOf(paymentPackage.getInt("year"));
                            String income = String.valueOf(paymentPackage.getInt("income"));
                            String month = String.valueOf(paymentPackage.getInt("month"));
                            String date = "Tháng  "+month+" năm "+year;
                            ItemStatisticRview item = new ItemStatisticRview(date,month,year,income);
                            ls.add(item);

                        }
                        adapter.setList(ls);
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

    private void initView(View view) {

        Fragment3.selectedRadioButton = "Tháng";
        radioGroup = view.findViewById(R.id.groupradio);

        recyclerView = view.findViewById(R.id.recycleView);
        adapter= new RviewStatisticAdapter();
        setList();
        // set layout cho r_view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        // set listener event
        adapter.setItemListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                Fragment3.selectedRadioButton=radioButton.getText().toString();
                setList();
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        if(Fragment3.selectedRadioButton.equals("Tháng")){
            ItemStatisticRview item = adapter.getItem(position);
            // //pass item ->  UpDelActivity
            Intent intent = new Intent(getActivity(), StatisticHistoryDetailActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setList();
    }
}
