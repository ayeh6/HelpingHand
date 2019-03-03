package com.example.helpinghand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SeekerPostingActivity extends AppCompatActivity {

    TextView seekerID, logo;
    EditText jobTitle, jobDescription, jobRequirements, date, time;
    Button pButton;

    String userID;

    boolean published = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_posting);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userID= null;
            } else {
                userID= extras.getString("userID");
            }
        } else {
            userID= (String) savedInstanceState.getSerializable("userID");
        }

        jobTitle = findViewById(R.id.title_input);
        jobDescription = findViewById(R.id.description_input);
        jobRequirements = findViewById(R.id.requirement_input);
        date = findViewById(R.id.date_input);
        time = findViewById(R.id.time_input);

        pButton = findViewById(R.id.postButton);

        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_page();
            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra(SeekerMainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.seeker_ID);
        textView.setText(message);


    }

    public void main_page() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> listing = new HashMap<>();
        listing.put("jobTitle",jobTitle.getText().toString());
        listing.put("jobDescription",jobDescription.getText().toString());
        listing.put("jobRequirements",jobRequirements.getText().toString());
        listing.put("jobTime",time.getText().toString());
        listing.put("jobDate",date.getText().toString());
        listing.put("jobUserHost",userID);

        db.collection("listings")
                .add(listing)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        Intent intent = new Intent(this, SeekerMainActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}