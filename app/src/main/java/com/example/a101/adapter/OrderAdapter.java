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
import com.example.a101.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private Context context;
    private List<Order> orderList;
    private OrderAdapter.OnRowClickListener listener;

    public OrderAdapter(Context context, List<Order> orderList, OrderAdapter.OnRowClickListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;
    }

    public interface OnRowClickListener {
        void onItemClick (int position);
        void onShareButtonClick2(View v, int position);


    }
    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_row,parent,false);
        return new OrderAdapter.OrderViewHolder(itemView, listener );
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        holder.orderImage.setImageResource(orderList.get(position).getImage());
        holder.orderNameTextView.setText(orderList.get(position).getReceiverName());
        holder.orderDescriptionTextView.setText("Drop off time: "+orderList.get(position).getDropoffTime());
    }

    @Override
    public int getItemCount() {
        return orderList.size();

    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView orderImage ;
        public TextView orderNameTextView;
        public TextView orderDescriptionTextView;
        public ImageButton shareOrderButton ;
        public OnRowClickListener OnRowClickListener;

        public OrderViewHolder(@NonNull View itemView, OnRowClickListener OnRowClickListener) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.orderImage);
            orderNameTextView = itemView.findViewById(R.id.orderNameTextView);
            orderDescriptionTextView = itemView.findViewById(R.id.orderDescriptionTextView);
            shareOrderButton = itemView.findViewById(R.id.shareOrderButton);
            shareOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnRowClickListener.onShareButtonClick2(view,getAdapterPosition());
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