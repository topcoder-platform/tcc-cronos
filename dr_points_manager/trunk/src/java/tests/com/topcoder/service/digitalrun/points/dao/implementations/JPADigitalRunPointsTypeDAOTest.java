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

import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.EntityNotFoundException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test cases for class JPADigitalRunPointsTypeDAO. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JPADigitalRunPointsTypeDAOTest extends TestCase {

    /**
     * A EntityManager instance for testing.
     */
    private static MockEntityManager em;

    /**
     * A JPADigitalRunPointsTypeDAO instance for testing.
     */
    private JPADigitalRunPointsTypeDAO impl;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsTypeDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    @Override
    public void setUp() {
        impl = new JPADigitalRunPointsTypeDAO();
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
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
            Query res = em.createNativeQuery("delete from dr_points_type_lu");
            res.executeUpdate();
            em.getTransaction().commit();
        }
        em.SetExceptionFlag(false);
        em.clear();
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
            Query res = em.createNativeQuery("delete from dr_points_type_lu");
            res.executeUpdate();
            em.getTransaction().commit();
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>JPADigitalRunPointsTypeDAO()</code>, expects the
     * instance is created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the JPADigitalRunPointsTypeDAO instance.", impl);
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();

        pointsType.setDescription("description");

        assertEquals("The pointsType id should be zero.", 0, pointsType.getId());
        impl.createDigitalRunPointsType(pointsType);
        System.out.println("The entity id is: " + pointsType.getId());
        assertEquals("The result should be same", "description", pointsType.getDescription());
        impl.removeDigitalRunPointsType(pointsType.getId());
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_WithExistedType() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        try {
            impl.createDigitalRunPointsType(pointsType);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsType_WithIdPositive() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setId(2);
        try {
            impl.createDigitalRunPointsType(pointsType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsType(DigitalRunPointsType pointsType)</code> method, expects no
     * error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setCreationDate(new Date());
        pointsType.setModificationDate(new Date());
        pointsType.setDescription("description");
        impl.createDigitalRunPointsType(pointsType);

        pointsType.setDescription("new description");
        impl.updateDigitalRunPointsType(pointsType);

        DigitalRunPointsType result = impl.getDigitalRunPointsType(pointsType.getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsType(result.getId());
    }

    /**
     * <p>
     * Failure test for the <code>updateDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsType_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateDigitalRunPointsType(DigitalRunPointsType pointsType)</code>
     * method with the pointsType is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsType_WithNotExistpointsOperation() throws Exception {
        JPADigitalRunPointsTypeDAOTest.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsType(new DigitalRunPointsType());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>removeDigitalRunPointsType(long pointsTypeId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setDescription("description");

        impl.createDigitalRunPointsType(pointsType);

        assertNotNull("The result should not be null.", impl.getDigitalRunPointsType(pointsType.getId()));

        impl.removeDigitalRunPointsType(pointsType.getId());

        try {
            impl.getDigitalRunPointsType(pointsType.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsType_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsType_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getDigitalRunPointsType(long pointsTypeId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsType_Accuracy() throws Exception {
        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setDescription("description");

        impl.createDigitalRunPointsType(pointsType);

        DigitalRunPointsType result = impl.getDigitalRunPointsType(pointsType.getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsType(pointsType.getId());
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsType_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getDigitalRunPointsType(long pointsTypeId)</code> method with
     * the pointsTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsType_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsTypes()</code> method, expects no error
     * occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsTypes_Accuracy() throws Exception {
        List<DigitalRunPointsType> result = impl.getAllDigitalRunPointsTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsType pointsType = new DigitalRunPointsType();
        pointsType.setDescription("description");
        impl.createDigitalRunPointsType(pointsType);

        result = impl.getAllDigitalRunPointsTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should not be empty. ", 1, result.size());
    }

}
