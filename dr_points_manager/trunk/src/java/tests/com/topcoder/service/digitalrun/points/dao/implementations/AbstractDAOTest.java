/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.dao.implementations;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test cases for class AbstractDAO. All the method are tested.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractDAOTest extends TestCase {

    /**
     * A AbstractDAO instance for testing.
     */
    private AbstractDAO impl;

    /**
     * Get this test suite.
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(AbstractDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    @Override
    public void setUp() {
        impl = new MockAbstractDAO();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AbstractDAO()</code>, expects the instance is
     * created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the AbstractDAO instance.", impl);
    }

    /**
     * <p>
     * Accuracy test for the <code>getEntityManager()</code> method, expects no error occurs.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testGetEntityManager_Accuracy() throws Exception {
        impl.setLogger(LogManager.getLog("temp"));
        MockSessionContext sc = new MockSessionContext();
        EntityManager manager = null;
        try {
            Ejb3Configuration cfg = new Ejb3Configuration();
            EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
            manager = emf.createEntityManager();
        } catch (Exception e) {
            // ignore
        }
        sc.setEm(new MockEntityManager(manager));
        impl.setSessionContext(sc);
        impl.setUnitName("unitName");
        EntityManager em = impl.getEntityManager();
        assertNotNull("The result should not be null.", em);
    }

    /**
     * <p>
     * Failure test for the <code>getEntityManager()</code> method with the sessionContext is
     * null.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testGetEntityManager_WithSessionContextNull() throws Exception {
        impl.setLogger(LogManager.getLog("temp"));
        try {
            impl.getEntityManager();
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getEntityManager()</code> method with no EntityManager found.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     * @throws Exception
     *             if any error occurs
     */
    public void testGetEntityManager_WithNoEntityManagerFound() throws Exception {
        impl.setLogger(LogManager.getLog("temp"));
        impl.setSessionContext(new MockSessionContext());
        try {
            impl.getEntityManager();
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getLogger()</code> method, expects no error occurs.
     * </p>
     */
    public void testGetLogger_Accuracy() {
        Log logger = LogManager.getLog("temp");
        impl.setLogger(logger);
        assertEquals("The logger should be same as ", logger, impl.getLogger());
    }

    /**
     * <p>
     * Accuracy test for the <code>setLogger(Log logger)</code> method, expects no error occurs.
     * </p>
     */
    public void testSetLogger_Accuracy() {
        Log logger = LogManager.getLog("temp");
        impl.setLogger(logger);
        assertEquals("The logger should be same as ", logger, impl.getLogger());
    }

    /**
     * <p>
     * Failure test for the <code>setLogger(Log logger)</code> method with the logger is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetLogger_WithNull() {
        try {
            impl.setLogger(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getUnitName()</code> method, expects no error occurs.
     * </p>
     */
    public void testGetUnitName_Accuracy() {
        String expect = "New_Unit_Name";
        impl.setUnitName(expect);
        assertEquals("The unit name should be same as ", expect, impl.getUnitName());
    }

    /**
     * <p>
     * Accuracy test for the <code>setUnitName(String unitName)</code> method, expects no error
     * occurs.
     * </p>
     */
    public void testSetUnitName_Accuracy() {
        String expect = "New_Unit_Name";
        impl.setUnitName(expect);
        assertEquals("The unit name should be same as ", expect, impl.getUnitName());
    }

    /**
     * <p>
     * Failure test for the <code>setUnitName(String unitName)</code> method with the unitName is
     * null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetUnitName_WithNull() {
        try {
            impl.setUnitName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>setUnitName(String unitName)</code> method with the unitName is
     * empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetUnitName_WithEmpty() {
        try {
            impl.setUnitName("");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>setUnitName(String unitName)</code> method with the unitName is
     * trimmed empty.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetUnitName_WithTrimmedEmpty() {
        try {
            impl.setUnitName("   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getSessionContext()</code> method, expects no error occurs.
     * </p>
     */
    public void testGetSessionContext_Accuracy() {
        SessionContext sc = new MockSessionContext();
        impl.setSessionContext(sc);
        assertEquals("The session context should be same as ", sc, impl.getSessionContext());
    }

    /**
     * <p>
     * Accuracy test for the <code>setSessionContext(SessionContext sessionContext)</code> method,
     * expects no error occurs.
     * </p>
     */
    public void testSetSessionContext_Accuracy() {
        SessionContext sc = new MockSessionContext();
        impl.setSessionContext(sc);
        assertEquals("The session context should be same as ", sc, impl.getSessionContext());
    }

    /**
     * <p>
     * Failure test for the <code>setSessionContext(SessionContext sessionContext)</code> method
     * with the sessionContext is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetSessionContext_WithNull() {
        try {
            impl.setSessionContext(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
