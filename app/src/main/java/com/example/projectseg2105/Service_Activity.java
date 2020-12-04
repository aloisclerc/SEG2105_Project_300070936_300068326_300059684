package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Service_Activity extends AppCompatActivity {
    private static final String TAG = "ServiceActivity";

    private Button form;
    private Button documents;
    private TextView rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_);

        form = (Button) findViewById(R.id.form);
        documents = (Button) findViewById(R.id.documents);
        rate = findViewById(R.id.rate);

        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openForm();
            }
        });

        documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openDocuments();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference colRef = db.collection("services").document(getIntent().getStringExtra("service_name"));
        colRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String tempRate = document.get("rate").toString();
                        rate.setText(tempRate);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
    }

    public void openForm(){
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("service_name", getIntent().getStringExtra("service_name"));
        startActivity(intent);
    }

    public void openDocuments(){
        Intent intent = new Intent(this, DocumentsActivity.class);
        intent.putExtra("service_name", getIntent().getStringExtra("service_name"));
        startActivity(intent);
    }
}