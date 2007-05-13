/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.project.ProjectWorker;

/**
 * <p>
 * This class represents a report of a single time entry and all information about it. This class
 * extends ReportEntryBean.
 * </p>
 *
 *
 * <p>
 * <strong>Thread-safety:</strong> this class isn't thread safe because it's a bean and haven't the
 * setter method synchronized.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class TimeEntryReport extends ReportEntryBean {

    /**
	 * Automatically generated unique ID for use qith serialization.
	 */
	private static final long serialVersionUID = 480997046478353848L;

	/**
     * <p>
     * Represents the time entry. It's changeable , can be null and it's null at the beginning.
     * </p>
     */
    private TimeEntry timeEntry;

    /**
     * <p>
     * Represents the time status. It's changeable , can be null and it's null at the beginning.
     * </p>
     */
    private TimeStatus timeStatus;

    /**
     * <p>
     * Represents the task type. It's changeable , can be null and it's null at the beginning.
     * </p>
     */
    private TaskType taskType;

    /**
     * <p>
     * Represents the project worker associate to the entry. It's changeable and initialized to
     * null.
     * </p>
     */
    private ProjectWorker projectWorker;

    /**
     * <p>
     * Constructs a void <code>TimeEntryReport</code>.
     * </p>
     */
    public TimeEntryReport() {
        // empty
    }

    /**
     * <p>
     * Returns the time entry.
     * </p>
     *
     * @return the time entry.
     */
    public TimeEntry getTimeEntry() {
        return this.timeEntry;
    }

    /**
     * <p>
     * Sets the time entry.
     * </p>
     *
     * @param timeEntry the time entry to set
     */
    public void setTimeEntry(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    /**
     * <p>
     * Returns the time status.
     * </p>
     *
     * @return the time status
     */
    public TimeStatus getTimeStatus() {
        return this.timeStatus;
    }

    /**
     * <p>
     * Sets the time status.
     * </p>
     *
     * @param timeStatus the time status
     */
    public void setTimeStatus(TimeStatus timeStatus) {
        this.timeStatus = timeStatus;
    }

    /**
     * <p>
     * Returns the task type.
     * </p>
     *
     * @return the task type
     */
    public TaskType getTaskType() {
        return this.taskType;
    }

    /**
     * <p>
     * Sets the task type.
     * </p>
     *
     * @param taskType the task type to set
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    /**
     * <p>
     * Returns the project worker.
     * </p>
     *
     * @return the project worker.
     */
    public ProjectWorker getProjectWorker() {
        return this.projectWorker;
    }

    /**
     * <p>
     * Sets the project worker.
     * </p>
     *
     * @param projectWorker the project worker to set
     */
    public void setProjectWorker(ProjectWorker projectWorker) {
        this.projectWorker = projectWorker;
    }
}
