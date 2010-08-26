/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the {@link TransactionAssessmentViewDetailsManagerException} class.
 * </p>
 *
 * @author akinwale
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsManagerExceptionTests extends TestCase {
    /**
     * <p>
     * Represents the inner exception to be used for testing.
     * </p>
     */
    private static final Exception EX = new Exception();

    /**
     * <p>
     * Represents the class name to be used for testing.
     * </p>
     */
    private static final String CLASS_NAME = "ClassName";

    /**
     * <p>
     * Represents the method name to be used for testing.
     * </p>
     */
    private static final String METHOD_NAME = "MethodName";

    /**
     * <p>
     * Represents the parameters value to be used for testing.
     * </p>
     */
    private static final String PARAMS = "params";

    /**
     * <p>
     * The {@link TransactionAssessmentViewDetailsManagerException} instance to be tested.
     * </p>
     */
    private TransactionAssessmentViewDetailsManagerException exception;

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManagerException(String[],
     * String, Exception, String, String) constructor creates a new instance that is not
     * null.
     * </p>
     */
    public void testCtor1() {
        exception = new TransactionAssessmentViewDetailsManagerException(
            new String[] {"param1", "param2"}, CLASS_NAME, EX, METHOD_NAME, PARAMS);
        assertNotNull("exception should not be null", exception);

        String error = "exception not properly initialized";
        assertEquals(error, ViewDetailsExceptionCodes.FMS_WEB_07_ERR_0020, exception.getExceptionId());
        assertEquals(error, 2, exception.getExceptionMessageParameters().length);
        assertEquals(error, "param1", exception.getExceptionMessageParameters()[0]);
        assertEquals(error, "param2", exception.getExceptionMessageParameters()[1]);
        assertEquals(error, CLASS_NAME, exception.getClassName());
        assertEquals(error, EX, exception.getException());
        assertEquals(error, METHOD_NAME, exception.getMethodName());
        assertEquals(error, PARAMS, exception.getParameters());
    }

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManagerException(String,
     * String, Exception, String, String) constructor creates a new instance that is not
     * null.
     * </p>
     */
    public void testCtor2() {
        exception = new TransactionAssessmentViewDetailsManagerException(
            "param3", CLASS_NAME, EX, METHOD_NAME, PARAMS);
        assertNotNull("exception should not be null", exception);

        String error = "exception not properly initialized";
        assertEquals(error, ViewDetailsExceptionCodes.FMS_WEB_07_ERR_0020, exception.getExceptionId());
        assertEquals(error, 1, exception.getExceptionMessageParameters().length);
        assertEquals(error, "param3", exception.getExceptionMessageParameters()[0]);
        assertEquals(error, CLASS_NAME, exception.getClassName());
        assertEquals(error, EX, exception.getException());
        assertEquals(error, METHOD_NAME, exception.getMethodName());
        assertEquals(error, PARAMS, exception.getParameters());
    }

    /**
     * <p>
     * Tests that the TransactionAssessmentViewDetailsManagerException(String, Exception,
     * String, String) constructor creates a new instance that is not null.
     * </p>
     */
    public void testCtor3() {
        exception = new TransactionAssessmentViewDetailsManagerException(
            CLASS_NAME, EX, METHOD_NAME, PARAMS);
        assertNotNull("exception should not be null", exception);

        String error = "exception not properly initialized";
        assertEquals(error, ViewDetailsExceptionCodes.FMS_WEB_07_ERR_0020, exception.getExceptionId());
        assertEquals(error, CLASS_NAME, exception.getClassName());
        assertEquals(error, EX, exception.getException());
        assertEquals(error, METHOD_NAME, exception.getMethodName());
        assertEquals(error, PARAMS, exception.getParameters());
    }
}
