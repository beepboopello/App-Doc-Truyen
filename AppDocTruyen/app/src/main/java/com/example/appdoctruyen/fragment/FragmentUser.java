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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.Activity.HistoryActivity;
import com.example.appdoctruyen.Activity.ListBookActivity;
import com.example.appdoctruyen.Activity.SubscriptionActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.CurrentUser;
import com.example.appdoctruyen.SQLite.ServerInfo;
import com.example.appdoctruyen.model.Book;
import com.example.appdoctruyen.model.Subscription;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bt1=view.findViewById(R.id.btsub);
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
