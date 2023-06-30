package com.example.sdzooseeker_team_64;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class DirectionsFromActualLocationTests {

    @Test
    public void testGetDirectionsFromActualLocationWithBriefPath() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        ArrayList<String> path = zooPlan.getCurrentBriefPath();
        assertEquals(false, path.isEmpty());

        zooPlan.setStartIndex(zooPlan.getCurrentStartIndex()+1);
        zooPlan.setEndIndex(zooPlan.getCurrentEndIndex()+1);

        ArrayList<String> path2 = zooPlan.getCurrentBriefPath();
        assertEquals(false, path.isEmpty());
    }

    @Test
    public void testGetDirectionsFromActualLocationWithDetailedPath() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        ArrayList<String> path = zooPlan.getCurrentDetailedPath();
        assertEquals(false, path.isEmpty());

        zooPlan.setStartIndex(zooPlan.getCurrentStartIndex()+1);
        zooPlan.setEndIndex(zooPlan.getCurrentEndIndex()+1);

        ArrayList<String> path2 = zooPlan.getCurrentDetailedPath();
        assertEquals(false, path.isEmpty());
    }

    @Test
    public void testGetDirectionsFromActualLocation() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        ArrayList<String> path = zooPlan.getCurrentBriefPath();
        assertEquals(false, path.isEmpty());

        zooPlan.setStartIndex(zooPlan.getCurrentStartIndex()+1);
        zooPlan.setEndIndex(zooPlan.getCurrentEndIndex()+1);

        ArrayList<String> path2 = zooPlan.getCurrentDetailedPath();
        assertEquals(false, path.isEmpty());
    }

}
