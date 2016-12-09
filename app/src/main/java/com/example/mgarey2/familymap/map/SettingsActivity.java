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
import android.widget.ToggleButton;

import com.amazon.geo.mapsv2.AmazonMap;
import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.activities.MainActivity;
import com.example.mgarey2.familymap.client.Client;

public class SettingsActivity extends AppCompatActivity {

    private final String LOG_TAG = "SettingsActivity";
    private final String SPINNER_KEY = "Spinner";
    private Spinner mapTypeSpinner;
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
        final ToggleButton button = (ToggleButton) view.findViewById(R.id.toggleButton);
        button.setChecked(FamilyMapOptions.familyTreeLinesActive);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = button.getText().toString();
                if (str.toLowerCase().equals("off")) {
                    FamilyMapOptions.familyTreeLinesActive = false;
                }
                else {
                    FamilyMapOptions.familyTreeLinesActive = true;
                }
            }
        });
        final Spinner familyTreeSpinner = (Spinner) view.findViewById(R.id.spinner);
        familyTreeSpinner.setSelection(FamilyMapOptions.familyTreeLinesHueIndex);
        familyTreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = (String) familyTreeSpinner.getSelectedItem();
                switch (color) {
                    case "Red":
                        FamilyMapOptions.familyTreeLinesHueIndex = FamilyMapOptions.RED_INDEX;
                        break;
                    case "Green":
                        FamilyMapOptions.familyTreeLinesHueIndex = FamilyMapOptions.GREEN_INDEX;
                        break;
                    default:
                        FamilyMapOptions.familyTreeLinesHueIndex = FamilyMapOptions.BLUE_INDEX;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Life story lines
        view = findViewById(R.id.LifeStoryLines);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Life Story Lines");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("SHOW LIFE STORY LINES");
        final ToggleButton button2 = (ToggleButton) view.findViewById(R.id.toggleButton);
        button2.setChecked(FamilyMapOptions.lifeStoryLinesActive);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = button2.getText().toString();
                if (str.toLowerCase().equals("off")) {
                    FamilyMapOptions.lifeStoryLinesActive = false;
                }
                else {
                    FamilyMapOptions.lifeStoryLinesActive = true;
                }
            }
        });
        final Spinner lifeLinesSpinner = (Spinner) view.findViewById(R.id.spinner);
        lifeLinesSpinner.setSelection(FamilyMapOptions.lifeStoryLinesHueIndex);
        lifeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = (String) lifeLinesSpinner.getSelectedItem();
                switch (color) {
                    case "Red":
                        FamilyMapOptions.lifeStoryLinesHueIndex = FamilyMapOptions.RED_INDEX;
                        break;
                    case "Green":
                        FamilyMapOptions.lifeStoryLinesHueIndex = FamilyMapOptions.GREEN_INDEX;
                        break;
                    default:
                        FamilyMapOptions.lifeStoryLinesHueIndex = FamilyMapOptions.BLUE_INDEX;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Spouse Lines
        view = findViewById(R.id.SpouseLines);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Spouse Lines");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("SHOW SPOUSE LINES");
        final ToggleButton button3 = (ToggleButton) view.findViewById(R.id.toggleButton);
        button3.setChecked(FamilyMapOptions.spouseLinesActive);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = button3.getText().toString();
                if (str.toLowerCase().equals("off")) {
                    FamilyMapOptions.spouseLinesActive = false;
                }
                else {
                    FamilyMapOptions.spouseLinesActive = true;
                }
            }
        });
        final Spinner spouseLinesSpinner = (Spinner) view.findViewById(R.id.spinner);
        spouseLinesSpinner.setSelection(FamilyMapOptions.spouseLinesHueIndex);
        spouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = (String) spouseLinesSpinner.getSelectedItem();
                switch (color) {
                    case "Red":
                        FamilyMapOptions.spouseLinesHueIndex = FamilyMapOptions.RED_INDEX;
                        break;
                    case "Green":
                        FamilyMapOptions.spouseLinesHueIndex = FamilyMapOptions.GREEN_INDEX;
                        break;
                    default:
                        FamilyMapOptions.spouseLinesHueIndex = FamilyMapOptions.BLUE_INDEX;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Map type
        view = findViewById(R.id.map_type);
        subView = (TextView) view.findViewById(R.id.main_text);
        subView.setText("Map Type");
        subView = (TextView) view.findViewById(R.id.sub_text);
        subView.setText("BACKGROUND DISPLAY ON MAP");

        // Spinner
        mapTypeSpinner = (Spinner) view.findViewById(R.id.spinner);
        mapTypeSpinner.setSelection(FamilyMapOptions.mapType-1);
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) mapTypeSpinner.getSelectedItem();

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
