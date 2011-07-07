/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers.ForumProfileCompletenessChecker;
import com.topcoder.util.log.log4j.Log4jLogFactory;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>Failure tests for {@link ForumProfileCompletenessChecker} class.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class ForumProfileCompletenessCheckerFailureTests {

    /**
     * <p>Represents the instance of the tested class.</p>
     */
    private ForumProfileCompletenessChecker instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ForumProfileCompletenessCheckerFailureTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new ForumProfileCompletenessChecker();
        instance.setLog(new Log4jLogFactory().createLog("test"));
    }

    /**
     * <p>Tests {@link ForumProfileCompletenessChecker#ForumProfileCompletenessChecker()} constructor.</p>
     */
    @Test
    public void testCtor() {

        instance = new ForumProfileCompletenessChecker();
        assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests {@link ForumProfileCompletenessChecker#isProfileComplete(com.topcoder.web.common.model.User)}
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
