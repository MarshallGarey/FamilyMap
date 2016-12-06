package com.example.mgarey2.familymap.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.example.mgarey2.familymap.R;

/**
 * Created by Marshall
 */
public class MainActivity
        extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener {

    private final String LOG_TAG = "Main Activity";
    private static Context context = null;
    private MapFragment mapFragment = null;
    // TODO (maybe): create a private field that stores the state of the main activity - map or login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        // If we're being restored from a previous state, don't do anything
        // or else we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }
        loadLoginFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    /**
     * Login fragment callback.
     * This events signifies the login was successful and the map fragment needs to be loaded.
     */
    @Override
    public void onSuccessfulLogin() {
        unloadLoginFragment();
        loadMapFragment();
    }

    @Override
    public void onMapInteraction() {
        Log.d(LOG_TAG, "Map Fragment callback");
    }

    private void loadLoginFragment() {
        // Create Login Fragment
        Log.d(LOG_TAG, "Load Login Fragment");
        LoginFragment loginFragment = LoginFragment.newInstance();

        // Add login fragment to the main_activity_container frame layout
        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_container, loginFragment).commit();
    }

    private void unloadLoginFragment() {
        getSupportFragmentManager().beginTransaction().remove(
                getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
    }

    private void loadMapFragment() {
        Log.d(LOG_TAG, "Load Map Fragment");
        // TODO: use the parameters to signify whether to zoom in or out
        MapFragment mapFragment = MapFragment.newInstance(null, null);
        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_container, mapFragment).commit();
        this.mapFragment = mapFragment;
    }

    // TODO: don't make this method static
    public static void newToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
