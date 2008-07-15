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

import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.EntityNotFoundException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit test cases for class JPADigitalRunPointsReferenceTypeDAO. All the method are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JPADigitalRunPointsReferenceTypeDAOTest extends TestCase {
    /**
     * A EntityManager instance for testing.
     */
    private static MockEntityManager em;

    /**
     * A JPADigitalRunPointsReferenceTypeDAO instance for testing.
     */
    private JPADigitalRunPointsReferenceTypeDAO impl;

    /**
     * Get this test suite.
     *
     * @return this test suite
     */
    public static Test suite() {
        return new TestSuite(JPADigitalRunPointsReferenceTypeDAOTest.class);
    }

    /**
     * Set up the test environment.
     */
    @Override
    public void setUp() {
        impl = new JPADigitalRunPointsReferenceTypeDAO();
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
            Query res = em.createNativeQuery("delete from dr_points_reference_type_lu");
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
            Query res = em.createNativeQuery("delete from dr_points_reference_type_lu");
            res.executeUpdate();
            em.getTransaction().commit();
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>JPADigitalRunPointsReferenceTypeDAO()</code>,
     * expects the instance is created properly.
     * </p>
     */
    public void testCtor_Accuracy() {
        assertNotNull("Failed to create the JPADigitalRunPointsReferenceTypeDAO instance.", impl);
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();

        pointsReferenceType.setDescription("description");

        assertEquals("The pointsReferenceType id should be zero.", 0, pointsReferenceType.getId());
        impl.createDigitalRunPointsReferenceType(pointsReferenceType);
        System.out.println("The entity id is: " + pointsReferenceType.getId());
        assertEquals("The result should be same", "description", pointsReferenceType.getDescription());
        impl.removeDigitalRunPointsReferenceType(pointsReferenceType.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_WithNull() throws Exception {
        try {
            impl.createDigitalRunPointsReferenceType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is exist.<br>
     * DigitalRunPointsManagerPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_WithExistedType() throws Exception {
        em.SetExceptionFlag(true);
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        try {
            impl.createDigitalRunPointsReferenceType(pointsReferenceType);
            fail("DigitalRunPointsManagerPersistenceException should be thrown.");
        } catch (DigitalRunPointsManagerPersistenceException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>createDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType's id is positive.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateDigitalRunPointsReferenceType_WithIdPositive() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setId(2);
        try {
            impl.createDigitalRunPointsReferenceType(pointsReferenceType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method, expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setCreationDate(new Date());
        pointsReferenceType.setModificationDate(new Date());
        pointsReferenceType.setDescription("description");
        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        pointsReferenceType.setDescription("new description");
        impl.updateDigitalRunPointsReferenceType(pointsReferenceType);

        DigitalRunPointsReferenceType result = impl.getDigitalRunPointsReferenceType(pointsReferenceType
                .getId());
        assertEquals("The description should be same as ", "new description", result.getDescription());

        impl.removeDigitalRunPointsReferenceType(result.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is null.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsReferenceType_WithNull() throws Exception {
        try {
            impl.updateDigitalRunPointsReferenceType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>updateDigitalRunPointsReferenceType(DigitalRunPointsReferenceType pointsReferenceType)</code>
     * method with the pointsReferenceType is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testUpdateDigitalRunPointsReferenceType_WithNotExistpointsReferenceType() throws Exception {
        JPADigitalRunPointsReferenceTypeDAOTest.em.SetExceptionFlag(true);
        try {
            impl.updateDigitalRunPointsReferenceType(new DigitalRunPointsReferenceType());
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method,
     * expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setDescription("description");

        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        assertNotNull("The result should not be null.", impl
                .getDigitalRunPointsReferenceType(pointsReferenceType.getId()));

        impl.removeDigitalRunPointsReferenceType(pointsReferenceType.getId());

        try {
            impl.getDigitalRunPointsReferenceType(pointsReferenceType.getId());
            fail("The result should be not exist");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with
     * the pointsReferenceTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsReferenceType_WithNegativeId() throws Exception {
        try {
            impl.removeDigitalRunPointsReferenceType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>removeDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with
     * the pointsReferenceTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testRemoveDigitalRunPointsReferenceType_WithNotExistId() throws Exception {
        try {
            impl.removeDigitalRunPointsReferenceType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the
     * <code>getDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method, expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsReferenceType_Accuracy() throws Exception {
        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setDescription("description");

        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        DigitalRunPointsReferenceType result = impl.getDigitalRunPointsReferenceType(pointsReferenceType
                .getId());

        assertEquals("The description should be same as ", "description", result.getDescription());

        impl.removeDigitalRunPointsReferenceType(pointsReferenceType.getId());
    }

    /**
     * <p>
     * Failure test for the
     * <code>getDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with the
     * pointsReferenceTypeId is negative.<br>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsReferenceType_WithNegativeId() throws Exception {
        try {
            impl.getDigitalRunPointsReferenceType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the
     * <code>getDigitalRunPointsReferenceType(long pointsReferenceTypeId)</code> method with the
     * pointsReferenceTypeId is not exist.<br>
     * EntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetDigitalRunPointsReferenceType_WithNotExistId() throws Exception {
        try {
            impl.getDigitalRunPointsReferenceType(3);
            fail("EntityNotFoundException should be thrown.");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getAllDigitalRunPointsReferenceTypes()</code> method, expects
     * no error occurs.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testGetAllDigitalRunPointsReferenceTypes_Accuracy() throws Exception {
        List<DigitalRunPointsReferenceType> result = impl.getAllDigitalRunPointsReferenceTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 0, result.size());

        DigitalRunPointsReferenceType pointsReferenceType = new DigitalRunPointsReferenceType();
        pointsReferenceType.setDescription("description");
        impl.createDigitalRunPointsReferenceType(pointsReferenceType);

        result = impl.getAllDigitalRunPointsReferenceTypes();
        assertNotNull("The result should not be null.", result);
        assertEquals("The list should be empty. ", 1, result.size());
    }

}
