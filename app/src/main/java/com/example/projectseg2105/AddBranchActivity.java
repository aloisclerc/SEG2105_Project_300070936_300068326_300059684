package com.example.projectseg2105;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBranchActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "AddBranchActivity";
    private TextView[] times;
    private int timeChanged;
    private EditText branchName;
    private EditText branchAddress;
    private EditText phoneNumber;
    private CheckBox driversCheck;
    private CheckBox healthCheck;
    private CheckBox photoCheck;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branch_name = branchName.getText().toString();
                String add = branchAddress.getText().toString();
                String phoneNum = phoneNumber.getText().toString();
                Boolean drivers_license = driversCheck.isChecked();
                Boolean health_card = healthCheck.isChecked();
                Boolean photoID = photoCheck.isChecked();
                String[] openTimes = new String[14];

                for(int i = 0; i < times.length; i++){
                    openTimes[i] = times[i].getText().toString();
                }


                if(TextUtils.isEmpty(branch_name) || TextUtils.isEmpty(add) || TextUtils.isEmpty(phoneNum)){
                    Toast.makeText( AddBranchActivity.this, "A field is missing a value", Toast.LENGTH_SHORT).show();
                } else {
                    if(getIntent().hasExtra("activity")){
                        if(getIntent().getStringExtra("activity").equals("edit")){
                            editBranch(branch_name, add, phoneNum, drivers_license, health_card, photoID, openTimes);
                            Intent intent = new Intent(AddBranchActivity.this, BranchesActivity.class);
                            intent.putExtra("branch_name", branch_name);
                            intent.putExtra("address", add);
                            intent.putExtra("phone", phoneNum);
                            intent.putExtra("drivers", drivers_license);
                            intent.putExtra("health", health_card);
                            intent.putExtra("photo", photoID);
                            intent.putExtra("times", openTimes);
                            startActivity(intent);
                            finish();
                        } else {
                            uploadBranch(branch_name, add, phoneNum, drivers_license, health_card, photoID, openTimes);
                            startActivity(new Intent(AddBranchActivity.this, EmployeeActivity.class));
                            finish();
                        }
                    }

                }
            }
        });
    }

    private void editBranch(String branch_name, String address, String phone, Boolean drivers_license, Boolean health_card, Boolean photo_ID, String[] openTimes){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference branches = db.collection("branches").document(getIntent().getStringExtra("branch_name"));

        Map<String, Object> storeBranch = new HashMap<>();
        storeBranch.put("branch", branch_name);
        storeBranch.put("address", address);
        storeBranch.put("phone", phone);
        storeBranch.put("driversLicense", drivers_license);
        storeBranch.put("healthCard", health_card);
        storeBranch.put("photoID", photo_ID);

        List<String> times = Arrays.asList(openTimes);
        storeBranch.put("openTimes", times);

        branches.set(storeBranch);


    }

    private void uploadBranch(String branch_name, String address, String phone, Boolean drivers_license, Boolean health_card, Boolean photo_ID, String[] openTimes){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference branches = db.collection("branches");

        Map<String, Object> storeBranch = new HashMap<>();
        storeBranch.put("branch", branch_name);
        storeBranch.put("address", address);
        storeBranch.put("phone", phone);
        storeBranch.put("driversLicense", drivers_license);
        storeBranch.put("healthCard", health_card);
        storeBranch.put("photoID", photo_ID);

        List<String> times = Arrays.asList(openTimes);
        storeBranch.put("openTimes", times);

        branches.document(branch_name).set(storeBranch);
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
        submit = findViewById(R.id.submitBranch);
        times[0] = findViewById(R.id.mondayStartSearch);
        times[1] = findViewById(R.id.mondayEndSearch);
        times[2] = findViewById(R.id.tuesdayStart);
        times[3] = findViewById(R.id.tuesdayEndSearch);
        times[4] = findViewById(R.id.wednedayStart);
        times[5] = findViewById(R.id.wednesdayEndSearch);
        times[6] = findViewById(R.id.thursdayStart);
        times[7] = findViewById(R.id.thursdayEndSearch);
        times[8] = findViewById(R.id.fridayStartSearch);
        times[9] = findViewById(R.id.fridayEndSearch);
        times[10] = findViewById(R.id.saturdayStartSearch);
        times[11] = findViewById(R.id.saturdayEnd);
        times[12] = findViewById(R.id.sundayStartSearch);
        times[13] = findViewById(R.id.sundayEnd);

        if(getIntent().hasExtra("activity")) {
            if (getIntent().getStringExtra("activity").equals("edit")) {
                ArrayList<String> openTimes = (ArrayList<String>) getIntent().getSerializableExtra("openTimes");
                for(int i = 0; i < openTimes.size(); i++){
                    times[i].setText((String) openTimes.get(i));
                }
                branchName.setText(getIntent().getStringExtra("branch_name"));
                branchAddress.setText(getIntent().getStringExtra("branch_address"));
                phoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
            }
        }

    }

}