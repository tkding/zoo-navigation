package com.example.sdzooseeker_team_64;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class TapToClearUnitTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testTapClear() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ZooGraph.Exhibit testitem = new ZooGraph.Exhibit("",
                    "","","testAnimal", new ArrayList<>(), 0, 0);
            activity.selectedExhibitList.add(testitem);
            assertTrue(activity.selectedExhibitList.contains(testitem));
            activity.selectedListView.performItemClick(activity.selectedListView,
                    0, activity.selectedListView.getItemIdAtPosition(0));
            assertTrue(activity.selectedExhibitList.contains(testitem));
        });
    }
}
