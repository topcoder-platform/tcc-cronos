/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsReferenceTypeDAO;
import com.topcoder.util.log.LogManager;

/**
 * Failure test for class JPADigitalRunPointsReferenceTypeDAO.
 *
 * @author extra
 * @version 1.0
 */
public class JPADigitalRunPointsReferenceTypeDAOFailureTests extends TestCase {

    /**
     * Represent the dao for test.
     */
    private JPADigitalRunPointsReferenceTypeDAO dao;

    /**
     * Represents the digital run points.
     */
    private DigitalRunPointsReferenceType pointsReferenceType;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsReferenceTypeDAO();
        dao.setSessionContext(new MockSessionContext());
        dao.setUnitName("entityManager");
        dao.setLogger(LogManager.getLog());

        pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setId(10L);
        super.setUp();
    }

    /**
     * Failure tests for method createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType). If
     * pointsReferenceType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsReferenceType_Null_pointsReferenceType() throws Exception {
        try {
            dao.createDigitalRunPointsReferenceType(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType). If id of
     * pointsReferenceType is positive, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsReferenceType_positive_pointsReferenceType_id() throws Exception {
        pointsReferenceType.setId(1L);
        try {
            dao.createDigitalRunPointsReferenceType(pointsReferenceType);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType). If
     * pointsReferenceType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateDigitalRunPointsReferenceType_Null_pointsReferenceType() throws Exception {
        try {
            dao.updateDigitalRunPointsReferenceType(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method removeDigitalRunPointsReferenceType(long pointsReferenceTypeId). If pointsReferenceTypeId is
     * negative, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveDigitalRunPointsReferenceType_Negative_pointsReferenceTypeId() throws Exception {
        try {
            dao.removeDigitalRunPointsReferenceType(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method getDigitalRunPointsReferenceType(long pointsReferenceTypeId). If pointsReferenceTypeId is negative,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDigitalRunPointsReferenceTypes_Negative_pointsReferenceTypeId() throws Exception {
        try {
            dao.getDigitalRunPointsReferenceType(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
