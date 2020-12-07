package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private static final String TAG = "SearchActivity";
    private TextView[] times;
    private int timeChanged;
    private EditText branchName;
    private EditText branchAddress;
    private EditText phoneNumber;
    private CheckBox driversCheck;
    private CheckBox healthCheck;
    private CheckBox photoCheck;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initItems();

        for(int i = 0; i < times.length; i++){
            final int index = i;
            times[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "clicked on: " + v.toString());
                    timeChanged = index;
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }
            });
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    private void search(){
        final String branch_name = branchName.getText().toString();
        final String add = branchAddress.getText().toString();
        final String phoneNum = phoneNumber.getText().toString();
        final Boolean drivers_license = driversCheck.isChecked();
        final Boolean health_card = healthCheck.isChecked();
        final Boolean photoID = photoCheck.isChecked();
        final String[] openTimes = new String[14];
        final ArrayList<String> validBranches  = new ArrayList<>();


        for(int i = 0; i < times.length; i++){
            openTimes[i] = times[i].getText().toString();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference colRef = db.collection("branches");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tempBranch = document.get("branch").toString();
                        String tempAdd = document.get("address").toString();
                        String tempPhone = document.get("phone").toString();
                        Boolean tempDrivers = (Boolean) document.get("driversLicense");
                        Boolean tempHealth = (Boolean) document.get("healthCard");
                        Boolean tempPhoto = (Boolean) document.get("photoID");
                        ArrayList<String> tempTimes = (ArrayList<String>) document.get("openTimes");

                        if((tempDrivers || !drivers_license) && (tempHealth || !health_card) && (tempPhoto || !photoID)){
                            Log.d(TAG, "Branch: "+ tempBranch + (branch_name.isEmpty() || (tempBranch.toLowerCase().contains(branch_name.toLowerCase()))));
                            if(branch_name.isEmpty() || (tempBranch.toLowerCase().contains(branch_name.toLowerCase()))){
                                Log.d(TAG, "Made it this far");
                                if(add.isEmpty() || (tempAdd.toLowerCase().contains(add.toLowerCase()))){
                                    if(phoneNum.isEmpty() || (tempPhone.toLowerCase().contains(phoneNum.toLowerCase()))){
                                        boolean flag = true;
                                        for (int i = 0; i < tempTimes.size(); i++) {
                                            if(!openTimes[i].equals("00:00") && !openTimes[i].equals(tempTimes.get(i))){
                                                flag = false;
                                            }
                                        }
                                        if(flag){
                                            validBranches.add(tempBranch);

                                        }
                                    }
                                }
                            }
                        }


                    }
                    if(getIntent().getStringExtra("previous").equals("User")){
                        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                        intent.putStringArrayListExtra("validBranches", validBranches);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SearchActivity.this, EmployeeActivity.class);
                        intent.putStringArrayListExtra("validBranches", validBranches);
                        startActivity(intent);
                        finish();
                    }


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String sMinute = minute+"";
        String sHour = hourOfDay+"";

        if(minute < 10){
            sMinute = "0"+minute;
        }
        if(hourOfDay < 10){
            sHour = "0"+hourOfDay;
        }
        times[timeChanged].setText(sHour+":"+sMinute);


    }

    private void initItems(){
        times = new TextView[14];


        branchName = findViewById(R.id.searchBranchName);
        branchAddress = findViewById(R.id.searchAddress);
        phoneNumber = findViewById(R.id.searchPhone);
        driversCheck = findViewById(R.id.searchDriversCheck);
        healthCheck = findViewById(R.id.searchHealthCheck);
        photoCheck = findViewById(R.id.searchPhotoCheck);
        search = findViewById(R.id.submitBranchSearch);
        times[0] = findViewById(R.id.mondayStartSearch);
        times[1] = findViewById(R.id.mondayEndSearch);
        times[2] = findViewById(R.id.tuesdayStartSearch);
        times[3] = findViewById(R.id.tuesdayEndSearch);
        times[4] = findViewById(R.id.wednedayStartSearch);
        times[5] = findViewById(R.id.wednesdayEndSearch);
        times[6] = findViewById(R.id.thursdayStartSearch);
        times[7] = findViewById(R.id.thursdayEndSearch);
        times[8] = findViewById(R.id.fridayStartSearch);
        times[9] = findViewById(R.id.fridayEndSearch);
        times[10] = findViewById(R.id.saturdayStartSearch);
        times[11] = findViewById(R.id.saturdayEndSearch);
        times[12] = findViewById(R.id.sundayStartSearch);
        times[13] = findViewById(R.id.sundayEndSearch);



    }
}