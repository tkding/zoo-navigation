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
public class SkipButtonUnitTest {
    @Rule
    public ActivityScenarioRule<NavigationPageActivity> scenarioRule = new ActivityScenarioRule<>(NavigationPageActivity.class);
    @Test
    public void SkipButtonCreated() {
        ActivityScenario<NavigationPageActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button skipbtn = activity.findViewById(R.id.skip_btn);
            boolean bool = skipbtn.isClickable();
            assertEquals(true, bool);
        });
    }




}
