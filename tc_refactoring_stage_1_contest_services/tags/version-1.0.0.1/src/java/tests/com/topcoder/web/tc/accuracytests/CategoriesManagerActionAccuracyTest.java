/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.tc.action.CategoriesManagerAction;
import com.topcoder.web.tc.impl.CategoriesManagerImpl;

/**
 * <p>
 * This class contains all Accuracy tests for CategoriesManagerAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class CategoriesManagerActionAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents CategoriesManagerAction instance for testing.
     * </p>
     */
    private CategoriesManagerAction action;

    /**
     * <p>
     * Represents CategoriesManagerImpl instance for testing.
     * </p>
     */
    private CategoriesManagerImpl impl;

    /**
     * <p>
     * Creates TestSuite that aggregates all Accuracy tests for class under test.
     * </p>
     * @return TestSuite that aggregates all Accuracy tests for class under test
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CategoriesManagerActionAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        super.setUp();
        action = new CategoriesManagerAction();
        impl = new CategoriesManagerImpl();
        impl.setHibernateTemplate(getHibernateTemplate());
        action.setCategoriesManager(impl);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        super.tearDown();
        impl = null;
        action = null;
    }

    /**
     * <p>
     * Tests CategoriesManagerAction constructor.
     * </p>
     * <p>
     * CategoriesManagerAction instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("CategoriesManagerAction instance should be created successfully.", impl);
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#retrieveCatalogs()}.
     * </p>
     * <p>
     * All catalogs should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveCatalogs() throws Exception {
        assertEquals("Success result is expected", ActionSupport.SUCCESS, action.retrieveCatalogs());
        List < String > catalogs = action.getCatalogs();
        assertEquals("Catalogs should be retrieved successfully.", 1, catalogs.size());
        assertEquals("Catalog should be retrieved successfully.", "Competitions", catalogs.get(0));
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#retrieveContestTypes()}.
     * </p>
     * <p>
     * All contest types should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveContestTypes1() throws Exception {
        action.setCategory("Competitions");
        assertEquals("Success result is expected", ActionSupport.SUCCESS, action.retrieveContestTypes());
        List < String > contestTypes = action.getContestTypes();
        assertEquals("Contest types should be retrieved successfully.", 1, contestTypes.size());
        assertEquals("Contest should be retrieved successfully.", "Design", contestTypes.get(0));
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#retrieveSubContestTypes(String)}.
     * </p>
     * <p>
     * All contest sub types should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testRetrieveSubContestTypes1() throws Exception {
        action.setType("Design");
        assertEquals("Success result is expected", ActionSupport.SUCCESS, action.retrieveContestSubTypes());
        List < String > contestTypes = action.getContestSubTypes();
        assertEquals("Contest types should be retrieved successfully.", 2, contestTypes.size());
    }
}
