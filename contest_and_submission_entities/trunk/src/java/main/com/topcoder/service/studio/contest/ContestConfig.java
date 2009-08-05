/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_config</i>.
 * </p>
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class ContestConfig implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -859939008179387964L;

    /**
     * Represents the composite identifier for this entity.
     */
    private Identifier id;

    /**
     * Represents the configuration value.
     */
    private String value;

    /**
     * Default constructor.
     */
    public ContestConfig() {
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
     * Returns the value.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Updates the value with the specified value.
     *
     * @param value
     *            the value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestConfig) {
            Identifier id2 = ((ContestConfig) obj).getId();
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
     * @return a hash code for this {@code ContestConfig}
     */
    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

    /**
     * Composite identifier for a ContestConfig.
     *
     * @since 1.1
     */
    public static class Identifier implements Serializable {
        /**
         * Generated serial version id.
         */
        private static final long serialVersionUID = -1512528048724458809L;

        /**
         * Contest to which this configuration belongs.
         */
        private Contest contest;

        /**
         * Property being configured.
         */
        private ContestProperty property;

        /**
         * Returns the contest.
         *
         * @return the contest.
         */
        public Contest getContest() {
            return contest;
        }

        /**
         * Updates the contest with the specified value.
         *
         * @param contest
         *            the contest to set.
         */
        public void setContest(Contest contest) {
            this.contest = contest;
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

                Long contestId1 = null;
                if (getContest() != null) {
                    contestId1 = getContest().getContestId();
                }
                long propertyId1 = -1;
                if (getProperty() != null) {
                    propertyId1 = getProperty().getPropertyId();
                }

                Identifier id = (Identifier) obj;

                Long contestId2 = null;
                if (id.getContest() != null) {
                    contestId2 = id.getContest().getContestId();
                }
                long propertyId2 = -1;
                if (id.getProperty() != null) {
                    propertyId2 = id.getProperty().getPropertyId();
                }

                return (contestId1 == null ? contestId2 == null : contestId1.equals(contestId2))
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
            Long contestId = null;
            long propertyId = -1;
            if (contest != null && contest.getContestId() != null) {
                contestId = contest.getContestId();
            }

            if (property != null) {
                propertyId = property.getPropertyId();
            }
            return (contestId + "-" + propertyId).hashCode();
        }

    }

}
