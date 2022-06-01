package com.example.a101.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a101.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String receivername = intent.getStringExtra("receivername");
        String dropofftime = intent.getStringExtra("dropofftime");
        String pickuptime = intent.getStringExtra("pickuptime");
        String weight = intent.getStringExtra("weight");
        String height = intent.getStringExtra("height");
        String width = intent.getStringExtra("width");
        String length = intent.getStringExtra("length");
        String quantity = intent.getStringExtra("quantity");
        String type = intent.getStringExtra("type");
        int image = intent.getIntExtra("image",0);
        String pickupLocation = intent.getStringExtra("pickupLocation");
        double pickupLatitude = intent.getDoubleExtra("pickupLatitude",0);
        double pickupLongitude = intent.getDoubleExtra("pickupLongitude",0);
        String dropoffLocation = intent.getStringExtra("dropoffLocation");
        double dropoffLatitude = intent.getDoubleExtra("dropoffLatitude",0);
        double dropoffLongitude = intent.getDoubleExtra("dropoffLongitude",0);

        TextView senderTextView = findViewById(R.id.senderTextView);
        TextView receiverTextView = findViewById(R.id.receiverTextView);
        TextView pickupTimeTextView = findViewById(R.id.pickupTimeTextView);
        TextView dropoffTimeTextView = findViewById(R.id.dropoffTimeTextView);
        TextView weightTextView = findViewById(R.id.weightTextView);
        TextView lengthTextView = findViewById(R.id.lengthTextView);
        TextView widthTextView = findViewById(R.id.widthTextView);
        TextView heightTextView = findViewById(R.id.heightTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        ImageView orderDetailImageView = findViewById(R.id.orderDetailImageView);
        Button getEstimateButton = findViewById(R.id.getEstimateButton);

        senderTextView.setText("From sender " + username);
        pickupTimeTextView.setText("Pick up time: " + pickuptime);
        receiverTextView.setText("To receiver "+ receivername);
        dropoffTimeTextView.setText("Drop off time: "+dropofftime);
        weightTextView.setText("Weight: "+weight);
        widthTextView.setText("Width: "+width);
        lengthTextView.setText("Length: "+length);
        heightTextView.setText("Height: "+height);
        typeTextView.setText("Type: "+type);
        quantityTextView.setText("Quantity: "+quantity);
        orderDetailImageView.setImageResource(image);

        getEstimateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapActivityIntent=new Intent(getApplicationContext(),MapsActivity.class);
                mapActivityIntent.putExtra("pickupLocation",pickupLocation);
                mapActivityIntent.putExtra("pickupLatitude",pickupLatitude);
                mapActivityIntent.putExtra("pickupLongitude",pickupLongitude);
                mapActivityIntent.putExtra("dropoffLocation",dropoffLocation);
                mapActivityIntent.putExtra("dropoffLatitude",dropoffLatitude);
                mapActivityIntent.putExtra("dropoffLongitude",dropoffLongitude);
                startActivity(mapActivityIntent);
            }
        });
    }
}