package com.example.mgarey2.familymap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.model.Person;

public class PersonActivity extends AppCompatActivity {

    private final String LOG_TAG = "PersonActivity";
    private Person person = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        // Get Person
        person = (Person) getIntent().getSerializableExtra("Person");
        Log.d(LOG_TAG, person.toString());

    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
