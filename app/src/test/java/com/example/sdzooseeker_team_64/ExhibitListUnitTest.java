package com.example.sdzooseeker_team_64;

import static org.junit.Assert.assertEquals;

import android.app.Instrumentation;
import android.widget.ListView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExhibitListUnitTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testExhibitListCreated() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();


//        scenario.onActivity(activity -> {
//            ListView listView = activity.findViewById(R.id.search_list_view);
//            instrumentation.runOnMainSync(new Runnable() {
//                @Override
//                public void run() {
//                    int position = 2;
//                    listView.performItemClick(listView.getChildAt(position), position, listView.getAdapter().getItemId(position));
//                }
//            });
//            ListView newlist = activity.findViewById(R.id.selected_list);
//
////            assertEquals("Lions",(String)newlist.getItemAtPosition(0));
//        });
    }
}
