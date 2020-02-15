package com.example.cz2006se;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BMIFragment extends Fragment {
    private EditText height;
    private EditText weight;
    private TextView BMIResult;
    private TextView comments;

    //Declare Others
    private static final String TAG = "BMIActivity";
    public String heightString = "";
    public String weightString = "";
    public String bmiString = "";
    static SharedPreferences bmidata;

    public static final String mybmidata = "bmidata";
    public static final String pastweight = "pastweight";

    public BMIFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi,container,false);

        //Find View by ID
        height = view.findViewById(R.id.HeightInput);
        weight = view.findViewById(R.id.WeightInput);
        BMIResult = view.findViewById(R.id.BMIResult);
        comments = view.findViewById(R.id.Comments);
        Button view_bmi= view.findViewById(R.id.ViewBMI);
        Button calculate_bmi = view.findViewById(R.id.Calculate);
        Button save_bmi = view.findViewById(R.id.Save);

        //Create Shared Preferences for bmidata
        bmidata = getActivity().getSharedPreferences(mybmidata, Context.MODE_PRIVATE);

        //Create Records if none exists
        if (!bmidata.contains("pastrecords")){
            Log.d(TAG,"Creating pastRecords for shared preferences");

            // allows editing to shared preferences
            SharedPreferences.Editor editor = bmidata.edit();
            String createData = CreateBMIRecords();
            editor.putString("pastrecords", createData);
            editor.commit();
        }
        calculate_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBMI(view);
            }
        });
        //when view BMI button is pressed
        view_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BMIHistory.class);
                startActivity(intent);
                //loadData();
                //Log.d(TAG, "New BMI History:" + newBmiHistory);

            }
        });
        //save results
        save_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
       // Toast.makeText(getActivity(),"Saving record...", Toast.LENGTH_SHORT).show();

        return view;
    }

    //Create BMI Records
    private String CreateBMIRecords() {

        //Create dummy jsonobject and jsonarray for initialising
        JSONObject dummy = new JSONObject();
        JSONArray dummyArray = new JSONArray();
        JSONObject initialise_bmirecord = new JSONObject();
        try {
            dummy.put("weight", "N.A.");
            dummy.put("height", "N.A.");
            dummy.put("result", "N.A.");
            dummyArray.put(dummy);
            Log.d(TAG,"JSONObject dummy: " + String.valueOf(dummy));
            Log.d(TAG,"JSONArray:" + dummyArray);

            //initialise
            initialise_bmirecord.put("0", dummyArray);
            initialise_bmirecord.put("1", dummyArray);
            initialise_bmirecord.put("2", dummyArray);
            initialise_bmirecord.put("3", dummyArray);
            initialise_bmirecord.put("4", dummyArray);
            initialise_bmirecord.put("oldestRecordIndex", 0);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        Log.d(TAG,"JSONObject for records created: " + String.valueOf(initialise_bmirecord));

        return String.valueOf(initialise_bmirecord);
    }

    //Calculate BMI
    public void calculateBMI(View v) {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        //string to float
        if (heightStr != null && !"".equals(heightStr)
                && weightStr != null  &&  !"".equals(weightStr)) {
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);

            //calculate bmi
            double bmi = weightValue / (heightValue * heightValue);

            //convert float to double
            double heightDouble = heightValue;
            double weightDouble = weightValue;

            //convert to string
            heightString = String.format("%.2f", heightDouble);
            weightString = String.format("%.2f",weightDouble);
            bmiString = String.format("%.2f",bmi);

            //display BMI results
            displayBMI(bmi);

            //save BMI Results
            //saveData();
        }

    }

    private void saveData (){
        SharedPreferences.Editor editor = bmidata.edit();
        JSONObject data_bmi = new JSONObject();
        JSONArray dataArray_bmi = new JSONArray();

        //put bmidata in json object format
        try {
            data_bmi.put("height", heightString);
            data_bmi.put("weight", weightString);
            data_bmi.put("result", bmiString);
            dataArray_bmi.put(data_bmi);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        String bmirecords_string = "";

        //edit json object in shared preferences
        try {
            JSONObject bmirecords_object = new JSONObject(bmidata.getString("pastrecords", ""));

            //rmb to mod 5 ltr when putting back into json
            int indexOfOldestRecord = bmirecords_object.getInt("oldestRecordIndex");
            bmirecords_object.put(String.valueOf(indexOfOldestRecord), dataArray_bmi);

            //change index of oldest record
            indexOfOldestRecord = (indexOfOldestRecord + 1) % 5;
            bmirecords_object.put("oldestRecordIndex", indexOfOldestRecord);

            //json to string
            bmirecords_string = String.valueOf(bmirecords_object);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        editor.putString("pastrecords", bmirecords_string);
        Log.d(TAG,"Updated pastRecords: " + bmirecords_string);

        // saving values to shared preferences
        editor.commit();



    }

    //Display BMI Comments
    private void displayBMI(Double bmi) {
        String bmiLabel = "";
        String commentsLabel = "";

        if (Double.compare(bmi, 18.5) <= 0) {
            bmiLabel = String.format("%.2f", bmi);
            commentsLabel = "Risk of nutritional deficiency diseases and osteoporosis";
        } else if (Double.compare(bmi, 18.5f) > 0 && Double.compare(bmi, 23f) <= 0) {
            bmiLabel = String.format("%.2f", bmi);
            commentsLabel = "Healthy!\nKeep it up!";
        } else if (Double.compare(bmi, 23f) > 0 && Double.compare(bmi, 27.5f) <= 0) {
            bmiLabel = String.format("%.2f", bmi);
            commentsLabel = "Moderate Risk of Obesity";
        } else {
            bmiLabel = String.format("%.2f", bmi);
            commentsLabel = "High Risk of Obesity";
        }

        BMIResult.setText(bmiLabel);
        comments.setText(commentsLabel);

    }
}
