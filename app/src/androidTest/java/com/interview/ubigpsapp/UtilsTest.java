/*
 * Unit tests for utils functions {@link com.interview.ubigpsapp.utils.Utils}
 */

package com.interview.ubigpsapp;

import com.interview.ubigpsapp.utils.Utils;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UtilsTest {

    @Test
    public void testAverageSpeedFunction() {
        //Average speed test : 500 meters for 60 sec => 30 km/h
        assertEquals(30, (int)Utils.getAverageSpeed(60 * 1000, 500));
    }
}


