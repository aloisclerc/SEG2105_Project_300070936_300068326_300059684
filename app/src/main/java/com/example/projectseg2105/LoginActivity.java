package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();
                loginUser(email_txt, password_txt);
            }
        });
    }

    private void loginUser(final String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText( LoginActivity.this, "You are now Logged In", Toast.LENGTH_SHORT).show();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("users").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.get("type").toString().equals("Admin")) {
                                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                finish();
                            } else if(document.get("type").toString().equals("Employee")) {
                                startActivity(new Intent(LoginActivity.this, EmployeeActivity.class));
                                finish();
                            } else {
                                Toast.makeText( LoginActivity.this, document.get("type").toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                    }
                });
            }
        });
    }
}