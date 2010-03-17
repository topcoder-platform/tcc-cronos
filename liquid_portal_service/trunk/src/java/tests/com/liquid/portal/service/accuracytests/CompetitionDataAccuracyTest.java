/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.accuracytests;

import java.io.Serializable;
import java.util.Date;

import junit.framework.TestCase;

import com.liquid.portal.service.CompetitionData;

/**
 * Accuracy tests for {@link CompetitionData}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionDataAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the CompetitionData. Just for test.
     * </p>
     */
    private CompetitionData instance;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        instance = new CompetitionData();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test for constructor. It verifies the new instance is created.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("should not be null", instance);
        assertTrue("should be true", instance instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for setter and getter for autoReschedule filed.
     * </p>
     */
    public void testSetterAndGetterFor_autoReschedule() {
        instance.setAutoReschedule(true);
        assertTrue("should be true", instance.getAutoReschedule());
    }

    /**
     * <p>
     * Accuracy test for setter and getter for billingProjectId filed.
     * </p>
     */
    public void testSetterAndGetterFor_billingProjectId() {
        instance.setBillingProjectId(1L);
        assertTrue("should be true", instance.getBillingProjectId() == 1L);
    }

    /**
     * <p>
     * Accuracy test for setter and getter for cca filed.
     * </p>
     */
    public void testSetterAndGetterFor_cca() {
        instance.setCca(true);
        assertTrue("should be true", instance.getCca());
    }

    /**
     * <p>
     * Accuracy test for setter and getter for cockpitProjectName filed.
     * </p>
     */
    public void testSetterAndGetterFor_cockpitProjectName() {
        instance.setCockpitProjectName("tc");
        assertTrue("should be true", instance.getCockpitProjectName().equals("tc"));
    }

    /**
     * <p>
     * Accuracy test for setter and getter for contestName filed.
     * </p>
     */
    public void testSetterAndGetterFor_contestName() {
        instance.setContestName("tc");
        assertTrue("should be true", instance.getContestName().equals("tc"));
    }

    /**
     * <p>
     * Accuracy test for setter and getter for contestTypeName filed.
     * </p>
     */
    public void testSetterAndGetterFor_contestTypeName() {
        instance.setContestTypeName("tc");
        assertTrue("should be true", instance.getContestTypeName().equals("tc"));
    }

    /**
     * <p>
     * Accuracy test for setter and getter for requestedStartDate filed.
     * </p>
     */
    public void testSetterAndGetterFor_requestedStartDate() {
        Date start = new Date();
        instance.setRequestedStartDate(start);
        assertTrue("should be true", instance.getRequestedStartDate().equals(start));
    }

    /**
     * <p>
     * Accuracy test for setter and getter for subContestTypeName filed.
     * </p>
     */
    public void testSetterAndGetterFor_subContestTypeName() {
        instance.setSubContestTypeName("tc");
        assertTrue("should be true", instance.getSubContestTypeName().equals("tc"));
    }
}