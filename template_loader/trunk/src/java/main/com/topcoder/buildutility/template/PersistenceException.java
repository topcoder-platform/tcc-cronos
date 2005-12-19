/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>An exception to be thrown by the implementations of <code>TemplateHierarchyPersistence</code> implementations to
 * indicate that an unrecoverable error preventing the loading of the details for the requested template hierarchy has
 * occurred. Actually, this exception is thrown by the <code>TemplateHierarchyPersistence</code> and is propagated to
 * the clients by the <code>TemplateLoader</code>.</p>
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public class PersistenceException extends BaseException {

    /**
     * <p>Constructs a new <code>PersistenceException</code>, with the given message.</p>
     *
     * @param message a <code>String</code> providing the descriptive message.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * <p>Constructs a new <code>PersistenceException</code>, with the given cause.</p>
     *
     * @param cause <code>Throwable</code> providing the cause of this exception.
     */
    public PersistenceException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>Constructs a new <code>PersistenceException</code>, with the given message and cause.</p>
     *
     * @param message a <code>String</code> providing the descriptive message.
     * @param cause <code>Throwable</code> providing the cause of this exception.
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
