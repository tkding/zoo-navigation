package com.example.sdzooseeker_team_64;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Data
    ZooGraph zooGraph;
    List<ZooGraph.Exhibit> selectedExhibitList;
    ArrayAdapter<ZooGraph.Exhibit> searchListAdapter;
    ArrayAdapter<ZooGraph.Exhibit> selectedListAdapter;

    // View Components
    ListView searchListView;
    ListView selectedListView;
    Button planButton;
    Button clearListButton;
    TextView countView;

    // location permission
    private final PermissionChecker permissionChecker = new PermissionChecker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize zoo graph, extract data from assets
        zooGraph = new ZooGraph(this);
        selectedExhibitList = new ArrayList<>();

        //marin
        saveClass();
        int currentSize = MyPrefs.getTheLength(App.getContext(), "exhibitListSize");
        loadList(currentSize);

        // Setup View Component References
        searchListView = findViewById(R.id.search_list);
        selectedListView = findViewById(R.id.selected_list);
        planButton = findViewById(R.id.plan_btn);
        clearListButton = findViewById(R.id.clear_list_btn);
        countView = findViewById(R.id.exhibit_count);

        // Setup View Components
        planButton.setOnClickListener(this::onPlanClicked);
        countView.setText(Integer.toString(selectedExhibitList.size()));
        setupSearchListView();
        setupSelectedListView();

        /* Permissions Setup */
        if (permissionChecker.ensurePermissions()) return;
    }

    public void setupSearchListView() {
        // Setup view data source
        searchListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, zooGraph.getAllStrictExhibits());
        searchListView.setAdapter(searchListAdapter);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ZooGraph.Exhibit item = (ZooGraph.Exhibit) adapterView.getItemAtPosition(i);

                if(selectedExhibitList.contains(item) == true) {
                    return;
                } else {
                    selectedExhibitList.add(item);
                    saveList(selectedExhibitList);
                    String number = Integer.toString(selectedExhibitList.size());
                    countView.setText(number);
                    selectedListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void setupSelectedListView() {
        selectedListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedExhibitList);
        selectedListView.setAdapter(selectedListAdapter);
        //clear whole list using a delete list button

        Button clearListButton = findViewById(R.id.clear_list_btn);
        clearListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteListData(selectedExhibitList.size());
                selectedExhibitList.clear();
                String number = Integer.toString(selectedExhibitList.size());
                countView.setText(number);
                selectedListAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Animal Name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // filter text when search box content changes
            @Override
            public boolean onQueryTextChange(String newText) {
                searchListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    void onPlanClicked(View view) {
        if(!selectedExhibitList.isEmpty()) {
            Intent intent = new Intent(this, PlanActivity.class);
            startActivity(intent);
        }
    }

    private void saveClass() {
        MyPrefs.setLastActivity(App.getContext(), "lastActivity", this.getClass().getName());
    }

    public void loadList(int length) {
        for(int i = 0; i < length; i++) {
            selectedExhibitList.add(MyPrefs.getTheExhibit(App.getContext(), "exhibitList"+i, zooGraph));
        }
    }

    public void saveList(List<ZooGraph.Exhibit> exhibits) {
        for(int i = 0; i < exhibits.size(); i++) {
            MyPrefs.saveString(App.getContext(), "exhibitList", exhibits.get(i).id, i);
        }
        MyPrefs.saveLength(App.getContext(), "exhibitListSize",exhibits.size());
    }

    public void deleteListData(int length) {
        for(int i = 0; i < length; i++) {
            MyPrefs.delete(App.getContext(), "exhibitList"+i);
        }
        MyPrefs.saveLength(App.getContext(), "exhibitListSize", 0);
    }
    private boolean ensurePermissions() {

        return permissionChecker.ensurePermissions();
    }
}
