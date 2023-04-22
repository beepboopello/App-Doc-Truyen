package com.example.appdoctruyen.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.model.ItemSubcriptionRview;

public class UpDelSubcriptionActivity extends AppCompatActivity {

    private EditText tv_price,tv_month;
    private Button btn_update,btn_cancel,btn_delete;
    private ItemSubcriptionRview item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_del_subcription);
        initView();
        getDataIntent();
        btnCancelAction();
        btnDeleteAction();
        btnUpdateAction();
    }

    private void getDataIntent() {
        Intent intent=getIntent();
        item = (ItemSubcriptionRview) intent.getSerializableExtra("item");
        tv_price.setText(item.getPrice());
        tv_month.setText(item.getMonth());
    }

    private void btnDeleteAction() {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               int id = item.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Bạn có chắc muốn xóa gói thanh toán thời hạn '" + item.getMonth() + "' tháng này không?");
                builder.setIcon(R.drawable.baseline_delete_24);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UpDelSubcriptionActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    private void btnUpdateAction() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = tv_price.getText().toString();
                String month = tv_month.getText().toString();
                if(month.isEmpty() || price.isEmpty()){
                    Toast.makeText(UpDelSubcriptionActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(month.matches("\\d+") && price.matches("\\d+") ){
                    //gọi api
                    Toast.makeText(UpDelSubcriptionActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(UpDelSubcriptionActivity.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
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
        btn_update = findViewById(R.id.btn_update);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_delete =findViewById(R.id.btn_delete);
    }
}