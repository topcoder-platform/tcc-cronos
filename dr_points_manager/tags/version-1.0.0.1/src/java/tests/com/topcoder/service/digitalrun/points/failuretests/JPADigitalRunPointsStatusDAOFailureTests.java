/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsStatusDAO;
import com.topcoder.util.log.LogManager;

/**
 * Failure test for class JPADigitalRunPointsStatusDAO.
 *
 * @author extra
 * @version 1.0
 */
public class JPADigitalRunPointsStatusDAOFailureTests extends TestCase {

    /**
     * Represent the dao for test.
     */
    private JPADigitalRunPointsStatusDAO dao;

    /**
     * Represents the digital run points.
     */
    private DigitalRunPointsStatus pointsStatus;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsStatusDAO();
        dao.setSessionContext(new MockSessionContext());
        dao.setUnitName("entityManager");
        dao.setLogger(LogManager.getLog());

        pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setId(10L);
        super.setUp();
    }

    /**
     * Failure tests for method createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus). If
     * pointsStatus is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsStatus_Null_pointsStatus() throws Exception {
        try {
            dao.createDigitalRunPointsStatus(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus). If id of
     * pointsStatus is positive, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsStatus_positive_pointsStatus_id() throws Exception {
        pointsStatus.setId(1L);
        try {
            dao.createDigitalRunPointsStatus(pointsStatus);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus). If
     * pointsStatus is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateDigitalRunPointsStatus_Null_pointsStatus() throws Exception {
        try {
            dao.updateDigitalRunPointsStatus(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method removeDigitalRunPointsStatus(long pointsStatusId). If pointsStatusId is
     * negative, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveDigitalRunPointsStatus_Negative_pointsStatusId() throws Exception {
        try {
            dao.removeDigitalRunPointsStatus(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method getDigitalRunPointsStatus(long pointsStatusId). If pointsStatusId is negative,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDigitalRunPointsStatuss_Negative_pointsStatusId() throws Exception {
        try {
            dao.getDigitalRunPointsStatus(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
