/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * <p>
 * It is the DTO class which is used to transfer contest status data. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters. It's the related to the equivalent ContestStatus entity.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestStatusData", propOrder = { "statusId", "name", "description", "allowableNextStatus",
        "displayIcon" })
public class ContestStatusData implements Serializable {
    /**
     * <p>
     * Represents the status Id.
     * </p>
     */
    private long statusId = -1;

    /**
     * <p>
     * Represents the name.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the description.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Represents the allowable next statuses.
     * </p>
     */
    private final List<Long> allowableNextStatus = new ArrayList<Long>();

    /**
     * Represents displayIcon.
     */
    private String displayIcon;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public ContestStatusData() {
    }

    /**
     * <p>
     * Return the statusId.
     * </p>
     *
     * @return the statusId
     */
    public long getStatusId() {
        return statusId;
    }

    /**
     * <p>
     * Set the statusId.
     * </p>
     *
     * @param statusId the statusId to set
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    /**
     * <p>
     * Return the name.
     * </p>
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Set the name.
     * </p>
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Return the description.
     * </p>
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Set the description.
     * </p>
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Return the allowableNextStatus. Make a shallow copy.
     * </p>
     *
     * @return the allowableNextStatus.
     */
    public List<Long> getAllowableNextStatus() {
        return new ArrayList<Long>(allowableNextStatus);
    }

    /**
     * <p>
     * Set the allowableNextStatus. Make a shallow copy.
     * </p>
     *
     * @param allowableNextStatus the allowableNextStatus to set.
     * @throws IllegalArgumentException if the argument is null.
     */
    public void setAllowableNextStatus(List<Long> allowableNextStatus) {
        Util.checkNull("allowableNextStatus", allowableNextStatus);
        this.allowableNextStatus.clear();
        this.allowableNextStatus.addAll(allowableNextStatus);
    }

    /**
     * Returns displayIcon.
     *
     * @return the displayIcon.
     */
    public String getDisplayIcon() {
        return displayIcon;
    }

    /**
     * Sets displayIcon.
     *
     * @param displayIcon the displayIcon to set.
     */
    public void setDisplayIcon(String displayIcon) {
        this.displayIcon = displayIcon;
    }
}
