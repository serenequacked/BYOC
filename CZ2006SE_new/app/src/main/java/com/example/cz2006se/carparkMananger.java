package com.example.cz2006se;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class carparkMananger extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;

    //arraylist more info in carpark.class
    ArrayList<carpark> carparkArrayList = new ArrayList<carpark>();
    ArrayList<carpark> finalnearestArrayList = new ArrayList<carpark>();
    ArrayList<Float> distanceBetweenList = new ArrayList<Float>();
    String place_id;
    String placeName = "";
    double latitude, longitude;
    boolean newUpdateGov = false;
    boolean readFromGoogle = false;

    private TextView wkdayBef6pmRates;
    private TextView wkdayAft6pmRates;
    private TextView satRates;
    private TextView sunRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpark_mananger);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(carparkMananger.this);
        //fetchLastLocation();
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(carparkMananger.this);
        checkCarparkUpdate();

    }


    private void fetchLastLocation() {
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(carparkMananger.this);
                }

            }


        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = getIntent().getExtras();
        String gymLat = bundle.getString("LAT");
        String gymLong = bundle.getString("LONG");
        LatLng latLng = new LatLng(Double.parseDouble(gymLat),Double.parseDouble(gymLong));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Gym Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mMap.addMarker(markerOptions);

        double lat = 0;
        double lon = 0;
        String carparkName = "";

        for (int i = 0; i < carparkArrayList.size(); i++){
            lat = carparkArrayList.get(i).getLatitude();
            lon = carparkArrayList.get(i).getLongitude();
            carparkName = carparkArrayList.get(i).getCarpark();

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(carparkName));
        }

        mMap.setOnMarkerClickListener(this);
    }


    public void checkCarparkUpdate() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            parseCarparkResult();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        writeLongLatToFile();

        // Read longlat from resourse
        double lat;
        double longi;
        String testing ="";
        try {

            //Get the text file
            File file = new File(getExternalFilesDir(null),"carpark_lat_long.txt");
            InputStream fis = new FileInputStream(file);
            InputStreamReader Reader = new InputStreamReader(fis);
            BufferedReader buffreader = new BufferedReader(Reader);
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ( (temp = buffreader.readLine()) != null ) {
                stringBuilder.append(temp + "+");

            }
            fis.close();
            testing = stringBuilder.toString();

            String coordinates[] = testing.split("\\+");

            for (int i = 0; i<10; i++){
                Log.i("ABCDEF: ", coordinates[i]);
            }
            int counter = 0;
            for (int j = 0; j < carparkArrayList.size(); j++) {
                // read every line of the file into the line-variable, on line at the time
                if ((counter%2) == 0) {
                    carparkArrayList.get(j).setLatitude(Double.parseDouble(coordinates[counter]));
                    counter++;
                }

                carparkArrayList.get(j).setLongitude(Double.parseDouble(coordinates[counter]));
                counter++;


                // do something with the line
            }
            counter =0;

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try {
            //Get the text file

            File file = new File(getExternalFilesDir(null), "carpark_lat_long.txt");
            InputStream fis = new FileInputStream(file);
            if (fis != null) {

                // prepare the file for reading
                InputStreamReader Reader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(Reader);

                String line = "";
                while (line != null) {
                    for (int j = 0; j < carparkArrayList.size(); j++) {
                        // read every line of the file into the line-variable, on line at the time
                        line = buffreader.readLine();
                        carparkArrayList.get(j).setLatitude(Double.parseDouble(line));
                        line = buffreader.readLine();
                        carparkArrayList.get(j).setLongitude(Double.parseDouble(line));

                        // do something with the line
                    }
                }
                fis.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        */
    }





    //get carpark rates but no place data
    //First to compare if there is any updates of carpark rates based on carparkrates_json saved previously
    //Extract updated/up-to-date result into carParkArray List
    private void parseCarparkResult() throws ExecutionException, InterruptedException {
        String category, weekdays_rate1, weekdays_rate2, sunday_public_holiday_rate, carpark, saturday_rate;
        String dataGovURL = "https://data.gov.sg/api/action/datastore_search?resource_id=85207289-6ae7-4a56-9066-e6090a3684a5&limit=200";
        carparkParse process = new carparkParse();
        String data = process.execute(dataGovURL).get();
        String checkFromResJson = "";
        try {
            try {

                //Get the text file
                File file = new File(getExternalFilesDir(null),"carparkrates_json.txt");
                InputStream fis = new FileInputStream(file);
                InputStreamReader Reader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(Reader);
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ( (temp = buffreader.readLine()) != null ) {
                    stringBuilder.append(temp);
                }
                fis.close();
                checkFromResJson = stringBuilder.toString();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if (!checkFromResJson.equals(data)) {
                try {
                    //Get the text file
                    File file = new File(getExternalFilesDir(null), "carparkrates_json.txt");
                    FileOutputStream output = new FileOutputStream(file);
                    OutputStreamWriter o = new OutputStreamWriter(output);
                    o.write(data);
                    o.flush();
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newUpdateGov = true;
            }

            String updated_carpark_rate = "";

            try {

                //Get the text file
                File file = new File(getExternalFilesDir(null),"carparkrates_json.txt");
                InputStream fis = new FileInputStream(file);
                InputStreamReader Reader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(Reader);
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ( (temp = buffreader.readLine()) != null ) {
                    stringBuilder.append(temp);
                }
                fis.close();
                updated_carpark_rate = stringBuilder.toString();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject result = new JSONObject(updated_carpark_rate);

            JSONObject resultObj = result.getJSONObject("result");
            JSONArray recordsArray = resultObj.getJSONArray("records");

            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject carparkObj = recordsArray.getJSONObject(i);

                category = carparkObj.getString("category");
                weekdays_rate1 = carparkObj.getString("weekdays_rate_1");
                weekdays_rate2 = carparkObj.getString("weekdays_rate_2");
                sunday_public_holiday_rate = carparkObj.getString("sunday_publicholiday_rate");
                carpark = carparkObj.getString("carpark");
                saturday_rate = carparkObj.getString("saturday_rate");

                carparkArrayList.add(new carpark(category, weekdays_rate1, weekdays_rate2, sunday_public_holiday_rate, carpark, saturday_rate, 0, 0));
            }
        /*
        int listSize = carparkArrayList.size();

        for (int i = 0; i<listSize; i++){
            Log.i("ABCDEF: ", carparkArrayList.get(i).getCarpark());
        }

         */

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void writeLongLatToFile() {
        if (newUpdateGov) {

            //to update carpark ArrayList long/ lat from google
            if (readFromGoogle) {
                for (int i = 0; i < carparkArrayList.size(); i++) {
                    String carparkName = carparkArrayList.get(i).getCarpark().replace(" ", "+");

                    try {
                        // To get Place ID
                        String place_ID_URL = "https://maps.googleapis.com/maps/api/place/queryautocomplete/json?key=" + gym_config.GOOGLE_API_KEY + "&input=" + carparkName;
                        carparkParse ID_process = new carparkParse();
                        String ID_data = ID_process.execute(place_ID_URL).get();

                        JSONObject googleObj = new JSONObject(ID_data);
                        JSONArray carparkArray = googleObj.getJSONArray("predictions");
                        JSONObject carparkObj = carparkArray.getJSONObject(0);

                        place_id = carparkObj.getString("place_id");

                        // To Get Long & Lat & Set it to Carpark object
                        String longlat_URL = "https://maps.googleapis.com/maps/api/place/details/json?key=" + gym_config.GOOGLE_API_KEY + "&placeid=" + place_id;
                        carparkParse longlat_process = new carparkParse();
                        String longlat_data = longlat_process.execute(longlat_URL).get();

                        /*
                        try {
                            //Get the text file
                            File file = new File(getExternalFilesDir(null), "longlong.txt");
                            FileOutputStream output = new FileOutputStream(file);
                            OutputStreamWriter o = new OutputStreamWriter(output);
                            o.write(longlat_data);
                            o.flush();
                            o.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        */
                        ///missing something here
                        JSONObject placeObj = new JSONObject(longlat_data);
                        JSONObject placeObj2 = placeObj.getJSONObject("result");
                        JSONObject placeObj3 = placeObj2.getJSONObject("geometry");
                        JSONObject placeObj4 = placeObj3.getJSONObject("location");
                        latitude = placeObj4.getDouble("lat");
                        longitude = placeObj4.getDouble("lng");

                        carparkArrayList.get(i).setLatitude(latitude);
                        carparkArrayList.get(i).setLongitude(longitude);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                try {
                    //Get the text file
                    File file = new File(getExternalFilesDir(null), "carpark_lat_long.txt");
                    FileOutputStream fOut = new FileOutputStream(file);
                    OutputStreamWriter osw = new OutputStreamWriter(fOut);
                    osw.close();
                    FileOutputStream fOut2 = new FileOutputStream(file);
                    OutputStreamWriter osw2 = new OutputStreamWriter(fOut2);

                    for (int j = 0; j < carparkArrayList.size(); j++) {
                        if (readFromGoogle) {
                            // update longlat resourse file
                            osw2.write(Double.toString(carparkArrayList.get(j).getLatitude()));
                            osw2.write("\n");
                            osw2.write(Double.toString(carparkArrayList.get(j).getLongitude()));
                            osw2.write("\n");
                            //updateLongLatResFile(latitude,longitude);
                        }
                        //else{
                        //  osw2.write(getLatLong());
                        //}
                    }
                    osw2.flush();
                    osw2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //no more updates
            newUpdateGov = false;
        }
    }
/*
                    int listSize = carparkArrayList.size();

                    for (int j = 0; j < listSize; j++) {
                        Log.d("lat: ", "hello " + String.valueOf(carparkArrayList.get(j).getLatitude()));
                        Log.d("long: ", "hello " + String.valueOf(carparkArrayList.get(j).getLongitude()));
*/

    ///////////////////////////////////
    private ArrayList findNearestCarpark(double latt, double longg){
        ArrayList<carpark> nearestArrayList = new ArrayList<carpark>();
        double lat;
        double longti;
        boolean isExistCarpark = false;
        float [] dist = new float[1];
        for(int i=0; i < carparkArrayList.size(); i++)
        {
            lat = carparkArrayList.get(i).getLatitude();
            longti = carparkArrayList.get(i).getLongitude();

            Location.distanceBetween(lat, longti, latt, longg, dist);
            if(dist[0] < 800)
            {
                for (int j=0; j<nearestArrayList.size(); j++){
                    if (carparkArrayList.get(i).getCarpark().equals(nearestArrayList.get(j).getCarpark())){
                        isExistCarpark = true;
                    }
                }
                if (!isExistCarpark) {
                    nearestArrayList.add(carparkArrayList.get(i));
                    distanceBetweenList.add(dist[0]);
                    isExistCarpark = false;
                }
            }
        }
        return nearestArrayList;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        wkdayBef6pmRates = (findViewById(R.id.wkdayBef6pmRates));
        wkdayAft6pmRates = (findViewById(R.id.wkdayAft6pmRates));
        satRates = (findViewById(R.id.satRates));
        sunRates = (findViewById(R.id.sunRates));

        for (int i = 0; i < carparkArrayList.size(); i++){
            if (marker.getTitle().equals(carparkArrayList.get(i).getCarpark())){
                wkdayBef6pmRates.setText(carparkArrayList.get(i).getWeekdays_rate1());
                wkdayAft6pmRates.setText(carparkArrayList.get(i).getWeekdays_rate2());
                satRates.setText(carparkArrayList.get(i).getSaturday_rate());
                sunRates.setText(carparkArrayList.get(i).getSunday_public_holiday_rate());
            }
            else if (marker.getTitle().equals(placeName)){
                wkdayBef6pmRates.setText("-");
                wkdayAft6pmRates.setText("-");
                satRates.setText("-");
                sunRates.setText("-");
            }
        }
        return false;
    }
}
