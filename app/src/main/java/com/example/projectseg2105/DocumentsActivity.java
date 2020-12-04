package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class DocumentsActivity extends AppCompatActivity implements EditDocsDialog.EditDocsDialogListener{
    private static final String TAG = "DocumentsActivity";

    private Button editDocument;
    private TextView firstDocument;
    private TextView secondDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        editDocument = findViewById(R.id.editDocuments);
        firstDocument = findViewById(R.id.firstImage);
        secondDocument = findViewById(R.id.secondImage);

        editDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDocuments();
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
                        firstDocument.setText("First Document: "+ document.get("firstDoc").toString());
                        secondDocument.setText("Second Document: " + document.get("secondDoc").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
    }
    public void editDocuments(){
        EditDocsDialog exampleDialog = new EditDocsDialog();
        exampleDialog.show(getSupportFragmentManager(), "edit documents dialog");
    }

    @Override
    public void applyResults(String firstDoc, String secondDoc) {
        firstDocument.setText("First Document: "+ firstDoc);
        secondDocument.setText("Second Document: "+secondDoc);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference services = db.collection("services").document(getIntent().getStringExtra("service_name"));

        Map<String, Object> storeBranch = new HashMap<>();
        storeBranch.put("firstDoc", firstDoc);
        storeBranch.put("secondDoc", firstDoc);

        services.update(storeBranch);
    }
}