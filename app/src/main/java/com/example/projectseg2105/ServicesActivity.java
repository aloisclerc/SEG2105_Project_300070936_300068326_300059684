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

    private Button driversLicence, healthCard, photoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        driversLicence = (Button) findViewById(R.id.driversLicenseBtn);
        healthCard = (Button) findViewById(R.id.healthCardBtn);
        photoID = (Button) findViewById(R.id.photoIdBtn);



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

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent");
        if(getIntent().hasExtra("service_name") && getIntent().hasExtra("drivers") && getIntent().hasExtra("health") && getIntent().hasExtra("photo")){
            String serviceName = getIntent().getStringExtra("service_name");
            Boolean drivers = getIntent().getBooleanExtra("drivers", false);
            Boolean health = getIntent().getBooleanExtra("health", false);
            Boolean photo = getIntent().getBooleanExtra("photo", false);

            Log.d(TAG, "getIncomingIntent:" + serviceName);

            setName(serviceName, drivers, health, photo);
        }
    }

    private void setName(String serviceName, Boolean drivers, Boolean health, Boolean photo){
        Log.d(TAG, "setName");
        Log.d(TAG, "booleans: " + drivers+ " " + health + " " + photo);
        TextView title = findViewById(R.id.servicesText);
        title.setText(serviceName);

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