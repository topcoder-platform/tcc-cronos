/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception;

import finance.tools.asia_pacific.salesperformance.base.exception.FMSCriticalException;

/**
 * <p>
 * The exception extends from {@link FMSCriticalException}. It is thrown if any error
 * occurs in configuration.
 * </p>
 *
 * <p>
 * Thread-Safety: The class is not thread safe because the base class is not thread safe.
 * </p>
 *
 * @author caru, akinwale
 * @version 1.0
 */
public class TransactionAssessmentViewDetailsConfigurationException extends FMSCriticalException {
    /**
     * <p>
     * The generated serial version UID.
     * </p>
     */
    private static final long serialVersionUID = -5048984937267135944L;

    /**
     * <p>
     * Constructor. Creates the exception instance with given parameters.
     * </p>
     *
     * @param className
     *            the given className to create the instance
     * @param exception
     *            the given exception detail to create the instance
     * @param methodName
     *            the given methodName to create the instance
     * @param parameters
     *            the given parameters to create the instance.
     */
    public TransactionAssessmentViewDetailsConfigurationException(String className, Exception exception,
        String methodName, String parameters) {
        super(ViewDetailsExceptionCodes.FMS_WEB_07_ERR_0021, className, exception, methodName, parameters);
    }

    /**
     * <p>
     * Constructor. Creates the exception instance with given parameters.
     * </p>
     *
     * @param exceptionMessageParameter
     *            the given exception message parameter to create the instance
     * @param className
     *            the given className to create the instance
     * @param exception
     *            the given exception detail to create the instance
     * @param methodName
     *            the given methodName to create the instance
     * @param parameters
     *            the given parameters to create the instance
     */
    public TransactionAssessmentViewDetailsConfigurationException(String exceptionMessageParameter,
        String className, Exception exception, String methodName, String parameters) {
        super(ViewDetailsExceptionCodes.FMS_WEB_07_ERR_0021, exceptionMessageParameter, className,
            exception, methodName, parameters);
    }

    /**
     * <p>
     * Constructor. Creates the exception instance with given parameters.
     * </p>
     *
     * @param exceptionMessageParameters
     *            the given exception message parameters to create the instance
     * @param className
     *            the given className to create the instance
     * @param exception
     *            the given exception detail to create the instance
     * @param methodName
     *            the given methodName to create the instance
     * @param parameters
     *            the given parameters to create the instance
     */
    public TransactionAssessmentViewDetailsConfigurationException(String[] exceptionMessageParameters,
        String className, Exception exception, String methodName, String parameters) {
        super(ViewDetailsExceptionCodes.FMS_WEB_07_ERR_0021, exceptionMessageParameters, className,
            exception, methodName, parameters);
    }
}
