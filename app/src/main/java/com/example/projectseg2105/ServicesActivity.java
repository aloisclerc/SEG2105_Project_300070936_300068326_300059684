package com.example.projectseg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServicesActivity extends AppCompatActivity {

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