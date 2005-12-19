/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>An exception to be thrown by <code>TemplateLoader</code> to indicate that the unexpected error occurs while
 * reading the configuration properties from the specified configuration namespace and using them for initializing the
 * state of the <code>TemplateLoader</code>. This exception may be (but not limited to) caused by absence of any of
 * required properties, inability to locate the specified class implementing the <code>TemplateHierarchyPersistence
 * </code> interface, negative values of cache timeout and maximum size, etc.</p>
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public class ConfigurationException extends BaseException {

    /**
     * <p>Constructs a new <code>ConfigurationException</code>, with the given message.</p>
     *
     * @param message a <code>String</code> providing the descriptive message.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>Constructs a new <code>ConfigurationException</code>, with the given cause.</p>
     *
     * @param cause <code>Throwable</code> providing the cause of this exception.
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>Constructs a new <code>ConfigurationException</code>, with the given message and cause.</p>
     *
     * @param message a <code>String</code> providing the descriptive message.
     * @param cause <code>Throwable</code> providing the cause of this exception.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
