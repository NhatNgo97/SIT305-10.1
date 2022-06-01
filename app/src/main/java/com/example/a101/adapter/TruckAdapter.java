package com.example.a101.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a101.R;
import com.example.a101.model.Truck;

import java.util.List;

public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.TruckViewHolder> {

    private Context context;
    private List<Truck> truckList;
    private OnRowClickListener listener;

    public TruckAdapter(Context context, List<Truck> truckList, OnRowClickListener listener) {
        this.context = context;
        this.truckList = truckList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TruckAdapter.TruckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.truck_row,parent,false);
        return new TruckViewHolder(itemView, listener );
    }
    public interface OnRowClickListener {
        void onItemClick (int position);
        void onShareButtonClick(View v, int position);

    }
    @Override
    public void onBindViewHolder(@NonNull TruckAdapter.TruckViewHolder holder, int position) {
        holder.truckImageView.setImageResource(truckList.get(position).getImage());
        holder.truckNameTextView.setText(truckList.get(position).getTruckname());
        holder.truckDescriptionTextView.setText(truckList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return truckList.size();
    }

    public class TruckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView truckImageView ;
        public TextView truckNameTextView;
        public TextView truckDescriptionTextView;
        public ImageButton shareButton ;
        public OnRowClickListener OnRowClickListener;

        public TruckViewHolder(@NonNull View itemView, OnRowClickListener OnRowClickListener) {
            super(itemView);
            truckImageView = itemView.findViewById(R.id.truckImageView);
            truckNameTextView = itemView.findViewById(R.id.truckNameTextView);
            truckDescriptionTextView = itemView.findViewById(R.id.truckDescriptionTextView);
            shareButton  =  itemView.findViewById(R.id.shareImageButton);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnRowClickListener.onShareButtonClick(view,getAdapterPosition());

                }
            });

            this.OnRowClickListener = OnRowClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            OnRowClickListener.onItemClick(getAdapterPosition());
        }
    }
}