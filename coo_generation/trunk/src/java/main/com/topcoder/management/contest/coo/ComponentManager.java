/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

/**
 * <p>
 * Represents the component manager. A component manager consists of the
 * following methods:
 * <li> to retrieve component based on the given name and version. The Component
 * instance would be fully populated.</li>
 * <li> to add components from the given input filename (it is up to
 * implementation on deciding the input format). </li>
 * </p>
 * <p>
 * It is used by <code>DefaultCOOReportGenerator</code> to retrieve the
 * component information.
 * </p>
 * <p>
 * <strong> Thread safety:</strong> Implementation is not required to be thread
 * safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public interface ComponentManager {
    /**
     * <p>
     * Retrieve the component information from the given component name and
     * version.
     * </p>
     *
     * @param name The component name. must not be null/empty.
     * @param version The component version. must not be null/empty.
     * @return The corresponding component instance. It will be fully populated
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ComponentManagerException if there is error in performing this
     *             method
     */
    public Component retrieveComponent(String name, String version)
        throws ComponentManagerException;

    /**
     * <p>
     * Add the components listed in the given input stream to the list of
     * available components.
     * </p>
     *
     * @param filename The input filename representing the list of components to
     *            be added. It is up to implementation to decide on the input
     *            format. must not be null/empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ComponentManagerException if there is error in performing this
     *             method
     */
    public void addComponents(String filename) throws ComponentManagerException;
}
