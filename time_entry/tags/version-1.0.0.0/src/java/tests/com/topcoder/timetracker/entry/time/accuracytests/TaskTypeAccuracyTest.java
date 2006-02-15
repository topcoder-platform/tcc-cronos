/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.TaskType;

/**
 * Unit test for <code>TaskType</code>.
 * @author fuyun
 * @version 1.0
 */
public class TaskTypeAccuracyTest extends TestCase {

    /**
     * Test constructor.
     */
    public void testTaskType() {
        assertNotNull("fail to create TaskType instance", new TaskType());
    }

}
