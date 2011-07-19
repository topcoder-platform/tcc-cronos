/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.sql.Date;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test case of {@link ReferralData}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class ReferralDataTest extends TestCase {
    /**
     * <p>
     * Represents the ReferralData instance to test against.
     * </p>
     */
    private ReferralData referralData;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ReferralDataTest.class);
        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        referralData = new ReferralData();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        referralData = null;
    }

    /**
     * <p>
     * Accuracy test for constructor <code>ReferralData()</code>. It verifies the new instance is created.
     * </p>
     */
    public void testReferralData() {
        assertNotNull("Unable to instantiate ReferralData", referralData);
    }

    /**
      * <p>
      * Test method for {@link ReferralData#getCoderId()}. It verifies the returned value is correct.
      * </p>
      */
    public void testGetCoderId() {
        assertEquals("Incorrect default value for coderId", 0, referralData.getCoderId());

        // set a value
        referralData.setCoderId(1);
        assertEquals("Incorrect coderId after set to a new one", 1, referralData.getCoderId());
    }

    /**
     * <p>
     * Test method for {@link ReferralData#setCoderId(long)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetCoderId() {
        // set a value
        referralData.setCoderId(2);
        assertEquals("Incorrect coderId after set to a new one", 2, referralData.getCoderId());
    }

    /**
      * <p>
      * Test method for {@link ReferralData#getRating()}. It verifies the returned value is correct.
      * </p>
      */
    public void testGetRating() {
        assertEquals("Incorrect default value for rating", 0, referralData.getRating());

        // set a value
        referralData.setRating(1);
        assertEquals("Incorrect rating after set to a new one", 1, referralData.getRating());
    }

    /**
     * <p>
     * Test method for {@link ReferralData#setRating(int)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetRating() {
        // set a value
        referralData.setRating(2);
        assertEquals("Incorrect rating after set to a new one", 2, referralData.getRating());
    }

    /**
     * <p>
     * Test method for {@link ReferralData#getHandle()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetHandle() {
        assertNull("Incorrect default handle for content", referralData.getHandle());

        // set a value
        referralData.setHandle("handle");
        assertEquals("Incorrect handle after set a new one", "handle", referralData.getHandle());
    }

    /**
     * <p>
     * Test method for {@link ReferralData#setHandle(String)}. It verifies the assigned value is correct.
     * </p>
     */
    public void testSetHandle() {
        // set a handle
        referralData.setHandle("handle");
        assertEquals("Incorrect handle after set a new one", "handle", referralData.getHandle());

        // set to an empty string
        referralData.setHandle("");
        assertEquals("Incorrect handle after set an empty one", "", referralData.getHandle());

        // set to null
        referralData.setHandle(null);
        assertNull("Incorrect handle after set to null", referralData.getHandle());
    }

    /**
     * <p>
     * Test method for {@link ReferralData#getMemberSince()}. It verifies the returned value is correct.
     * </p>
     */
    public void testGetMemberSince() {
        assertNull("Incorrect default value for memberSince", referralData.getMemberSince());

        // set a value
        Date date = new Date(123);
        referralData.setMemberSince(date);
        assertEquals("Incorrect memberSince after set a new one", date, referralData.getMemberSince());
    }

    /**
     * <p>
     * Test method for {@link ReferralData#setMemberSince(java.sql.Date)}. It verifies the assigned value is
     * correct.
     * </p>
     */
    public void testSetMemberSince() {
        // set a value
        Date date = new Date(123);
        referralData.setMemberSince(date);
        assertEquals("Incorrect memberSince after set a new one", date, referralData.getMemberSince());

        // set to null
        referralData.setMemberSince(null);
        assertNull("Incorrect memberSince after set to null", referralData.getMemberSince());
    }

}
