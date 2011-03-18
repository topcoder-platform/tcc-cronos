/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for {@link ProjectCategoryNotSupportedException} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectCategoryNotSupportedExceptionUnitTests {
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
     * Represents the ID of the project category.
     * </p>
     */
    private static final int PROJECT_CATEGORY_ID = 1;

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
        return new JUnit4TestAdapter(ProjectCategoryNotSupportedExceptionUnitTests.class);
    }

    /**
     * <p>
     * <code>ProjectCategoryNotSupportedException</code> should be subclass of
     * <code>ReliabilityCalculationException</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("ProjectCategoryNotSupportedException should be subclass of ReliabilityCalculationException.",
            ProjectCategoryNotSupportedException.class.getSuperclass() == ReliabilityCalculationException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectCategoryNotSupportedException(String, long)</code> constructor.<br>
     * The detail error message and the ID of the project category should be properly set.
     * </p>
     */
    @Test
    public void testCtor1() {
        ProjectCategoryNotSupportedException exception =
            new ProjectCategoryNotSupportedException(DETAIL_MESSAGE, PROJECT_CATEGORY_ID);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());

        assertEquals("ID of the project category should be identical.",
            PROJECT_CATEGORY_ID, exception.getProjectCategoryId());
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectCategoryNotSupportedException(String, Throwable, long)</code> constructor.<br>
     * The detail error message, the original cause of error and the ID of the project category should be properly set.
     * </p>
     */
    @Test
    public void testCtor2() {
        ProjectCategoryNotSupportedException exception =
            new ProjectCategoryNotSupportedException(DETAIL_MESSAGE, CAUSE, PROJECT_CATEGORY_ID);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());

        assertEquals("ID of the project category should be identical.",
            PROJECT_CATEGORY_ID, exception.getProjectCategoryId());
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectCategoryNotSupportedException(String, ExceptionData, long)</code>
     * constructor.<br>
     * The detail error message, the exception data and the ID of the project category should be properly set.
     * </p>
     */
    @Test
    public void testCtor3() {
        ProjectCategoryNotSupportedException exception =
            new ProjectCategoryNotSupportedException(DETAIL_MESSAGE, EXCEPTION_DATA, PROJECT_CATEGORY_ID);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("Application code should not null.", exception.getApplicationCode());
        assertEquals("Exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        assertEquals("ID of the project category should be identical.",
            PROJECT_CATEGORY_ID, exception.getProjectCategoryId());
    }

    /**
     * <p>
     * Tests accuracy of <code>ProjectCategoryNotSupportedException(String, Throwable, ExceptionData, long)</code>
     * constructor.<br>
     * The detail error message, the cause exception, the exception data and the ID of the project category should be
     * properly set.
     * </p>
     */
    @Test
    public void testCtor4() {
        ProjectCategoryNotSupportedException exception =
            new ProjectCategoryNotSupportedException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA, PROJECT_CATEGORY_ID);

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

        assertEquals("ID of the project category should be identical.",
            PROJECT_CATEGORY_ID, exception.getProjectCategoryId());
    }

    /**
     * <p>
     * Tests accuracy of <code>getProjectCategoryId()</code> method.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_getProjectCategoryId() {
        ProjectCategoryNotSupportedException exception =
            new ProjectCategoryNotSupportedException(DETAIL_MESSAGE, PROJECT_CATEGORY_ID);

        assertEquals("'getProjectCategoryId' should be correct.",
            PROJECT_CATEGORY_ID, exception.getProjectCategoryId());
    }
}
