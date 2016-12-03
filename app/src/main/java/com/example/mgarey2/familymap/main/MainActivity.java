package com.example.mgarey2.familymap.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mgarey2.familymap.R;

/**
 * Created by Marshall
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    private final String TAG = "Main Activity";
    private static Context context = null;

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

        // Create Login Fragment
        Log.d(TAG, "Create fragment");
        LoginFragment loginFragment = LoginFragment.newInstance();

        // Add login fragment to the main_activity_container frame layout
        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_container, loginFragment).commit();
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

    public static void newToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(View view) {

    }
}
