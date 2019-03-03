package com.example.helpinghand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProviderMainActivity extends AppCompatActivity {

    ArrayList<String> requestList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    ListView lvTimes;

    public void readData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("listings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String temp = document.getData().get("jobTitle").toString();
                        arrayAdapter.add(temp);
                    }
                } else {

                }
            }
        });
    }


    public void initializeListView() {
        lvTimes = findViewById(R.id.providermainListview);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,requestList);
        lvTimes.setAdapter(arrayAdapter);

        lvTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotolisting(position);
            }
        });
        readData();
    }

    public void gotolisting(int position) {
        String selected = requestList.get(position);
        Intent intent = new Intent(this,ListingPost.class);
        intent.putExtra("item",selected);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);

        initializeListView();

    }

}