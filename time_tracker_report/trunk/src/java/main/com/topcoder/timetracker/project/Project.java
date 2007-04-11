/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.project;

import com.topcoder.timetracker.common.TimeTrackerBean;
import java.util.Date;

/**
 * <p>
 * This class holds the information of a project. No parameter checking is provided in this class,
 * since the class acts as a mock class for Time Tracker Report component. All the information is
 * reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class Project extends TimeTrackerBean {

    /**
     * <p>
     * This is the name of the project.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getName
     * </p>
     * <p>
     * Modified In: setName
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null values, or Strings that are not empty
     * </p>
     */
    private String name;

    /**
     * <p>
     * This is a number that uniquely identifies the company that this project is associated with.
     * This is the company that owns the project. This is used to tie into the Time Tracker Company
     * component.
     * </p>
     * <p>
     * Initial Value: 0 (This indicates an uninitialized value)
     * </p>
     * <p>
     * Accessed In: getCompanyId
     * </p>
     * <p>
     * Modified In: setCompanyId
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Ids that are >= 0.
     * </p>
     */
    private long companyId;

    /**
     * <p>
     * This is the description of a project, which is a human-readable String that briefly describes
     * the project.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getDescription
     * </p>
     * <p>
     * Modified In: setDescription
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Nulls, or Strings that are not empty
     * </p>
     */
    private String description;

    /**
     * <p>
     * This is the date when the project started, or is estimated to start.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getStartDate
     * </p>
     * <p>
     * Modified In: setStartDate
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null values, or a Date object
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * This is the date when the project ended, or is estimated to end.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getEndDate
     * </p>
     * <p>
     * Modified In: setEndDate
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null values, or a Date object
     * </p>
     */
    private Date endDate;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public Project() {
        // empty
    }

    /**
     * <p>
     * Retrieves the name of the project.
     * </p>
     *
     * @return the name of the project.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Sets the name of the project.
     * </p>
     *
     * @param name the name of the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Retrieves a number that uniquely identifies the company that this project is associated with.
     * This is the company that owns the project. This is used to tie into the Time Tracker Company
     * component.
     * </p>
     *
     * @return a number that uniquely identifies the company that this project is associated with.
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>
     * Sets a number that uniquely identifies the company that this project is associated with. This
     * is the company that owns the project. This is used to tie into the Time Tracker Company
     * component.
     * </p>
     *
     * @param companyId a number that uniquely identifies the company that this project is
     *        associated with.
     * @throws IllegalArgumentException if companyId is a negative number or 0.
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * <p>
     * Retrieves the description of the project.
     * </p>
     *
     * @return the description of a project.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets the description of a project.
     * </p>
     *
     * @param description the description of a project.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Retrieves the date when the project started, or is estimated to start.
     * </p>
     *
     * @return the date when the project started, or is estimated to start.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * <p>
     * Sets the date when the project started, or is estimated to start.
     * </p>
     *
     * @param startDate the date when the project started, or is estimated to start.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Retrieves the date when the project ended, or is estimated to end.
     * </p>
     *
     * @return the date when the project ended, or is estimated to end.
     */
    public java.util.Date getEndDate() {
        return this.endDate;
    }

    /**
     * <p>
     * Sets the date when the project ended, or is estimated to end.
     * </p>
     *
     * @param endDate the date when the project ended, or is estimated to end.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
