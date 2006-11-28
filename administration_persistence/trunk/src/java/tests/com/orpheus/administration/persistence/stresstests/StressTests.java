/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all stress test cases for the <i>Administration Persistence</i> component.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class StressTests extends TestCase {
    /**
     * Returns all stress test cases for the <i>Administration Persistence</i> component.
     *
     * @return all stress test cases for the <i>Administration Persistence</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        //suite.addTest(XXX.suite());
        return suite;
    }
}
