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

import com.example.appdoctruyen.Activity.HistoryActivity;
import com.example.appdoctruyen.Activity.ListBookActivity;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.Activity.Subcription;

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
                Intent intent=new Intent(getActivity(), Subcription.class);
                startActivity(intent);
            }
        });
        bt2=view.findViewById(R.id.btlove);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ListBookActivity.class);
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
//                Intent intent=new Intent(getContext(), Subcription.class);
//                startActivity(intent);
//                break;
//        }
//    }
}
