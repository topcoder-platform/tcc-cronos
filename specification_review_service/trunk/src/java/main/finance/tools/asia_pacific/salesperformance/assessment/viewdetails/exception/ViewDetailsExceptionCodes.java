/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails.exception;

import finance.tools.asia_pacific.salesperformance.base.exception.BaseExceptionCodes;

/**
 * <p>
 * Defines exception codes used by the exceptions of component.
 * </p>
 *
 * <p>
 * Thread safety: Only constants are defined here, no thread safety issues.
 * </p>
 *
 * @author caru, akinwale
 * @version 1.0
 */
public interface ViewDetailsExceptionCodes extends BaseExceptionCodes {
    /**
     * <p>
     * Exception code for TransactionAssessmentViewDetailsManagerException.
     * </p>
     */
    public final long FMS_WEB_07_ERR_0020 = 700020;

    /**
     * <p>
     * Exception code for TransactionAssessmentViewDetailsConfigurationException.
     * </p>
     */
    public final long FMS_WEB_07_ERR_0021 = 700021;

    /**
     * <p>
     * Exception code for TransactionAssessmentViewDetailsDaoException.
     * </p>
     */
    public final long FMS_WEB_07_ERR_0022 = 700022;
}