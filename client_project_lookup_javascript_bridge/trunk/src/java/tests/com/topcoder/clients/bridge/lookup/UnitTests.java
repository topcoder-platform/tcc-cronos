/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.lookup;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The suite test.
     *
     * @return the suite test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // package com.topcoder.clients.bridge.management
        suite.addTest(ClientProjectLookupServiceBridgeServletTest.suite());

        return suite;
    }

}
