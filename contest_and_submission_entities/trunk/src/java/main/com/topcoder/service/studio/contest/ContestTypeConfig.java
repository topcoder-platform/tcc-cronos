/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_type_config</i>.
 * </p>
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class ContestTypeConfig implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6399784187021922758L;

    /**
     * Represents the composite identifier for this entity.
     */
    private Identifier id;

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
     * Return the identifier.
     *
     * @return the identifier.
     */
    public Identifier getId() {
        return id;
    }

    /**
     * Set the identifier.
     *
     * @param id
     *            the identifier
     */
    public void setId(Identifier id) {
        this.id = id;
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
            Identifier id2 = ((ContestTypeConfig) obj).getId();
            if (id != null) {
                return id.equals(id2);
            } else {
                return id2 == null;
            }
        }

        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}}
     * method.
     *
     * @return a hash code for this {@code ContestTypeConfig}
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    /**
     * Composite identifier for a ContestTypeConfig.
     *
     * @since 1.1
     */
    public static class Identifier implements Serializable {
        /**
         * Generated serial version id.
         */
        private static final long serialVersionUID = -1512528048724458809L;

        /**
         * ContestType to which this configuration belongs.
         */
        private ContestType contestType;

        /**
         * Property being configured.
         */
        private ContestProperty property;

        /**
         * Returns the contestType.
         *
         * @return the contestType.
         */
        public ContestType getContestType() {
            return contestType;
        }

        /**
         * Updates the contestType with the specified value.
         *
         * @param contestType
         *            the contest type to set.
         */
        public void setContestType(ContestType contestType) {
            this.contestType = contestType;
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
         * Override the equals method to be consistent with this class definition.
         *
         * @param obj
         *            object to compare to this
         * @return true if obj is equal to this object
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Identifier) {
                Long contestType1 = null;
                if (getContestType() != null) {
                    contestType1 = getContestType().getContestType();
                }
                long propertyId1 = -1;
                if (getProperty() != null) {
                    propertyId1 = getProperty().getPropertyId();
                }

                Identifier id = (Identifier) obj;
                Long contestType2 = null;
                if (id.getContestType() != null) {
                    contestType2 = id.getContestType().getContestType();
                }
                long propertyId2 = -1;
                if (id.getProperty() != null) {
                    propertyId2 = id.getProperty().getPropertyId();
                }

                return (contestType1 == null ? contestType2 == null : contestType1.equals(contestType2))
                        && (propertyId1 == propertyId2);
            }

            return false;
        }

        /**
         * Override the hashCode method to provide a hash code based on the identifier.
         *
         * @return a hashcode for this identifier
         */
        @Override
        public int hashCode() {
            Long contestType1 = null;
            if (getContestType() != null) {
                contestType1 = getContestType().getContestType();
            }
            long propertyId1 = -1;
            if (getProperty() != null) {
                propertyId1 = getProperty().getPropertyId();
            }

            return (contestType1 + "-" + propertyId1).hashCode();
        }

    }
}
