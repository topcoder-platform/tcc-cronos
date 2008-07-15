/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;

/**
 * Failure test for class AbstractDAO.
 *
 * @author extra
 * @version 1.0
 */
public class AbstractDAOFailureTests extends TestCase {

    /**
     * Represent the dao for test.
     */
    private MockAbstractDAO dao;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        dao = new MockAbstractDAO();

        super.setUp();
    }

    /**
     * Failure test for method setSessionContext(SessionContext sessionContext). If sessionContext is null,
     * IllegalArgumentException expected.
     */
    public void testSetSessionContext_Null_sessionContext() {
        try {
            dao.setSessionContext(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for method setLogger(Log logger). If logger is null, IllegalArgumentException expected.
     */
    public void testSetLogger_Null_logger() {
        try {
            dao.setLogger(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setUnitName(String unitName). If unitName is null, IllegalArgumentException expected.
     */
    public void testSetUnitName_Null_unitName() {
        try {
            dao.setUnitName(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setUnitName(String unitName). If unitName is empty, IllegalArgumentException expected.
     */
    public void testSetUnitName_Empty_unitName() {
        try {
            dao.setUnitName(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getEntityManager(). If sessionContext is null, DigitalRunPointsManagerPersistenceException
     * expected.
     */
    public void testGetEntityManager_Null_sessionContext() {
        try {
            dao.getEntityManager();
            fail("DigitalRunPointsManagerPersistenceException expected.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for getEntityManager(). If return type if not correct, DigitalRunPointsManagerPersistenceException
     * expected.
     */
    public void testGetEntityManager_InvalidType() {
        dao.setSessionContext(new MockSessionContext());
        dao.setUnitName("invalid");
        try {
            dao.getEntityManager();
            fail("DigitalRunPointsManagerPersistenceException expected.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for getEntityManager(). If return value is null, DigitalRunPointsManagerPersistenceException
     * expected.
     */
    public void testGetEntityManager_Null_result() {
        dao.setSessionContext(new MockSessionContext());
        dao.setUnitName("notfound");
        try {
            dao.getEntityManager();
            fail("DigitalRunPointsManagerPersistenceException expected.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }
}
