/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.persistence;

/**
 * <p>
 * This class is the mock class of DefaultValuesContainer for the stress test.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class MockContainer extends DefaultValuesContainer {
    /**
     * <p>
     * Initializes default values based on configuration parameters.
     * </p>
     */
    public MockContainer() {
        super(DefaultValuesContainer.class.getName());
    }
    
    /**
     * <p>
     * Initializes default values with the given namespace.
     * </p>
     * 
     * @param namespace Namespace within configuration to load default values from.
     * @throws IllegalArgumentException if the parameter is null or empty
     */
    public MockContainer(String namespace) {
        super(DefaultValuesContainer.class.getName() + namespace);
    }

    /**
     * <p>
     * Gets the property value from the config manager with given namespace and property name.
     * </p>
     *
     * @param namespace the namespace.
     * @param name the property name.
     * @return the property value with given namespace and property name, or null if any errors happen.
     */
    public String getDefaultValue(String namespace, String name) {
        return getDefaultValue(namespace, name);
    }
}
