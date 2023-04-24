package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.SQLite.ServerInfo;

public class ServerIPActivity extends AppCompatActivity {
    private EditText editIp, editUserPort, editContentPort, editPaymentPort;
    private Button btnIp, btnDefault;

//    private static final String DEFAULT_SERVER_IP = "10.0.2.2";

    private static final String DEFAULT_SERVER_IP = "192.168.1.11";
    private static final String DEFAULT_USER_SERVICE_PORT = "8000";
    private static final String DEFAULT_CONTENT_SERVICE_PORT = "8000";
    private static final String DEFAULT_PAYMENT_SERVICE_PORT = "8001";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serverip);

        editIp = findViewById(R.id.editIp);
        editUserPort = findViewById(R.id.editUserPort);
        editContentPort = findViewById(R.id.editContentPort);
        editPaymentPort = findViewById(R.id.editPaymentPort);
        btnIp = findViewById(R.id.btnIp);

        btnDefault = findViewById(R.id.btnDefault);

        btnIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = editIp.getText().toString();
                String userPort, contentPort, paymentPort;
                userPort = editUserPort.getText().toString();
                contentPort = editContentPort.getText().toString();
                paymentPort = editPaymentPort.getText().toString();
                if(validate(ServerIPActivity.this, ip, userPort, contentPort, paymentPort)){
                    finish();
                    Intent intent = new Intent(ServerIPActivity.this, AuthActivity.class);
                    startActivity(intent);
                };

            }
        });

        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(ServerIPActivity.this,
                        DEFAULT_SERVER_IP,
                        DEFAULT_USER_SERVICE_PORT,
                        DEFAULT_CONTENT_SERVICE_PORT,
                        DEFAULT_PAYMENT_SERVICE_PORT)){
                    finish();
                    Intent intent = new Intent(ServerIPActivity.this, AuthActivity.class);
                    startActivity(intent);
                };
            }
        });


    }

    public static boolean validate(Context context, String ip, String userPort, String contentPort, String paymentPort){
        if(!validate_ip(ip)){
            Toast.makeText(context, "Invalid ip", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!server_valid_port(userPort)){
            Toast.makeText(context, "Invalid user service port", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!server_valid_port(contentPort)){
            Toast.makeText(context, "Invalid content service port", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!server_valid_port(paymentPort)){
            Toast.makeText(context,"Invalid payment service port",Toast.LENGTH_SHORT).show();
            return false;
        }
        ServerInfo serverInfo = new ServerInfo(context, ip, userPort, contentPort, paymentPort);
        return true;
    }

    public static boolean server_valid_port(final String port){
        if(port.isEmpty() || Integer.valueOf(port)<1024 || Integer.valueOf(port)>49151){
            return false;
        }
        return true;
    }

    public static boolean validate_ip(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }
}