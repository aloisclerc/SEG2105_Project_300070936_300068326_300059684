package com.example.projectseg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {
    private static final String TAG = "ServicesActivity";

    private Button editBranch, driversLicence, healthCard, photoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

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
        //intent.putExtra("activity","edit");
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

            Log.d(TAG, "getIncomingIntent:" + serviceName);

            setName(serviceName, branchAddress, phoneNumber, drivers, health, photo);
        }
    }

    private void setName(String serviceName, String branchAddress, String phoneNumber, Boolean drivers, Boolean health, Boolean photo){
        Log.d(TAG, "setName");
        Log.d(TAG, "booleans: " + drivers+ " " + health + " " + photo);
        TextView title = findViewById(R.id.servicesText);
        TextView add = findViewById(R.id.branchAddress);
        TextView phone = findViewById(R.id.branchPhone);


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