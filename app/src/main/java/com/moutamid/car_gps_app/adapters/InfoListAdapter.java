package com.moutamid.car_gps_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.car_gps_app.MaintenanceDetails;
import com.moutamid.car_gps_app.R;
import com.moutamid.car_gps_app.listener.ItemPressListener;
import com.moutamid.car_gps_app.model.CarDetails;

import java.util.ArrayList;

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.PositionViewHolder>{

    private Context mContext;
    private ArrayList<CarDetails> positionArrayList;
    private ItemPressListener itemPressListener;

    public InfoListAdapter(Context mContext, ArrayList<CarDetails> positionArrayList) {
        this.mContext = mContext;
        this.positionArrayList = positionArrayList;
    }

    @NonNull
    @Override
    public PositionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_list_custom_layout,parent,false);
        return new PositionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PositionViewHolder holder, int position) {
        CarDetails model = positionArrayList.get(position);
        holder.nametxt.setText(model.getCar());
        holder.timttext.setText(model.getTime());

        if (model.getStatus().equals("moving")){
            holder.posImg.setImageResource(R.drawable.ic_baseline_directions_24);
        }else {
            holder.posImg.setImageResource(R.drawable.ic_baseline_local_parking_24);
        }

        boolean isVisible = model.isVisibility();
        holder.parentCard.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MaintenanceDetails.class);
                intent.putExtra("title","Draining");
                intent.putExtra("carDetails",model);
                mContext.startActivity(intent);
            }
        });

        holder.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MaintenanceDetails.class);
                intent.putExtra("title","Insurance");
                intent.putExtra("carDetails",model);
                mContext.startActivity(intent);
            }
        });
        holder.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MaintenanceDetails.class);
                intent.putExtra("title","Technical Visit");
                intent.putExtra("carDetails",model);
                mContext.startActivity(intent);
            }
        });
        holder.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MaintenanceDetails.class);
                intent.putExtra("title","Entretiens");
                intent.putExtra("carDetails",model);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return positionArrayList.size();
    }

    public class PositionViewHolder extends RecyclerView.ViewHolder{

        TextView nametxt,timttext;
        ImageView posImg;
        private CardView parentCard,cardView1,cardView2,cardView3,cardView4;

        public PositionViewHolder(@NonNull View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.text1);
            timttext = itemView.findViewById(R.id.text3);
            posImg = itemView.findViewById(R.id.positionIcon);
            parentCard = itemView.findViewById(R.id.cardView);
            cardView1 = itemView.findViewById(R.id.card1);
            cardView2 = itemView.findViewById(R.id.card2);
            cardView3 = itemView.findViewById(R.id.card3);
            cardView4 = itemView.findViewById(R.id.card4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemPressListener != null){
                        itemPressListener.onItemClick(getAdapterPosition(),view);
                    }
                }
            });
        }
    }

    public void setItemPressListener(ItemPressListener itemPressListener){
        this.itemPressListener = itemPressListener;
    }
}
