package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoctruyen.R;

public class AddSubcriptionActivity extends AppCompatActivity {

    private EditText tv_price,tv_month;
    private Button btn_add,btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subcription);
        initView();
        btnCancelAction();
        btnAddAction();
    }

    private void btnAddAction() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = tv_price.getText().toString();
                String month = tv_month.getText().toString();
                if(month.isEmpty() || price.isEmpty()){
                    Toast.makeText(AddSubcriptionActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(month.matches("\\d+") && price.matches("\\d+") ){
                    //gọi api
                    Toast.makeText(AddSubcriptionActivity.this, "Thêm gói thanh toán thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(AddSubcriptionActivity.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void btnCancelAction() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tv_price = findViewById(R.id.tv_price);
        tv_month = findViewById(R.id.tv_month);
        btn_add = findViewById(R.id.btn_add);
        btn_cancel = findViewById(R.id.btn_cancel);
    }
}