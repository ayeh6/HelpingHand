package com.example.helpinghand;

import java.util.ArrayList;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SeekerMainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.ihatethis.MESSAGE";

    //Allows for an expanding dynamic list on the screen
    ListView lvTimes;

    String userID;

    /*Array list used to store and pass/receive JobTitles to and from the FireBase database.
    The JobTitles Originate in the SeekerPostingActivity Screen.
    Ideally requestList would contain more information, like date and time.
     */
    ArrayList<String> requestList = new ArrayList<>(); //ArrayList<TextView> requestList = new ArrayList<TextView>();

    //Used to access the SeekerPostingActivity screen via the MAKE POST button
    TextView seekerID;
    Button pButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_main);

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //find the MAKE POST button
        pButton = findViewById(R.id.makePostButton);
        //when button is clicked call function to open SeekerPostingActivity screen
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_activity();
            }
        });

        lvTimes = findViewById(R.id.seekermainListview);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,requestList);
        lvTimes.setAdapter(arrayAdapter);
        lvTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        db.collection("listings")
                .whereEqualTo("jobUserHost", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                arrayAdapter.add(document.getData().get("jobTitle").toString());
                            }
                        } else {

                        }
                    }
                });

    }

    public void post_activity() {
        //Passes the Seeker's ID to the SeekerPostingActivity screen - More a quality of life decision than a necessity
        Intent intent = new Intent(this, SeekerPostingActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}