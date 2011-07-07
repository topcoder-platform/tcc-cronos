/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.JiraProfileCompletenessChecker;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>Failure tests for {@link JiraProfileCompletenessChecker} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class JiraProfileCompletenessCheckerFailureTests {

    /**
     * <p>Represents the instance of the tested class.</p>
     */
    private JiraProfileCompletenessChecker instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JiraProfileCompletenessCheckerFailureTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new JiraProfileCompletenessChecker();
        instance.setLog(new Log4jLogFactory().createLog("test"));
    }

    /**
     * <p>Tests {@link JiraProfileCompletenessChecker#JiraProfileCompletenessChecker()} constructor.</p>
     */
    @Test
    public void testCtor() {

        instance = new JiraProfileCompletenessChecker();
        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests {@link JiraProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)}
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
