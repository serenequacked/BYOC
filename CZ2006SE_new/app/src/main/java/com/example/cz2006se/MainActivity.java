package com.example.cz2006se;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterClass.OnItemClickListener {
    DatabaseReference ref;
    //same list as Adapterclass

    ArrayList<Gym> list;
    RecyclerView recyclerView;
    AdapterClass adapterClass;
    SearchView searchView;



    //Declare Others
    private static final String TAG = "MainActivity";
    public String heightString = "";
    public String weightString = "";
    public String bmiString = "";
    static SharedPreferences bmidata;

    public static final String mybmidata = "bmidata";
    public static final String pastweight = "pastweight";
    private BMIFragment bmiFragment = null;
    private IPPTFragment ipptFragment = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setTitle("Gym List");


        //bottom navigation bar

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        //firebase database
        ref = FirebaseDatabase.getInstance().getReference("Gym");
        searchView = findViewById(R.id.searchView);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterClass = new AdapterClass(list, this);
        recyclerView.setAdapter(adapterClass);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ref!=null)
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(Gym.class));
                        }
//                        String value = dataSnapshot.getValue(String.class);

                        Log.d(TAG, "initialising adapterClass");
                        Log.d(TAG, "initialized adapterClass");

                        recyclerView.setAdapter(adapterClass);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        if (searchView!= null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<Gym> myList = new ArrayList<>();
        for(Gym object: list)
        {
            System.out.println(object);
            if (object.getAddress().toLowerCase().contains(str.toLowerCase())|| object.getName().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);

            }
        }
        AdapterClass adapterClass = new AdapterClass(myList,this);
        Log.d(TAG, "initialising adapterClass");
        Log.d(TAG, "initialized adapterClass");
        recyclerView.setAdapter(adapterClass);
    }


    //When View BMI button pressed



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch(menuItem.getItemId())
                    {
                        case R.id.nav_exercise:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_ippt:
                            selectedFragment = new IPPTFragment();
                            break;
                        case R.id.nav_bmi:
                            selectedFragment = new BMIFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                    return true;
                }
            };


    @Override
    public void onItemClick(int position) {
        //gives a reference to the node that was selected
        Log.d(TAG,"onItemClick: Clicked");

        //if u want to navigate to a new activity, this is where u include the code.
        Intent intent = new Intent(this,GymGoogleMap.class);
        //if u want to carry the info to the next page, u can do a intent.putExtra

        intent.putExtra("Gym",list.get(position));
        startActivity(intent);



    }
}
