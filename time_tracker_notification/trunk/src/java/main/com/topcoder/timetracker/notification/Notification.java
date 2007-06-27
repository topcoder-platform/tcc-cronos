/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.timetracker.common.TimeTrackerBean;

import java.util.Date;

/**
 * <p>
 * The notification bean holds the necessary information to send the email.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 1.0
 */
public class Notification extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 5595866255405659827L;

    /**
     * <p>
     * Represents the company id.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private long companyId = -1;

    /**
     * <p>
     * Represents the email subject.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private String subject = null;

    /**
     * <p>
     * Represents the email message.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private String message = null;

    /**
     * <p>
     * Represents the email address.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private String fromAddress = null;

    /**
     * <p>
     * Represents the last time when send the notification.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private Date lastTimeSent = null;

    /**
     * <p>
     * Represents the next time to send the notification.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private Date nextTimeToSend = null;

    /**
     * <p>
     * Represents if the notification is active.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private boolean active = false;

    /**
     * <p>
     * Represents the associated projects for this notification,  The array must contain positive and no duplicate
     * element.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private long[] toProjects = new long[0];

    /**
     * <p>
     * Represents the associated resources for this notification,  The array must contain positive and no duplicate
     * element.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private long[] toResources = new long[0];

    /**
     * <p>
     * Represents the associated clients for this notification,  The array must contain positive and no duplicate
     * element.
     * </p>
     *
     * <p>
     * It's set/accessed in set/get method.
     * </p>
     */
    private long[] toClients = new long[0];

    /**
     * Contains the name of the job (from Job Scheduling component) to use with this notification.
     */
    private String jobName = null;

    /**
     * Create the bean, empty constructor.
     */
    public Notification() {
        // empty constructor
    }

    /**
     * Get the company id.
     *
     * @return he company id
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * Set the company id.
     *
     * @param id the company id
     *
     * @throws IllegalArgumentException if argument is not positive
     */
    public void setCompnayId(long id) {
        // check the argument first.
        Helper.checkPositive(id, "id");

        this.companyId = id;
        this.setChanged(true);
    }

    /**
     * get the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * set the subject.
     *
     * @param subject the subject
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setSubject(String subject) {
        Helper.checkString(subject, "subject");

        this.subject = subject;
        this.setChanged(true);
    }

    /**
     * get the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * set the message.
     *
     * @param msg the message
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setMessage(String msg) {
        Helper.checkString(msg, "msg");

        this.message = msg;
        this.setChanged(true);
    }

    /**
     * get the from address.
     *
     * @return the from address
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * set the from address.
     *
     * @param addr the from address
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setFromAddress(String addr) {
        Helper.checkString(addr, "addr");

        this.fromAddress = addr;
        this.setChanged(true);
    }

    /**
     * get the last time sent.
     *
     * @return the last time sent
     */
    public Date getLastTimeSent() {
        return lastTimeSent;
    }

    /**
     * set the last time sent.
     *
     * @param time the last time sent
     *
     * @throws IllegalArgumentException if argument is null
     */
    public void setLastTimeSent(Date time) {
        Helper.checkNull(time, "time");

        this.lastTimeSent = time;
        this.setChanged(true);
    }

    /**
     * get the next time to sent.
     *
     * @return the next time to send
     */
    public Date getNextTimeToSend() {
        return nextTimeToSend;
    }

    /**
     * the next time to sent.
     *
     * @param time the next time to sent
     *
     * @throws IllegalArgumentException if argument is null
     */
    public void setNextTimeToSend(Date time) {
        Helper.checkNull(time, "time");

        this.nextTimeToSend = time;
        this.setChanged(true);
    }

    /**
     * check if the notification is active.
     *
     * @return a boolean indicating if the notification is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the active.
     *
     * @param active the active status
     */
    public void setActive(boolean active) {
        this.active = active;
        this.setChanged(true);
    }

    /**
     * Get non-null the associated projects.
     *
     * @return non-null the associated projects
     */
    public long[] getToProjects() {
        return (long[]) toProjects.clone();
    }

    /**
     * set non-null the associated projects.
     *
     * @param ids non-null the associated projects
     *
     * @throws IllegalArgumentException if argument is null, contains non-positive or duplicate elements
     */
    public void setToProjects(long[] ids) {
        // check if the argument is an valid array.
        Helper.checkUniqueArray(ids, "ids");

        this.toProjects = (long[]) ids.clone();
        this.setChanged(true);
    }

    /**
     * get non-null the associated clients.
     *
     * @return non-null the associated client
     */
    public long[] getToClients() {
        return (long[]) toClients.clone();
    }

    /**
     * set non-null the associated client.
     *
     * @param ids non-null the associated client
     *
     * @throws IllegalArgumentException if argument is null, contains non-positive or duplicate elements
     */
    public void setToClients(long[] ids) {
        // check if the argument is an valid array.
        Helper.checkUniqueArray(ids, "ids");

        this.toClients = (long[]) ids.clone();
        this.setChanged(true);
    }

    /**
     * Get non-null the associated resources.
     *
     * @return non-null the associated resources
     */
    public long[] getToResources() {
        return (long[]) toResources.clone();
    }

    /**
     * set non-null the associated resources.
     *
     * @param ids non-null the associated resources
     *
     * @throws IllegalArgumentException if argument is null, contains non-positive or duplicate elements
     */
    public void setToResources(long[] ids) {
        // check if the argument is an valid array
        Helper.checkUniqueArray(ids, "ids");

        this.toResources = (long[]) ids.clone();
        this.setChanged(true);
    }

    /**
     * Gets the name of the Job this notification is associated with.
     *
     * @return a string containing the name of the job.
     */
    public String getJobName() {
        return this.jobName;
    }

    /**
     * Sets new value for the name of the Job to associate with this notification.
     *
     * @param jobName
     *            new name of the job.
     * @throws IllegalArgumentException
     *             if parameter <code>jobName</code> is <code>null</code> or empty (trimmed)
     *             string.
     */
    public void setJobName(String jobName) {
        // Validate argument first
        Helper.checkString(jobName, "jobName");

        // Set the value and update changed flag
        this.jobName = jobName;
        this.setChanged(true);
    }
}
