package com.example.cz2006se;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class GymGoogleMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button carBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_google_map);

        Intent intent = getIntent();
        Gym gym = intent.getParcelableExtra("Gym");

        //item is saved in gym variable
        //populate the views
        String gymName = gym.getName();
        String gymAddress = gym.getAddress();
        String gymContact = gym.getContact();
        String gymOpeningHour = gym.getOpeningHour();
        final String gymLatitude = gym.getLatitude();
        final String gymLongitude = gym.getLongitude();
        String gymImageURL = gym.getImage();

        TextView gymNameTV = findViewById(R.id.gymId2);
        gymNameTV.setText(gymName);

        TextView gymAddressTV = findViewById(R.id.gymAddress2);
        gymAddressTV.setText(gymAddress);

        TextView gymContactTV = findViewById(R.id.gymContact2);
        gymContactTV.setText(gymContact);

        TextView gymOpeningHourTV = findViewById(R.id.gymOpeningHours2);
        gymOpeningHourTV.setText(gymOpeningHour);

        TextView gymLatitudeTV = findViewById(R.id.gymLatitude2);
        gymLatitudeTV.setText(gymLatitude);

        TextView gymLongitudeTV = findViewById(R.id.gymLongitude2);
        gymLongitudeTV.setText(gymLongitude);

        TextView gymImageURLTV = findViewById(R.id.url2);
        gymImageURLTV.setText(gymImageURL);

        ImageView gymImageIV = findViewById(R.id.gymImage2);
        Picasso.get()
                .load(gym.getImage()).into(gymImageIV);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(GymGoogleMap.this);


        carBtn = findViewById(R.id.Parking_button);

            carBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    Gym gym = intent.getParcelableExtra("Gym");
                    String gymLatitude = gym.getLatitude();
                    String gymLongitude = gym.getLongitude();

                    Intent intent2 = new Intent(GymGoogleMap.this, carparkMananger.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("LAT", gymLatitude);
                    bundle.putString("LONG",gymLongitude);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                }
            });


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Intent intent = getIntent();
        Gym gym = intent.getParcelableExtra("Gym");
        String gymLatitude = gym.getLatitude();
        String gymLongitude = gym.getLongitude();
        String gymName = gym.getName();

        mMap = googleMap;
        LatLng latLng = new LatLng(Double.parseDouble(gymLatitude), Double.parseDouble(gymLongitude));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title(gymName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mMap.addMarker(markerOptions);
    }

}