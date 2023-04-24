package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Title;

public class TitleUpdateActivity extends AppCompatActivity {

    EditText userid, titleid, name, description, author;
    CheckBox ck;
    Button btnupdate, btndelete;

    private Title title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_update);
        userid=findViewById(R.id.edtTitleIDUser);
        titleid=findViewById(R.id.edt_title_id);
        name=findViewById(R.id.edtTitleName);
        description=findViewById(R.id.edtTitleDes);
        author=findViewById(R.id.edtTitleAuthor);
        ck=findViewById(R.id.updateTitleCK);
        btndelete=findViewById(R.id.TitleDelete);
        btnupdate=findViewById(R.id.TitleUpdate);
        Intent intent=getIntent();
        title = (Title) intent.getSerializableExtra("item");

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleUpdateActivity.this, "Xoa truyen thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleUpdateActivity.this, "Cap nhat truyen thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}