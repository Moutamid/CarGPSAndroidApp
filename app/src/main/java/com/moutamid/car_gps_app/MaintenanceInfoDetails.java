package com.moutamid.car_gps_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MaintenanceInfoDetails extends AppCompatActivity {

    private ImageView backImg;
    private TextView titleTxt;
    private String title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_info_details);
        backImg = findViewById(R.id.back);
        titleTxt = findViewById(R.id.title);
        title = getIntent().getStringExtra("title");
        titleTxt.setText(title);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MaintenanceInfoDetails.this,MainActivity.class));
                finish();
            }
        });
    }
}