package com.example.mgarey2.familymap.map;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mgarey2.familymap.R;
import com.example.mgarey2.familymap.client.Client;

public class SettingsActivity extends AppCompatActivity {

    private final String LOG_TAG = "SettingsActivity";
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
                // TODO: logout
                Log.d(LOG_TAG, "Logout");
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
            }
            else {
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
