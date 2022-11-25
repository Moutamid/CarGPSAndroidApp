package com.moutamid.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.car_gps_app.R;
import com.moutamid.car_gps_app.adapters.InfoListAdapter;
import com.moutamid.car_gps_app.listener.ItemPressListener;
import com.moutamid.car_gps_app.model.CarDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class VehicleInfoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CarDetails> positionArrayList;
    private DatabaseReference db;
    private ImageView closeImg;
    private EditText nameTxt,kmTxt;
    private TextView registerBtn;
    private LinearLayout layout;
    private DatabaseReference db1;
    private String id = "";

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vehicle_info_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        closeImg = view.findViewById(R.id.close);
        nameTxt = view.findViewById(R.id.name);
        kmTxt = view.findViewById(R.id.km);
        registerBtn = view.findViewById(R.id.register);
        layout = view.findViewById(R.id.linear);
        db1 = FirebaseDatabase.getInstance().getReference().child("Maintenance");
        positionArrayList = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference().child("Car");
        getPosition();
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private void getPosition() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    positionArrayList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        CarDetails model = ds.getValue(CarDetails.class);
                        positionArrayList.add(model);
                    }

                    InfoListAdapter adapter = new
                            InfoListAdapter(getActivity(),positionArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setItemPressListener(new ItemPressListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            CarDetails details = positionArrayList.get(position);
                            layout.setVisibility(View.VISIBLE);
                            nameTxt.setText(details.getCar());
                            id = details.getId();

                            registerBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String km = kmTxt.getText().toString();
                                    if (!TextUtils.isEmpty(km)){
                                        HashMap<String,Object> hashMap = new HashMap<>();
                                        hashMap.put("speed",km);
                                        db.child(id).updateChildren(hashMap);
                                        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                                        layout.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(getActivity(), "Enter km value", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
