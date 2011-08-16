/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.failuretests.ejb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.ejb.StudioServiceBean;

/**
 * Generated failure test for the <code>StudioServiceBean.java</code> class.
 *
 * @author kakarotto
 * @version 1.0
 */
public class StudioServiceBeanFailureTests {

    /** The flag to denote whether to test with value overrides. */
    private boolean useOverrides = true;

    /** The flag to denote whether to test with default value. */
    private boolean useDefaults = true;

    /** The object to test. */
    private StudioServiceBean testObject;

    /**
     * Sets up testing environment
     *
     * @throws Exception when error occurs
     */
    @Before
    public void setUp() throws Exception {
        testObject = new StudioServiceBean();
    }

    /**
     * Tears down testing environment
     *
     * @throws Exception when error occurs
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Gets flag denoting whether to use default value for testing.
     *
     * @return flag denoting whether to use default value for testing
     */
    public boolean getUseDefaults() {
        return useDefaults;
    }

    /**
     * Sets flag denoting whether to use default value for testing.
     *
     * @param value the flag denoting whether to use default value for testing
     */
    public void setUseDefaults(boolean value) {
        useDefaults = value;
    }

    /**
     * Gets flag denoting whether to use value overrides for testing
     *
     * @return flag denoting whether to use default value for testing
     */
    public boolean getUseOverrides() {
        return useOverrides;
    }

    /**
     * Sets flag denoting whether to use value overrides for testing
     *
     * @param value flag denoting whether to use value overrides for testing
     */
    public void setUseOverrides(boolean value) {
        useOverrides = value;
    }

    /**
     * Gets the object to test.
     *
     * @return the object to test
     */
    public StudioServiceBean getTestObject() {
        return testObject;
    }

    /**
     * Sets the object to test.
     *
     * @param value the object to test
     */
    public void setTestObject(StudioServiceBean value) {
        testObject = value;
    }

    // This section contains tests for the <code>createContest</code> method.

    /**
     * Tests for the <code>createContest</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateContest0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().createContest(null, 0);

    }

    /**
     * Tests for the <code>createContest</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateContest1() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().createContest(new ContestData(), -1);

    }

    // This section contains tests for the <code>getContest</code> method.

    /**
     * Tests for the <code>getContest</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContest0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().getContest(-1);

    }

    // This section contains tests for the <code>getContestsForProject</code> method.

    /**
     * Tests for the <code>getContestsForProject</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestsForProject0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().getContestsForProject(-1);

    }

    // This section contains tests for the <code>updateContest</code> method.

    /**
     * Tests for the <code>updateContest</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateContest0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().updateContest(null);

    }

    // This section contains tests for the <code>updateContestStatus</code> method.

    /**
     * Tests for the <code>updateContestStatus</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateContestStatus0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().updateContestStatus(-1, 1);

    }

    /**
     * Tests for the <code>updateContestStatus</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateContestStatus1() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().updateContestStatus(1, -1);

    }

    // This section contains tests for the <code>uploadDocumentForContest</code> method.

    /**
     * Tests for the <code>uploadDocumentForContest</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUploadDocumentForContest0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().uploadDocumentForContest(null);

    }

    // This section contains tests for the <code>removeDocumentFromContest</code> method.

    /**
     * Tests for the <code>removeDocumentFromContest</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDocumentFromContest0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().removeDocumentFromContest(null);

    }

    // This section contains tests for the <code>retrieveSubmissionsForContest</code> method.

    /**
     * Tests for the <code>retrieveSubmissionsForContest</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveSubmissionsForContest0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().retrieveSubmissionsForContest(-1);

    }

    // This section contains tests for the <code>retrieveAllSubmissionsByMember</code> method.

    /**
     * Tests for the <code>retrieveAllSubmissionsByMember</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveAllSubmissionsByMember0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().retrieveAllSubmissionsByMember(-1);

    }

    // This section contains tests for the <code>updateSubmission</code> method.

    /**
     * Tests for the <code>updateSubmission</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateSubmission0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().updateSubmission(null);

    }

    // This section contains tests for the <code>removeSubmission</code> method.

    /**
     * Tests for the <code>removeSubmission</code> method with parameters containing invalid values.
     * The <code>IllegalArgumentException</code> exception is expected.
     * @throws Exception thrown to junit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveSubmission0() throws Exception {
        if (!getUseDefaults()) {
            return;
        }
        getTestObject().removeSubmission(-1);

    }

    /**
     * Tests getUserContests(username) method for null parameter.
     * IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserContestsNull() throws Exception {
        getTestObject().getUserContests(null);
    }

    /**
     * Tests getUserContests(username) method for empty parameter.
     * IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper.
     * @since 1.3
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserContestsEmpty() throws Exception {
        getTestObject().getUserContests(" ");
    }
}