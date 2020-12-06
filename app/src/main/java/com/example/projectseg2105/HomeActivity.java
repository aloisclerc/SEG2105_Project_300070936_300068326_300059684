  package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeActivity";

    private Button addBranch;

    private ArrayList<String> mBranches = new ArrayList<>();
    private ArrayList<String> mAddresses = new ArrayList<>();
    private ArrayList<String> mPhones = new ArrayList<>();
    private ArrayList<Boolean> mDriversLicenses = new ArrayList<>();
    private ArrayList<Boolean> mHealthCards = new ArrayList<>();
    private ArrayList<Boolean> mPhotoIDs = new ArrayList<>();

    private ArrayList<ArrayList<String>> mTimes = new ArrayList<>();

    private Button searchBranch;

    private RecyclerView recyclerView;
    private BranchViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        addBranch = findViewById(R.id.addBranch);
        searchBranch = findViewById(R.id.searchButton);

        searchBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                finish();
            }
        });

        addBranch.setVisibility(View.GONE);


        initBranches();
    }


    private void initBranches(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference colRef = db.collection("branches");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tempBranch = document.get("branch").toString();

                        if(getIntent().hasExtra("validBranches")){
                            ArrayList<String> validBranches = getIntent().getStringArrayListExtra("validBranches");

                            if(validBranches.contains(tempBranch)){
                                String tempAdd = document.get("address").toString();
                                String tempPhone = document.get("phone").toString();
                                Boolean tempDrivers = (Boolean) document.get("driversLicense");
                                Boolean tempHealth = (Boolean) document.get("healthCard");
                                Boolean tempPhoto = (Boolean) document.get("photoID");
                                ArrayList<String> tempTimes = (ArrayList<String>) document.get("openTimes");

                                mBranches.add(tempBranch);
                                mAddresses.add(tempAdd);
                                mPhones.add(tempPhone);
                                mDriversLicenses.add(tempDrivers);
                                mHealthCards.add(tempHealth);
                                mPhotoIDs.add(tempPhoto);
                                mTimes.add(tempTimes);
                            }
                        } else {
                            String tempAdd = document.get("address").toString();
                            String tempPhone = document.get("phone").toString();
                            Boolean tempDrivers = (Boolean) document.get("driversLicense");
                            Boolean tempHealth = (Boolean) document.get("healthCard");
                            Boolean tempPhoto = (Boolean) document.get("photoID");
                            ArrayList<String> tempTimes = (ArrayList<String>) document.get("openTimes");

                            mBranches.add(tempBranch);
                            mAddresses.add(tempAdd);
                            mPhones.add(tempPhone);
                            mDriversLicenses.add(tempDrivers);
                            mHealthCards.add(tempHealth);
                            mPhotoIDs.add(tempPhoto);
                            mTimes.add(tempTimes);
                        }
                        Log.d(TAG, "Branch: "+ tempBranch);
                    }


                    initRecyclerView();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });


    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        recyclerView = findViewById((R.id.branchList));
        adapter = new BranchViewAdapter(mBranches, mAddresses, mPhones, mDriversLicenses, mHealthCards, mPhotoIDs, mTimes, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }


}