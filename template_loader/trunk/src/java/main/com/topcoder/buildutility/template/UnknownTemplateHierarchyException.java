/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>An exception to be thrown to indicate that the requested top-level template hierarchy could not be located (does
 * not exist). Actually, this exception is thrown by the <code>TemplateHierarchyPersistence</code> and is propagated to
 * the clients by the <code>TemplateLoader</code>.</p>
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public class UnknownTemplateHierarchyException extends BaseRuntimeException {

    /**
     * <p>A <code>String</code> providing the name of requested template hierarchy which could not be located. This
     * instance field is initialized within the constructor and is accessed with the corresponding getter method.</p>
     */
    private String hierarchyName = null;

    /**
     * <p>Constructs a new <code>UnknownTemplateHierarchyException</code> with the specified name of template
     * hierarchy. This constructor initializes the superclass with a message indicating that the specified template
     * hierarchy could not be located.</p>
     *
     * @param templateHierarchyName a <code>String</code> providing the name of requested template hierarchy
     * which could not be located.
     */
    public UnknownTemplateHierarchyException(String templateHierarchyName) {
        this.hierarchyName = templateHierarchyName;
    }

    /**
     * <p>Gets the name of template hierarchy which could not be successfully located.</p>
     *
     * @return a <code>String</code> providing the name of requested template hierarchy which could not be located.
     */
    public String getHierarchyName() {
        return hierarchyName;
    }
}
