/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.action.CategoriesManagerAction;
import com.topcoder.web.tc.action.ContestServicesActionException;
import com.topcoder.web.tc.impl.CategoriesManagerImpl;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for CategoriesManagerAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class CategoriesManagerActionFailureTests extends TestCase {
    /**
     * <P>
     * The CategoriesManagerAction instance for testing.
     * </p>
     */
    private CategoriesManagerAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("test_files/failure/ApplicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);

        instance = new CategoriesManagerAction();
        CategoriesManagerImpl categoriesManager = new CategoriesManagerImpl();
        categoriesManager.setHibernateTemplate(hibernateTemplate);
        instance.setCategoriesManager(categoriesManager);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CategoriesManagerActionFailureTests.class);
    }

    /**
     * <p>
     * Tests CategoriesManagerAction#retrieveContestTypes() for failure.
     * Expects ContestServicesActionException.
     * </p>
     */
    public void testRetrieveContestTypes_ContestServicesActionException() {
        try {
            instance.retrieveContestTypes();
            fail("ContestServicesActionException expected.");
        } catch (ContestServicesActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerAction#retrieveContestSubTypes() for failure.
     * Expects ContestServicesActionException.
     * </p>
     */
    public void testRetrieveContestSubTypes_ContestServicesActionException() {
        instance.setType("type");
        try {
            instance.retrieveContestSubTypes();
            fail("ContestServicesActionException expected.");
        } catch (ContestServicesActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerAction#retrieveCatalogs() for failure.
     * Expects ContestServicesActionException.
     * </p>
     */
    public void testRetrieveCatalogs_ContestServicesActionException() {
        try {
            instance.retrieveCatalogs();
            fail("ContestServicesActionException expected.");
        } catch (ContestServicesActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerAction#checkConfiguration() for failure.
     * It tests the case that when categoriesManager is null and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_NullCategoriesManager() {
        try {
            instance.setCategoriesManager(null);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

}