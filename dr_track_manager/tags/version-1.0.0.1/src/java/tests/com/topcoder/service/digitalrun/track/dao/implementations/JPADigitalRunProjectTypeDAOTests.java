/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;

import com.topcoder.util.log.LogManager;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import org.hibernate.ejb.Ejb3Configuration;

import javax.ejb.SessionContext;

import javax.persistence.EntityManagerFactory;


/**
 * <p>
 * Unit test cases for the class <code>JPADigitalRunProjectTypeDAO.</code>
 * </p>
 * @author waits
 * @version 1.0
 */
public class JPADigitalRunProjectTypeDAOTests extends BaseTestCase {
    /** JPADigitalRunProjectTypeDAO instance to test against. */
    private static JPADigitalRunProjectTypeDAO dao = null;

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(JPADigitalRunProjectTypeDAOTests.class);

        /**
         * <p>
         * Setup the unit test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * Create instance.
                 */
                protected void setUp() {
                    dao = new JPADigitalRunProjectTypeDAO();

                    // get the EntityManager
                    Ejb3Configuration cfg = new Ejb3Configuration();
                    EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
                    manager = emf.createEntityManager();
                    context = EasyMock.createMock(SessionContext.class);
                    dao.setSessionContext(context);
                    dao.setUnitName("unitName");
                    dao.setLogger(LogManager.getLog());
                }

                /**
                 * <p>
                 * Tear down the test.
                 * </p>
                 */
                @Override
                protected void tearDown() throws Exception {
                    //to remove all
                }
            };

        return wrapper;
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunProjectTypeDAO#getProjectType(long)} method.It is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(3);
        EasyMock.replay(context);

        //persist
        ProjectType projectType = createProjectType(10L);
        manager.persist(projectType);

        //retrieve
        projectType = dao.getProjectType(10L);
        manager.getTransaction().commit();

        //verify
        assertEquals("The name is invalid.", "description", projectType.getDescription());
        manager.getTransaction().begin();
        //remove
        dao.remove(ProjectType.class, projectType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllProjectTypes().size());
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunProjectTypeDAO#getProjectType(long)} method.the id does not exist.
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType_notExistEntity() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(1);
        EasyMock.replay(context);

        //retrieve
        try {
            dao.getProjectType(100);
            fail("The ProjectType id does not exist.");
        } catch (EntityNotFoundException iae) {
            //good
        }

        manager.getTransaction().commit();

        //verify
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunProjectTypeDAO#getProjectType(long)} method.Fail to retrieve entity manager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType_error() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        //retrieve
        try {
            dao.getProjectType(100);
            fail("Fail to retrieve the entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();

        //verify
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunProjectTypeDAO#getProjectType(long)} method. The id is negative.
     * IllegalArgumentException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType_negativeId() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.replay(context);

        //retrieve
        try {
            dao.getProjectType(-1L);
            fail("The id is negative.");
        } catch (IllegalArgumentException iae) {
            //good
        }

        manager.getTransaction().commit();
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunProjectTypeDAO#getAllProjectTypes()} method.Fail to retrieve entity manager,
     * DigitalRunTrackManagerPersistenceException expected.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetAllProjectTypes_error() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(null).times(1);
        EasyMock.replay(context);

        //retrieve
        try {
            dao.getAllProjectTypes();
            fail("Fail to retrieve the entity manager.");
        } catch (DigitalRunTrackManagerPersistenceException iae) {
            //good
        }

        manager.getTransaction().commit();

        //verify
        EasyMock.verify(context);
    }

    /**
     * <p>
     * Test the {@link JPADigitalRunProjectTypeDAO#getAllProjectTypes()} method.It is an accuracy test case.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetAllProjectTypes() throws Exception {
        manager.getTransaction().begin();
        EasyMock.reset(context);
        dao.setSessionContext(context);
        EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(4);
        EasyMock.replay(context);

        //persist
        ProjectType projectType = createProjectType(10L);
        manager.persist(projectType);

        //verify
        assertEquals("The size of result is invalid.", 1, dao.getAllProjectTypes().size());

        //retrieve
        projectType = dao.getAllProjectTypes().get(0);

        //verify
        assertEquals("The name is invalid.", "description", projectType.getDescription());

        //remove
        dao.remove(ProjectType.class, projectType.getId());
        manager.getTransaction().commit();

        //verify
        assertEquals("The size of result is invalid.", 0, dao.getAllProjectTypes().size());
        EasyMock.verify(context);
    }
}
