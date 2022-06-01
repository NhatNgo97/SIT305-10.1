package com.example.a101.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.a101.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewOrderActivity extends AppCompatActivity {
    String pickupTime;
    String dropoffTime;
    String pickupDate ;
    String dropoffDate ;
    String hour;
    String minute;
    String pickupLocation;
    String dropoffLocation;
    EditText pickupLocationEditText;
    EditText dropoffLocationEditText;
    double pickupLatitude,pickupLongitude,dropoffLatitude,dropoffLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        Button nextButton = findViewById(R.id.nextButton);

        EditText receiverNameInput = findViewById(R.id.receiverNameInput);
        pickupLocationEditText = findViewById(R.id.pickupLocationEditText);
        dropoffLocationEditText = findViewById(R.id.dropoffLocationTextEdit);
        TimePicker timePicker = findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);


        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(i,i1,i2);
                pickupDate = formatter.format(c.getTime());
                c.add(Calendar.DAY_OF_YEAR,1);
                dropoffDate = formatter.format(c.getTime());
            }
        });

        Places.initialize(getApplicationContext(),"AIzaSyDH4ohQAFof08m_LGpExVi51cYIS6Vvwyc");
        pickupLocationEditText.setFocusable(false);
        dropoffLocationEditText.setFocusable(false);
        pickupLocationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(AddNewOrderActivity.this);
                startActivityForResult(intent, 100);
            }
        });
        dropoffLocationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickupLocationEditText.setFocusable(false);
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(AddNewOrderActivity.this);
                startActivityForResult(intent, 101);
            }
        });




        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receiverName = receiverNameInput.getText().toString();

                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = String.valueOf(timePicker.getHour());
                    minute = String.valueOf(timePicker.getMinute());

                }
                else {
                    hour = String.valueOf(timePicker.getCurrentHour());
                    minute = String.valueOf(timePicker.getCurrentMinute());
                }
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                String time = hour+":"+minute;
                Calendar c = Calendar.getInstance();
                Date d = null;
                try {
                    d = df.parse(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(d);
                c.add(Calendar.HOUR, 3);
                time = df.format(c.getTime());
                String newTime = df.format(c.getTime());
                pickupTime =  time + " " + pickupDate;

                dropoffTime = newTime+ " " + dropoffDate;

                pickupLocation = pickupLocationEditText.getText().toString();
                dropoffLocation = dropoffLocationEditText.getText().toString();
                Intent newOrder2Intent = new Intent(getApplicationContext(), AddNewOrderActivity2.class);
                newOrder2Intent.putExtra("username",username);
                newOrder2Intent.putExtra("receivername",receiverName);
                newOrder2Intent.putExtra("pickupTime",pickupTime);
                newOrder2Intent.putExtra("dropoffTime",dropoffTime);
                newOrder2Intent.putExtra("pickupLocation",pickupLocation);
                newOrder2Intent.putExtra("pickupLatitude",pickupLatitude);
                newOrder2Intent.putExtra("pickupLongitude",pickupLongitude);
                newOrder2Intent.putExtra("dropoffLocation",dropoffLocation);
                newOrder2Intent.putExtra("dropoffLatitude",dropoffLatitude);
                newOrder2Intent.putExtra("dropoffLongitude",dropoffLongitude);
                startActivity(newOrder2Intent);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Place place = Autocomplete.getPlaceFromIntent(data);
            pickupLocationEditText.setText(place.getName());
            pickupLatitude = place.getLatLng().latitude;
            pickupLongitude = place.getLatLng().longitude;
        }
        else if (requestCode==101){
            Place place = Autocomplete.getPlaceFromIntent(data);
            dropoffLocationEditText.setText(place.getName());
            dropoffLatitude = place.getLatLng().latitude;
            dropoffLongitude= place.getLatLng().longitude;
        }
    }
}