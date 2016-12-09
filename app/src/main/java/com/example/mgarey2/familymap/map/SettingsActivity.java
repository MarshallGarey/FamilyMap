package com.example.mgarey2.familymap.map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.geo.mapsv2.AmazonMap;
import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.activities.MainActivity;
import com.example.mgarey2.familymap.client.Client;

public class SettingsActivity extends AppCompatActivity {

    private final String LOG_TAG = "SettingsActivity";
    private final String SPINNER_KEY = "Spinner";
    private Spinner spinner;
    private Context context;
    private ResyncTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.context = this;

        // Setup actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup Views:

        // Family tree lines
        View view = findViewById(R.id.FamilyTreeLines);
        TextView subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Family Tree Lines");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("SHOW TREE LINES");


        // Life story lines
        view = findViewById(R.id.LifeStoryLines);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Life Story Lines");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("SHOW LIFE STORY LINES");

        // Spouse Lines
        view = findViewById(R.id.SpouseLines);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Spouse Lines");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("SHOW SPOUSE LINES");

        // Map type
        view = findViewById(R.id.map_type);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Map Type");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("BACKGROUND DISPLAY ON MAP");

        // Spinner
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setSelection(FamilyMapOptions.mapType-1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) spinner.getSelectedItem();

                switch (item) {
                    case "Normal":
                        FamilyMapOptions.mapType = AmazonMap.MAP_TYPE_NORMAL;
                        break;
                    case "Satellite":
                        FamilyMapOptions.mapType = AmazonMap.MAP_TYPE_SATELLITE;
                        break;
                    case "Terrain":
                        FamilyMapOptions.mapType = AmazonMap.MAP_TYPE_TERRAIN;
                        break;
                    default:
                        FamilyMapOptions.mapType = AmazonMap.MAP_TYPE_HYBRID;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Re-sync data
        view = findViewById(R.id.resync_text);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Re-sync Data");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("FROM FAMILYMAP SERVICE");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resync data
                Log.d(LOG_TAG, "Attempt Resync");
                mAuthTask = new ResyncTask();
                mAuthTask.execute((Void) null);
            }
        });

        // Logout
        view = findViewById(R.id.logout_text);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Logout");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("RETURNS TO LOGIN SCREEN");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout - kills and restarts the app.
                Log.d(LOG_TAG, "Logout");
                Intent mainActivity = new Intent(context, MainActivity.class);
                int pendingIntentId = 51732; // just random
                PendingIntent pendingIntent = PendingIntent.getActivity(context, pendingIntentId, mainActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 10, pendingIntent);
                System.exit(0);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SPINNER_KEY, spinner.getSelectedItemPosition());
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ResyncTask extends AsyncTask<Void, Void, Boolean> {

        // Constructor
        ResyncTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Attempt to synchronize with network service.
            boolean success = Client.sync();
            if (!success) {
                // sync failed
                Log.d(LOG_TAG, "Login failed");
                return false;
            } else {
                // sync succeeded.
                Log.d(LOG_TAG, "Login successful");
                return true;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Toast.makeText(context, "Resync successful", Toast.LENGTH_SHORT).show();
                FamilyMapOptions.reSync = true;
                finish();
            } else {
                Toast.makeText(context, "Resync failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
