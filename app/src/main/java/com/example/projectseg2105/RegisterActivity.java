package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email;
    private EditText password;
    private Button register;
    private Spinner type;
    private EditText firstName;
    private EditText lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        type = findViewById(R.id.type);
        String[] items = new String[]{"User", "Employee", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        type.setAdapter(adapter);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();
                String first_txt = firstName.getText().toString();
                String last_txt = lastName.getText().toString();
                String type_txt = type.getSelectedItem().toString();

                if(TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)){
                    Toast.makeText( RegisterActivity.this, "Please Enter a Value into both Fields", Toast.LENGTH_SHORT).show();
                } else if(password_txt.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be > 5 characters", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email_txt, password_txt, first_txt, last_txt, type_txt);

                }
            }
        });
    }

    private void registerUser(final String email, String password, final String first, final String last, final String type) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String userEmail = user.getEmail();


                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(firstName.getText().toString()).build();

                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, userEmail, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });;

                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    CollectionReference users = db.collection("users");

                    Map<String, Object> storeUser = new HashMap<>();
                    storeUser.put("email", email);
                    storeUser.put("type", type);
                    storeUser.put("first", first);
                    storeUser.put("last", last);

                    users.document(email).set(storeUser);

                    DocumentReference docRef = db.collection("users").document(email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.get("type").toString().equals("Admin")) {
                                    startActivity(new Intent(RegisterActivity.this, AdminActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                    finish();
                                }
                            }
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}