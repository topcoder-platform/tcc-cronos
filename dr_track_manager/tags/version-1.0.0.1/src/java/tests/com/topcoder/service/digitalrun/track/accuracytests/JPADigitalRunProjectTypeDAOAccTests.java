/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunProjectTypeDAO;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

import junit.framework.TestCase;

import java.util.List;

import javax.ejb.SessionContext;

import javax.persistence.EntityManager;


/**
 * The accuracy test for the class {@link JPADigitalRunProjectTypeDAO}.
 *
 * @author KLW
 * @version 1.0
 */
public class JPADigitalRunProjectTypeDAOAccTests extends TestCase {
    /**
     * The JPADigitalRunProjectTypeDAO instance for test.
     */
    private JPADigitalRunProjectTypeDAO dao;

    /**
     * The EntityManager for test.
     */
    private EntityManager manager;

    /**
     * The ProjectType for test.
     */
    private ProjectType type1;

    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void setUp() throws Exception {
        dao = new JPADigitalRunProjectTypeDAO();

        Log logger = LogManager.getLog();
        dao.setLogger(logger);

        SessionContext context = new MockSessionContext();
        manager = (EntityManager) context.lookup("entitManager");
        dao.setSessionContext(new MockSessionContext());
        manager.getTransaction().begin();
        type1 = new ProjectType();
        type1.setId(10);
        type1.setCreationUser("creationUser");
        type1.setDescription("description");
        type1.setModificationUser("modificationUser");
        type1.setName("name");
        manager.persist(type1);
    }

    /**
     * tear down the test environment.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    protected void tearDown() throws Exception {
        manager.remove(type1);
        manager.getTransaction().commit();
        dao = null;
    }

    /**
     * the accuracy test for the constructor.
     */
    public void testCtor() {
        assertNotNull("The instance should not be null.", dao);
    }

    /**
     * The accuracy test for the method getProjectType.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getProjectType() throws Exception {
        ProjectType result = dao.getProjectType(type1.getId());
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", type1.getId(), result.getId());
        assertEquals("The result is incorrect.", type1.getCreationDate(), result.getCreationDate());
        assertEquals("The result is incorrect.", type1.getCreationUser(), result.getCreationUser());
        assertEquals("The result is incorrect.", type1.getDescription(), result.getDescription());
        assertEquals("The result is incorrect.", type1.getModificationDate(), result.getModificationDate());
    }

    /**
     * The accuracy test for the method getAllProjectTypes.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void test_getAllProjectTypes() throws Exception {
        List<ProjectType> list = dao.getAllProjectTypes();
        assertEquals("The size of list is incorrect.", 1, list.size());

        ProjectType result = list.get(0);
        assertNotNull("The result should not be null.", result);
        assertEquals("The result is incorrect.", type1.getId(), result.getId());
        assertEquals("The result is incorrect.", type1.getCreationDate(), result.getCreationDate());
        assertEquals("The result is incorrect.", type1.getCreationUser(), result.getCreationUser());
        assertEquals("The result is incorrect.", type1.getDescription(), result.getDescription());
        assertEquals("The result is incorrect.", type1.getModificationDate(), result.getModificationDate());
    }
}
