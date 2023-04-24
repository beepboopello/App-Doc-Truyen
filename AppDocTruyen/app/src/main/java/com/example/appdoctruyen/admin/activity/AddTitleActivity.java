package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdoctruyen.R;

public class AddTitleActivity extends AppCompatActivity {
    EditText genreid;
    EditText userid, name, des, author;
    CheckBox cbFree;
    Button btnAddTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);
        genreid=findViewById(R.id.add_title_genreid);
        name=findViewById(R.id.add_title_name);
        des=findViewById(R.id.add_title_des);
        author=findViewById(R.id.add_title_author);
        cbFree=findViewById(R.id.ckFeeAddTitle);
        btnAddTitle=findViewById(R.id.btnAddTitle);
        btnAddTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Them truyen thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}