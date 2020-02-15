package com.example.cz2006se;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.cz2006se.MainActivity.bmidata;
import static com.example.cz2006se.MainActivity.mybmidata;
import static com.example.cz2006se.MainActivity.pastweight;

public class BMIHistory extends Activity {

    //Declare variables
    private static final String TAG = "Displaying pastBMI";
    private TextView pastRecords_1, pastRecords_2, pastRecords_3, pastRecords_4, pastRecords_5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bmihistory);

        //Find View by ID
        pastRecords_1 = (TextView) findViewById(R.id.pastrecord1);
        pastRecords_2 = (TextView) findViewById(R.id.pastrecord2);
        pastRecords_3 = (TextView)findViewById(R.id.pastrecord3);
        pastRecords_4 = (TextView)findViewById(R.id.pastrecord4);
        pastRecords_5 = (TextView)findViewById(R.id.pastrecord5);

        //Set size of popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.9));

        //Retrieve data from shared preferences
        loadData();

    }

    //Retrieve data from shared preferences
    public void loadData(){

        // set shared preferences
        SharedPreferences loadbmidata = getSharedPreferences(mybmidata, Context.MODE_PRIVATE);

        try {
            JSONObject records = new JSONObject(loadbmidata.getString("pastrecords", ""));
            String height, weight, result;
            int newestIndex = (Integer.parseInt(records.getString("oldestRecordIndex")) - 1);

            // iterate through all the keys in jsonObject
            for(int i=0; i<records.names().length(); i++) {

                switch (records.names().getString(i)) {

                    case "0":
                        JSONArray recordData_1 = (newestIndex < 0) ? records.getJSONArray(String.valueOf(5 + newestIndex)): records.getJSONArray(String.valueOf(newestIndex));
                        height = recordData_1.getJSONObject(0).getString("height");
                        weight = recordData_1.getJSONObject(0).getString("weight");
                        result = recordData_1.getJSONObject(0).getString("result");

                        pastRecords_1.setText("Record 1 \n" + "Height: " + height +"   Weight: " + weight + "\n"
                                + "Result: " + result);
                        Log.d(TAG,"Record 1 shown: " + String.valueOf(recordData_1));
                        break;

                    case "1":
                        JSONArray recordData_2 = ((newestIndex - 1) < 0) ? records.getJSONArray(String.valueOf(5 + (newestIndex - 1))): records.getJSONArray(String.valueOf((newestIndex - 1)));
                        height = recordData_2.getJSONObject(0).getString("height");
                        weight = recordData_2.getJSONObject(0).getString("weight");
                        result = recordData_2.getJSONObject(0).getString("result");

                        pastRecords_2.setText("Record 2 \n" + "Height: " + height +"   Weight: " + weight + "\n"
                                + "Result: " + result);
                        Log.d(TAG,"Record 2 shown: " + String.valueOf(recordData_2));
                        break;

                    case "2":
                        JSONArray recordData_3 = ((newestIndex - 2) < 0) ? records.getJSONArray(String.valueOf(5 + (newestIndex - 2))): records.getJSONArray(String.valueOf((newestIndex - 2)));
                        height = recordData_3.getJSONObject(0).getString("height");
                        weight = recordData_3.getJSONObject(0).getString("weight");
                        result = recordData_3.getJSONObject(0).getString("result");

                        pastRecords_3.setText("Record 3 \n" + "Height: " + height +"   Weight: " + weight + "\n"
                                + "Result: " + result);
                        Log.d(TAG,"Record 3 shown: " + String.valueOf(recordData_3));
                        break;

                    case "3":
                        JSONArray recordData_4 = ((newestIndex - 3) < 0) ? records.getJSONArray(String.valueOf(5 + (newestIndex - 3))): records.getJSONArray(String.valueOf((newestIndex - 3)));
                        height = recordData_4.getJSONObject(0).getString("height");
                        weight = recordData_4.getJSONObject(0).getString("weight");
                        result = recordData_4.getJSONObject(0).getString("result");

                        pastRecords_4.setText("Record 4 \n" + "Height: " + height +"   Weight: " + weight + "\n"
                                + "Result: " + result);
                        Log.d(TAG,"Record 4 shown: " + String.valueOf(recordData_4));
                        break;

                    case "4":
                        JSONArray recordData_5 = records.getJSONArray(String.valueOf((newestIndex + 1)));
                        height = recordData_5.getJSONObject(0).getString("height");
                        weight = recordData_5.getJSONObject(0).getString("weight");
                        result = recordData_5.getJSONObject(0).getString("result");

                        pastRecords_5.setText("Record 5 \n" + "Height: " + height +"   Weight: " + weight + "\n"
                                + "Result: " + result);
                        Log.d(TAG,"Record 5 shown: " + String.valueOf(recordData_5));
                        break;

                    default:
                        break;
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
