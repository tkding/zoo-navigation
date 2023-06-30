package com.example.sdzooseeker_team_64;

import static junit.framework.TestCase.assertEquals;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class RetainAndDeleteDataTests {

    @Test
    public void testRetainExhibitList() {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph = new ZooGraph(context);
        ArrayList<ZooGraph.Exhibit> exhibits = new ArrayList<>();
        exhibits.add(zooGraph.getExhibitWithId("capuchin"));
        exhibits.add(zooGraph.getExhibitWithId("gorilla"));
        exhibits.add(zooGraph.getExhibitWithId("orangutan"));

        for(int i = 0; i < exhibits.size(); i++) {
            MyPrefs.saveString(App.getContext(), "exhibitList", exhibits.get(i).id, i);
        }
        MyPrefs.saveLength(App.getContext(), "exhibitListSize",exhibits.size());

        ArrayList<ZooGraph.Exhibit> reloadedExhibitList = new ArrayList<>();
        for(int i = 0; i < exhibits.size(); i++) {
            reloadedExhibitList.add(MyPrefs.getTheExhibit(App.getContext(), "exhibitList"+i, zooGraph));
        }

        for(int i = 0; i < exhibits.size(); i++) {
            assertEquals(true, reloadedExhibitList.get(i).equals(exhibits.get(i)));
        }
    }

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testRetainActivity() {

        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            MyPrefs.setLastActivity(App.getContext(), "lastActivity", activity.getClass().getName());
            String name = MyPrefs.getLastActivity(App.getContext(),"lastActivity");
            assertEquals(name, activity.getClass().getName());
        });
    }

}
