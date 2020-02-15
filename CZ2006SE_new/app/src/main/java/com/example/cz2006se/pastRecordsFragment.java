package com.example.cz2006se;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class pastRecordsFragment extends DialogFragment {

    private static final String TAG = "pastRecordsFragment";

    // for saving values
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // declaration of variables
    TextView pastRecords_1, pastRecords_2, pastRecords_3, pastRecords_4, pastRecords_5;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        showLog("Entering onCreateView");
        // create dialog fragment view
        rootView = inflater.inflate(R.layout.popup_pastrecords, container, false);
        super.onCreate(savedInstanceState);

        // set TAG and Mode for shared preferences
        sharedPreferences = getActivity().getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);

        // find all view by id
        pastRecords_1 = rootView.findViewById(R.id.pastRecords_1);
        pastRecords_2 = rootView.findViewById(R.id.pastRecords_2);
        pastRecords_3 = rootView.findViewById(R.id.pastRecords_3);
        pastRecords_4 = rootView.findViewById(R.id.pastRecords_4);
        pastRecords_5 = rootView.findViewById(R.id.pastRecords_5);

        // if shared preferences does not have pastrecords
        if(!sharedPreferences.contains("pastrecords")){
            showLog("Shared preferences does not contain pastrecords");
            Toast.makeText(getActivity(), "Database Error", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
        }

        // get jsonObject from shared preferences
        try {
            JSONObject records = new JSONObject(sharedPreferences.getString("pastrecords", ""));
            String age, gender, sit_up, push_up, runTime, resultData;
            int newestIndex = (Integer.parseInt(records.getString("oldestRecordIndex")) - 1);

            // iterate through all the keys in jsonObject
            for(int i=0; i<records.names().length(); i++) {

                switch (records.names().getString(i)) {

                    case "0":
                        JSONArray recordData_1 = (newestIndex < 0) ? records.getJSONArray(String.valueOf(5 + newestIndex)): records.getJSONArray(String.valueOf(newestIndex));
                        age = recordData_1.getJSONObject(0).getString("age");
                        gender = recordData_1.getJSONObject(0).getString("gender");
                        sit_up = recordData_1.getJSONObject(0).getString("sit-up");
                        push_up = recordData_1.getJSONObject(0).getString("push-up");
                        runTime = recordData_1.getJSONObject(0).getString("2.4km");
                        resultData = recordData_1.getJSONObject(0).getString("results");
                        pastRecords_1.setText("Record 1 \n" + "Age: " + age +"   Gender: " + gender + "\n"
                                + "Sit-up: " + sit_up + "   Push-up: " + push_up + "   2.4km: " + runTime + " mins\n"
                                + "Results: " + resultData+"\n");
                        showLog("Record 1 shown: " + String.valueOf(recordData_1));
                        break;

                    case "1":
                        JSONArray recordData_2 = ((newestIndex - 1) < 0) ? records.getJSONArray(String.valueOf(5 + (newestIndex - 1))): records.getJSONArray(String.valueOf((newestIndex - 1)));
                        age = recordData_2.getJSONObject(0).getString("age");
                        gender = recordData_2.getJSONObject(0).getString("gender");
                        sit_up = recordData_2.getJSONObject(0).getString("sit-up");
                        push_up = recordData_2.getJSONObject(0).getString("push-up");
                        runTime = recordData_2.getJSONObject(0).getString("2.4km");
                        resultData = recordData_2.getJSONObject(0).getString("results");
                        pastRecords_2.setText("Record 2 \n" + "Age: " + age +"   Gender: " + gender + "\n"
                                + "Sit-up: " + sit_up + "   Push-up: " + push_up + "   2.4km: " + runTime + " mins\n"
                                + "Results: " + resultData+"\n");
                        showLog("Record 2 shown: " + String.valueOf(recordData_2));
                        break;

                    case "2":
                        JSONArray recordData_3 = ((newestIndex - 2) < 0) ? records.getJSONArray(String.valueOf(5 + (newestIndex - 2))): records.getJSONArray(String.valueOf((newestIndex - 2)));
                        age = recordData_3.getJSONObject(0).getString("age");
                        gender = recordData_3.getJSONObject(0).getString("gender");
                        sit_up = recordData_3.getJSONObject(0).getString("sit-up");
                        push_up = recordData_3.getJSONObject(0).getString("push-up");
                        runTime = recordData_3.getJSONObject(0).getString("2.4km");
                        resultData = recordData_3.getJSONObject(0).getString("results");
                        pastRecords_3.setText("Record 3 \n" + "Age: " + age +"   Gender: " + gender + "\n"
                                + "Sit-up: " + sit_up + "   Push-up: " + push_up + "   2.4km: " + runTime + " mins\n"
                                + "Results: " + resultData+"\n");
                        showLog("Record 3 shown: " + String.valueOf(recordData_3));
                        break;

                    case "3":
                        JSONArray recordData_4 = ((newestIndex - 3) < 0) ? records.getJSONArray(String.valueOf(5 + (newestIndex - 3))): records.getJSONArray(String.valueOf((newestIndex - 3)));
                        age = recordData_4.getJSONObject(0).getString("age");
                        gender = recordData_4.getJSONObject(0).getString("gender");
                        sit_up = recordData_4.getJSONObject(0).getString("sit-up");
                        push_up = recordData_4.getJSONObject(0).getString("push-up");
                        runTime = recordData_4.getJSONObject(0).getString("2.4km");
                        resultData = recordData_4.getJSONObject(0).getString("results");
                        pastRecords_4.setText("Record 4 \n" + "Age: " + age +"   Gender: " + gender + "\n"
                                + "Sit-up: " + sit_up + "   Push-up: " + push_up + "   2.4km: " + runTime + " mins\n"
                                + "Results: " + resultData+"\n");
                        showLog("Record 4 shown: " + String.valueOf(recordData_4));
                        break;

                    case "4":
                        JSONArray recordData_5 = records.getJSONArray(String.valueOf((newestIndex + 1)));
                        age = recordData_5.getJSONObject(0).getString("age");
                        gender = recordData_5.getJSONObject(0).getString("gender");
                        sit_up = recordData_5.getJSONObject(0).getString("sit-up");
                        push_up = recordData_5.getJSONObject(0).getString("push-up");
                        runTime = recordData_5.getJSONObject(0).getString("2.4km");
                        resultData = recordData_5.getJSONObject(0).getString("results");
                        pastRecords_5.setText("Record 5 \n" + "Age: " + age +"   Gender: " + gender + "\n"
                                + "Sit-up: " + sit_up + "   Push-up: " + push_up + "   2.4km: " + runTime + " mins\n"
                                + "Results: " + resultData);
                        showLog("Record 5 shown: " + String.valueOf(recordData_5));
                        break;

                    default:
                        break;
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        // close dialog fragment
        getDialog().dismiss();

        return rootView;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        showLog("Entering onDismiss");
        super.onDismiss(dialog);
        showLog("Exiting onDismiss");
    }

    // show log message
    private void showLog(String message) {
        Log.d(TAG, message);
    }
}


