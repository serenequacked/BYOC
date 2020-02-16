package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import java.io.IOException;
import java.util.List;

public class HomePage extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Button scanbutton = (Button) findViewById(R.id.Scan);
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });
        Button accountbutton = (Button)findViewById(R.id.Account);
        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        Button historybutton = (Button) findViewById(R.id.History);
        historybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });




        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        /*SearchView*/
        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location1 = searchView.getQuery().toString(); /*converts user's query into string*/
//                System.out.println("Location1" + location1);
                Log.d("Location1",location1);
                List<Address> addressList = null;

                if (location1 != null || !location1.equals("")){
                    Geocoder geocoder = new Geocoder(HomePage.this); /*need to user geocoder to search location*/
                    try {
                        addressList = geocoder.getFromLocationName(location1,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0); /*index 0 gives the address?*/
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                    Toast toast = Toast.makeText(getApplicationContext(),location1, Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(30);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER,0,0);
                    toast.show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    public void openActivity2(){
        Intent intent = new Intent(this,serenepart.class);
        startActivity(intent);
    }


    public void openActivity4(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openActivity5(){
        Intent intent = new Intent(this,HistoryActivity.class);
        startActivity(intent);
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!= null)
                {
                    currentLocation = location;
                    Toast toast = Toast.makeText(getApplicationContext(),"The Arc", Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(30);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER,0,0);
                    toast.show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(HomePage.this);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng current = new LatLng(1.3477632,103.6794825);
        MarkerOptions markerOptions = new MarkerOptions().position(current).title("I Am Here (Arc)");
        LatLng latLng2 = new LatLng(1.3475116,103.6787189);
        LatLng latLng3 = new LatLng(1.344152,103.6778873);
        LatLng latLng4 = new LatLng(1.3490609,103.6766721);
        LatLng latLng5 = new LatLng(1.3448888,103.6785952);
        LatLng latLng6 = new LatLng(1.3494879,103.6824651);
        LatLng latLng7 = new LatLng(1.3492627,103.6751748);
        LatLng latLng8 = new LatLng(1.3490784,103.687208);
        LatLng latLng9 = new LatLng(1.3472056,103.679839);
        LatLng latLng10 = new LatLng(1.3426965,103.6799275);
        map.addMarker(new MarkerOptions().position(latLng2).title("Lee Wee Nam Library"));
        map.addMarker(new MarkerOptions().position(latLng3).title("Nanyang Audi"));
        map.addMarker(new MarkerOptions().position(latLng4).title("NIE"));
        map.addMarker(new MarkerOptions().position(latLng5).title("Tan Chin Tuan Lecture"));
        map.addMarker(new MarkerOptions().position(latLng6).title("School of ADM"));
        map.addMarker(new MarkerOptions().position(latLng7).title("North Hill"));
        map.addMarker(new MarkerOptions().position(latLng8).title("The Wave"));
        map.addMarker(new MarkerOptions().position(latLng9).title("North Spine Food Court"));
        map.addMarker(new MarkerOptions().position(latLng10).title("South Spine Kou Fu"));
        map.animateCamera(CameraUpdateFactory.newLatLng(current));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current,17));
        map.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    fetchLastLocation();
                }
                break;
        }
    }
}
