/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.user.profile.UserProfile;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>An entity corresponding to a single active HTTP session. Maintains the session ID, the reference to user account
 * associated with the session, the times of session creation and the last request arrival and the total number of
 * requests issued in context of the session.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusSession implements Serializable {

    /**
     * <p>A <code>UserProfile</code> representing the user associated with this session.</p>
     */
    private final UserProfile user;

    /**
     * <p>A <code>String</code> providing the ID of this session.</p>
     */
    private final String sessionId;

    /**
     * <p>A <code>long</code> containing the number of requests issued in context of this session.</p>
     */
    private long requestsCount = 0;

    /**
     * <p>A <code>Date</code> providing the time of creation of this session.</p>
     */
    private final Date creationTime;

    /**
     * <p>A <code>Date</code> providing the time the last request has been sent in context of this session.</p>
     */
    private Date lastAccessedTime = null;

    /**
     * <p>Constructs new <code>OrpheusSession</code> instance representing the specified session created at specified
     * time and associated with the specified user.</p>
     *
     * @param user a <code>UserProfile</code> representing the user associated with this session.
     * @param sessionId a <code>String</code> providing the ID of this session.
     * @param creationTime a <code>Date</code> providing the time of creation of this session.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public OrpheusSession(UserProfile user, String sessionId, Date creationTime) {
        if (user == null) {
            throw new IllegalArgumentException("The parameter [user] is NULL");
        }
        if (sessionId == null) {
            throw new IllegalArgumentException("The parameter [sessionId] is NULL");
        }
        if (creationTime == null) {
            throw new IllegalArgumentException("The parameter [creationTime] is NULL");
        }
        this.user = user;
        this.sessionId = sessionId;
        this.creationTime = (Date) creationTime.clone();
    }

    /**
     * <p>Notifies this session on new request sent in context of this session. Causes the total number requests to
     * increase and the current time to be recorded as the session's last accessed time.</p>
     */
    public synchronized void touch() {
        this.requestsCount++;
        this.lastAccessedTime = new Date();
    }

    /**
     * <p>Gets the details for user account associated with this session.</p>
     *
     * @return a <code>UserProfile</code> representing the user associated with this session.
     */
    public UserProfile getUser() {
        return this.user;
    }

    /**
     * <p>Gets the ID of this session.</p>
     *
     * @return a <code>String</code> providing the ID of this session.
     */
    public String getSessionId() {
        return this.sessionId;
    }

    /**
     * <p>Gets the number of requests sent in context of this session.</p>
     *
     * @return a <code>long</code> containing the number of requests issued in context of this session.
     */
    public synchronized long getRequestsCount() {
        return this.requestsCount;
    }

    /**
     * <p>Gets the time of creation of this session.</p>
     *
     * @return a <code>Date</code> providing the time of creation of this session.
     */
    public Date getCreationTime() {
        return this.creationTime;
    }

    /**
     * <p>Gets the time the last request has been sent in context of this session.</p>
     *
     * @return a <code>Date</code> providing the time the last request has been sent in context of this session.
     */
    public synchronized Date getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    /**
     * <p>Gets the average frequency for the requests issued in context of this session.</p>
     *
     * @return a <code>double</code> providing the average freguency (in seconds) for the requests issued in context of
     *         this session.
     */
    public synchronized double getRequestsAverageFrequency() {
        if (this.requestsCount != 0) {
            if (this.lastAccessedTime != null) {
                long duration = this.lastAccessedTime.getTime() - this.creationTime.getTime();
                return duration * 1.00 / this.requestsCount / 1000.00; 
            }
        }
        return 0;
    }
}
