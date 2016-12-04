package com.example.mgarey2.familymap.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.mgarey2.familymap.R;

/**
 * Created by Marshall
 */
public class MainActivity
        extends AppCompatActivity
        implements LoginFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener {

    private final String TAG = "Main Activity";
    private static Context context = null;
    // TODO (maybe): create a private field that stores the state of the main activity - map or login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
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
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
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
        Log.d(TAG, "Map Fragment callback");
    }

    private void loadLoginFragment() {
        // Create Login Fragment
        Log.d(TAG, "Load Login Fragment");
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
        Log.d(TAG, "Load Map Fragment");
        // TODO: use the parameters to signify whether to zoom in or out
        MapFragment mapFragment = MapFragment.newInstance(null, null);
        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_container, mapFragment).commit();
    }

    // TODO: don't make this method static
    public static void newToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
