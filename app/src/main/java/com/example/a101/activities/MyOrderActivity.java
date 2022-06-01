package com.example.a101.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.a101.R;
import com.example.a101.db.DatabaseHelper;
import com.example.a101.adapter.OrderAdapter;
import com.example.a101.model.Order;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity implements OrderAdapter.OnRowClickListener {
    String username;
    DatabaseHelper db;
    Integer[] goodImageList = {R.drawable.furniture, R.drawable.food, R.drawable.drygood,R.drawable.buildingmaterial,R.drawable.other};
    List<Order> orderList = new ArrayList<>();
    List<Order> tempt;
    RecyclerView orderRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        ImageButton addNewOrderButton2 = findViewById(R.id.addNewOrderButton2);

        db = new DatabaseHelper(this);
        tempt = db.fetchAllOrders(username);

        for (int i = 0; i < tempt.size(); i++) {

            Order order = new Order(tempt.get(i).getReceiverName(), tempt.get(i).getDropoffTime(), goodImageList[i]);
            orderList.add(order);

        }
        orderRecycleView = findViewById(R.id.orderRecycleView);
        OrderAdapter orderAdapter = new OrderAdapter(MyOrderActivity.this, orderList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        orderRecycleView.setLayoutManager(layoutManager);
        orderRecycleView.setAdapter(orderAdapter);

        addNewOrderButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), AddNewOrderActivity.class);
                intent2.putExtra("username", username);
                startActivity(intent2);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_item:
                Intent intent3 = new Intent(getApplicationContext(), HomeActivity.class);
                intent3.putExtra("username",username);
                startActivity(intent3);
                finish();
                return true;
            case R.id.order_item:
                Intent intent4 = new Intent(getApplicationContext(), MyOrderActivity.class);
                intent4.putExtra("username",username);
                startActivity(intent4);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        String receivername = tempt.get(position).getReceiverName();
        String dropofftime = tempt.get(position).getDropoffTime();
        String pickuptime = tempt.get(position).getPickupTime();
        String weight = tempt.get(position).getWeight();
        String height = tempt.get(position).getHeight();
        String width = tempt.get(position).getWidth();
        String length = tempt.get(position).getLength();
        String quantity = tempt.get(position).getQuantity();
        String type = tempt.get(position).getType();
        String pickupLocation = tempt.get(position).getPickupLocation();
        double pickupLatitude = tempt.get(position).getPickupLatitude();
        double pickupLongitude = tempt.get(position).getPickupLongitude();
        String dropoffLocation = tempt.get(position).getDropoffLocation();
        double dropoffLatitude = tempt.get(position).getDropoffLatitude();
        double dropoffLongitude = tempt.get(position).getDropoffLongitude();
        int image = goodImageList[position];

        Intent orderDetailIntent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
        orderDetailIntent.putExtra("username",username);
        orderDetailIntent.putExtra("receivername",receivername);
        orderDetailIntent.putExtra("dropofftime",dropofftime);
        orderDetailIntent.putExtra("pickuptime",pickuptime);
        orderDetailIntent.putExtra("weight",weight);
        orderDetailIntent.putExtra("height",height);
        orderDetailIntent.putExtra("width",width);
        orderDetailIntent.putExtra("length",length);
        orderDetailIntent.putExtra("quantity",quantity);
        orderDetailIntent.putExtra("type",type);
        orderDetailIntent.putExtra("image",image);
        orderDetailIntent.putExtra("pickupLocation",pickupLocation);
        orderDetailIntent.putExtra("pickupLatitude",pickupLatitude);
        orderDetailIntent.putExtra("pickupLongitude",pickupLongitude);
        orderDetailIntent.putExtra("dropoffLocation",dropoffLocation);
        orderDetailIntent.putExtra("dropoffLatitude",dropoffLatitude);
        orderDetailIntent.putExtra("dropoffLongitude",dropoffLongitude);


        startActivity(orderDetailIntent);

    }

    @Override
    public void onShareButtonClick2(View v, int position) {
        Intent sharedIntent = new Intent(Intent.ACTION_SEND);
        sharedIntent.setType("text/plain");
        String body = "Share your order";
        sharedIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(sharedIntent, "Share Order"));
    }
}