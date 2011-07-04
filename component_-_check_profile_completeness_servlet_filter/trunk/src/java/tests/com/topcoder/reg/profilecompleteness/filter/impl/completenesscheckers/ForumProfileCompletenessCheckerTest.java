/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers;

import static com.topcoder.reg.profilecompleteness.filter.TestHelper.createBasicCompleteUser;
import junit.framework.TestCase;

import com.topcoder.reg.profilecompleteness.filter.MockLog;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;

/**
 * Unit tests for <code>ForumProfileCompletenessChecker</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class ForumProfileCompletenessCheckerTest extends TestCase {

    /**
     * Represents the <code>ForumProfileCompletenessChecker</code> instance used to test against.
     */
    private ForumProfileCompletenessChecker t;

    /**
     * Represents the <code>User</code> instance used to test against.
     */
    private User user;

    /**
     * Represents the <code>Log</code> instance used to test against.
     */
    private MockLog log;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        t = new ForumProfileCompletenessChecker();
        log = new MockLog();
        user = createBasicCompleteUser();
        user.setTimeZone(new TimeZone());
    }

    /**
     * Tears down the fixture. This method is called after a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        t = null;
        user = null;
        log = null;
    }

    /**
     * Accuracy test for constructor <code>ForumProfileCompletenessChecker()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return true, if the user has complete profile.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy1() throws Exception {
        t.setLog(log);
        boolean complete = t.isProfileComplete(user);
        assertEquals(true, complete);

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {ForumProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage
            .contains("[Exiting method {ForumProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage.contains("[Output parameter {true}]"));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false, if the user has incomplete profile.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy2() throws Exception {
        t.setLog(log);
        user.setTimeZone(null);
        boolean complete = t.isProfileComplete(user);
        assertEquals(false, complete);
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false, if the user has incomplete profile.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy3() throws Exception {
        t.setLog(log);
        user.setFirstName(null);
        boolean complete = t.isProfileComplete(user);
        assertEquals(false, complete);
    }

    /**
     * Failure test for method <code>isProfileComplete(User user)</code>.
     *
     * If user is null, <code>IllegalArgumentException</code> will be thrown.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Failure_IllegalArgumentException() throws Exception {
        t.setLog(log);
        try {
            t.isProfileComplete(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

}
