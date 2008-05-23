package com.topcoder.web.tc.distance.data;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by <code>MemberDataAccess</code> implementations, when errors occur in the member retrieval
 * methods of the implementations.  The constructors of this exception wrap those of the base class.
 * </p>
 */
public class MemberDataAccessException extends BaseRuntimeException {

    /**
     * Creates a new, empty exception.
     */
    public MemberDataAccessException()
    {
        super();
    }

    /**
     * <p>
     * Creates a new exception containing the given message.
     * </p>
     *
     * @param message The message the exception will contain.
     */
    public MemberDataAccessException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new exception containing the given cause.
     * </p>
     *
     * @param cause The cause of the exception.
    */
    public MemberDataAccessException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Creates a new exception containing the given message and cause.
     * </p>
     *
     * @param message The message the exception will contain.
     * @param cause The cause of the exception.
     */
    public MemberDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new exception containing the given exception data.
     * </p>
     *
     * @param data The additional data to attach to the runtime exception.
     */
    public MemberDataAccessException(ExceptionData data) {
        super(data);
    }

    /**
     * <p>
     * Creates a new exception containing the given message and exception data.
     * </p>
     *
     * @param message The message the exception will contain.
     * @param data The additional data to attach to the runtime exception.
     */
    public MemberDataAccessException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new exception containing the given cause and exception data.
     * </p>
     *
     * @param cause The cause of the exception.
     * @param data The additional data to attach to the runtime exception.
     */
    public MemberDataAccessException(Throwable cause, ExceptionData data) {
        super(cause, data);
    }

    /**
     * <p>
     * Creates a new exception containing the given message, cause and exception data.
     * </p>
     *
     * @param message The message this exception will contain.
     * @param cause The cause of the exception.
     * @param data The additional data to attach to the runtime exception.
     */
    public MemberDataAccessException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
