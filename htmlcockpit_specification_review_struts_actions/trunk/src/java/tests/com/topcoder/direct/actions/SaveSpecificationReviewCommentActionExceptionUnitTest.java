/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test cases for <code>SaveSpecificationReviewCommentActionException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SaveSpecificationReviewCommentActionExceptionUnitTest extends TestCase {

    /**
     * The error message used for testing.
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * An exception instance used to create the SaveSpecificationReviewCommentActionException.
     */
    private final Exception cause = new Exception("Exception");

    /**
     * The Exception data used as fixture.
     */
    private final ExceptionData data = new ExceptionData();

    /**
     * Tests the class inheritance to make sure it is correct.
     */
    public void testInheritance() {
        SaveSpecificationReviewCommentActionException instance =
            new SaveSpecificationReviewCommentActionException("");
        TestHelper.assertSuperclass(instance.getClass(), SpecificationReviewActionException.class);
    }

    /**
     * Tests <code>SaveSpecificationReviewCommentActionException(String)</code> with error message. The
     * exception should be constructed correctly with the error message.
     */
    public void testCtorString_Accuracy() {
        SaveSpecificationReviewCommentActionException ex =
            new SaveSpecificationReviewCommentActionException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate SaveSpecificationReviewCommentActionException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("Exception data is incorrect", ex.getApplicationCode());
    }

    /**
     * Tests <code>SaveSpecificationReviewCommentActionException(String, Throwable)</code> with error message
     * and cause. The exception should be constructed correctly with the error message and cause.
     */
    public void testCtorStringThrowable_Accuracy() {
        SaveSpecificationReviewCommentActionException ex =
            new SaveSpecificationReviewCommentActionException(ERROR_MESSAGE, cause);
        assertNotNull("Unable to instantiate SaveSpecificationReviewCommentActionException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertNull("Exception data is incorrect", ex.getApplicationCode());
    }

    /**
     * Tests <code>SaveSpecificationReviewCommentActionException(String, ExceptionData)</code> with error
     * message and data. The exception should be constructed correctly with the error message and data.
     */
    public void testCtorStringExceptionData_Accuracy() {
        data.setApplicationCode("test");
        SaveSpecificationReviewCommentActionException ex =
            new SaveSpecificationReviewCommentActionException(ERROR_MESSAGE, data);

        assertNotNull("Unable to instantiate SaveSpecificationReviewCommentActionException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertNull("The inner exception should match.", ex.getCause());
        assertEquals("Exception data is incorrect", "test", ex.getApplicationCode());
    }

    /**
     * Tests <code>SaveSpecificationReviewCommentActionException(String, Throwable, ExceptionData)</code> with
     * error message, cause and data. The exception should be constructed correctly with the error message,
     * cause and
     * data.
     */
    public void testCtorStringThrowableExceptionData_Accuracy() {
        data.setApplicationCode("test");
        SaveSpecificationReviewCommentActionException ex =
            new SaveSpecificationReviewCommentActionException(ERROR_MESSAGE, cause, data);

        assertNotNull("Unable to instantiate SaveSpecificationReviewCommentActionException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex.getMessage());
        assertEquals("The inner exception should match.", cause, ex.getCause());
        assertEquals("Exception data is incorrect", "test", ex.getApplicationCode());
    }
}
