package com.example.projectseg2105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

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
    }

    public void openUsers(){
        Intent intent = new Intent(this, ViewUserActivity.class);
        startActivity(intent);
    }
}