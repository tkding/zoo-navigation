package com.example.sdzooseeker_team_64;

import static org.junit.Assert.assertEquals;
import static org.robolectric.shadows.ShadowInstrumentation.getInstrumentation;

import android.app.Instrumentation;
import android.content.Intent;
import android.widget.Button;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class PlannedPageTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testPlanButtonCreated() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button planbtn = activity.findViewById(R.id.clear_list_btn);
            boolean bool = planbtn.isClickable();
            assertEquals(true, bool);
        });
    }

    @Test
    public void testPlanButtonClick() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(PlanActivity.class.getName(), null, true);

        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ZooGraph.Exhibit testitem = new ZooGraph.Exhibit("",
                    "", "", "testAnimal", new ArrayList<>(), 0, 0);
            activity.selectedExhibitList.add(testitem);
            assertEquals(activity.selectedExhibitList.size(), 1);
            Button planbtn = activity.findViewById(R.id.plan_btn);
            activityMonitor.waitForActivityWithTimeout(1000);
            planbtn.performClick();
            assertEquals(1, activityMonitor.getHits());
        });



    }

}
