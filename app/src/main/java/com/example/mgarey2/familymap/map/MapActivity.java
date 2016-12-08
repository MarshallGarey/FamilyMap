package com.example.mgarey2.familymap.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.event.Event;
import com.example.mgarey2.familymap.person.Person;
import com.example.mgarey2.familymap.person.PersonActivity;

public class MapActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener {

    public static final String ZOOM_KEY = "ZOOM_KEY";
    public static final String EVENT_KEY = "EVENT_KEY";
    private final String LOG_TAG = "Map Activity";
    private MapFragment mapFragment = null;
    private String zoomLevel;
    private Event selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get parameters: Zoom level and selected event
        zoomLevel = getIntent().getStringExtra(MapActivity.ZOOM_KEY);
        selectedEvent = (Event) getIntent().getSerializableExtra(MapActivity.EVENT_KEY);
        Log.d(LOG_TAG, "Go to event: " + selectedEvent.toString());

        // If we're being restored from a previous state, don't do anything
        // or else we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }
        loadMapFragment();
    }

    private void loadMapFragment() {
        Log.d(LOG_TAG, "Load Map Fragment");
        // TODO: use the parameters to signify whether to zoom in or out
        MapFragment mapFragment = MapFragment.newInstance(zoomLevel, selectedEvent);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.map_container, mapFragment).commit();
        this.mapFragment = mapFragment;
    }

    @Override
    public void onItemSelection(Person person, int item) {
        switch(item) {
            // Start Person activity
            case MapFragment.ITEM_PERSON_SELECTED:
                Log.d(LOG_TAG, "Map Fragment callback - start Person activity");
                Intent intent = new Intent(this, PersonActivity.class);
                intent.putExtra("Person", person);
                startActivity(intent);
                break;
            // Close Map activity
            case MapFragment.ITEM_BACK:
                finish();
                break;
        }

    }
}
