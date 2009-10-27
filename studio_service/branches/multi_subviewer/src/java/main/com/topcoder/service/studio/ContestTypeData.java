/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * It is the DTO class which is used to transfer contest type data. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters. It's the related to the equivalent ContestType entity.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestTypeData", propOrder = { "contestTypeId", "description", "requirePreviewFile",
        "requirePreviewImage", "config" })
public class ContestTypeData {
    /**
     * Represents contestType id.
     */
    private Long contestTypeId;

    /**
     * Represents description.
     */
    private String description;

    /**
     * Represents requirePreviewFile.
     */
    private boolean requirePreviewFile;

    /**
     * Represents requirePreviewImage.
     */
    private boolean requirePreviewImage;

    /**
     * Represents config.
     */
    private List<ContestPayload> config;

    /**
     * Returns description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns whether preview file is required.
     *
     * @return whether preview file is required.
     */
    public boolean isRequirePreviewFile() {
        return requirePreviewFile;
    }

    /**
     * Sets whether preview file is required.
     *
     * @param requirePreviewFile the requirePreviewFile to set
     */
    public void setRequirePreviewFile(boolean requirePreviewFile) {
        this.requirePreviewFile = requirePreviewFile;
    }

    /**
     * Sets whether preview file is required.
     *
     * @param requirePreviewImage the requirePreviewImage to set
     */
    public void setRequirePreviewImage(boolean requirePreviewImage) {
        this.requirePreviewImage = requirePreviewImage;
    }

    /**
     * Returns whether preview file is required.
     *
     * @return whether preview file is required.
     */
    public boolean isRequirePreviewImage() {
        return requirePreviewImage;
    }

    /**
     * Returns contestTypeId.
     *
     * @return the contestTypeId
     */
    public Long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * Sets contestTypeId.
     *
     * @param contestTypeId the contestTypeId to set
     */
    public void setContestTypeId(Long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * Sets config.
     *
     * @param config the config to set
     * @throws IllegalArgumentException if the argument is null
     */
    public void setConfig(List<ContestPayload> config) {
        Util.checkNull("config", config);
        this.config = new ArrayList<ContestPayload>(config);
    }

    /**
     * Returns contest payloads.
     *
     * @return payloads converted by configs.
     */
    public List<ContestPayload> getConfig() {
        return new ArrayList<ContestPayload>(config);
    }
}
