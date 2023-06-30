package com.example.sdzooseeker_team_64;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class SkipButtonSkipTests {

    @Test
    public void testSkipFunction() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);
        int exhibitCountBeforeSkip = zooPlan.exhibits.size();

        if(zooPlan.canSkip()) {
            zooPlan.skipThisExhibit(0, 0);
            int exhibitCountAfterSkip = zooPlan.exhibits.size();
            assertEquals(true, exhibitCountAfterSkip+1 == exhibitCountBeforeSkip);
        }
    }

    @Test
    public void testSkipFunction2() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);
        int exhibitCountBeforeSkip = zooPlan.exhibits.size();

        if(zooPlan.canSkip()) {
            zooPlan.skipThisExhibit(0, 0);
            zooPlan.skipThisExhibit(0, 0);
            int exhibitCountAfterSkip = zooPlan.exhibits.size();
            assertEquals(true, exhibitCountAfterSkip+2 == exhibitCountBeforeSkip);
        }
    }
}
