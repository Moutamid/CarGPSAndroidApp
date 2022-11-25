package com.moutamid.car_gps_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.car_gps_app.model.CarDetails;
import com.moutamid.car_gps_app.model.Maintenance;

import java.util.Calendar;
import java.util.HashMap;

public class MaintenanceDetails extends AppCompatActivity {

    private TextView registerBtn,dateTxt,sdateTxt,edateTxt,titleTxt,carTxt;
    private EditText nameTxt,priceTxt,kmStartTxt,kmEndTxt;
    private RelativeLayout dateLayout,sdateLayout,edateLayout,nameLayout,priceLayout,skmLayout,ekmLayout;
    private String title;
    private CarDetails model;
    private String sdate = "";
    private String edate = "";
    private String date = "";
    private String name,start_km,end_km,price = "";
    private DatabaseReference db;
    private ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_details);
        title = getIntent().getStringExtra("title");
        model = getIntent().getParcelableExtra("carDetails");
        titleTxt = findViewById(R.id.title);
        backImg = findViewById(R.id.back);
        carTxt = findViewById(R.id.car);
        carTxt.setText(model.getCar());
        titleTxt.setText(title);
        db = FirebaseDatabase.getInstance().getReference().child("Maintenance");
        registerBtn = findViewById(R.id.register);
        dateTxt = findViewById(R.id.date);
        sdateTxt = findViewById(R.id.date_start);
        edateTxt = findViewById(R.id.date_end);
        nameTxt = findViewById(R.id.name);
        priceTxt = findViewById(R.id.price);
        kmStartTxt = findViewById(R.id.km_start);
        kmEndTxt = findViewById(R.id.km_end);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MaintenanceDetails.this,MainActivity.class));
                finish();
            }
        });

        dateLayout = findViewById(R.id.date_layout);
        sdateLayout = findViewById(R.id.sdate_layout);
        edateLayout = findViewById(R.id.edate_layout);
        nameLayout = findViewById(R.id.name_layout);
        priceLayout = findViewById(R.id.price_layout);
        skmLayout = findViewById(R.id.km_start_layout);
        ekmLayout = findViewById(R.id.km_end_layout);

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(MaintenanceDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                        dateTxt.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        sdateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(MaintenanceDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sdate = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                        sdateTxt.setText(sdate);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        edateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(MaintenanceDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edate = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                        edateTxt.setText(edate);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        if (title.equals("Draining")){
            nameLayout.setVisibility(View.VISIBLE);
            dateLayout.setVisibility(View.VISIBLE);
            skmLayout.setVisibility(View.VISIBLE);
            ekmLayout.setVisibility(View.VISIBLE);
            priceLayout.setVisibility(View.GONE);
            sdateLayout.setVisibility(View.GONE);
            edateLayout.setVisibility(View.GONE);
        }else if (title.equals("Insurance")){
            nameLayout.setVisibility(View.VISIBLE);
            dateLayout.setVisibility(View.GONE);
            skmLayout.setVisibility(View.GONE);
            ekmLayout.setVisibility(View.GONE);
            priceLayout.setVisibility(View.GONE);
            sdateLayout.setVisibility(View.VISIBLE);
            edateLayout.setVisibility(View.VISIBLE);
        }
        else if (title.equals("Technical Visit")){
            nameLayout.setVisibility(View.VISIBLE);
            dateLayout.setVisibility(View.GONE);
            skmLayout.setVisibility(View.GONE);
            ekmLayout.setVisibility(View.GONE);
            priceLayout.setVisibility(View.GONE);
            sdateLayout.setVisibility(View.VISIBLE);
            edateLayout.setVisibility(View.VISIBLE);
        }
        else if (title.equals("Entretiens")){
            nameLayout.setVisibility(View.VISIBLE);
            dateLayout.setVisibility(View.VISIBLE);
            skmLayout.setVisibility(View.GONE);
            ekmLayout.setVisibility(View.GONE);
            priceLayout.setVisibility(View.VISIBLE);
            sdateLayout.setVisibility(View.GONE);
            edateLayout.setVisibility(View.GONE);
        }



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameTxt.getText().toString();
                price = priceTxt.getText().toString();
                start_km = kmStartTxt.getText().toString();
                end_km = kmEndTxt.getText().toString();

                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(price) || !TextUtils.isEmpty(start_km) ||
                        !TextUtils.isEmpty(end_km)){
                    saveSettings();
                }
            }
        });

        getDetails();

    }

    private void getDetails() {
        Query query = db.child(model.getId()).orderByChild("title").equalTo(title);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Maintenance maintenance = ds.getValue(Maintenance.class);
                        // if (maintenance.getTitle().equals(title)) {
                        name = maintenance.getName();
                        date = maintenance.getDate();
                        sdate = maintenance.getStartDate();
                        edate = maintenance.getEndDate();
                        start_km = maintenance.getStartKm();
                        end_km = maintenance.getEndKm();
                        price = maintenance.getPrice();
                        //}

                        nameTxt.setText(name);
                        dateTxt.setText(date);
                        sdateTxt.setText(sdate);
                        edateTxt.setText(edate);
                        kmStartTxt.setText(start_km);
                        kmEndTxt.setText(end_km);
                        priceTxt.setText(price);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveSettings() {

        String key = db.child(model.getId()).push().getKey();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",key);
        hashMap.put("name",name);
        hashMap.put("title",title);
        hashMap.put("date",date);
        hashMap.put("startDate",sdate);
        hashMap.put("endDate",edate);
        hashMap.put("startKm",start_km);
        hashMap.put("endKm",end_km);
        hashMap.put("price",price);
        db.child(model.getId()).child(key).updateChildren(hashMap);

        startActivity(new Intent(MaintenanceDetails.this,MainActivity.class));
        finish();
    }


}