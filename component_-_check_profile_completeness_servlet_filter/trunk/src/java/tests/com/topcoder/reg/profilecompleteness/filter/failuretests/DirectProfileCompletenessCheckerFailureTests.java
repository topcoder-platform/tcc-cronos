/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.DirectProfileCompletenessChecker;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>Failure tests for {@link DirectProfileCompletenessChecker} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class DirectProfileCompletenessCheckerFailureTests {

    /**
     * <p>Represents the instance of the tested class.</p>
     */
    private DirectProfileCompletenessChecker instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProfileCompletenessCheckerFailureTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new DirectProfileCompletenessChecker();
        instance.setLog(new Log4jLogFactory().createLog("test"));
    }

    /**
     * <p>Tests {@link DirectProfileCompletenessChecker#DirectProfileCompletenessChecker()} constructor.</p>
     */
    @Test
    public void testCtor() {

        instance = new DirectProfileCompletenessChecker();
        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests {@link DirectProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)}
     * method.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     *
     * @throws Exception if any error occurs during test
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsProfileComplete() throws Exception {

        instance.isProfileComplete(null);
    }
}
