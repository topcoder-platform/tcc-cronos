/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_type_config</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestTypeConfig implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6399784187021922758L;

    /**
     * Represents the contest type config id.
     */
    private long contestTypeConfigId;

    /**
     * Represents the property.
     */
    private ContestProperty property;

    /**
     * Represents the contest type.
     */
    private ContestType type;

    /**
     * Represents the property value.
     */
    private String propertyValue;

    /**
     * Represents the required flag.
     */
    private boolean required;

    /**
     * Default Constructor.
     */
    public ContestTypeConfig() {
        // empty
    }

    /**
     * Returns the property.
     *
     * @return the property.
     */
    public ContestProperty getProperty() {
        return property;
    }

    /**
     * Updates the property with the specified value.
     *
     * @param property
     *            the property to set.
     */
    public void setProperty(ContestProperty property) {
        this.property = property;
    }

    /**
     * Returns the type.
     *
     * @return the type.
     */
    public ContestType getType() {
        return type;
    }

    /**
     * Updates the type with the specified value.
     *
     * @param type
     *            the type to set.
     */
    public void setType(ContestType type) {
        this.type = type;
    }

    /**
     * Returns the propertyValue.
     *
     * @return the propertyValue.
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * Updates the propertyValue with the specified value.
     *
     * @param propertyValue
     *            the propertyValue to set.
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    /**
     * Returns the required.
     *
     * @return the required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Updates the required with the specified value.
     *
     * @param required
     *            the required to set.
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Compares this object with the passed object for equality. Only the composite id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestTypeConfig) {
            ContestTypeConfig result = (ContestTypeConfig) obj;
            return (property != null && getProperty().equals(result.getProperty()))
                    && (type != null && getType().equals(result.getType()));
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code ContestTypeConfig}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(property != null ? property.getPropertyId() : null, type != null ? type
                .getContestType() : null);
    }

    /**
     * Returns the contestTypeConfigId.
     *
     * @return the contestTypeConfigId.
     */
    public long getContestTypeConfigId() {
        return contestTypeConfigId;
    }

    /**
     * Updates the contestTypeConfigId with the specified value.
     *
     * @param contestTypeConfigId
     *            the contestTypeConfigId to set.
     */
    public void setContestTypeConfigId(long contestTypeConfigId) {
        this.contestTypeConfigId = contestTypeConfigId;
    }
}
