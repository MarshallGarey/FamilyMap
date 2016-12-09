package com.example.mgarey2.familymap.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.AmazonMapOptions;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.MapView;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.event.Event;
import com.example.mgarey2.familymap.person.Person;

import java.util.TreeSet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, AmazonMap.OnMarkerClickListener,
        View.OnClickListener {
    protected static final String LOG_TAG = "MapFragment";


    // Use parameters to determine whether to zoom in on a specific event or be zoomed out.
    private String mapState;            // param 1
    private Event selectedEvent = null; // param 2
    private static final String ARG_PARAM1 = "MAP STATE";
    private static final String ARG_PARAM2 = "MAP_EVENT";

    // Views
    private MapView mapView;
    private TextView textView;
    private ImageView genderIconView;

    // Other variables
    private OnFragmentInteractionListener mListener;
    private Person selectedPerson;
    private AmazonMap amazonMap;
    private Menu menu;

    // Constants
    public static final int ITEM_PERSON_SELECTED = 0;
    public static final int ITEM_BACK = 1;
    public static final int ITEM_SEARCH = 2;
    public static final int ITEM_SETTINGS = 3;
    public static final int ITEM_FILTER = 4;
    private final float ZOOM_IN = 5.5F;
    public static final int MAP_STATE_REGULAR = 0;
    public static final int MAP_STATE_ZOOMED = 1;
    public static final String[] MAP_STATES = {
            "regular", "zoomed"
    };

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mapState Zoomed in or out.
     * @param event    Selected event
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String mapState, Event event) {
        Log.d(LOG_TAG, "newInstance");
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mapState);
        args.putSerializable(ARG_PARAM2, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mapState = getArguments().getString(ARG_PARAM1);
            selectedEvent = (Event) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            ActionBar ab = activity.getSupportActionBar();
            ab.setTitle("Map");
            setHasOptionsMenu(true);

            // If zoom in is set (started from map activity), display the up button
            if (mapState != null) {
                ab.setDisplayHomeAsUpEnabled(true);
            }

            // Otherwise (started from main activity), display the settings, filter, and search buttons
            else {

            }
        } else {
            Log.e(LOG_TAG, "Unable to find toolbar");
        }

        // Create the map view.
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        // Event description view.
        textView = (TextView) view.findViewById(R.id.eventDescription);
        setEventText("Event Description");
        textView.setOnClickListener(this);

        // Gender icon view.
        genderIconView = (ImageView) view.findViewById(R.id.genderIcon);

        return view;
    }

    private void setEventText(String text) {
        Log.d(LOG_TAG, "setEventText");
        textView.setText(text);
        textView.setTextSize(18.0F);
    }

    private void setGenderIcon(String gender) {
        if (gender.toLowerCase().equals("m")) {
            genderIconView.setImageResource(R.drawable.blue_male_icon);
        } else {
            genderIconView.setImageResource(R.drawable.pink_female_icon);
        }
    }

    // Called by eventDescription onClick callback.
    public void OnFragmentInteractionListener(Person person, int item) {
        if (mListener != null) {
            mListener.onItemSelection(person, item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Look at options
     */
    @Override
    public void onResume() {
        super.onResume();
        if (FamilyMapOptions.reSync) {
            FamilyMapOptions.reSync = false;
            selectedEvent = null;
            selectedPerson = null;
            mapState = null;
        }
        if (amazonMap != null) {
            amazonMap.setMapType(FamilyMapOptions.mapType);
        }
    }

    @Override
    public void onMapReady(AmazonMap amazonMap) {
        Log.d(LOG_TAG, "onMapReady");
        this.amazonMap = amazonMap;
        // TODO: Apply filters
        // TODO: Apply stored settings
        Log.d(LOG_TAG, "set map type: " + FamilyMapOptions.mapType);
        amazonMap.setMapType(FamilyMapOptions.mapType);

        // Set callback for clicking on markers.
        amazonMap.setOnMarkerClickListener(this);

        // Draw markers; get data from local cache
        // TODO: if the app exits, events gets thrown out of memory, and upon resume events is null. Find a way to
        // store events persistently, or re-synchronize, or go back to log-in.
        TreeSet<Event> events = Event.getEvents();
        for (Event event : events) {
            LatLng location = new LatLng(event.getLatitude(), event.getLongitutde());
            amazonMap.addMarker(newMarker(location, event.getEventId(), event.getEventSummary(),
                    event.getMarkerHue()));
        }

        // Apply zoom if event is selected
        if (mapState == null) {
            return;
        }
        if (mapState.equals(MAP_STATES[MAP_STATE_ZOOMED])) {
            setEventText(selectedEvent.getEventSummary());
            amazonMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                    selectedEvent.getLatitude(), selectedEvent.getLongitutde()), ZOOM_IN));
        }

    }

    private MarkerOptions newMarker(LatLng location, String eventId, String summary, float hue) {
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        markerOptions.title(eventId); // Do this so it's easy to find the event later.
        markerOptions.snippet(summary);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(hue));
        return markerOptions;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(LOG_TAG, "onMarkerClick");
        setEventText(marker.getSnippet());
        selectedEvent = Event.findEvent(marker.getTitle());
        if (selectedEvent == null) {
            return false;
        }
        selectedPerson = Person.findPerson(selectedEvent.getPersonId());
        if (selectedPerson == null) {
            return false;
        }
        setGenderIcon(selectedPerson.getGender());
        amazonMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                selectedEvent.getLatitude(), selectedEvent.getLongitutde()), ZOOM_IN));

        return true;
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onClick");
        if (selectedPerson == null) {
            return; // no event has been selected - do nothing.
        }
        // Load Person Activity if an event has been selected.
        OnFragmentInteractionListener(selectedPerson, ITEM_PERSON_SELECTED);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onItemSelection(Person person, int item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            // Back button
            case android.R.id.home:
                mListener.onItemSelection(null, ITEM_BACK);
                return true;
            // SettingsActivity
            case R.id.action_settings:
                Log.d(LOG_TAG, "settings clicked");
                mListener.onItemSelection(null, ITEM_SETTINGS);
                return true;
            // Search
            case R.id.action_search:
                Log.d(LOG_TAG, "search clicked");
                mListener.onItemSelection(null, ITEM_SEARCH);
                break;
            // Filter
            case R.id.action_filter:
                Log.d(LOG_TAG, "filter clicked");
                mListener.onItemSelection(null, ITEM_FILTER);
                break;
        }
        // Unrecognized action
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;

        // Hide the other option buttons if opened from map activity.
        if (mapState != null) {
            MenuItem item = menu.findItem(R.id.action_settings);
            item.setVisible(false);
            item = menu.findItem(R.id.action_search);
            item.setVisible(false);
            item = menu.findItem(R.id.action_filter);
            item.setVisible(false);
        }
    }

}
