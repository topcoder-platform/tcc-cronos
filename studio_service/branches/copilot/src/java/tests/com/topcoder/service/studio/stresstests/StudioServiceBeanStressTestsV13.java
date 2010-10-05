/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.topcoder.service.studio.MockContestManager;
import com.topcoder.service.studio.MockSessionContext;
import com.topcoder.service.studio.MockSubmissionManager;
import com.topcoder.service.studio.ejb.StudioServiceBean;


/**
 * <p>
 * Test the StudioServiceBean updates in version 1.3.
 * </p>
 */
public class StudioServiceBeanStressTestsV13 extends TestCase {
    /** The count to test against. */
    private static final int COUNT = 1000;

    /** Bean to test. Made public, so it can be used outside of this test class (in demo, for example). */
    public StudioServiceBean target;

    /** MockContestManager used for test. */
    private MockContestManager contestMgr;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to junit
     */
    public void setUp() throws Exception {
        target = new StudioServiceBean();
        contestMgr = new MockContestManager();
        setPrivateField("sessionContext", new MockSessionContext());
        setPrivateField("submissionManager", new MockSubmissionManager());
        setPrivateField("contestManager", contestMgr);
        callInit();
    }

    /**
     * Tests getUserContests(username) method for valid data.
     *
     * @throws Exception to JUnit.
     *
     * @since 1.3
     */
    public void testGetUserContests() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < COUNT; i++) {
            target.getUserContests("2");
        }

        long end = System.currentTimeMillis();
        System.out.println("Execute getUserContests for " + COUNT + " times costs " + (end - start) + " mileseconds.");
    }

    /**
     * Tests getMilestoneSubmissionsForContest(long contestId) method for valid data.
     *
     * @throws Exception to JUnit.
     *
     * @since 1.3
     */
    public void testGetMilestoneSubmissionsForContest()
        throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < COUNT; i++) {
            target.getMilestoneSubmissionsForContest(1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Execute getMilestoneSubmissionsForContest for " + COUNT + " times costs " + (end - start) +
            " mileseconds.");
    }

    
    /**
     * Tests getFinalSubmissionsForContest(long contestId) method for valid data.
     *
     * @throws Exception to JUnit.
     * @since 1.3
     */
    public void testGetFinalSubmissionsForContest() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < COUNT; i++) {
            target.getFinalSubmissionsForContest(1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Execute getFinalSubmissionsForContest for " + COUNT + " times costs " + (end - start) +
            " mileseconds.");
    }

    /**
     * Used to set contents of private field in tested bean (inject resources).
     *
     * @param fieldName name of the field to set
     * @param value value to set in the field
     *
     * @throws Exception when it occurs deeper
     */
    private void setPrivateField(String fieldName, Object value)
        throws Exception {
        Field f = StudioServiceBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    /**
     * Simple routine to call private method init through reflection api.
     *
     * @throws Exception when it occurs deeper
     */
    private void callInit() throws Exception {
        Method m = target.getClass().getDeclaredMethod("init");
        m.setAccessible(true);
        m.invoke(target);
    }
}
