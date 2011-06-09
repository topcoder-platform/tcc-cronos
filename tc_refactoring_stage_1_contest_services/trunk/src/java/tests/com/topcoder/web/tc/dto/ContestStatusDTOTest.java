/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>ContestStatusDTO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestStatusDTOTest {
    /**
     * <p>
     * Represents the <code>ContestStatusDTO</code> instance used to test against.
     * </p>
     */
    private ContestStatusDTO impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestStatusDTOTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ContestStatusDTO();
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
     * Inheritance test, verifies <code>ContestStatusDTO</code> subclasses should be correct.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof Object);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ContestStatusDTO()</code>.<br>
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
     * Accuracy test for the method <code>getSubmissionDueDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSubmissionDueDate() {
        assertNull("The initial value should be null ", impl.getSubmissionDueDate());

        Date expect = new Date();

        impl.setSubmissionDueDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionDueDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSubmissionDueDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSubmissionDueDate() {
        assertNull("The initial value should be null ", impl.getSubmissionDueDate());

        Date expect = new Date();

        impl.setSubmissionDueDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getSubmissionDueDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFinalReviewDueDate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetFinalReviewDueDate() {
        assertNull("The initial value should be null ", impl.getFinalReviewDueDate());

        Date expect = new Date();

        impl.setFinalReviewDueDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getFinalReviewDueDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFinalReviewDueDate(Date)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetFinalReviewDueDate() {
        assertNull("The initial value should be null ", impl.getFinalReviewDueDate());

        Date expect = new Date();

        impl.setFinalReviewDueDate(expect);

        assertEquals("The return value should be same as ", expect, impl.getFinalReviewDueDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCurrentPhase()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetCurrentPhase() {
        assertNull("The initial value should be null ", impl.getCurrentPhase());

        String expect = "test";

        impl.setCurrentPhase(expect);

        assertEquals("The return value should be same as ", expect, impl.getCurrentPhase());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCurrentPhase(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetCurrentPhase() {
        assertNull("The initial value should be null ", impl.getCurrentPhase());

        String expect = "test";

        impl.setCurrentPhase(expect);

        assertEquals("The return value should be same as ", expect, impl.getCurrentPhase());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFirstPlaceHandle()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetFirstPlaceHandle() {
        assertNull("The initial value should be null ", impl.getFirstPlaceHandle());

        String expect = "test";

        impl.setFirstPlaceHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getFirstPlaceHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFirstPlaceHandle(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetFirstPlaceHandle() {
        assertNull("The initial value should be null ", impl.getFirstPlaceHandle());

        String expect = "test";

        impl.setFirstPlaceHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getFirstPlaceHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSecondPlaceHandle()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testGetSecondPlaceHandle() {
        assertNull("The initial value should be null ", impl.getSecondPlaceHandle());

        String expect = "test";

        impl.setSecondPlaceHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecondPlaceHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSecondPlaceHandle(String)</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testSetSecondPlaceHandle() {
        assertNull("The initial value should be null ", impl.getSecondPlaceHandle());

        String expect = "test";

        impl.setSecondPlaceHandle(expect);

        assertEquals("The return value should be same as ", expect, impl.getSecondPlaceHandle());
    }
}
