/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.tc.CategoriesManager;
import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.TestHelper;
import com.topcoder.web.tc.impl.CategoriesManagerImpl;

/**
 * <p>
 * Unit tests for class <code>CategoriesManagerAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CategoriesManagerActionTest {
    /**
     * <p>
     * Represents the <code>HibernateTemplate</code> instance used to test against.
     * </p>
     */
    private static HibernateTemplate hibernateTemplate;

    /**
     * <p>
     * Represents the <code>CategoriesManagerAction</code> instance used to test against.
     * </p>
     */
    private CategoriesManagerAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CategoriesManagerActionTest.class);
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
        impl = new CategoriesManagerAction();
        CategoriesManagerImpl categoriesManagerImpl = new CategoriesManagerImpl();
        categoriesManagerImpl.setHibernateTemplate(hibernateTemplate);
        impl.setCategoriesManager(categoriesManagerImpl);
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
     * Inheritance test, verifies <code>CategoriesManagerAction</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof ActionSupport);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CategoriesManagerAction()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveContestTypes()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestTypes() throws Exception {
        impl.retrieveContestTypes();
        List<String> contestTypes = impl.getContestTypes();
        assertNotNull("The return list should not be null ", contestTypes);
        assertEquals("The return list's size should be same as ", 8, contestTypes.size());

        assertEquals("The return value should be same as ", "Type1", contestTypes.get(0));
        assertEquals("The return value should be same as ", "Type2", contestTypes.get(1));
        assertEquals("The return value should be same as ", "Type3", contestTypes.get(2));
        assertEquals("The return value should be same as ", "Type4", contestTypes.get(3));
        assertEquals("The return value should be same as ", "Type5", contestTypes.get(4));
        assertEquals("The return value should be same as ", "Type6", contestTypes.get(5));
        assertEquals("The return value should be same as ", "Type7", contestTypes.get(6));
        assertEquals("The return value should be same as ", "Type1", contestTypes.get(7));

        impl.setCategory("ProjectCatalog21");
        impl.retrieveContestTypes();
        contestTypes = impl.getContestTypes();
        assertNotNull("The return list should not be null ", contestTypes);
        assertEquals("The return list's size should be same as ", 2, contestTypes.size());

        assertEquals("The return value should be same as ", "Type1", contestTypes.get(0));
        assertEquals("The return value should be same as ", "Type7", contestTypes.get(1));

        impl.setCategory("another");
        impl.retrieveContestTypes();
        contestTypes = impl.getContestTypes();
        assertNotNull("The return list should not be null ", contestTypes);
        assertEquals("The return list's size should be same as ", 0, contestTypes.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveContestSubTypes()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveContestSubTypes() throws Exception {
        String result = impl.retrieveContestSubTypes();
        assertEquals("The return value should be same as ", "input", result);

        impl.setType("Type1");
        impl.retrieveContestSubTypes();
        List<String> contestSubTypes = impl.getContestSubTypes();
        assertNotNull("The return list should not be null ", contestSubTypes);
        assertEquals("The return list's size should be same as ", 2, contestSubTypes.size());

        assertEquals("The return value should be same as ", "SubType101", contestSubTypes.get(0));
        assertEquals("The return value should be same as ", "SubType108", contestSubTypes.get(1));
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveCatalogs()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testRetrieveCatalogs() throws Exception {
        impl.retrieveCatalogs();
        List<String> catalogs = impl.getCatalogs();
        assertNotNull("The return list should not be null ", catalogs);
        assertEquals("The return list's size should be same as ", 6, catalogs.size());

        assertEquals("The return value should be same as ", "ProjectCatalog21", catalogs.get(0));
        assertEquals("The return value should be same as ", "ProjectCatalog22", catalogs.get(1));
        assertEquals("The return value should be same as ", "ProjectCatalog23", catalogs.get(2));
        assertEquals("The return value should be same as ", "ProjectCatalog24", catalogs.get(3));
        assertEquals("The return value should be same as ", "ProjectCatalog25", catalogs.get(4));
        assertEquals("The return value should be same as ", "ProjectCatalog26", catalogs.get(5));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCategory()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCategory() {
        String value = "test";
        impl.setCategory(value);
        assertEquals("The return value should be same as ", value, impl.getCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCategory(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCategory() {
        String value = "test";
        impl.setCategory(value);
        assertEquals("The return value should be same as ", value, impl.getCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getType()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetType() {
        String value = "test";
        impl.setType(value);
        assertEquals("The return value should be same as ", value, impl.getType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setType(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetType() {
        String value = "test";
        impl.setType(value);
        assertEquals("The return value should be same as ", value, impl.getType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestTypes()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestTypes() {
        List<String> res = impl.getContestTypes();
        assertNull("The return value should be null", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestSubTypes()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestSubTypes() {
        List<String> res = impl.getContestSubTypes();
        assertNull("The return value should be null", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCatalogs()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCatalogs() {
        List<String> res = impl.getCatalogs();
        assertNull("The return value should be null", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCategoriesManager(CategoriesManager)</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testSetCategoriesManager() throws Exception {
        CategoriesManagerImpl categoriesManagerImpl = new CategoriesManagerImpl();

        impl.setCategoriesManager(categoriesManagerImpl);

        CategoriesManager result = (CategoriesManager) TestHelper.getField(CategoriesManagerAction.class,
            impl, "categoriesManager");

        assertEquals("The return value should be same as ", categoriesManagerImpl, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkConfiguration()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckConfiguration() {
        impl.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for the method <code>checkConfiguration()</code>.<br>
     * The categoriesManager is null.<br>
     * Expect ContestServicesConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_CategoriesManagerNull() throws Exception {
        impl.setCategoriesManager(null);

        impl.checkConfiguration();
    }

}
