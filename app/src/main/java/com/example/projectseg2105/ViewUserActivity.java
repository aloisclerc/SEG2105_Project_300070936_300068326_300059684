package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewUserActivity extends AppCompatActivity {
    private static final String TAG = "ViewUserActivity";

    private ArrayList<String> mEmails = new ArrayList<>();
    private ArrayList<String> mTypes = new ArrayList<>();
    private Button servicePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        Log.d(TAG, "onCreate: started");

        servicePage = (Button) findViewById(R.id.servicePage);

        servicePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openServices();
            }
        });

        initEmails();


    }

    public void openServices(){
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }



    private void initEmails(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference colRef = db.collection("users");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tempEmail = document.get("email").toString();
                        String tempType = document.get("type").toString();
                        mEmails.add(tempEmail);
                        mTypes.add(tempType);
                        Log.d(TAG, "Email: "+ tempEmail);
                        Log.d(TAG, "Type: "+ tempType);
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

        RecyclerView recyclerView = findViewById((R.id.userList));
        UserViewAdapter adapter = new UserViewAdapter(mEmails, mTypes, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }




}