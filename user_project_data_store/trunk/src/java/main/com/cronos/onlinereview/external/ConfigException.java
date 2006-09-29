/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external;

/**
 * <p>This exception represents a problem with the configuration in the User Project Data Store
 * component.</p>
 * <p>It is thrown by the UserRetrieval and ProjectRetrieval interfaces (and their implementing
 * and base classes.) It can be used with a message, and with or without an underlying cause.</p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public class ConfigException extends UserProjectDataStoreException {

    /**
     * <p>Create this exception with the given message.</p>
     *
     * @param msg The message of this exception. May be null or empty after trim.
     */
    public ConfigException(String msg) {
        super(msg);
    }

    /**
     * <p>Create this exception with the given message and underlying cause.</p>
     *
     * @param msg The message of this exception. May be null or empty after trim.
     * @param cause the underlying cause of the exception.
     */
    public ConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
