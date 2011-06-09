/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.topcoder.web.tc.CategoriesManager;

/**
 * <p>
 * Unit tests for class <code>CategoriesManagerImpl</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CategoriesManagerImplTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>CategoriesManagerImpl</code> instance used to test against.
     * </p>
     */
    private CategoriesManagerImpl impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CategoriesManagerImplTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @BeforeClass
    public static void beforeClass() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("test_files" + File.separator
            + "ApplicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new CategoriesManagerImpl();
        impl.setHibernateTemplate(hibernateTemplate);
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @AfterClass
    public static void afterClass() {
        hibernateTemplate = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>CategoriesManagerImpl</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof HibernateDaoSupport);
        assertTrue("The instance's subclass is not correct.", impl instanceof CategoriesManager);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CategoriesManagerImpl()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCatalogs()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetCatalogs() throws Exception {
        List<String> result = impl.getCatalogs();
        assertNotNull("The return list should not be null ", result);
        assertEquals("The return list's size should be same as ", 6, result.size());

        assertEquals("The return value should be same as ", "ProjectCatalog21", result.get(0));
        assertEquals("The return value should be same as ", "ProjectCatalog22", result.get(1));
        assertEquals("The return value should be same as ", "ProjectCatalog23", result.get(2));
        assertEquals("The return value should be same as ", "ProjectCatalog24", result.get(3));
        assertEquals("The return value should be same as ", "ProjectCatalog25", result.get(4));
        assertEquals("The return value should be same as ", "ProjectCatalog26", result.get(5));

    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestTypes()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetContestTypes() throws Exception {
        List<String> result = impl.getContestTypes();
        assertNotNull("The return list should not be null ", result);
        assertEquals("The return list's size should be same as ", 8, result.size());

        assertEquals("The return value should be same as ", "Type1", result.get(0));
        assertEquals("The return value should be same as ", "Type2", result.get(1));
        assertEquals("The return value should be same as ", "Type3", result.get(2));
        assertEquals("The return value should be same as ", "Type4", result.get(3));
        assertEquals("The return value should be same as ", "Type5", result.get(4));
        assertEquals("The return value should be same as ", "Type6", result.get(5));
        assertEquals("The return value should be same as ", "Type7", result.get(6));
        assertEquals("The return value should be same as ", "Type1", result.get(7));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestTypes(String)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetContestTypesString() throws Exception {
        List<String> result = impl.getContestTypes("ProjectCatalog21");
        assertNotNull("The return list should not be null ", result);
        assertEquals("The return list's size should be same as ", 2, result.size());

        assertEquals("The return value should be same as ", "Type1", result.get(0));
        assertEquals("The return value should be same as ", "Type7", result.get(1));
    }

    /**
     * <p>
     * Failure test for the method <code>getContestTypes(String)</code>.<br>
     * The category is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestTypesString_CategoryNull() throws Exception {
        impl.getContestTypes(null);
    }

    /**
     * <p>
     * Failure test for the method <code>getContestTypes(String)</code>.<br>
     * The category is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestTypesString_CategoryEmpty() throws Exception {
        impl.getContestTypes("");
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestSubTypes(String)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetContestSubTypes() throws Exception {
        List<String> result = impl.getContestSubTypes("Type1");
        assertNotNull("The return list should not be null ", result);
        assertEquals("The return list's size should be same as ", 2, result.size());

        assertEquals("The return value should be same as ", "SubType101", result.get(0));
        assertEquals("The return value should be same as ", "SubType108", result.get(1));
    }

    /**
     * <p>
     * Failure test for the method <code>getContestSubTypes(String)</code>.<br>
     * The type is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestSubTypes_TypeNull() throws Exception {
        impl.getContestSubTypes(null);
    }

    /**
     * <p>
     * Failure test for the method <code>getContestSubTypes(String)</code>.<br>
     * The type is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetContestSubTypes_TypeEmpty() throws Exception {
        impl.getContestSubTypes("   ");
    }

}
