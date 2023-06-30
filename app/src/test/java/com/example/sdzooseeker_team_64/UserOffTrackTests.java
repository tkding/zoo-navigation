package com.example.sdzooseeker_team_64;


import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class UserOffTrackTests {

    @Test
    public void testOffTrackWithReplan() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        zooPlan.replanExhibitsWithUserLocation(0, 0, 1, 3);

        //Check if first and last exhibits are gate
        assertEquals(true, zooPlan.exhibits.get(0).equals(zooGraph.getExhibitWithId("entrance_exit_gate")));
        assertEquals(true, zooPlan.exhibits.get(zooPlan.exhibits.size()-1).equals(zooGraph.getExhibitWithId("entrance_exit_gate")));

        // Check if the strict exhibits have changed their order
        assertEquals(false, zooPlan.exhibits.get(1).equals(zooGraph.getExhibitWithId("capuchin")));
        assertEquals(false, zooPlan.exhibits.get(2).equals(zooGraph.getExhibitWithId("gorilla")));
        assertEquals(false, zooPlan.exhibits.get(3).equals(zooGraph.getExhibitWithId("orangutan")));
    }

    @Test
    public void testOffTrackWithoutReplan() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        // User did not click on replan
//        zooPlan.replanExhibitsWithUserLocation(0, 0, 1, 3);

        //Check if first and last exhibits are gate
        assertEquals(true, zooPlan.exhibits.get(0).equals(zooGraph.getExhibitWithId("entrance_exit_gate")));
        assertEquals(true, zooPlan.exhibits.get(zooPlan.exhibits.size()-1).equals(zooGraph.getExhibitWithId("entrance_exit_gate")));

        // Check if the strict exhibits have changed their order
        assertEquals(false, zooPlan.exhibits.get(1).equals(zooGraph.getExhibitWithId("capuchin")));
        assertEquals(false, zooPlan.exhibits.get(2).equals(zooGraph.getExhibitWithId("gorilla")));
        assertEquals(false, zooPlan.exhibits.get(3).equals(zooGraph.getExhibitWithId("orangutan")));
    }
}
