package com.example.sdzooseeker_team_64;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlanActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    ZooGraph zooGraph;
    ZooPlan zooPlan;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        setTitle("Exhibit Planning");

        // Setup Back Button on Navigation Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Initialize data and Restore activity status
        PlanExhibitListAdapter adapter = new PlanExhibitListAdapter();
        adapter.setHasStableIds(true);
        int currentSize = MyPrefs.getTheLength(App.getContext(), "exhibitListSize");
        zooGraph = new ZooGraph(this);
        zooPlan = new ZooPlan(zooGraph, loadList(currentSize));
        LinkedHashMap<ZooGraph.Exhibit, Double> distanceMap = zooPlan.getDistanceMapForPlanSummary();
        adapter.setExhibitList(zooPlan.exhibits, distanceMap);

        // Setup view references
        recyclerView = findViewById(R.id.exhibit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Preserve activity status
        saveClass();
    }

    public void onStartDirectionClicked(View view) {
        Intent intent = new Intent(this, NavigationPageActivity.class);
        startActivity(intent);
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveClass() {
        MyPrefs.setLastActivity(App.getContext(), "lastActivity", this.getClass().getName());
    }
    public List<ZooGraph.Exhibit> loadList(int length) {
        List<ZooGraph.Exhibit> exhibitList = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            exhibitList.add(MyPrefs.getTheExhibit(App.getContext(), "exhibitList"+i, zooGraph));
        }
        return exhibitList;
    }
}