package com.example.projectseg2105;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewUserActivity extends AppCompatActivity {

    private RecyclerView userList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] emails = new String[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        userList = (RecyclerView) findViewById(R.id.userList);

        layoutManager = new LinearLayoutManager(this);
        userList.setLayoutManager(layoutManager);

        emails[0] = "test@test.com";
        emails[1] = "test2@test.com";

        mAdapter = new MyAdapter(emails);
        userList.setAdapter(mAdapter);


    }


}