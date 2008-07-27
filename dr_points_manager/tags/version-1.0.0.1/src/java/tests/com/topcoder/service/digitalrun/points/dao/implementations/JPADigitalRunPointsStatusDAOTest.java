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

import com.topcoder.service.digitalrun.entity.DigitalRunPointsStatus;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.EntityNotFoundException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test cases for class JPADigitalRunPointsStatusDAO. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JPADigitalRunPointsStatusDAOTest extends TestCase {
    /**
     * A EntityManager instance for testing.
     */
    private static MockEntityManager em;

    /**
     * A JPADigitalRunPointsStatusDAO instance for testing.
     */
    private JPADigitalRunPointsStatusDAO impl;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsStatusDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    @Override
    public void setUp() {
        impl = new JPADigitalRunPointsStatusDAO();
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
            Query res = em.createNativeQuery("delete from dr_points_status_lu");
            res.executeUpdate();
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>JPADigitalRunPointsStatusDAO()</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the JPADigitalRunPointsStatusDAO instance.", impl);
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();

        pointsStatus.setDescription("description");

        assertEquals("The pointsStatus id should be zero.", 0, pointsStatus.getId());
        impl.createDigitalRunPointsStatus(pointsStatus);
        System.out.println("The entity id is: " + pointsStatus.getId());
        assertEquals("The result should be same", "description", pointsStatus.getDescription());
        impl.removeDigitalRunPointsStatus(pointsStatus.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_WithExistedStatus() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        try {
            impl.createDigitalRunPointsStatus(pointsStatus);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsStatus_WithIdPositive() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setId(2);
        try {
            impl.createDigitalRunPointsStatus(pointsStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setCreationDate(new Date());
        pointsStatus.setModificationDate(new Date());
        pointsStatus.setDescription("description");
        impl.createDigitalRunPointsStatus(pointsStatus);

        pointsStatus.setDescription("new description");
        impl.updateDigitalRunPointsStatus(pointsStatus);

        DigitalRunPointsStatus result = impl.getDigitalRunPointsStatus(pointsStatus.getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsStatus(result.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsStatus_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsStatus(DigitalRunPointsStatus pointsStatus)</code> method with
     * the pointsStatus is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsStatus_WithNotExistpointsOperation() throws Exception {
        JPADigitalRunPointsStatusDAOTest.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsStatus(new DigitalRunPointsStatus());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>removeDigitalRunPointsStatus(long pointsStatusId)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setDescription("description");

        impl.createDigitalRunPointsStatus(pointsStatus);

        assertNotNull("The result should not be null.", impl.getDigitalRunPointsStatus(pointsStatus.getId()));

        impl.removeDigitalRunPointsStatus(pointsStatus.getId());

        try {
            impl.getDigitalRunPointsStatus(pointsStatus.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsStatus_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsStatus_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsStatus(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getDigitalRunPointsStatus(long pointsStatusId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsStatus_Accuracy() throws Exception {
        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setDescription("description");

        impl.createDigitalRunPointsStatus(pointsStatus);

        DigitalRunPointsStatus result = impl.getDigitalRunPointsStatus(pointsStatus.getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsStatus(pointsStatus.getId());
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsStatus_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsStatus(long pointsStatusId)</code> method
     * with the pointsStatusId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsStatus_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsStatus(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsStatuses()</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsStatuses_Accuracy() throws Exception {
        List<DigitalRunPointsStatus> result = impl.getAllDigitalRunPointsStatuses();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsStatus pointsStatus = new DigitalRunPointsStatus();
        pointsStatus.setDescription("description");
        impl.createDigitalRunPointsStatus(pointsStatus);

        result = impl.getAllDigitalRunPointsStatuses();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 1, result.size());
    }

}
