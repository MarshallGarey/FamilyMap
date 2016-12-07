package com.example.mgarey2.familymap.main;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.AmazonMapOptions;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.MapView;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.model.Event;
import com.example.mgarey2.familymap.model.LocalData;
import com.example.mgarey2.familymap.model.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, AmazonMap.OnMarkerClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static AmazonMapOptions amazonMapOptions = null;
    protected static final String LOG_TAG = "MapFragment";

    // TODO: Use parameters to determine whether to zoom in on a specific event or be zoomed out.
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MapView mapView;
    private TextView textView;
    private ImageView genderIconView;
    protected static AmazonMap amazonMap = null;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        Log.d(LOG_TAG, "newInstance");
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Create the map view.
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        // Event description view.
        textView = (TextView) view.findViewById(R.id.eventDescription);
        setEventText("Event Description");

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
        }
        else {
            genderIconView.setImageResource(R.drawable.pink_female_icon);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void OnFragmentInteractionListener() {
        if (mListener != null) {
            mListener.onMapInteraction();
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

    @Override
    public void onMapReady(AmazonMap amazonMap) {

        // TODO: Apply filters
        // TODO: Apply stored settings

        // Set callback for clicking on markers.
        amazonMap.setOnMarkerClickListener(this);

        // Draw markers; get data from local cache
        HashSet<Event> events = LocalData.getEvents();
        for (Event event : events) {
            LatLng location = new LatLng(event.getLatitude(), event.getLongitutde());
            amazonMap.addMarker(newMarker(location, event.getEventId(), event.getEventSummary()));
        }
    }

    private MarkerOptions newMarker(LatLng location, String eventId, String summary) {
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        markerOptions.title(eventId); // Do this so it's easy to find the event later.
        markerOptions.snippet(summary);

        return markerOptions;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(LOG_TAG, "onMarkerClick");
        setEventText(marker.getSnippet());
        Event event = LocalData.findEvent(marker.getTitle());
        if (event == null) {
            return false;
        }
        Person person = LocalData.findPerson(event.getPersonId());
        if (person == null) {
            return false;
        }
        setGenderIcon(person.getGender());
        return true;
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
        // TODO: Update argument type and name
        void onMapInteraction();
    }


}
