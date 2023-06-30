package com.example.sdzooseeker_team_64;

import static org.junit.Assert.assertEquals;

import android.widget.Button;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ClearListButtonUnitTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testClearListButtonCreated() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button clearbtn = activity.findViewById(R.id.clear_list_btn);
            boolean bool = clearbtn.isClickable();
            assertEquals(true, bool);
        });
    }
    @Test
    public void testClick(){
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ZooGraph.Exhibit testitem = new ZooGraph.Exhibit("",
                    "","","testAnimal", new ArrayList<>(), 0, 0);
            activity.selectedExhibitList.add(testitem);
            assertEquals(activity.selectedExhibitList.size(),1);
            Button clearbtn = activity.findViewById(R.id.clear_list_btn);
            clearbtn.performClick();
            assertEquals(0, activity.selectedExhibitList.size());
        });
    }



}
