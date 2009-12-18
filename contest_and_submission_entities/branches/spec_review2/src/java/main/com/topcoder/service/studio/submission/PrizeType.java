/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;

import com.topcoder.service.studio.contest.Helper;

/**
 * <p>
 * Represents the entity class for db table <i>prize_type_lu</i>.
 * </p>
 *
 * <p>
 * Currently three possible types are CONTEST and CLIENT_SELECTION AND MILESTONE.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Studio Multi-Rounds Assembly - Launch Contest): Added id attribute
 * with corresponding getter and setter. Changed milestoneDate type to XMLGregorianCalendar.
 * 'XmlType' was also updated to include this new field.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag, pulky
 * @version 1.1
 */
public class PrizeType implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 8305610240926339L;

    /**
     * Constant describing CONTEST prize type.
     *
     * @since 1.1
     */
    public static final long CONTEST = 1;

    /**
     * Constant describing CLIENT_SELECTION prize type.
     *
     * @since 1.1
     */
    public static final long CLIENT_SELECTION = 2;

    /**
     * Constant describing MILESTONE prize type.
     *
     * @since 1.1
     */
    public static final long MILESTONE = 3;

    /**
     * Represents the entity id.
     */
    private Long prizeTypeId;

    /**
     * Represents the description of prize type.
     */
    private String description;

    /**
     * Default constructor.
     */
    public PrizeType() {
        // empty
    }

    /**
     * Returns the prizeTypeId.
     *
     * @return the prizeTypeId.
     */
    public Long getPrizeTypeId() {
        return prizeTypeId;
    }

    /**
     * Updates the prizeTypeId with the specified value.
     *
     * @param prizeTypeId
     *            the prizeTypeId to set.
     */
    public void setPrizeTypeId(Long prizeTypeId) {
        this.prizeTypeId = prizeTypeId;
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
        if (obj instanceof PrizeType) {
            return getPrizeTypeId() == ((PrizeType) obj).getPrizeTypeId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code PrizeType}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(prizeTypeId);
    }
}
