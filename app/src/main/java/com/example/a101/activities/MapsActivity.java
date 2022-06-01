package com.example.a101.activities;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a101.R;
import com.example.a101.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.google.maps.android.PolyUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    DirectionsResult result;
    double pickupLatitude,pickupLongitude,dropoffLatitude,dropoffLongitude,fare;
    private ActivityMapsBinding binding;
    String pickupLocation,dropoffLocation,distance,travelTime;
    TextView fareTextView,travelTimeTextView;
    private static final int REQUEST_PHONE_CALL = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("ARAauiaasjk3xK5tXVEB0rk4Rr402EUq-xLTwu8Wc9ozqG2C5QurGiAwmjsXxqYcaAxuQquh4SoeaSO2");
    Button bookButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView pickUpLocationTextView =findViewById(R.id.pickUpLocationTextView);
        TextView dropOffLocationTextView =  findViewById(R.id.dropOffLocation);
        fareTextView = findViewById(R.id.fareTextView);
        travelTimeTextView = findViewById(R.id.travelTimeTextView);
        bookButton = findViewById(R.id.bookButton);
        Button callButton = findViewById(R.id.callButton);

        Intent intent = getIntent();
        pickupLocation = intent.getStringExtra("pickupLocation");
        pickupLatitude = intent.getDoubleExtra("pickupLatitude",0);
        pickupLongitude = intent.getDoubleExtra("pickupLongitude",0);
        dropoffLocation = intent.getStringExtra("dropoffLocation");
        dropoffLatitude = intent.getDoubleExtra("dropoffLatitude",0);
        dropoffLongitude = intent.getDoubleExtra("dropoffLongitude",0);
        pickUpLocationTextView.setText("Pickup Location: "+pickupLocation);
        dropOffLocationTextView.setText("Drop-off Location: "+dropoffLocation);
        Log.d("pickupLatitude", String.valueOf(pickupLatitude));
        Log.d("pickupLongitude", String.valueOf(pickupLongitude));
        Log.d("dropoffLongitude", String.valueOf(dropoffLongitude));
        Log.d("dropoffLatitude", String.valueOf(dropoffLatitude));

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayPalPayment payment = new PayPalPayment(new BigDecimal(fare), "AUD", "Course Fees",
                        PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent3 = new Intent(getApplicationContext(), PaymentActivity.class);
                intent3.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent3.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivity(intent3);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:0377778888"));

                if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(intent1);
                }
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng pickUpLng = new LatLng(pickupLatitude, pickupLongitude);
        LatLng dropOffLng = new LatLng(dropoffLatitude, dropoffLongitude);



        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDH4ohQAFof08m_LGpExVi51cYIS6Vvwyc")
                .build();

        try {
            result = DirectionsApi.newRequest(geoApiContext).mode(TravelMode.DRIVING).origin(new com.google.maps.model.LatLng(pickUpLng.latitude, pickUpLng.longitude))
                    .destination(new com.google.maps.model.LatLng(dropOffLng.latitude, dropOffLng.longitude))
                    .transitMode()
                    .await();
            List<LatLng> decodedPath = PolyUtil.decode(result.routes[0].overviewPolyline.getEncodedPath());
            mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        distance =  result.routes[0].legs[0].distance.toString();
        travelTime =  result.routes[0].legs[0].duration.toString();
        String distanceText = distance.substring(0,distance.length()-3).trim();
        double distanceNum = Double.parseDouble(distanceText);
        fare = distanceNum*5;
        String fareText = String.valueOf(fare);
        fareTextView.setText("Approx.Fare: $" +fareText);
        travelTimeTextView.setText("Approx.Travel time: " +travelTime);


        mMap.addMarker(new MarkerOptions().position(pickUpLng));
        mMap.addMarker(new MarkerOptions().position(dropOffLng));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUpLng,13));
    }

}

