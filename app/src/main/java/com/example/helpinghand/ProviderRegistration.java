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

public class ProviderRegistration extends AppCompatActivity {

    Spinner gender,load;
    EditText username,age,address,hoursPerMonth;
    Button register;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_registration);

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

        gender = findViewById(R.id.genderSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        load = findViewById(R.id.loadspinnerseek);
        adapter = ArrayAdapter.createFromResource(this,R.array.loads,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        load.setAdapter(adapter);

        username = findViewById(R.id.username_input);
        age = findViewById(R.id.age_input);
        address = findViewById(R.id.address_input);
        hoursPerMonth = findViewById(R.id.hoursPerMonth);

        register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registered();
            }
        });

    }

    public void registered() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String usr = username.getText().toString();
        String agge = age.getText().toString();
        String adddress = address.getText().toString();
        Map<String, Object> user = new HashMap<>();
        user.put("username", usr);
        user.put("age", agge);
        user.put("address", adddress);
        user.put("gender",gender.getSelectedItem().toString().trim());
        user.put("preferredLoad",load.getSelectedItem().toString().trim());
        user.put("totalHours","0");
        user.put("hoursPerMonth",hoursPerMonth.getText().toString());
        db.collection("users").document(userID).set(user, SetOptions.merge());
        Intent intent = new Intent(this,ProviderMainActivity.class);
        startActivity(intent);
    }


}
