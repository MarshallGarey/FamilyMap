package com.example.mgarey2.familymap.person;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.event.Event;
import com.example.mgarey2.familymap.map.MapActivity;
import com.example.mgarey2.familymap.map.MapFragment;
import com.example.mgarey2.familymap.ui_tools.ExpandableListAdapater;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class PersonActivity extends AppCompatActivity {

    // TODO: Display an event on the map or person when touched.

    private final String LOG_TAG = "PersonActivity";
    private Person person;
    private Context context;
    private ArrayList<Object> childItems;
    private ArrayList<String> groupItems;

    // Keep a list of events and family members associated with the person in the same order that they are displayed
    private ArrayList<Event> personEvents;
    private ArrayList<Person> familyMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        context = this;

        // Setup actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

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
        groupItems = new ArrayList<>();
        childItems = new ArrayList<>();

        // Events:
        setEventItems();

        // Family:
        setFamilyItems();

        // Set events view:
        ExpandableListView eventsView = (ExpandableListView) findViewById(R.id.events_list);
        eventsView.setClickable(true);
        ExpandableListAdapter eventsAdapter = new ExpandableListAdapater(this, groupItems, childItems,
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        eventsView.setAdapter(eventsAdapter);

        // Expand by default
        eventsView.expandGroup(0);
        eventsView.expandGroup(1);

        // callbacks
        eventsView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v,
                                        int groupPosition,
                                        int childPosition, long id) {
                Log.d(LOG_TAG, "onChildClick, groupPosition: " + groupPosition +
                        " ,childPosition:" + childPosition + " ,id: " + id);

                // Event:
                if (groupPosition == 0) {
                    Log.d(LOG_TAG, personEvents.get(childPosition).toString());
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra(MapActivity.ZOOM_KEY, MapFragment.MAP_STATES[MapFragment.MAP_STATE_ZOOMED]);
                    intent.putExtra(MapActivity.EVENT_KEY, personEvents.get(childPosition));
                    startActivity(intent);
                }
                // Family member: Start new Person activity for that person.
                else {
                    Person p = familyMembers.get(childPosition);
                    Log.d(LOG_TAG, p.toString());
                    Intent intent = new Intent(context, PersonActivity.class);
                    intent.putExtra("Person", p);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    // As I add events to the collapsible list, also add them to a local ArrayList so I can keep
    // track of the objects and go to them when clicked on
    private void setEventItems() {
        personEvents = new ArrayList<>();
        TreeSet<Event> events = Event.getPersonEvents(person.getPersonId());
        ArrayList<String> child = new ArrayList<>();
        groupItems.add("Life Events");
        for (Event event : events) {
            child.add(event.getEventSummary());
            personEvents.add(event);
        }
        childItems.add(child);
    }

    // As I add family members to the collapsible list, also add them to a local ArrayList so I can keep
    // track of the objects and go to them when clicked on
    private void setFamilyItems() {
        familyMembers = new ArrayList<>();
        ArrayList<Object> child = new ArrayList<>();
        groupItems.add("Family");
        String name;
        if ((name = person.getFatherName()) != null) {
            child.add("Father: " + name);
            familyMembers.add(Person.findPerson(person.getFatherId()));
        }
        if (person.getMotherName() != null) {
            child.add("Mother: " + person.getMotherName());
            familyMembers.add(Person.findPerson(person.getMotherId()));
        }
        if ((name = person.getSpouseName()) != null) {
            child.add("Spouse: " + name);
            familyMembers.add(Person.findPerson(person.getSpouseId()));
        }
        childItems.add(child);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
