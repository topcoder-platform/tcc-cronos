/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsTypeDAO;
import com.topcoder.util.log.LogManager;

/**
 * Failure test for class JPADigitalRunPointsTypeDAO.
 *
 * @author extra
 * @version 1.0
 */
public class JPADigitalRunPointsTypeDAOFailureTests extends TestCase {

    /**
     * Represent the dao for test.
     */
    private JPADigitalRunPointsTypeDAO dao;

    /**
     * Represents the digital run points.
     */
    private DigitalRunPointsType pointsType;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsTypeDAO();
        dao.setSessionContext(new MockSessionContext());
        dao.setUnitName("entityManager");
        dao.setLogger(LogManager.getLog());

        pointsType = new DigitalRunPointsType();
        pointsType.setId(10L);
        super.setUp();
    }

    /**
     * Failure tests for method createDigitalRunPointsType(DigitalRunPointsType pointsType). If
     * pointsType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsType_Null_pointsType() throws Exception {
        try {
            dao.createDigitalRunPointsType(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method createDigitalRunPointsType(DigitalRunPointsType pointsType). If id of
     * pointsType is positive, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsType_positive_pointsType_id() throws Exception {
        pointsType.setId(1L);
        try {
            dao.createDigitalRunPointsType(pointsType);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method updateDigitalRunPointsType(DigitalRunPointsType pointsType). If
     * pointsType is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateDigitalRunPointsType_Null_pointsType() throws Exception {
        try {
            dao.updateDigitalRunPointsType(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method removeDigitalRunPointsType(long pointsTypeId). If pointsTypeId is
     * negative, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveDigitalRunPointsType_Negative_pointsTypeId() throws Exception {
        try {
            dao.removeDigitalRunPointsType(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method getDigitalRunPointsType(long pointsTypeId). If pointsTypeId is negative,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDigitalRunPointsTypes_Negative_pointsTypeId() throws Exception {
        try {
            dao.getDigitalRunPointsType(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
