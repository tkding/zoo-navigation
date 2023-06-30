package com.example.sdzooseeker_team_64;

import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DisplaySelectedExhibitsTests {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testSelectedExhibit() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ZooGraph.Exhibit testItem = new ZooGraph.Exhibit("",
                    "","","testAnimal", new ArrayList<>(), 0, 0);
            activity.selectedExhibitList.add(testItem);
            assertTrue(activity.selectedExhibitList.contains(testItem));
        });
    }

    @Test
    public void testSelectedExhibit2() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            List<ZooGraph.Exhibit> allExhibitList = activity.zooGraph.getAllStrictExhibits();

            activity.selectedExhibitList.addAll(allExhibitList);
            for (ZooGraph.Exhibit exhibit : allExhibitList) {
                assertTrue(activity.selectedExhibitList.contains(exhibit));
            }

        });
    }

    @Test
    public void testSelectedExhibitWithDelete() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            List<ZooGraph.Exhibit> allExhibitList = activity.zooGraph.getAllStrictExhibits();

            activity.selectedExhibitList.addAll(allExhibitList);
            activity.selectedExhibitList.remove(0);
            for (int i = 1; i < allExhibitList.size(); i++) {
                assertTrue(activity.selectedExhibitList.contains(allExhibitList.get(i)));
            }
            assertTrue(!activity.selectedExhibitList.contains(allExhibitList.get(0)));

        });
    }
}
