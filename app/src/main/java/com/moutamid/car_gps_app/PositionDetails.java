package com.moutamid.car_gps_app;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moutamid.car_gps_app.model.CarDetails;

public class PositionDetails extends AppCompatActivity implements OnMapReadyCallback {

    private CarDetails model;
    private TextView carnameTxt,speedTxt,titleTxt;
    private GoogleMap mMap;
    private ImageView backImg;
    private ImageView shareImg,emojiImg,exploreImg,signalImg;
    private String title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_details);
        model = getIntent().getParcelableExtra("carDetail");
        title = getIntent().getStringExtra("title");
        carnameTxt = findViewById(R.id.car);
        titleTxt = findViewById(R.id.title);
        speedTxt = findViewById(R.id.speed);
        backImg = findViewById(R.id.back);
        shareImg = findViewById(R.id.share);
        emojiImg = findViewById(R.id.emoji);
        exploreImg = findViewById(R.id.dir);
        signalImg = findViewById(R.id.signal);
        carnameTxt.setText(model.getCar());
        double speed = Double.parseDouble(model.getSpeed());
        speedTxt.setText(speed + " km/h");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (title.equals("notification")){
            emojiImg.setVisibility(View.GONE);
            titleTxt.setText("START UP");
        }else {
            emojiImg.setVisibility(View.VISIBLE);
            titleTxt.setText("Position");
        }

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionDetails.this,MainActivity.class));
                finish();
            }
        });
        shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "https://www.google.com/maps/?q=" + model.getLat()+ "," +model.getLng() ;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,  uri);
                startActivity(Intent.createChooser(sharingIntent, "Share in..."));
            }
        });

        emojiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.waze.com/en-GB/live-map/directions?navigate=yes&to="+model.getLat()+","+model.getLng();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (title.equals("position")) {
            if (model.getStatus().equals("moving")) {
                LatLng latLng = new LatLng(model.getLat(), model.getLng());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(model.getCar());
                markerOptions.icon(bitmapDescriptorFromVector(this, R.drawable.cars));
                mMap.addMarker(markerOptions);

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            } else {
                LatLng latLng = new LatLng(model.getLat(), model.getLng());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(model.getCar());
                markerOptions.icon(bitmapDescriptorFromVector(this, R.drawable.ic_baseline_local_parking_24));
                mMap.addMarker(markerOptions);

                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            }
        }else {

            LatLng latLng = new LatLng(model.getLat(), model.getLng());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(model.getCar());
            markerOptions.icon(bitmapDescriptorFromVector(this, R.drawable.ic_baseline_directions_24));
            mMap.addMarker(markerOptions);

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}