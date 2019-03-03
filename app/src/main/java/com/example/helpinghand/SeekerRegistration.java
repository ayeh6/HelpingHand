package com.example.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SeekerRegistration extends AppCompatActivity {

    EditText orgName, orgAddress;
    Spinner load;
    Button register;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_registration);

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

        ArrayAdapter<CharSequence> adapter;

        load = findViewById(R.id.loadspinnerseek);
        adapter = ArrayAdapter.createFromResource(this,R.array.loads,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        load.setAdapter(adapter);

        orgAddress = findViewById(R.id.orgAddressInput);
        orgName = findViewById(R.id.orgNameInput);

        register = findViewById(R.id.seekerButtonRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerseeker();
            }
        });

    }

    public void registerseeker() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("address",orgAddress.getText().toString());
        user.put("orgName",orgName.getText().toString());
        user.put("load",load.getSelectedItem().toString());
        db.collection("users").document(userID).set(user, SetOptions.merge());
        Intent intent = new Intent(this,SeekerMainActivity.class);
        startActivity(intent);
    }
}
