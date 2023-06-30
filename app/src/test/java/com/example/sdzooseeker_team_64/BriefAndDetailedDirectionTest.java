package com.example.sdzooseeker_team_64;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class BriefAndDetailedDirectionTest {

    @Test
    public void testGettingBriefDirection() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        ArrayList<String> path = zooPlan.getCurrentBriefPath();
        assertEquals(true, path.size() > 0);
    }

    @Test
    public void testGettingDetailedDirection() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        ArrayList<String> path = zooPlan.getCurrentDetailedPath();
        assertEquals(true, path.size() > 0);
    }

    @Test
    public void testGettingBriefAndDetailedDirectionDifferent() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);

        ArrayList<String> dPath = zooPlan.getCurrentDetailedPath();
        ArrayList<String> bPath = zooPlan.getCurrentBriefPath();
        assertEquals(false, dPath.equals(bPath));
    }

}
