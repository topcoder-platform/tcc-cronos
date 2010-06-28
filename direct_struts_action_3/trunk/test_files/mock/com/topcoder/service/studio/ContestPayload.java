/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * <p>
 * It is the DTO class which is used to transfer contest type configuration
 * data. The information can be null or can be empty, therefore this check is
 * not present in the setters. the ContestPayload is related with a
 * configuration parameter (Config class). It can only be retrieved.
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
@XmlType(name = "contestPayload", propOrder = { "name", "value", "required", "description", "contestTypeId" })
public class ContestPayload implements Serializable {
    /**
     * <p>
     * Represents the name.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the value.
     * </p>
     */
    private String value;

    /**
     * <p>
     * Represents the required.
     * </p>
     */
    private boolean required = false;

    /**
     * <p>
     * Represents description.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Represents contest type id.
     * </p>
     */
    private long contestTypeId;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public ContestPayload() {
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
     * Return the value.
     * </p>
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>
     * Set the value.
     * </p>
     *
     * @param value the value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>
     * Return the required.
     * </p>
     *
     * @return the required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * <p>
     * Set the required.
     * </p>
     *
     * @param required the required to set.
     */
    public void setRequired(boolean required) {
        this.required = required;
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
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return contestTypeId.
     *
     * @return the contestTypeId
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Sets contestTypeId.
     *
     * @param contestTypeId the contestTypeId to set
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }
}
