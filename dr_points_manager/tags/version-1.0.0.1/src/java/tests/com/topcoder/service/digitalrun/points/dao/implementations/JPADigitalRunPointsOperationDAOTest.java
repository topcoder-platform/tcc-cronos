/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.dao.implementations;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.EntityNotFoundException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test cases for class JPADigitalRunPointsOperationDAO. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JPADigitalRunPointsOperationDAOTest extends TestCase {
    /**
     * A EntityManager instance for testing.
     */
    private static MockEntityManager em;

    /**
     * A JPADigitalRunPointsOperationDAO instance for testing.
     */
    private JPADigitalRunPointsOperationDAO impl;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsOperationDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    @Override
    public void setUp() {
        impl = new JPADigitalRunPointsOperationDAO();
        impl.setLogger(LogManager.getLog("temp"));

        if (em == null) {
            EntityManager manager = null;
            try {
                Ejb3Configuration cfg = new Ejb3Configuration();
                EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                manager = emf.createEntityManager();
            } catch (Exception e) {
                // ignore
            }
            em = new MockEntityManager(manager);
        }
        em.SetExceptionFlag(false);
        em.clear();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        MockSessionContext sc = new MockSessionContext();
        sc.setEm(em);
        impl.setSessionContext(sc);
        impl.setUnitName("unitName");
    }

    /**
     * Clear the database.
     */
    public void tearDown() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
            Query res = em.createNativeQuery("delete from dr_points_operation_lu");
            res.executeUpdate();
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>JPADigitalRunPointsOperationDAO()</code>, expects
     * the instance is created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the JPADigitalRunPointsOperationDAO instance.", impl);
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();

        pointsOperation.setDescription("description");

        assertEquals("The pointsOperation id should be zero.", 0, pointsOperation.getId());
        impl.createDigitalRunPointsOperation(pointsOperation);
        System.out.println("The entity id is: " + pointsOperation.getId());
        assertEquals("The result should be same", "description", pointsOperation.getDescription());
        impl.removeDigitalRunPointsOperation(pointsOperation.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method with the pointsOperation is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsOperation(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method with the pointsOperation is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_WithExistedOperation() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        try {
            impl.createDigitalRunPointsOperation(pointsOperation);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method with the pointsOperation's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsOperation_WithIdPositive() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setId(2);
        try {
            impl.createDigitalRunPointsOperation(pointsOperation);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsOperation(DigitalRunPointsOperation pointsOperation)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setCreationDate(new Date());
        pointsOperation.setModificationDate(new Date());
        pointsOperation.setDescription("description");
        impl.createDigitalRunPointsOperation(pointsOperation);

        pointsOperation.setDescription("new description");
        impl.updateDigitalRunPointsOperation(pointsOperation);

        DigitalRunPointsOperation result = impl.getDigitalRunPointsOperation(pointsOperation.getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsOperation(result.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsOperation(DigitalRunpointsOperation pointsOperation)</code>
     * method with the pointsOperation is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsOperation_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsOperation(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsOperation(DigitalRunpointsOperation pointsOperation)</code>
     * method with the pointsOperation is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsOperation_WithNotExistpointsOperation() throws Exception {
        JPADigitalRunPointsOperationDAOTest.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsOperation(new DigitalRunPointsOperation());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>removeDigitalRunPointsOperation(long pointsOperationId)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setDescription("description");

        impl.createDigitalRunPointsOperation(pointsOperation);

        assertNotNull("The result should not be null.", impl.getDigitalRunPointsOperation(pointsOperation
                .getId()));

        impl.removeDigitalRunPointsOperation(pointsOperation.getId());

        try {
            impl.getDigitalRunPointsOperation(pointsOperation.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsOperation_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsOperation(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsOperation_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsOperation(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getDigitalRunPointsOperation(long pointsOperationId)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsOperation_Accuracy() throws Exception {
        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setDescription("description");

        impl.createDigitalRunPointsOperation(pointsOperation);

        DigitalRunPointsOperation result = impl.getDigitalRunPointsOperation(pointsOperation.getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsOperation(pointsOperation.getId());
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsOperation_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsOperation(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsOperation(long pointsOperationId)</code>
     * method with the pointsOperationId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsOperation_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsOperation(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsOperations()</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsOperations_Accuracy() throws Exception {
        List<DigitalRunPointsOperation> result = impl.getAllDigitalRunPointsOperations();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsOperation pointsOperation = new DigitalRunPointsOperation();
        pointsOperation.setDescription("description");
        impl.createDigitalRunPointsOperation(pointsOperation);

        result = impl.getAllDigitalRunPointsOperations();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 1, result.size());
    }

}
