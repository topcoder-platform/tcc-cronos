/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>BaseContestDTO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseContestDTOTest {
    /**
     * <p>
     * Represents the <code>BaseContestDTO</code> instance used to test against.
     * </p>
     */
    private BaseContestDTO impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseContestDTOTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ActiveContestDTO();
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
     * Inheritance test, verifies <code>BaseContestDTO</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Object);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseContestDTO()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getType()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetType() {
        assertNull("The initial value should be null ", impl.getType());

        String expect = "test";

        impl.setType(expect);

        assertEquals("The return value should be same as ", expect, impl.getType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setType(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetType() {
        assertNull("The initial value should be null ", impl.getType());

        String expect = "test";

        impl.setType(expect);

        assertEquals("The return value should be same as ", expect, impl.getType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubType()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubType() {
        assertNull("The initial value should be null ", impl.getSubType());

        String expect = "test";

        impl.setSubType(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubType(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubType() {
        assertNull("The initial value should be null ", impl.getSubType());

        String expect = "test";

        impl.setSubType(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCatalog()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCatalog() {
        assertNull("The initial value should be null ", impl.getCatalog());

        String expect = "test";

        impl.setCatalog(expect);

        assertEquals("The return value should be same as ", expect, impl.getCatalog());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCatalog(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCatalog() {
        assertNull("The initial value should be null ", impl.getCatalog());

        String expect = "test";

        impl.setCatalog(expect);

        assertEquals("The return value should be same as ", expect, impl.getCatalog());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestName()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestName() {
        assertNull("The initial value should be null ", impl.getContestName());

        String expect = "test";

        impl.setContestName(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestName(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestName() {
        assertNull("The initial value should be null ", impl.getContestName());

        String expect = "test";

        impl.setContestName(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getNumberOfSubmissions()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetNumberOfSubmissions() {
        assertEquals("The initial value should be 0", 0, impl.getNumberOfSubmissions());

        int expect = 56;

        impl.setNumberOfSubmissions(expect);

        assertEquals("The return value should be same as ", expect, impl.getNumberOfSubmissions());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setNumberOfSubmissions(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetNumberOfSubmissions() {
        assertEquals("The initial value should be 0", 0, impl.getNumberOfSubmissions());

        int expect = 56;

        impl.setNumberOfSubmissions(expect);

        assertEquals("The return value should be same as ", expect, impl.getNumberOfSubmissions());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getNumberOfRegistrants()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetNumberOfRegistrants() {
        assertEquals("The initial value should be 0", 0, impl.getNumberOfRegistrants());

        int expect = 56;

        impl.setNumberOfRegistrants(expect);

        assertEquals("The return value should be same as ", expect, impl.getNumberOfRegistrants());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setNumberOfRegistrants(int)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetNumberOfRegistrants() {
        assertEquals("The initial value should be 0", 0, impl.getNumberOfRegistrants());

        int expect = 56;

        impl.setNumberOfRegistrants(expect);

        assertEquals("The return value should be same as ", expect, impl.getNumberOfRegistrants());
    }
}
