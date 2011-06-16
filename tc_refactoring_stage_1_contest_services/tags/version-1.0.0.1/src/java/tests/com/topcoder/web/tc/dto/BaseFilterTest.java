/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>BaseFilter</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseFilterTest {
    /**
     * <p>
     * Represents the <code>BaseFilter</code> instance used to test against.
     * </p>
     */
    private BaseFilter impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseFilterTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ActiveContestFilter();
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
     * Inheritance test, verifies <code>BaseFilter</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Filterable);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseFilter()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTypes()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetTypes() {
        assertNull("The initial value should be null ", impl.getTypes());

        List<String> expect = new ArrayList<String>();

        impl.setTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getTypes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTypes(List)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetTypes() {
        assertNull("The initial value should be null ", impl.getTypes());

        List<String> expect = new ArrayList<String>();

        impl.setTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getTypes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubTypes()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubTypes() {
        assertNull("The initial value should be null ", impl.getSubTypes());

        List<String> expect = new ArrayList<String>();

        impl.setSubTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubTypes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubTypes(List)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubTypes() {
        assertNull("The initial value should be null ", impl.getSubTypes());

        List<String> expect = new ArrayList<String>();

        impl.setSubTypes(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubTypes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCatalogs()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCatalogs() {
        assertNull("The initial value should be null ", impl.getCatalogs());

        List<String> expect = new ArrayList<String>();

        impl.setCatalogs(expect);

        assertEquals("The return value should be same as ", expect, impl.getCatalogs());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCatalogs(List)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCatalogs() {
        assertNull("The initial value should be null ", impl.getCatalogs());

        List<String> expect = new ArrayList<String>();

        impl.setCatalogs(expect);

        assertEquals("The return value should be same as ", expect, impl.getCatalogs());
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
     * Accuracy test for the method <code>getRegistrationStartDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetRegistrationStartDate() {
        assertNull("The initial value should be null ", impl.getRegistrationStartDate());

        DateIntervalSpecification expect = new DateIntervalSpecification();

        impl.setRegistrationStartDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getRegistrationStartDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRegistrationStartDate(DateIntervalSpecification)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetRegistrationStartDate() {
        assertNull("The initial value should be null ", impl.getRegistrationStartDate());

        DateIntervalSpecification expect = new DateIntervalSpecification();

        impl.setRegistrationStartDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getRegistrationStartDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSubmissionEndDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubmissionEndDate() {
        assertNull("The initial value should be null ", impl.getSubmissionEndDate());

        DateIntervalSpecification expect = new DateIntervalSpecification();

        impl.setSubmissionEndDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionEndDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionEndDate(DateIntervalSpecification)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubmissionEndDate() {
        assertNull("The initial value should be null ", impl.getSubmissionEndDate());

        DateIntervalSpecification expect = new DateIntervalSpecification();

        impl.setSubmissionEndDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionEndDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContestFinalizationDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetContestFinalizationDate() {
        assertNull("The initial value should be null ", impl.getContestFinalizationDate());

        DateIntervalSpecification expect = new DateIntervalSpecification();

        impl.setContestFinalizationDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestFinalizationDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContestFinalizationDate(DateIntervalSpecification)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetContestFinalizationDate() {
        assertNull("The initial value should be null ", impl.getContestFinalizationDate());

        DateIntervalSpecification expect = new DateIntervalSpecification();

        impl.setContestFinalizationDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getContestFinalizationDate());
    }
}
