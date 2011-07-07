/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl.completenesscheckers;

import static com.topcoder.reg.profilecompleteness.filter.TestHelper.createBasicCompleteUser;
import junit.framework.TestCase;

import com.topcoder.reg.profilecompleteness.filter.MockLog;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.User;

/**
 * Unit tests for <code>CopilotProfileCompletenessChecker</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class CopilotProfileCompletenessCheckerTest extends TestCase {

    /**
     * Represents the <code>CopilotProfileCompletenessChecker</code> instance used to test against.
     */
    private CopilotProfileCompletenessChecker t;

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
        t = new CopilotProfileCompletenessChecker();
        log = new MockLog();
        user = createBasicCompleteUser();
        CoderType coderType = new CoderType();
        coderType.setId(CoderType.PROFESSIONAL);
        Country country = new Country();
        country.setName("US");

        Coder coder = new Coder();
        coder.setCoderType(coderType);
        coder.setCompCountry(country);
        user.setCoder(coder);
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
        this.log = null;
        user = null;
    }

    /**
     * Accuracy test for constructor <code>CopilotProfileCompletenessChecker()</code>.
     *
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     */
    public void testCtor_Accuracy() {
        assertNotNull("Instance should be created.", t);
        assertEquals(null, t.getLog());
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return true, if the user profile is complete.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy1() throws Exception {
        t.setLog(log);
        assertEquals(true, t.isProfileComplete(user));

        // check the log
        String logMessage = log.getLogMessages();
        assertTrue(logMessage
            .contains("[Entering method {CopilotProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage
            .contains("[Exiting method {CopilotProfileCompletenessChecker.isProfileComplete}]"));
        assertTrue(logMessage.contains("[Output parameter {true}]"));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false, if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy2() throws Exception {
        t.setLog(log);
        user.setCoder(null);
        assertEquals(false, t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false, if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy3() throws Exception {
        t.setLog(log);
        user.getCoder().setCoderType(null);
        assertFalse(t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false, if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy4() throws Exception {
        t.setLog(log);
        user.getCoder().setCompCountry(null);
        assertFalse(t.isProfileComplete(user));
    }

    /**
     * Accuracy test for <code>isProfileComplete(User user)</code>.
     *
     * It should return false, if the user profile is incomplete.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testIsProfileComplete_Accuracy5() throws Exception {
        t.setLog(log);
        user.setFirstName(null); // incomplete
        assertFalse(t.isProfileComplete(user));
    }

    /**
     * Failure test for method <code>isProfileComplete(User user)</code>.
     *
     * If user is null, <code>IllegalArgumentException</code> will be thrown
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
