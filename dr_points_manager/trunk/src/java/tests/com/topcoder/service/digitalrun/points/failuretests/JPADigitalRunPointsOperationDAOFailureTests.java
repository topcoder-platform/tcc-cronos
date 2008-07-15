/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsOperationDAO;
import com.topcoder.util.log.LogManager;

/**
 * Failure test for class JPADigitalRunPointsOperationDAO.
 *
 * @author extra
 * @version 1.0
 */
public class JPADigitalRunPointsOperationDAOFailureTests extends TestCase {

    /**
     * Represent the dao for test.
     */
    private JPADigitalRunPointsOperationDAO dao;

    /**
     * Represents the digital run points.
     */
    private DigitalRunPointsOperation pointsOperation;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsOperationDAO();
        dao.setSessionContext(new MockSessionContext());
        dao.setUnitName("entityManager");
        dao.setLogger(LogManager.getLog());

        pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setId(10L);
        super.setUp();
    }

    /**
     * Failure tests for method createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation). If
     * pointsOperation is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsOperation_Null_pointsOperation() throws Exception {
        try {
            dao.createDigitalRunPointsOperation(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation). If id of
     * pointsOperation is positive, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateDigitalRunPointsOperation_positive_pointsOperation_id() throws Exception {
        pointsOperation.setId(1L);
        try {
            dao.createDigitalRunPointsOperation(pointsOperation);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method updateDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation). If
     * pointsOperation is null, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateDigitalRunPointsOperation_Null_pointsOperation() throws Exception {
        try {
            dao.updateDigitalRunPointsOperation(null);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method removeDigitalRunPointsOperation(long pointsOperationId). If pointsOperationId is
     * negative, IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveDigitalRunPointsOperation_Negative_pointsOperationId() throws Exception {
        try {
            dao.removeDigitalRunPointsOperation(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure tests for method getDigitalRunPointsOperation(long pointsOperationId). If pointsOperationId is negative,
     * IllegalArgumentException expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetDigitalRunPointsOperations_Negative_pointsOperationId() throws Exception {
        try {
            dao.getDigitalRunPointsOperation(-1L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
