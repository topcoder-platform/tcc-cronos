/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.io.Serializable;

/**
 * Project summary data object to hold project data for each status.
 *
 * @author BeBetter
 * @version 1.0
 */
public class ProjectSummaryData implements Serializable {
    /**
     * Serial id.
     */
    private static final long serialVersionUID = 5601619496370424382L;

    /**
     * <p>
     * The projectId field.
     * </p>
     */
    private Long projectId;

    /**
     * <p>
     * The projectName field.
     * </p>
     */
    private String projectName;

    /**
     * <p>
     * The draft field.
     * </p>
     */
    private ProjectStatusData draft = new ProjectStatusData();

    /**
     * <p>
     * The scheduled field.
     * </p>
     */
    private ProjectStatusData scheduled = new ProjectStatusData();

    /**
     * <p>
     * The active field.
     * </p>
     */
    private ProjectStatusData active = new ProjectStatusData();

    /**
     * <p>
     * The finished field.
     * </p>
     */
    private ProjectStatusData finished = new ProjectStatusData();

    /**
     * <p>
     * The cancelled field.
     * </p>
     */
    private ProjectStatusData cancelled = new ProjectStatusData();

    /**
     * <p>
     * Sets the <code>projectId</code> field value.
     * </p>
     *
     * @param projectId the value to set
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the <code>projectId</code> field value.
     * </p>
     *
     * @return the <code>projectId</code> field value
     */
    public Long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>
     * Sets the <code>projectName</code> field value.
     * </p>
     *
     * @param projectName the value to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * <p>
     * Gets the <code>projectName</code> field value.
     * </p>
     *
     * @return the <code>projectName</code> field value
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * <p>
     * Sets the <code>draft</code> field value.
     * </p>
     *
     * @param draft the value to set
     */
    public void setDraft(ProjectStatusData draft) {
        this.draft = draft;
    }

    /**
     * <p>
     * Gets the <code>draft</code> field value.
     * </p>
     *
     * @return the <code>draft</code> field value
     */
    public ProjectStatusData getDraft() {
        return this.draft;
    }

    /**
     * <p>
     * Sets the <code>scheduled</code> field value.
     * </p>
     *
     * @param scheduled the value to set
     */
    public void setScheduled(ProjectStatusData scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * <p>
     * Gets the <code>scheduled</code> field value.
     * </p>
     *
     * @return the <code>scheduled</code> field value
     */
    public ProjectStatusData getScheduled() {
        return this.scheduled;
    }

    /**
     * <p>
     * Sets the <code>active</code> field value.
     * </p>
     *
     * @param active the value to set
     */
    public void setActive(ProjectStatusData active) {
        this.active = active;
    }

    /**
     * <p>
     * Gets the <code>active</code> field value.
     * </p>
     *
     * @return the <code>active</code> field value
     */
    public ProjectStatusData getActive() {
        return this.active;
    }

    /**
     * <p>
     * Sets the <code>finished</code> field value.
     * </p>
     *
     * @param finished the value to set
     */
    public void setFinished(ProjectStatusData finished) {
        this.finished = finished;
    }

    /**
     * <p>
     * Gets the <code>finished</code> field value.
     * </p>
     *
     * @return the <code>finished</code> field value
     */
    public ProjectStatusData getFinished() {
        return this.finished;
    }

    /**
     * <p>
     * Sets the <code>cancelled</code> field value.
     * </p>
     *
     * @param cancelled the value to set
     */
    public void setCancelled(ProjectStatusData cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * <p>
     * Gets the <code>cancelled</code> field value.
     * </p>
     *
     * @return the <code>cancelled</code> field value
     */
    public ProjectStatusData getCancelled() {
        return this.cancelled;
    }
}
