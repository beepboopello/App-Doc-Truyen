package com.example.appdoctruyen.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.activity.AdminActivity;

public class MainActivityAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        Intent intent=new Intent(MainActivityAdmin.this, AdminActivity.class);
        startActivity(intent);
    }
}