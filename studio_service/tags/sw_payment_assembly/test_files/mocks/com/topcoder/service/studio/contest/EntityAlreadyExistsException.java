package com.topcoder.service.studio.contest;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * This exception extends the ContestManagementException, and it is thrown when the entity already exists the
 * persistence, but it's not supposed to be.
 */
public class EntityAlreadyExistsException extends ContestManagementException {
    /**
     * <p>Constructor with the error message. simply call super(message). </p>
     *
     * #Param message - the error message.
     *
     * @param message
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * <p>Constructor with the error message and the inner cause. simply call super(message, cause). </p>
     *
     * #Param message - the error message. cause - the inner exception.
     *
     * @param message
     * @param cause
     */
    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p> Constructs the exception taking both a <code>message</code> and the additional <code>data</code> to attach to the
     * critical exception. Simply call super(message, data)</p>
     *
     * #Param message - the error message. data - The additional data to attach to the exception
     *
     * @param message
     * @param data
     */
    public EntityAlreadyExistsException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>Constructs the exception taking both a <code>message</code> and a <code>cause</code>, as well as the additional
     * <code>data</code>to attach to the critical exception. Simply call super(message, cause, data). </p>
     *
     * #Param message - the error message. cause - the inner exception. data - The additional data to attach to the
     * exception
     *
     * @param message
     * @param cause
     * @param data
     */
    public EntityAlreadyExistsException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
