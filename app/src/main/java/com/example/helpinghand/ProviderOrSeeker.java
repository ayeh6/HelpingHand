package com.example.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ProviderOrSeeker extends AppCompatActivity {

    Button provider,seeker;
    String userID;

    public void registerAsSeeker() {
        Map<String,Object> user = new HashMap<>();
        user.put("type","seeker");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userID).set(user, SetOptions.merge());
        Intent intent = new Intent(this,SeekerRegistration.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    }

    public void registerAsProvider() {
        Map<String,Object> user = new HashMap<>();
        user.put("type","provider");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userID).set(user, SetOptions.merge());
        Intent intent = new Intent(this,ProviderRegistration.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_or_seeker);

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

        provider = findViewById(R.id.providerButton);
        seeker = findViewById(R.id.seekerButton);

        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAsProvider();
            }
        });

        seeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAsSeeker();
            }
        });

    }
}
