package com.moutamid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.car_gps_app.PositionDetails;
import com.moutamid.car_gps_app.R;
import com.moutamid.car_gps_app.adapters.NotificationListAdapter;
import com.moutamid.car_gps_app.adapters.PositionListAdapter;
import com.moutamid.car_gps_app.listener.ItemPressListener;
import com.moutamid.car_gps_app.model.CarDetails;

import java.util.ArrayList;

public class MyNotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CarDetails> positionArrayList;
    private DatabaseReference db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_notification_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        positionArrayList = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference().child("Car");
        getPosition();

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

                    NotificationListAdapter adapter = new
                            NotificationListAdapter(getActivity(),positionArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setItemPressListener(new ItemPressListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            Intent intent = new Intent(getActivity(), PositionDetails.class);
                            intent.putExtra("carDetail",positionArrayList.get(position));
                            intent.putExtra("title","notification");
                            startActivity(intent);
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
