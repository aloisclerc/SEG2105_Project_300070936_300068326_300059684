package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "AdminActivity";

    private ArrayList<String> mServices = new ArrayList<>();
    private ArrayList<String> mDocuments = new ArrayList<>();

    private Button userPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userPage = (Button) findViewById(R.id.userPage);

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openUsers();
            }
        });
        initServices();
    }

    public void openUsers(){
        Intent intent = new Intent(this, ViewUserActivity.class);
        startActivity(intent);
    }

    private void initServices(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference colRef = db.collection("services");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tempService = document.get("service").toString();
                        String tempDocument = document.get("documents").toString();
                        mServices.add(tempService);
                        mDocuments.add(tempDocument);
                        Log.d(TAG, "Service: "+ tempService);
                        Log.d(TAG, "Documents: "+ tempDocument);
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

        RecyclerView recyclerView = findViewById((R.id.serviceList));
        ServiceViewAdapter adapter = new ServiceViewAdapter(mServices, mDocuments, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}