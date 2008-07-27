/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.entity.DigitalRunPointsOperation;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsOperationDAO;

import junit.framework.TestCase;

import java.util.Date;
import java.util.List;


/**
 * The accuracy test for the class {@link JPADigitalRunPointsOperationDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunPointsOperationDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunPointsOperation instance for test.
     */
    private JPADigitalRunPointsOperationDAO dao;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunPointsOperationDAO();
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
     * the accuracy test for the method createDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testcreateDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method updateDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testupdateDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsOperation(entity);
        result = dao.getDigitalRunPointsOperation(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method removeDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testremoveDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        // it is removed.
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method getDigitalRunPointsOperation.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetDigitalRunPointsOperation() throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        // get the result
        DigitalRunPointsOperation result = dao.getDigitalRunPointsOperation(entity.getId());
        assertNotNull("The entity is not null.", result);
        assertEquals("The entity is incorrect.", entity.getCreationDate(), result.getCreationDate());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        entity.setDescription("new test");
        dao.updateDigitalRunPointsOperation(entity);
        result = dao.getDigitalRunPointsOperation(entity.getId());
        assertEquals("The entity is incorrect.", entity.getDescription(), result.getDescription());
        dao.removeDigitalRunPointsOperation(entity.getId());
    }

    /**
     * the accuracy test for the method getAllDigitalRunPointsOperations.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testgetAllDigitalRunPointsOperations()
        throws Exception {
        DigitalRunPointsOperation entity = new DigitalRunPointsOperation();
        setData(entity);
        dao.createDigitalRunPointsOperation(entity);
        assertTrue("The id should be set. (mean safed.)", entity.getId() > 0);
        System.out.println("The DigitalRunPointsOperation id is " + entity.getId());

        // get the result
        List<DigitalRunPointsOperation> list = dao.getAllDigitalRunPointsOperations();
    }
}
