package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoctruyen.R;

public class Register extends AppCompatActivity {

    Button btn;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn = findViewById(R.id.signUpBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Intent t = getIntent();
                String email = t.getStringExtra("email");
                Toast.makeText(Register.this, "Du lieu dung de goi api"
                        + user + " "
                        + pass + " "
                        + email, Toast.LENGTH_SHORT).show();
            }
        });
    }
}