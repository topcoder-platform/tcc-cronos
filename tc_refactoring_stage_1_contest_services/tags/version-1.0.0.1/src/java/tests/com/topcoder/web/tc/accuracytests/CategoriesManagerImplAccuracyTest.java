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

import com.topcoder.web.tc.impl.CategoriesManagerImpl;

/**
 * <p>
 * This class contains all Accuracy tests for CategoriesManagerImpl.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class CategoriesManagerImplAccuracyTest extends BaseAccuracyTest {

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
        return new JUnit4TestAdapter(CategoriesManagerImplAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        super.setUp();
        impl = new CategoriesManagerImpl();
        impl.setHibernateTemplate(getHibernateTemplate());
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
    }

    /**
     * <p>
     * Tests CategoriesManagerImpl constructor.
     * </p>
     * <p>
     * CategoriesManagerImpl instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("CategoriesManagerImpl instance should be created successfully.", impl);
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#getCatalogs()}.
     * </p>
     * <p>
     * All catalogs should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetCatalogs() throws Exception {
        List < String > catalogs = impl.getCatalogs();
        assertEquals("Catalogs should be retrieved successfully.", 1, catalogs.size());
        assertEquals("Catalog should be retrieved successfully.", "Competitions", catalogs.get(0));
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#getContestTypes()}.
     * </p>
     * <p>
     * All contest types should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestTypes1() throws Exception {
        List < String > contestTypes = impl.getContestTypes();
        assertEquals("Contest types should be retrieved successfully.", 1, contestTypes.size());
        assertEquals("Contest should be retrieved successfully.", "Design", contestTypes.get(0));
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#getContestTypes(String)}.
     * </p>
     * <p>
     * Contest types should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestTypes2() throws Exception {
        List < String > contestTypes = impl.getContestTypes("Competitions");
        assertEquals("Contest types should be retrieved successfully.", 1, contestTypes.size());
        assertEquals("Contest should be retrieved successfully.", "Design", contestTypes.get(0));
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#getContestTypes(String)}.
     * </p>
     * <p>
     * Contest types should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetContestTypes3() throws Exception {
        List < String > contestTypes = impl.getContestTypes("Unknown");
        assertEquals("Contest types should be retrieved successfully.", 0, contestTypes.size());
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#getContestSubTypes(String)}.
     * </p>
     * <p>
     * All contest sub types should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetSubContestTypes1() throws Exception {
        List < String > contestTypes = impl.getContestSubTypes("Design");
        assertEquals("Contest types should be retrieved successfully.", 2, contestTypes.size());
    }

    /**
     * <p>
     * Tests {@link CategoriesManagerImpl#getContestSubTypes(String)}.
     * </p>
     * <p>
     * No contest sub types should be returned.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testGetSubContestTypes2() throws Exception {
        List < String > contestTypes = impl.getContestSubTypes("Unknown");
        assertEquals("Contest types should be retrieved successfully.", 0, contestTypes.size());
    }
}
