/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsReferenceType;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsReferenceTypeDAO;


/**
 * The accuracy test for the class {@link JPADigitalRunPointsReferenceTypeDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunPointsReferenceTypeDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunPointsReferenceTypeDAO instance for test.
     */
    private JPADigitalRunPointsReferenceTypeDAO dao;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsReferenceTypeDAO();
        dao.setSessionContext(new MockSessionContext());
        AccuracyTestHelper.clearDatabase();
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        dao = null;
        AccuracyTestHelper.clearDatabase();
    }

    /**
     * the accuracy test for the constructor.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.", dao);
    }

    /**
     * set some data.
     */
    private void setData(BaseEntity entity) {
        entity.setCreationDate(new Date());
        entity.setDescription("description");
        entity.setModificationDate(new Date());
    }

    /**
     * the accuracy test for the method createDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_createDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method updateDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_updateDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method removeDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_removeDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it should be removed.
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method getDigitalRunPointsReferenceType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getDigitalRunPointsReferenceType()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        // get by id.
        DigitalRunPointsReferenceType result = dao.getDigitalRunPointsReferenceType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it should be removed.
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }

    /**
     * the accuracy test for the method getAllDigitalRunPointsReferenceTypes.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getAllDigitalRunPointsReferenceTypes()
        throws Exception {
        DigitalRunPointsReferenceType entity = new DigitalRunPointsReferenceType();
        setData(entity);
        dao.createDigitalRunPointsReferenceType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The new created DigitalRunPointsReferenceType id is " + entity.getId());

        entity.setDescription("new description");
        dao.updateDigitalRunPointsReferenceType(entity);

        List<DigitalRunPointsReferenceType> list = dao.getAllDigitalRunPointsReferenceTypes();
        assertEquals("The size is incorrect.", 1, list.size());

        DigitalRunPointsReferenceType result = list.get(0);
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it should be removed.
        dao.removeDigitalRunPointsReferenceType(entity.getId());
    }
}
