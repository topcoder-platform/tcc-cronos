package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.TimeStatus;

/**
 * Unit test for <code>TimeStatus</code>.
 * @author fuyun
 * @version 1.0
 */
public class TimeStatusAccuracyTest extends TestCase {

    /**
     * Test constructor.
     */
    public void testTimeStatus() {
        assertNotNull("fail to create TimeStatus instance", new TimeStatus());
    }

}
