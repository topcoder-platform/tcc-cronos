/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_specifications</i>.
 * </p>
 * <p>
 * It holds the attributes contest specifications id,colors,fonts,layout and size,etc.
 * </p>
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestSpecifications implements Serializable {

    /**
     * <p>
     * Generated serial version id.
     * </p>
     */
    private static final long serialVersionUID = -6208773926607855405L;

    /**
     * <p>
     * Represents the contest specifications id.
     * </p>
     */
    private Long contestSpecificationsId;

    /**
     * <p>
     * Represents the colors.
     * </p>
     */
    private String colors;

    /**
     * <p>
     * Represents the fonts.
     * </p>
     */
    private String fonts;

    /**
     * <p>
     * Represents the layout and size.
     * </p>
     */
    private String layoutAndSize;

    /**
     * <p>
     * Represents the additional requirements and restrictions.
     * </p>
     */
    private String additionalRequirementsAndRestrictions;

    /**
     * <p>
     * Default no-arg constructor.
     * </p>
     */
    public ContestSpecifications() {
    }

    /**
     * <p>
     * Gets the contest specifications id.
     * </p>
     *
     * @return the contest specifications id.
     */
    public Long getContestSpecificationsId() {
        return contestSpecificationsId;
    }

    /**
     * <p>
     * Sets the contest specifications id.
     * </p>
     *
     * @param contestSpecificationsId
     *            the contest specifications id.
     */
    public void setContestSpecificationsId(Long contestSpecificationsId) {
        this.contestSpecificationsId = contestSpecificationsId;
    }

    /**
     * <p>
     * Gets the colors.
     * </p>
     *
     * @return the colors.
     */
    public String getColors() {
        return colors;
    }

    /**
     * <p>
     * Sets the colors.
     * </p>
     *
     * @param colors
     *            the colors.
     */
    public void setColors(String colors) {
        this.colors = colors;
    }

    /**
     * <p>
     * Gets the fonts.
     * </p>
     *
     * @return the fonts.
     */
    public String getFonts() {
        return fonts;
    }

    /**
     * <p>
     * Sets the fonts.
     * </p>
     *
     * @param fonts
     *            the fonts.
     */
    public void setFonts(String fonts) {
        this.fonts = fonts;
    }

    /**
     * <p>
     * Gets the layout and size.
     * </p>
     *
     * @return the layout and size.
     */
    public String getLayoutAndSize() {
        return layoutAndSize;
    }

    /**
     * <p>
     * Sets the layout and size.
     * </p>
     *
     * @param layoutAndSize
     *            the layout and size.
     */
    public void setLayoutAndSize(String layoutAndSize) {
        this.layoutAndSize = layoutAndSize;
    }

    /**
     * <p>
     * Gets the additional requirements and restrictions.
     * </p>
     *
     * @return the additional requirements and restrictions.
     */
    public String getAdditionalRequirementsAndRestrictions() {
        return additionalRequirementsAndRestrictions;
    }

    /**
     * <p>
     * Sets the additional requirements and restrictions.
     * </p>
     *
     * @param additionalRequirementsAndRestrictions
     *            the additional requirements and restrictions.
     */
    public void setAdditionalRequirementsAndRestrictions(String additionalRequirementsAndRestrictions) {
        this.additionalRequirementsAndRestrictions = additionalRequirementsAndRestrictions;
    }

    /**
     * <p>
     * Compares this object with the passed object for equality. Only the entity id will be compared.
     * </p>
     *
     * @param obj
     *            the obj to compare to this one
     * @return true if this object is equal to the other, false otherwise
     */
    @Override
    public synchronized boolean equals(Object obj) {
        if (obj instanceof ContestSpecifications) {
            return getContestSpecificationsId() == ((ContestSpecifications) obj).getContestSpecificationsId();
        }
        return false;
    }

    /**
     * <p>
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}}
     * method.
     * </p>
     *
     * @return a hash code for this entity
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(contestSpecificationsId);
    }

}
