/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Represents the entity class for db table <i>medium_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class Medium implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 3488842007794442721L;

    /**
     * Represents the entity id.
     */
    private Long mediumId;

    /**
     * Represents the description.
     */
    private String description;

    /**
     * Represents the contest which have this document.
     */
    private Set<Contest> contests = new HashSet<Contest>();

    /**
     * Default constructor.
     */
    public Medium() {
        // empty
    }

    /**
     * Returns the mediumId.
     *
     * @return the mediumId.
     */
    public Long getMediumId() {
        return mediumId;
    }

    /**
     * Updates the mediumId with the specified value.
     *
     * @param mediumId
     *            the mediumId to set.
     */
    public void setMediumId(Long mediumId) {
        this.mediumId = mediumId;
    }

    /**
     * Returns the contests.
     *
     * @return the contests.
     */
    public Set<Contest> getContests() {
        return contests;
    }

    /**
     * Updates the contests with the specified value.
     *
     * @param contests
     *            the contests to set.
     */
    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     *
     * @param description
     *            the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
        if (obj instanceof Medium) {
            return getMediumId() == ((Medium) obj).getMediumId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code Document}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(mediumId);
    }
}
