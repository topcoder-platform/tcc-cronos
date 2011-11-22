/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.io.Serializable;
import java.util.Date;

/**
 * Project summary data object to hold project data for each status.
 *
 * <p>
 *     Version 1.1 changes: add the property directProjectStatusId into the ProjectSummaryData.
 * </p>
 *
 * <p>
 *     Version 1.2 Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar changes:
 *     - Add property {@link #projectCreationDate}, {@link #customerId} and {@link #customerName}
 * </p>
 *
 * @author BeBetter, GreatKevin
 * @version 1.2
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
     * The direct project status id
     * @since 1.1
     */
    private Long directProjectStatusId;


    /**
     * The project creation date.
     * @since 1.2
     */
    private Date projectCreationDate;

    /**
     * The id of the customer.
     * @since 1.2
     */
    private long customerId;

    /**
     * The name of the customer
     * @since 1.2
     */
    private String customerName;

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

    /**
     * Gets the direct project status id.
     *
     * @return the direct project status id.
     * @since 1.1
     */
    public Long getDirectProjectStatusId() {
        return directProjectStatusId;
    }

    /**
     * Sets the direct project status id.
     *
     * @param directProjectStatusId the direct project status id.
     * @since 1.1
     */
    public void setDirectProjectStatusId(Long directProjectStatusId) {
        this.directProjectStatusId = directProjectStatusId;
    }

    /**
     * Gets the project creation date.
     *
     * @return the project creation date.
     * @since 1.2
     */
    public Date getProjectCreationDate() {
        return projectCreationDate;
    }

    /**
     * Sets the project creation date.
     *
     * @param projectCreationDate the project creation date.
     * @since 1.2
     */
    public void setProjectCreationDate(Date projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    /**
     * Gets the id of the project customer.
     *
     * @return the id of the project customer.
     * @since 1.2
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the id of the project customer.
     *
     * @param customerId the id of the project customer.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the name of the project customer.
     *
     * @return the name of the project customer.
     * @since 1.2
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the project customer.
     *
     * @param customerName the name of the project customer.
     * @since 1.2
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
