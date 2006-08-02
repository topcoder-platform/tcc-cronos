package com.topcoder.management.phase;

import com.topcoder.util.errorhandling.BaseException;

/**
 * This is a custom wrapping exception for any persistence issues coming from implementations.
 *
 */
public class PhaseManagementException extends BaseException {

    /**
     * <p>
     * Creates an instance wit the input params
     * </p>
     *
     *
     * @param msg exception message.
     */
    public PhaseManagementException(String msg) {
        super(msg);
    }

    /** lock-begin */

    /**
     * <p>
     * Creates an instance wit the input params
     * </p>
     *
     *
     * @param msg exception message.
     * @param cause chained cause exception.
     */
    public PhaseManagementException(String msg, Throwable cause) {
        super(msg, cause);
        // your code here
    }
    /** lock-begin */
}
