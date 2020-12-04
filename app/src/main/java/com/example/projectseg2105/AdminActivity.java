package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity implements AddServiceDialog.AddServiceDialogListener {
    private static final String TAG = "AdminActivity";

    private ArrayList<String> mServices = new ArrayList<>();
    private ArrayList<Boolean> mDriversLicenses = new ArrayList<>();
    private ArrayList<Boolean> mHealthCards = new ArrayList<>();
    private ArrayList<Boolean> mPhotoIDs = new ArrayList<>();

    private RecyclerView recyclerView;
    private ServiceViewAdapter adapter;

    private Button userPage;
    private Button addService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userPage = findViewById(R.id.userPage);
        addService = findViewById(R.id.addService);

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openUsers();
            }
        });
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService();
            }
        });
        initServices();
    }

    public void addService(){
        AddServiceDialog exampleDialog = new AddServiceDialog();
        exampleDialog.show(getSupportFragmentManager(), "add service dialog");
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
                        mServices.add(tempService);
                        Log.d(TAG, "Service: "+ tempService);
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

        recyclerView = findViewById((R.id.serviceList));
        adapter = new ServiceViewAdapter(mServices, mDriversLicenses, mHealthCards, mPhotoIDs, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    public void applyResults(String service_name, String addQuest, String addDoc1, String addDoc2, String addRate) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference services = db.collection("services");

        Map<String, Object> storeService = new HashMap<>();
        storeService.put("service", service_name);
        storeService.put("additionalQuestion", addQuest);
        storeService.put("firstDoc", addDoc1);
        storeService.put("secondDoc", addDoc2);
        storeService.put("rate", addRate);

        services.document(service_name).set(storeService);




        mServices.add(service_name);
        adapter.notifyItemInserted(mServices.size() - 1);


    }
}