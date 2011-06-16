/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.web.tc.CategoriesManagerException;
import com.topcoder.web.tc.impl.CategoriesManagerImpl;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for CategoriesManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class CategoriesManagerImplFailureTests extends TestCase {
    /**
     * <P>
     * The CategoriesManagerImpl instance for testing.
     * </p>
     */
    private CategoriesManagerImpl instance;

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

        instance = new CategoriesManagerImpl();
        instance.setHibernateTemplate(hibernateTemplate);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CategoriesManagerImplFailureTests.class);
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl#getCatalogs() for failure.
     * Expects CategoriesManagerException.
     * </p>
     */
    public void testGetCatalogs_CategoriesManagerException() {
        try {
            instance.getCatalogs();
            fail("CategoriesManagerException expected.");
        } catch (CategoriesManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl#getContestTypes() for failure.
     * Expects CategoriesManagerException.
     * </p>
     */
    public void testGetContestTypes1_CategoriesManagerException() {
        try {
            instance.getContestTypes();
            fail("CategoriesManagerException expected.");
        } catch (CategoriesManagerException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl#getContestTypes(String) for failure.
     * It tests the case that when category is null and expects IllegalArgumentException.
     * </p>
     * @throws CategoriesManagerException to JUnit
     */
    public void testGetContestTypes2_NullCategory() throws CategoriesManagerException {
        try {
            instance.getContestTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl#getContestTypes(String) for failure.
     * It tests the case that when category is empty and expects IllegalArgumentException.
     * </p>
     * @throws CategoriesManagerException to JUnit
     */
    public void testGetContestTypes2_EmptyCategory() throws CategoriesManagerException {
        try {
            instance.getContestTypes(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl#getContestSubTypes(String) for failure.
     * It tests the case that when type is null and expects IllegalArgumentException.
     * </p>
     * @throws CategoriesManagerException to JUnit
     */
    public void testGetContestSubTypes_NullType() throws CategoriesManagerException {
        try {
            instance.getContestSubTypes(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl#getContestSubTypes(String) for failure.
     * It tests the case that when type is empty and expects IllegalArgumentException.
     * </p>
     * @throws CategoriesManagerException to JUnit
     */
    public void testGetContestSubTypes_EmptyType() throws CategoriesManagerException {
        try {
            instance.getContestSubTypes(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl#getContestSubTypes(String) for failure.
     * Expects CategoriesManagerException.
     * </p>
     */
    public void testGetContestSubTypes_CategoriesManagerException() {
        try {
            instance.getContestSubTypes("type");
            fail("CategoriesManagerException expected.");
        } catch (CategoriesManagerException e) {
            //good
        }
    }
}