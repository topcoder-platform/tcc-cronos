/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import java.sql.Date;

import com.topcoder.web.reg.actions.miscellaneous.ReferralData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Accuracy test for ReferralData class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReferralDataAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of ReferralData used in test.
     * </p>
     */
    private ReferralData referralData;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ReferralDataAccuracyTests.class);
        return suite;
    }

    /**
     * <p>
     * Set up for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        referralData = new ReferralData();
    }

    /**
     * <p>
     * Accuracy test for ReferralData().
     * </p>
     */
    public void testCtor() {
        assertNotNull("The instance should be created.", referralData);
    }

    /**
     * <p>
     * Accuracy test for coderId.
     * </p>
     */
    public void testCoderId() {
        referralData.setCoderId(3);
        assertEquals("The coderId is incorrect.", 3, referralData.getCoderId());
    }

    /**
     * <p>
     * Accuracy test for rating.
     * </p>
     */
    public void testRating() {
        referralData.setRating(100);
        assertEquals("The rating is incorrect.", 100, referralData.getRating());
    }

    /**
     * <p>
     * Accuracy test for handle.
     * </p>
     */
    public void testHandle() {
        referralData.setHandle("tc");
        assertEquals("The handle is incorrect.", "tc", referralData.getHandle());
    }

    /**
     * <p>
     * Accuracy test for memberSince.
     * </p>
     */
    public void testMemberSince() {
        Date date = new Date(0);
        referralData.setMemberSince(date);
        assertEquals("The memberSince is incorrect.", date, referralData.getMemberSince());
    }
}