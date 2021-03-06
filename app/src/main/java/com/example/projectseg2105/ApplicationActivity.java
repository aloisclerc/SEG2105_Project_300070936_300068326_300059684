package com.example.projectseg2105;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.lang.ref.Reference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationActivity extends AppCompatActivity {
    private static final String TAG = "ApplicationActivity";

    TextView application;
    EditText firstName;
    EditText lastName;
    EditText DOB;
    EditText appointmentDate;
    EditText address;
    TextView addQuestion;
    EditText addReply;
    TextView firstDocText;
    Button firstDoc;
    ImageView firstImage;
    TextView secondDocText;
    Button secondDoc;
    ImageView secondImage;
    int whichDoc;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        initFields();

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    apply();
                } catch (ParseException e) {
                    Toast.makeText(ApplicationActivity.this, "An error took place with one of your dates. Please check the format and try again.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        firstDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDoc = 0;
                selectImage(ApplicationActivity.this);
            }
        });
        secondDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDoc = 1;
                selectImage(ApplicationActivity.this);
            }
        });
    }


    private void apply() throws ParseException {



        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(appointmentDate.getText().toString());
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(DOB.getText().toString());
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();

        if(firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || DOB.getText().toString().isEmpty() || appointmentDate.getText().toString().isEmpty() || address.getText().toString().isEmpty() || addReply.getText().toString().isEmpty()){
            Toast.makeText(ApplicationActivity.this, "One or more of your fields were left empty. Please try again.", Toast.LENGTH_SHORT).show();
        } else if(date1.before(dateobj)){
            Toast.makeText(ApplicationActivity.this, "Appointment Date must be after current date.", Toast.LENGTH_SHORT).show();
        } else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();


            String branch_name = getIntent().getStringExtra("branch_name");

            DocumentReference branch = db.collection("branches").document(branch_name);

            Map<String, Object> storeApplication = new HashMap<>();

            storeApplication.put("firstName", firstName.getText().toString());
            storeApplication.put("lastName", lastName.getText().toString());
            storeApplication.put("DOB", DOB.getText().toString());
            storeApplication.put("appointmentDate", appointmentDate.getText().toString());
            storeApplication.put("address", address.getText().toString());
            storeApplication.put("addReply", addReply.getText().toString());

            branch.collection(application.getText().toString()).document(firstName.getText().toString()).set(storeApplication);

            startActivity(new Intent(ApplicationActivity.this, HomeActivity.class));
            finish();
        }


    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        if(whichDoc == 0){
                            firstImage.setImageBitmap(selectedImage);
                        } else {
                            secondImage.setImageBitmap(selectedImage);
                        }

                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                Log.d(TAG, "Picture: " + picturePath);
                                Log.d(TAG, "Picture 2 Electric Boogaloo: " + BitmapFactory.decodeFile(picturePath));
                                verifyStoragePermissions(ApplicationActivity.this);
                                if(whichDoc == 0){
                                    firstImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                } else {
                                    secondImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                }

                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void initFields(){
        application = findViewById(R.id.applicationName);
        firstName = findViewById(R.id.applicationFirstName);
        lastName = findViewById(R.id.applicationLastName);
        DOB = findViewById(R.id.applicationDate);
        appointmentDate = findViewById(R.id.appointmentDate);
        address = findViewById(R.id.applicationAddress);
        addQuestion = findViewById(R.id.additionalQuestion);
        addReply = findViewById(R.id.applicationAnswer);
        firstDocText = findViewById(R.id.applicationFirstDoc);
        firstDoc = findViewById(R.id.firstDocButton);
        firstImage = findViewById(R.id.firstDocImage);
        secondDocText = findViewById(R.id.applicationSecondDocument);
        secondDoc = findViewById(R.id.secondDocButton);
        secondImage = findViewById(R.id.secondDocImage);
        whichDoc = 0;
        apply = findViewById(R.id.apply);


        String applicationType = getIntent().getStringExtra("appType");


        application.setText(applicationType);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference colRef = db.collection("services").document(applicationType);
        colRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String tempQuestion = document.get("additionalQuestion").toString();
                        String tempFirst = document.get("firstDoc").toString();
                        addQuestion.setText(tempQuestion);
                        firstDocText.setText(tempFirst);
                        secondDocText.setText(document.get("secondDoc").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
    }
}