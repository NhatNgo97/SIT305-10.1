package com.example.a101.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a101.R;
import com.example.a101.db.DatabaseHelper;
import com.example.a101.model.Order;

public class AddNewOrderActivity2 extends AppCompatActivity {
    String goodType;
    String vehicleType;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order2);

        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String receivername = intent.getStringExtra("receivername");
        String pickupTime = intent.getStringExtra("pickupTime");
        String dropoffTime = intent.getStringExtra("dropoffTime");
        String pickupLocation = intent.getStringExtra("pickupLocation");
        String dropoffLocation = intent.getStringExtra("dropoffLocation");
        Log.d(dropoffLocation, dropoffLocation);
        Log.d(pickupLocation, pickupLocation);
        double pickupLatitude = intent.getDoubleExtra("pickupLatitude",0);
        double pickupLongitude = intent.getDoubleExtra("pickupLongitude",0);
        double dropoffLatitude = intent.getDoubleExtra("dropoffLatitude",0);
        double dropoffLongitude = intent.getDoubleExtra("dropoffLongitude",0);
        Log.d("pickuplatittude", String.valueOf(pickupLatitude));

        TextView otherGood = findViewById(R.id.otherGoodTextView);
        EditText otherGoodInput = findViewById(R.id.otherGoodInput);

        TextView otherVehicle = findViewById(R.id.otherVehicleTextView);
        EditText otherVehicleInput = findViewById(R.id.otherVehicleInput);

        EditText widthInput = findViewById(R.id.widthInput);
        EditText heightInput = findViewById(R.id.heightInput);
        EditText lengthInput = findViewById(R.id.lengthInput);
        EditText weightInput = findViewById(R.id.weightInput);
        EditText quantityInput = findViewById(R.id.quantityInput);

        Button createOrderButton = findViewById(R.id.createOrderButton);

        Spinner goodSpinner = findViewById(R.id.goodSpinne);
        String[] items = new String[]{"Furniture", "Dry goods", "Food","Building materials","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        goodSpinner.setAdapter(adapter);
        goodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch(position){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        otherGood.setVisibility(View.INVISIBLE);
                        otherGoodInput.setVisibility(View.INVISIBLE);
                        otherGoodInput.setText("");

                        break;
                    case 4:
                        otherGood.setVisibility(View.VISIBLE);
                        otherGoodInput.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner vehicleSpinner = findViewById(R.id.vehicleSpinner);
        String[] vehicles = new String[]{"Truck", "Van", "Refrigerated Truck","Other"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehicles);
        vehicleSpinner.setAdapter(adapter2);
        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch(position){
                    case 0:
                    case 1:
                    case 2:
                        otherVehicle.setVisibility(View.INVISIBLE);
                        otherVehicleInput.setVisibility(View.INVISIBLE);
                        otherVehicleInput.setText("");
                        break;
                    case 3:
                        otherVehicle.setVisibility(View.VISIBLE);
                        otherVehicleInput.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        createOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weight = weightInput.getText().toString();
                String width = widthInput.getText().toString();
                String length = lengthInput.getText().toString();
                String height = heightInput.getText().toString();
                String quantity = quantityInput.getText().toString();

                String otherGood = otherGoodInput.getText().toString();

                if(otherGood.equals(""))
                {
                    goodType = goodSpinner.getSelectedItem().toString();
                }
                else
                {
                    goodType = otherGood;
                }

                String otherVehicle = otherVehicleInput.getText().toString();

                if(otherVehicle.equals(""))
                {
                    vehicleType = vehicleSpinner.getSelectedItem().toString();
                }
                else
                {
                    vehicleType = otherVehicle;
                }

                Order order = new Order(username,receivername,pickupTime,dropoffTime,weight,height,width,length,quantity,goodType,pickupLocation,pickupLatitude,pickupLongitude,dropoffLocation,dropoffLatitude,dropoffLongitude);
                db.insertOrder(order);
                Log.d("pickupLatitude", String.valueOf(pickupLatitude));
                Log.d("pickupLongitude", String.valueOf(pickupLongitude));
                Log.d("dropoffLocation", String.valueOf(dropoffLocation));
                Log.d("dropoffLatitude", String.valueOf(dropoffLatitude));
                Toast.makeText(AddNewOrderActivity2.this, "Create Order Successfully", Toast.LENGTH_SHORT).show();

                Intent intent4 = new Intent(AddNewOrderActivity2.this, HomeActivity.class);
                intent4.putExtra("username",username);
                startActivity(intent4);
                finish();
            }
        });
    }
}