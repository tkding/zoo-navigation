package com.example.sdzooseeker_team_64;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;
import android.widget.Button;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
public class PreviousButtonUnitTest {

    @Rule
    public ActivityScenarioRule<NavigationPageActivity> scenarioRule =
            new ActivityScenarioRule<>(NavigationPageActivity.class);
    @Test
    public void testPrevButtonCreated() {
        ActivityScenario<NavigationPageActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button prevButton = activity.findViewById(R.id.previous_btn);
            boolean bool = prevButton.isClickable();
            Assert.assertTrue(bool);
        });
    }

    @Test
    public void testPreviousButton() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));
        ZooPlan zooPlan = new ZooPlan(zooGraph, exhibits);
        int startIndex = zooPlan.getCurrentStartIndex();

        if (zooPlan.canGoPrev()) {
            zooPlan.goToNextExhibit();
            zooPlan.goToNextExhibit();
            zooPlan.goToNextExhibit();
            zooPlan.goToPrevExhibit();
            zooPlan.goToPrevExhibit();
            assertEquals(startIndex + 3, zooPlan.getCurrentStartIndex());

        }


    }
}
