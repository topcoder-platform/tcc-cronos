/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.notification.Helper;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.NotificationFilterFactory;
import com.topcoder.timetracker.notification.NotificationManager;
import com.topcoder.timetracker.notification.StringMatchType;

import com.topcoder.util.file.fieldconfig.NodeList;
import com.topcoder.util.scheduler.scheduling.ScheduledEnable;

/**
 * <p>
 * NotificationEvent will be executed by the Job. It will parse the notification id from the job name and then invoke
 * NotificationManager to send the notification.
 * </p>
 * <p>
 * The jobname should be of the format "TT_NOTIFICATION_ID".
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is not thread safe since it's mutable.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class NotificationEvent implements ScheduledEnable {
    /**
     * <p>
     * The job in which this event will be executed.
     * </p>
     *
     * <p>
     * it's set in the set method, non-null after set.
     * </p>
     */
    private String jobName;

    /**
     * <p>
     * The NotificationManager is used to send the notification by the notification id
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set.
     * </p>
     */
    private final NotificationManager manager;

    /**
     * <p>
     * A boolean indicating if the even is done.
     * </p>
     *
     * <p>
     * It's changed in the run method.
     * </p>
     */
    private boolean isDone = false;

    /**
     * <p>
     * The cached exception caught in the run method.
     * </p>
     *
     * <p>
     * It's set by the run method.
     * </p>
     */
    private Exception cachedException = null;

    /**
     * <p>
     * A boolean indicating if the even is started
     * </p>
     *
     * <p>
     * It's changed in the run method.
     * </p>
     */
    private boolean isStarted = false;

    /**
     * Create this.manage by new NotificationManager.
     *
     * @throws NotificationConfigurationException if error occurred
     */
    public NotificationEvent() throws NotificationConfigurationException {
        this.manager = new NotificationManager();
    }

    /**
     * Send the notification.
     */
    public void run() {
        synchronized (this) {
            this.isDone = false;
            this.isStarted = true;

            this.cachedException = null;

            if (this.jobName == null) {
                this.cachedException = new IllegalStateException("Job is null.");
            }

            try {
                Filter jobNameFilter =
                    NotificationFilterFactory.createJobNameFilter(this.jobName, StringMatchType.EXACT_MATCH);
                Notification[] notifications = this.manager.searchNotifications(jobNameFilter);

                // defer to the manager to send the notification
                this.manager.sendNotification(notifications[0].getId());
            } catch (Exception e) {
                this.cachedException = e;
            }

            this.isDone = true;
        }
    }

    /**
     * Check if the event is done.
     *
     * @return a boolean indication if the event is done
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * close the task, do nothing.
     */
    public void close() {
        // empty
    }

    /**
     * Get the status of the job. It is deprecated in 2.0, using getRunningStatus instead
     *
     * @return the running status
     */
    public String getStatus() {
        return getRunningStatus();
    }

    /**
     * Return the message data of the job at runtime. Return null always
     *
     * @return Return null always
     */
    public NodeList getMessageData() {
        return null;
    }

    /**
     * Get the running status.
     *
     * @return the running status
     */
    public String getRunningStatus() {
        if (!this.isStarted) {
            return ScheduledEnable.NOT_STARTED;
        } else if (this.isDone && (this.cachedException != null)) {
            return ScheduledEnable.FAILED;
        } else if (this.isDone && (this.cachedException == null)) {
            return ScheduledEnable.SUCCESSFUL;
        } else {
            return ScheduledEnable.RUNNING;
        }
    }

    /**
     * Set the job for this event.
     *
     * @param jobName the job to set
     *
     * @throws IllegalArgumentExceptin if argument is null or empty
     */
    public void setJobName(String jobName) {
        Helper.checkString(jobName, "jobName");

        this.jobName = jobName;
    }

    /**
     * get the job in which this event will be executed, possibly null
     *
     * @return the job in which this event will be executed, possibly null
     */
    public String getJobName() {
        return jobName;
    }
}
