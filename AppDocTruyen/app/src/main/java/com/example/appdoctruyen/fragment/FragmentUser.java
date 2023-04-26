package com.example.appdoctruyen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.Activity.AuthActivity;
import com.example.appdoctruyen.Activity.ChapterActivity;
import com.example.appdoctruyen.Activity.HistoryActivity;
import com.example.appdoctruyen.Activity.ListBookActivity;
import com.example.appdoctruyen.Activity.MainActivity;
import com.example.appdoctruyen.Activity.SubscriptionActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.CurrentUser;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.admin.activity.AdminActivity;
import com.example.appdoctruyen.model.Book;
import com.example.appdoctruyen.model.Subscription;
import com.example.appdoctruyen.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentUser extends Fragment {
    Button bt1,bt2,bt3;
    CardView cardView;
    TextView months, time;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bt1=view.findViewById(R.id.btsub);
        cardView = view.findViewById(R.id.cardView);
        months = view.findViewById(R.id.months);
        time = view.findViewById(R.id.time);

        ServerInfo serverInfo = new ServerInfo(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = serverInfo.getUserServiceUrl()+"api/checkPayment/";

        CurrentUser currentUser = new CurrentUser(getActivity());
        Map<String,String> body = new HashMap<>();

        body.put("userID", String.valueOf(currentUser.getCurrentUser().getId()));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    months.setText(jsonObject.getString("months") + " tháng");
                    time.setText(jsonObject.getString("end_at").substring(0,10));
                    bt1.setVisibility(View.GONE);
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
                    cardView.setVisibility(View.GONE);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                } catch (NullPointerException e){
                    System.out.println("Server is offline....");
                    Toast.makeText(getActivity(),"Server is offline....",Toast.LENGTH_SHORT);
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
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
            }
        });
        bt2=view.findViewById(R.id.btlove);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListBookActivity.class);
                intent.putExtra("mode",5);
                startActivity(intent);

            }
        });
        bt3=view.findViewById(R.id.bthis);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btsub:
//                Intent intent=new Intent(getContext(), SubscriptionActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }
}
