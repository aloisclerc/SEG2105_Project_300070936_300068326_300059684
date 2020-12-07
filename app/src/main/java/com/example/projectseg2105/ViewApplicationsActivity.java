package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewApplicationsActivity extends AppCompatActivity {
    private static final String TAG = "ViewUserActivity";

    private TextView applicationType;

    private String branch;

    private ArrayList<String> mFirstNames = new ArrayList<>();
    private ArrayList<String> mLastNames = new ArrayList<>();
    private ArrayList<String> mDOB = new ArrayList<>();
    private ArrayList<String> mAppointmentDates = new ArrayList<>();
    private ArrayList<String> mAddresses = new ArrayList<>();
    private ArrayList<String> mAddReplies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applications);

        applicationType = findViewById(R.id.applicationType);

        initApplications();

    }

    private void initApplications(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String appType = getIntent().getStringExtra("appType");
        String branchName = getIntent().getStringExtra("branch_name");
        branch = branchName;
        applicationType.setText(appType);

        CollectionReference colRef = db.collection("branches").document(branchName).collection(appType);
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tempFirst = document.get("firstName").toString();
                        String tempLast = document.get("lastName").toString();
                        String tempDOB = document.get("DOB").toString();
                        String tempAppointmentDate = document.get("appointmentDate").toString();
                        String tempAddress = document.get("address").toString();
                        String tempAddReplies = document.get("addReply").toString();
                        mFirstNames.add(tempFirst);
                        mLastNames.add(tempLast);
                        mDOB.add(tempDOB);
                        mAppointmentDates.add(tempAppointmentDate);
                        mAddresses.add(tempAddress);
                        mAddReplies.add(tempAddReplies);
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

        RecyclerView recyclerView = findViewById((R.id.applicationList));
        ApplicationViewAdapter adapter = new ApplicationViewAdapter(mFirstNames, mLastNames, mDOB, mAppointmentDates, mAddresses, mAddReplies, branch, getIntent().getStringExtra("appType"), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}