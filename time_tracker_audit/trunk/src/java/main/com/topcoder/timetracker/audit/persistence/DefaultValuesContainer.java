/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.persistence;

import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.TimeTrackerAuditHelper;

/**
 * <p>
 * This package-level container is provided to allow easy storage of default values for Audit Detail records.
 * </p>
 *
 * <p>
 * At first, it extends the <code>AuditDetail</code> object, defaulting all values to null/-1 as shown on the class
 * diagram. Then, for each member in configuration, the new default value is loaded, and the appropriate setXXX is
 * called. Afterwards, whenever an <code>AuditDetail</code> is loaded from persistence, if any records are null the
 * corresponding setXXX(defaultValues#getXXX()) can be called to replace it with the desired default value.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Similar to <code>AuditDetail</code>, for efficiency this is not threadsafe (does not forbid
 * simultaneous read/writes of members) so it is assumed that application will use it in a threadsafe manner if that
 * is required.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
class DefaultValuesContainer extends AuditDetail {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -6307222015595299238L;

	/**
     * <p>
     * Initializes default values based on configuration parameters.
     * </p>
     *
     * <p>
     * Each member is checked for a configuration parameter of the same name. If one exists, then the config value is
     * read, converted to the right type if required. Then the relevant setXXX is called with the new value to use. If
     * the values do not exist, or are invalid, then simply no setXXX is called, and AuditDetail's default value for
     * that member remains instead.
     * </p>
     *
     * @param namespace Namespace within configuration to load default values from.
     *
     * @throws IllegalArgumentException if the parameter is null or empty
     */
    DefaultValuesContainer(String namespace) {
        TimeTrackerAuditHelper.validateString(namespace, "namespace");

        String id = getDefaultValue(namespace, "id");

        if (id != null) {
            try {
                this.setId(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        this.setNewValue(getDefaultValue(namespace, "newValue"));
        this.setOldValue(getDefaultValue(namespace, "oldValue"));
        this.setColumnName(getDefaultValue(namespace, "columnName"));
    }

    /**
     * <p>
     * Gets the property value from the config manager with given namespace and property name.
     * </p>
     *
     * @param namespace the namespace.
     * @param name the property name.
     *
     * @return the property value with given namespace and property name, or null if any errors happen.
     */
    private String getDefaultValue(String namespace, String name) {
        try {
            return TimeTrackerAuditHelper.getStringPropertyValue(namespace, name, false);
        } catch (AuditConfigurationException e) {
            return null;
        }
    }
}
