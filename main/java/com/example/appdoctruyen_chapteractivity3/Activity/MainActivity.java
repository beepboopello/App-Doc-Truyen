package com.example.appdoctruyen_chapteractivity3.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appdoctruyen_chapteractivity3.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this,ChapterActivity.class);
        intent.putExtra("chapterid",3);
        intent.putExtra("title","Đấu la đại lục 3");
        startActivity(intent);
    }
}