package com.example.sdzooseeker_team_64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SearchBarUnitTests {

    @Test
    public void testLoadingDatabaseFromNonExistingFile() {
        Context context = ApplicationProvider.getApplicationContext();
//        String[] exhibitNames = MainActivity.loadMapFromAssets(context, "FileThatDoesn'tExist");
//        assertEquals(exhibitNames, new String[0]);
    }

    @Test
    public void testLoadingDatabaseFromExistingFile() {
        Context context = ApplicationProvider.getApplicationContext();
//        String[] exhibitNames = MainActivity.loadMapFromAssets(context, "sample_vertex_info.json");
//        assertTrue(exhibitNames.length > 0);
    }

}