/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>config</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestConfig implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6941184025378017040L;

    /**
     * Represents the contest config id. 
     */
    private long contestConfigId;

    /**
     * Represents the contest.
     */
    private Contest contest;

    /**
     * Represents the config value.
     */
    private String value;

    /**
     * Represents the property.
     */
    private ContestProperty property;

    /**
     * Default constructor.
     */
    public ContestConfig() {
        // empty
    }

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
            ContestConfig config = (ContestConfig) obj;
            return (contest != null && getContest().equals(config.getContest()))
                && (property != null && getProperty().equals(config.getProperty()));
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code Config}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contest != null ? contest.getContestId() : null,
                property != null ? property.getPropertyId() : null);
    }

    /**
     * Returns the contestConfigId.
     *
     * @return the contestConfigId.
     */
    public long getContestConfigId() {
        return contestConfigId;
    }

    /**
     * Updates the contestConfigId with the specified value.
     *
     * @param contestConfigId
     *            the contestConfigId to set.
     */
    public void setContestConfigId(long contestConfigId) {
        this.contestConfigId = contestConfigId;
    }
    
}
