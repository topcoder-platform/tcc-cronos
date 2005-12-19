/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */

package com.topcoder.buildutility.template;


/**
 * <p>An exception to be thrown by the <code>TemplateHierarchy</code> to indicate that an attempt to add an object
 * (template/template hierarchy) with existing ID is made. This exception will provide an ID of duplicate object.</p>
 *
 * @author isv
 * @author TCSDEVELOPER
 *
 * @version 1.0
 */
public class DuplicateObjectException extends RuntimeException {

    /**
     * <p>A <code>long</code> providing the ID of duplicated object.</p>
     */
    private long id = 0;

    /**
     * <p>Constructs a new <code>DuplicateObjectException</code> with the given message and the ID of
     * duplicate object.</p>
     *
     * @param message a <code>String</code> providing the descriptive message.
     * @param id ID of the duplicated object.
     */
    public DuplicateObjectException(String message, long id) {
        super(message);
        this.id = id;
    }

    /**
     * <p>Gets the ID of duplicated object.</p>
     *
     * @return a <code>long</code> providing the ID of duplicated object.
     */
    public long getId() {
        return id;
    }
}
