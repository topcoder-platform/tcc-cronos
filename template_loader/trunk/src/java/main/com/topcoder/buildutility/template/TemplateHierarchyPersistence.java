/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

/**
 * <p>An interface specifying the contract for the pluggable persistent data store which is used by the <code>
 * TemplateLoader</code> to load the requested template hierarchies into memory.</p>
 *
 * <p>The implementation classes are required to provide a public constructor accepting a single <code>Property</code>
 * argument if the implementation class is specified by the "persistence.class" configuration property and the
 * "persistence.config" configuration property is also provided. The implementation classes are required to provide a
 * public constructor taking no arguments if the implementation class is specified by the "persistence.class"
 * configuration property and the "persistence.config" configuration property is not provided.</p>
 *
 * <p>Thread safety: the implementations are required to operate in a thread safe manner.</p>
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public interface TemplateHierarchyPersistence {

    /**
     * <p>Gets the top-level template hierarchy corresponding to specified name.</p>
     *
     * @throws PersistenceException if an unrecoverable error preventing the loading of the details for the
     * requested template hierarchy has occurred
     * @throws UnknownTemplateHierarchyException if the specified top-level template hierarchy is not recognized.
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws IllegalArgumentException if specified argument is an empty <code>String</code> being trimmed.
     *
     * @return a <code>TemplateHierarchy</code> corresponding to specified name.
     * @param name a <code>String</code> specifying the name of the requested top-level template hierarchy.
     */
    public TemplateHierarchy getTemplateHierarchy(String name) throws PersistenceException;
}


