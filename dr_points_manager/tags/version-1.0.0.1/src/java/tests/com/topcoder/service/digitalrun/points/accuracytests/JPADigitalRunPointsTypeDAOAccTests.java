/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import java.util.Date;
import java.util.List;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsType;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsTypeDAO;

import junit.framework.TestCase;


/**
 * The accuracy test for the class {@link JPADigitalRunPointsTypeDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunPointsTypeDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunPointsTypeDAO instance for test.
     */
    private JPADigitalRunPointsTypeDAO dao;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsTypeDAO();
        dao.setSessionContext(new MockSessionContext());
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        dao = null;
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
     * the accuracy test for the method createDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsType(entity.getId());
    }
    /**
     * the accuracy test for the method updateDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsType(entity);
        result = dao.getDigitalRunPointsType(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsType(entity.getId());
    }

    /**
     * the accuracy test for the method removeDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removeDigitalRunPointsType(entity.getId());
    }

    /**
     * the accuracy test for the method getDigitalRunPointsType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetDigitalRunPointsType() throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        // get the result
        DigitalRunPointsType result = dao.getDigitalRunPointsType(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsType(entity);
        result = dao.getDigitalRunPointsType(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsType(entity.getId());
    }

    /**
     * the accuracy test for the method getAllDigitalRunPointsTypes.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllDigitalRunPointsTypes()
        throws Exception {
        DigitalRunPointsType entity = new DigitalRunPointsType();
        setData(entity);
        dao.createDigitalRunPointsType(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsType id is " + entity.getId());

        // get the result
        // List<DigitalRunPointsType> list = dao.getAllDigitalRunPointsTypes();
    }
}
