package com.example.cz2006se;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class IPPTFragment extends Fragment {

    private static final String TAG = "IPPTFragment";

    // for saving values
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // declaration of variables
    Button gender_F_button, gender_M_button, saveResults_button, pastRecords_button, calculate_button;
    EditText age_editText, sitUp_editText, pushUp_editText, run_editText;
    TextView results2_textView;
    String age_string, sitUp_string, pushUp_string, run_string, gender_string, result_string;
    View rootView;

    public IPPTFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        showLog("Entering onCreateView");
        // create dialog fragment view
        rootView = inflater.inflate(R.layout.fragment_ippt, container, false);
        super.onCreate(savedInstanceState);

        // for pop up fragment
        final FragmentManager fragmentManager = getFragmentManager();
        final pastRecordsFragment pastRecordsFragment = new pastRecordsFragment();

        // find all view by id
        gender_F_button = rootView.findViewById(R.id.Gender_F_button);
        gender_M_button = rootView.findViewById(R.id.Gender_M_button);
        saveResults_button = rootView.findViewById(R.id.SaveResults_button);
        pastRecords_button = rootView.findViewById(R.id.PastRecords_button);
        calculate_button = rootView.findViewById(R.id.Calculate_button);

        age_editText = rootView.findViewById(R.id.Age_editText);
        sitUp_editText = rootView.findViewById(R.id.SitUp_editText);
        pushUp_editText = rootView.findViewById(R.id.PushUp_editText);
        run_editText = rootView.findViewById(R.id.Run_editText);

        results2_textView = rootView.findViewById(R.id.Results2_textView);

        // set TAG and Mode for shared preferences
        sharedPreferences = getActivity().getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);

        // initialise variables
        age_string = sitUp_string = pushUp_string = run_string = gender_string = "";

        // create pastRecords for sharedPreferences if none exist
        if (!sharedPreferences.contains("pastrecords")){
            showLog("Creating pastRecords for shared preferences");

            // allows editing to shared preferences
            editor = sharedPreferences.edit();
            String createData = CreatePastRecords();
            editor.putString("pastrecords", createData);
            editor.commit();
        }

        // set gender button
        gender_M_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked gender-M-button");
                if(gender_string.equals("F"))
                    gender_F_button.setBackground(getResources().getDrawable(R.drawable.button_background));

                view.setBackground(getResources().getDrawable(R.drawable.pressed_button_background));
                gender_string = "M";
            }
        });

        gender_F_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked gender-F-button");
                if(gender_string.equals("M"))
                    gender_M_button.setBackground(getResources().getDrawable(R.drawable.button_background));

                view.setBackground(getResources().getDrawable(R.drawable.pressed_button_background));
                gender_string = "F";
            }
        });

        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked calculate button");

                // get data
                age_string = age_editText.getText().toString();
                sitUp_string = sitUp_editText.getText().toString();
                pushUp_string = pushUp_editText.getText().toString();
                run_string = run_editText.getText().toString();

                // check for unfilled data
                if(TextUtils.isEmpty(age_string) || TextUtils.isEmpty(sitUp_string) || TextUtils.isEmpty(pushUp_string) || TextUtils.isEmpty(run_string))
                    showToast("Please enter all fields");

                else {
                    showLog("Data received: " + age_string + gender_string + sitUp_string + pushUp_string + run_string);

                    if (Integer.parseInt(age_string) < 18 || Integer.parseInt(age_string) > 60)
                        showToast("Enter age between 18 and 60");

                    else {
                        showLog("Getting Results");
                        result_string = IPPTCalculateData.getResults(age_string, gender_string, sitUp_string, pushUp_string, run_string);
                        results2_textView.setText(result_string);
                        showLog("Results shown: " + results2_textView.getText().toString());
                    }
                }
            }
        });

        // saving results
        saveResults_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked save results");

                // edit shared preferences
                editor = sharedPreferences.edit();

                // check if there is results calculated
                if(results2_textView.getText().toString().equals(""))
                    showToast("There is no calculated data to be saved");

                else {
                    // put data in jsonObject format
                    JSONObject data = new JSONObject();
                    JSONArray dataArray = new JSONArray();
                    try {
                        data.put("age", age_string);
                        data.put("gender", gender_string);
                        data.put("sit-up", sitUp_string);
                        data.put("push-up", pushUp_string);
                        data.put("2.4km", run_string);
                        data.put("results", result_string);
                        dataArray.put(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Edit jsonObject in shared preferences
                    String pastrecords_string = "";
                    try {
                        JSONObject pastrecords_object = new JSONObject(sharedPreferences.getString("pastrecords", ""));
                        int indexOfOldestRecord = pastrecords_object.getInt("oldestRecordIndex"); //rmb to mod 5 ltr when putting back into json
                        pastrecords_object.put(String.valueOf(indexOfOldestRecord), dataArray);
                        indexOfOldestRecord = (indexOfOldestRecord + 1) % 5;
                        pastrecords_object.put("oldestRecordIndex", indexOfOldestRecord);
                        pastrecords_string = String.valueOf(pastrecords_object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    editor.putString("pastrecords", pastrecords_string);
                    showLog("Updated pastRecords: " + pastrecords_string);

                    // saving values to shared preferences
                    editor.commit();

                    Toast.makeText(getActivity(), "Saving record...", Toast.LENGTH_SHORT).show();
                    showLog("Exiting saveResults");
                }
            }
        });

        pastRecords_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked pastRecords button");
                // for fragment view
                pastRecordsFragment.show(fragmentManager, "PastRecords Fragment");
                showLog("Exiting pastRecords button");
            }
        });

        return rootView;
    }

    private String CreatePastRecords(){
        showLog("Entering CreatePastRecords");
        JSONObject dummy = new JSONObject();
        JSONArray dummyArray = new JSONArray();
        JSONObject initialise_records = new JSONObject();
        try {
            dummy.put("age", "N.A.");
            dummy.put("gender", "N.A.");
            dummy.put("sit-up", "N.A.");
            dummy.put("push-up", "N.A.");
            dummy.put("2.4km", "N.A.");
            dummy.put("results", "N.A.");
            dummyArray.put(dummy);
            showLog("JSONObject dummy: " + String.valueOf(dummy));

            initialise_records.put("0", dummyArray);
            initialise_records.put("1", dummyArray);
            initialise_records.put("2", dummyArray);
            initialise_records.put("3", dummyArray);
            initialise_records.put("4", dummyArray);
            initialise_records.put("oldestRecordIndex", 0);
        }catch (JSONException E){
            E.printStackTrace();
        }
        showLog("JSONObject for records created: " + String.valueOf(initialise_records));
        showLog("Exiting CreatePastRecords");

        return String.valueOf(initialise_records);
    }

    // show log message
    private void showLog(String message) {
        Log.d(TAG, message);
    }

    // show toast
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
