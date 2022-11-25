package com.moutamid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moutamid.car_gps_app.MaintenanceInfoDetails;
import com.moutamid.car_gps_app.R;

public class LocationSettingsFragment extends Fragment {

    private LinearLayout card1,card2,card3,card4,card5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_settings_fragment,container,false);
        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MaintenanceInfoDetails.class);
                intent.putExtra("title","Etat du parc");
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MaintenanceInfoDetails.class);
                intent.putExtra("title","Etat Vidange");
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MaintenanceInfoDetails.class);
                intent.putExtra("title","Etat Assurance");
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MaintenanceInfoDetails.class);
                intent.putExtra("title","Etat Visite Tecnique");
                startActivity(intent);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MaintenanceInfoDetails.class);
                intent.putExtra("title","Entretiens");
                startActivity(intent);
            }
        });

        return view;
    }
}
