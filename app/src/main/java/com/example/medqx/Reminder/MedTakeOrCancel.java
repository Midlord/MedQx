package com.example.medqx.Reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medqx.R;

public class MedTakeOrCancel extends AppCompatActivity {
    Button btnTake, btnMedCancel;
    TextView tv_MedTime, tv_GP;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_takeor_cancel);
        btnTake=findViewById(R.id.btnTake);
        btnMedCancel=findViewById(R.id.btnMedCancel);
        tv_MedTime=findViewById(R.id.tv_MedTime);
        tv_GP=findViewById(R.id.tv_GP);

        btnTake.setOnClickListener(v -> {



        });



        btnMedCancel.setOnClickListener(v -> {



        });


    }
}