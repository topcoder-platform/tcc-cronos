/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for {@link ContributionServiceEntityNotFoundException} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ContributionServiceEntityNotFoundExceptionUnitTests {
    /**
     * <p>
     * Represents a detail message.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * Represents an error cause.
     * </p>
     */
    private static final Throwable CAUSE = new Exception("UnitTests");

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    /**
     * <p>
     * Represents the entity type name.
     * </p>
     */
    private static final String  ENTITY_TYPE_NAME = "entity_type_name";

    /**
     * <p>
     * Represents the entity id.
     * </p>
     */
    private static final long  ENTITY_ID = 1;

    /**
     * <p>
     * Initializes the exception data.
     * </p>
     */
    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContributionServiceEntityNotFoundExceptionUnitTests.class);
    }

    /**
     * <p>
     * <code>ContributionServiceEntityNotFoundException</code> should be subclass of
     * <code>ContributionServiceException</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("ContributionServiceEntityNotFoundException should be subclass of ContributionServiceException.",
            ContributionServiceEntityNotFoundException.class.getSuperclass() == ContributionServiceException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>ContributionServiceEntityNotFoundException(String, String, long)</code> constructor.<br>
     * The detail error message, entity type name and entity ID should be properly set.
     * </p>
     */
    @Test
    public void testCtor1() {
        ContributionServiceEntityNotFoundException exception =
            new ContributionServiceEntityNotFoundException(DETAIL_MESSAGE, ENTITY_TYPE_NAME, ENTITY_ID);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());

        // Verify the entity type name
        assertEquals("Entity type name should be correct.", ENTITY_TYPE_NAME, exception.getEntityTypeName());
        // Verify the entity id
        assertEquals("Entity id should be correct.", ENTITY_ID, exception.getEntityId());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContributionServiceEntityNotFoundException(String, Throwable, String, long)</code>
     * constructor.<br>
     * The detail error message, the original cause of error, entity type name and entity ID should be properly set.
     * </p>
     */
    @Test
    public void testCtor2() {
        ContributionServiceEntityNotFoundException exception =
            new ContributionServiceEntityNotFoundException(DETAIL_MESSAGE, CAUSE, ENTITY_TYPE_NAME, ENTITY_ID);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());

        // Verify the entity type name
        assertEquals("Entity type name should be correct.", ENTITY_TYPE_NAME, exception.getEntityTypeName());
        // Verify the entity id
        assertEquals("Entity id should be correct.", ENTITY_ID, exception.getEntityId());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContributionServiceEntityNotFoundException(String, ExceptionData, String, long)</code>
     * constructor.<br>
     * The detail error message, the exception data, entity type name and entity ID should be properly set.
     * </p>
     */
    @Test
    public void testCtor3() {
        ContributionServiceEntityNotFoundException exception =
            new ContributionServiceEntityNotFoundException(DETAIL_MESSAGE, EXCEPTION_DATA, ENTITY_TYPE_NAME, ENTITY_ID);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("Application code should not null.", exception.getApplicationCode());
        assertEquals("Exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify the entity type name
        assertEquals("Entity type name should be correct.", ENTITY_TYPE_NAME, exception.getEntityTypeName());
        // Verify the entity id
        assertEquals("Entity id should be correct.", ENTITY_ID, exception.getEntityId());
    }

    /**
     * <p>
     * Tests accuracy of <code>ContributionServiceEntityNotFoundException(String, Throwable, ExceptionData,
     * String, long)</code> constructor.<br>
     * The detail error message, the cause exception, the exception data, entity type name and entity ID should
     * be properly set.
     * </p>
     */
    @Test
    public void testCtor4() {
        ContributionServiceEntityNotFoundException exception = new ContributionServiceEntityNotFoundException(
            DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA, ENTITY_TYPE_NAME, ENTITY_ID);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("Application code should not null.", exception.getApplicationCode());
        assertEquals("Exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());

        // Verify the entity type name
        assertEquals("Entity type name should be correct.", ENTITY_TYPE_NAME, exception.getEntityTypeName());
        // Verify the entity id
        assertEquals("Entity id should be correct.", ENTITY_ID, exception.getEntityId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getEntityTypeName()</code> method.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_getEntityTypeName() {
        ContributionServiceEntityNotFoundException exception =
            new ContributionServiceEntityNotFoundException(DETAIL_MESSAGE, ENTITY_TYPE_NAME, ENTITY_ID);

        assertEquals("'getEntityTypeName' should be correct.", ENTITY_TYPE_NAME, exception.getEntityTypeName());
    }

    /**
     * <p>
     * Tests accuracy of <code>getEntityId()</code> method.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_getEntityId() {
        ContributionServiceEntityNotFoundException exception =
            new ContributionServiceEntityNotFoundException(DETAIL_MESSAGE, ENTITY_TYPE_NAME, ENTITY_ID);

        assertEquals("'getEntityId' should be correct.", ENTITY_ID, exception.getEntityId());
    }
}
