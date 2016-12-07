package com.example.mgarey2.familymap.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.model.Event;
import com.example.mgarey2.familymap.model.LocalData;
import com.example.mgarey2.familymap.model.Person;
import com.example.mgarey2.familymap.ui_tools.ExpandableListAdapater;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class PersonActivity extends AppCompatActivity {

    // TODO: Display an event on the map or person when touched.
    // Store the people and event objects
    // so that when a person or event is clicked, it is easier to get to the person/event.

    private final String LOG_TAG = "PersonActivity";
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        // Get Person
        person = (Person) getIntent().getSerializableExtra("Person");
        Log.d(LOG_TAG, person.toString());

        // Set person summary views: name and gender
        TextView firstNameView = (TextView) findViewById(R.id.person_first_name);
        firstNameView.setText(person.getFirstName());
        TextView lastNameView = (TextView) findViewById(R.id.person_last_name);
        lastNameView.setText(person.getLastName());
        TextView genderView = (TextView) findViewById(R.id.person_gender);
        String gender = person.getGender().equals("f") ? "Female" : "Male";
        genderView.setText(gender);

        // Prepare data for collapsible lists:
        // 2 groups: events and family
        ArrayList<String> groupItems = new ArrayList<>();
        ArrayList<Object> childItems = new ArrayList<>();
        ArrayList<String> child;

        // Events:
        HashSet<Event> events = LocalData.getPersonEvents(person.getPersonId());
        child = new ArrayList<>();
        groupItems.add("Life Events");
        for (Event event : events) {
            child.add(event.getEventSummary());
        }
        childItems.add(child);

        // Family:
        child = new ArrayList<>();
        groupItems.add("Family");
        child.add(person.getFather());
        child.add(person.getMother());
        child.add(person.getSpouse());
        childItems.add(child);

        // Set events view:
        ExpandableListView eventsView = (ExpandableListView) findViewById(R.id.events_list);
        eventsView.setClickable(true);
        ExpandableListAdapter eventsAdapter = new ExpandableListAdapater(this, groupItems, childItems,
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        eventsView.setAdapter(eventsAdapter);

        // Expand by default
        eventsView.expandGroup(0);
        eventsView.expandGroup(1);

        // Setup callbacks
        eventsView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v,
                                        int groupPosition,
                                        int childPosition, long id) {
                Log.d(LOG_TAG, "onChildClick");
                return false;
            }
        });
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
