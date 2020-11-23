package com.example.projectseg2105;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
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
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
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
                    uploadBranch(branch_name, add, phoneNum, drivers_license, health_card, photoID, openTimes);
                    startActivity(new Intent(AddBranchActivity.this, EmployeeActivity.class));
                    finish();
                }
            }
        });
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


        branchName = findViewById(R.id.addBranchName);
        branchAddress = findViewById(R.id.addAddress);
        phoneNumber = findViewById(R.id.addPhone);
        driversCheck = findViewById(R.id.driversCheck);
        healthCheck = findViewById(R.id.healthCheck);
        photoCheck = findViewById(R.id.photoCheck);
        submit = findViewById(R.id.submitBranch);
        times[0] = findViewById(R.id.mondayStart);
        times[1] = findViewById(R.id.mondayEnd);
        times[2] = findViewById(R.id.tuesdayStart);
        times[3] = findViewById(R.id.tuesdayEnd);
        times[4] = findViewById(R.id.wednedayStart);
        times[5] = findViewById(R.id.wednesdayEnd);
        times[6] = findViewById(R.id.thursdayStart);
        times[7] = findViewById(R.id.thursdayEnd);
        times[8] = findViewById(R.id.fridayStart);
        times[9] = findViewById(R.id.fridayEnd);
        times[10] = findViewById(R.id.saturdayStart);
        times[11] = findViewById(R.id.saturdayEnd);
        times[12] = findViewById(R.id.sundayStart);
        times[13] = findViewById(R.id.sundayEnd);

    }

}