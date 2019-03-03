package com.example.helpinghand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ListingPost extends AppCompatActivity {

    TextView jobDescription, jobTime, jobDate, load, jobTitle;
    Button aButton;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_post);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                selected= null;
            } else {
                selected= extras.getString("item");
            }
        } else {
            selected= (String) savedInstanceState.getSerializable("item");
        }

        jobDescription = findViewById(R.id.jobDescription);
        jobDate = findViewById(R.id.jobDate);
        jobTime = findViewById(R.id.jobTIme);
        jobTitle = findViewById(R.id.jobTitle);
        load = findViewById(R.id.load);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("listings").whereEqualTo("jobTitle",selected).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        jobDescription.setText(document.get("jobDescription").toString());
                        jobDate.setText(document.get("jobDate").toString());
                        jobTime.setText(document.get("jobTime").toString());
                        jobTitle.setText(document.get("jobTitle").toString());
                    }
                } else {

                }
            }
        });

        aButton = findViewById(R.id.acceptButton);

        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_Listing();
            }
        });
    }

    public void accept_Listing() {
        Intent intent = new Intent(this,ProviderMainActivity.class);
        startActivity(intent);
    }
}