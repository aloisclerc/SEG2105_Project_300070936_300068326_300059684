package com.example.projectseg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BranchesActivity extends AppCompatActivity {
    private static final String TAG = "ServicesActivity";

    private Button editBranch, driversLicence, healthCard, photoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);

        editBranch = findViewById(R.id.editBranch);
        driversLicence = (Button) findViewById(R.id.driversLicenseBtn);
        healthCard = (Button) findViewById(R.id.healthCardBtn);
        photoID = (Button) findViewById(R.id.photoIdBtn);


        editBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                editBranch();
            }
        });
        driversLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openDriversLicence();
            }
        });
        healthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openHealthCard();
            }
        });
        photoID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openPhotoID();
            }
        });

        driversLicence.setVisibility(View.GONE);
        healthCard.setVisibility(View.GONE);
        photoID.setVisibility(View.GONE);



        getIncomingIntent();
    }

    private void editBranch(){

        Intent intent = new Intent(this, AddBranchActivity.class);
        intent.putExtra("activity","edit");
        intent.putExtra("branch_address",getIntent().getStringExtra("address"));
        intent.putExtra("phoneNumber",getIntent().getStringExtra("phone"));
        intent.putExtra("openTimes", (ArrayList<String>) getIntent().getSerializableExtra("times"));
        intent.putExtra("branch_name",getIntent().getStringExtra("branch_name"));
        startActivity(intent);
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent");
        if(getIntent().hasExtra("branch_name") && getIntent().hasExtra("drivers") && getIntent().hasExtra("health") && getIntent().hasExtra("photo") && getIntent().hasExtra("address") && getIntent().hasExtra("phone")){
            String serviceName = getIntent().getStringExtra("branch_name");
            String branchAddress = getIntent().getStringExtra("address");
            String phoneNumber = getIntent().getStringExtra("phone");
            Boolean drivers = getIntent().getBooleanExtra("drivers", false);
            Boolean health = getIntent().getBooleanExtra("health", false);
            Boolean photo = getIntent().getBooleanExtra("photo", false);
            ArrayList<String> times = (ArrayList<String>) getIntent().getSerializableExtra("times");
            String userType = getIntent().getStringExtra("previous");

            Log.d(TAG, "getIncomingIntent:" + serviceName);

            setName(serviceName, branchAddress, phoneNumber, drivers, health, photo, times, userType);
        }
    }

    private void setName(final String serviceName, String branchAddress, String phoneNumber, Boolean drivers, Boolean health, Boolean photo, ArrayList times, String userType){
        Log.d(TAG, "setName");
        Log.d(TAG, "booleans: " + drivers+ " " + health + " " + photo);



        TextView title = findViewById(R.id.servicesText);
        TextView add = findViewById(R.id.branchAddress);
        TextView phone = findViewById(R.id.branchPhone);

        TextView[] layoutTimes = new TextView[14];

        layoutTimes[0] = findViewById(R.id.monday_start1);
        layoutTimes[1] = findViewById(R.id.monday_end);
        layoutTimes[2] = findViewById(R.id.tuesday_start);
        layoutTimes[3] = findViewById(R.id.tuesday_end);
        layoutTimes[4] = findViewById(R.id.wedneaday_start);
        layoutTimes[5] = findViewById(R.id.wednesday_end);
        layoutTimes[6] = findViewById(R.id.thursday_start);
        layoutTimes[7] = findViewById(R.id.thursday_end);
        layoutTimes[8] = findViewById(R.id.friday_start);
        layoutTimes[9] = findViewById(R.id.friday_end);
        layoutTimes[10] = findViewById(R.id.saturday_start);
        layoutTimes[11] = findViewById(R.id.saturday_end);
        layoutTimes[12] = findViewById(R.id.sunday_start);
        layoutTimes[13] = findViewById(R.id.sunday_end);


        for(int i = 0; i < times.size(); i++){
            layoutTimes[i].setText((String) times.get(i));
        }
        title.setText(serviceName);
        add.setText(branchAddress);
        phone.setText(phoneNumber);


        if(drivers){
            driversLicence.setVisibility(View.VISIBLE);
        }
        if(health){
            healthCard.setVisibility(View.VISIBLE);
        }
        if(photo){
            photoID.setVisibility(View.VISIBLE);
        }

        if(userType.equals("User")){
            editBranch.setVisibility(View.GONE);
            driversLicence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(BranchesActivity.this, ApplicationActivity.class);
                    intent.putExtra("branch_name", serviceName);
                    intent.putExtra("appType", "Driver's License");
                    startActivity(intent);
                }
            });
            healthCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(BranchesActivity.this, ApplicationActivity.class);
                    intent.putExtra("branch_name", serviceName);
                    intent.putExtra("appType", "Health Card");
                    startActivity(intent);
                }
            });
            photoID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(BranchesActivity.this, ApplicationActivity.class);
                    intent.putExtra("branch_name", serviceName);
                    intent.putExtra("appType", "Photo ID");
                    startActivity(intent);
                }
            });
        }
    }

    public void openDriversLicence(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openHealthCard(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openPhotoID(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}