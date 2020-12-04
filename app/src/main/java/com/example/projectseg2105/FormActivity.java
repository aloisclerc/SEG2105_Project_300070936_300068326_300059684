package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormActivity extends AppCompatActivity implements EditFormDialog.EditFormDialogListener{
    private static final String TAG = "DocumentsActivity";

    private Button editForm;
    private TextView additional_quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editForm = findViewById(R.id.editForm);
        additional_quest = findViewById(R.id.extraQuestion);

        editForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editForm();
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
                        additional_quest.setText("Additional Question: "+ document.get("additionalQuestion").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });

    }

    public void editForm(){
        EditFormDialog exampleDialog = new EditFormDialog();
        exampleDialog.show(getSupportFragmentManager(), "edit documents dialog");
    }

    @Override
    public void applyResults(String addQuest) {
        additional_quest.setText("Additional Question: " + addQuest);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference services = db.collection("services").document(getIntent().getStringExtra("service_name"));

        Map<String, Object> storeBranch = new HashMap<>();
        storeBranch.put("additionalQuestion", addQuest);

        services.update(storeBranch);
    }
}