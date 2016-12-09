package com.example.mgarey2.familymap.map;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.event.Event;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private ArrayList<String> events;
    private String LOG_TAG = "FilterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // Setup actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Filter");

        // Initialize list of event filters.
        getEventFilters();

        // Setup each view:
        Log.d(LOG_TAG, "Active event filters:\n" + FamilyMapOptions.activeFiltersToString());
        for (int i = 0; i < events.size(); i++) {
            setupEventFilter(i);
        }

    }

    private void setupEventFilter(final int index) {
        View view = getEventView(index);
        TextView textView = (TextView) view.findViewById(R.id.main_text);
        textView.setText(events.get(index));
        textView = (TextView) view.findViewById(R.id.sub_text);
        textView.setText("Filter by " + events.get(index));
        final ToggleButton button = (ToggleButton) view.findViewById(R.id.toggleButton);
        button.setChecked(FamilyMapOptions.activeEventFilters.contains(events.get(index)));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = button.getText().toString();
                if (str.toLowerCase().equals("on")) {
                    FamilyMapOptions.addFilter(events.get(index));
                } else {
                    FamilyMapOptions.removeFilter(events.get(index));
                }
                Log.d(LOG_TAG, "new filter applied: \n" + FamilyMapOptions.activeFiltersToString());
            }
        });
    }

    private View getEventView(final int index) {
        switch (index) {
            case 0:
                return findViewById(R.id.filter_option1);
            case 1:
                return findViewById(R.id.filter_option2);
            case 2:
                return findViewById(R.id.filter_option3);
            case 3:
                return findViewById(R.id.filter_option4);
            case 4:
                return findViewById(R.id.filter_option5);
            case 5:
                return findViewById(R.id.filter_option6);
            case 6:
                return findViewById(R.id.filter_option7);
            case 7:
                return findViewById(R.id.filter_option8);
            case 8:
                return findViewById(R.id.filter_option9);
            default:
                return findViewById(R.id.filter_option10);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                FamilyMapOptions.reSync = true;
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getEventFilters() {
        events = new ArrayList<>();
        for (String event : Event.eventTypes) {
            this.events.add(event);
        }
    }

}
