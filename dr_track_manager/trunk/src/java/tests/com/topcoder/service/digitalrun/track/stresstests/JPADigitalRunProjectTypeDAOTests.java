/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.stresstests;

import com.topcoder.service.digitalrun.entity.ProjectType;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunProjectTypeDAO;

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
 * Stress test cases for the class <code>JPADigitalRunProjectTypeDAO.</code>
 * </p>
 * @author telly
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
                    dao.setUnitName("unitName");
                    dao.setLogger(LogManager.getLog());
                    context = EasyMock.createMock(SessionContext.class);
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
     * Test the {@link JPADigitalRunProjectTypeDAO#getProjectType(long)} method.
     * </p>
     *
     * @throws Exception into Junit
     */
    public void testGetProjectType() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            manager.getTransaction().begin();
            EasyMock.reset(context);
            dao.setSessionContext(context);
            EasyMock.expect(context.lookup("unitName")).andReturn(manager).times(2);
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
            manager.remove(manager.find(ProjectType.class, projectType.getId()));
            manager.getTransaction().commit();

            //verify
            assertEquals("The size of result is invalid.", 0, dao.getAllProjectTypes().size());
            EasyMock.verify(context);
        }
        printResult("GetProjectType/getAllProjectTypes", NUMBER);
    }
}
