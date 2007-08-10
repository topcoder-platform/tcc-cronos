/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Iterator;

/**
 * <p>A simple <code>DTO</code> object providing the details for a single event to be logged.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LogEvent implements Serializable {

    /**
     * <p>An <code>Object</code> providing the ID of a user associated with event.</p>
     */
    private final Object userId;

    /**
     * <p>An <code>int</code> referencing the type of the event.</p>
     */
    private final int eventTypeId;

    /**
     * <p>A <code>Date</code> providing the time when the event has occurred.</p>
     */
    private final Date time;

    /**
     * <p>A <code>String</code> referencing the session ID associated with event.</p>
     */
    private final String sessionId;

    /**
     * <p>A <code>String</code> providing the IP-address of the user.</p>
     */
    private final String ipAddress;

    /**
     * <p>A <code>Map</code> mapping the event parameter names to event parameter values.</p>
     */
    private final Map parameters;

    public LogEvent(Object userId, int eventTypeId, Date time, String sessionId, Map parameters, String ipAddress) {
        if (userId == null) {
            throw new IllegalArgumentException("The parameter [userId] is NULL");
        }
        if (time == null) {
            throw new IllegalArgumentException("The parameter [time] is NULL");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("The parameter [parameters] is NULL");
        }
        if ((sessionId == null) || (sessionId.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [sessionId] is not valid. [" + sessionId + "]");
        }
        if ((ipAddress == null) || (ipAddress.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [ipAddress] is not valid. [" + ipAddress + "]");
        }
        this.userId = userId;
        this.eventTypeId = eventTypeId;
        this.time = time;
        this.sessionId = sessionId;
        this.parameters = parameters;
        this.ipAddress = ipAddress;
    }

    /**
     * <p>Gets the ID of a user associated with event.</p>
     *
     * @return an <code>Object</code> providing the ID of a user associated with event.
     */
    public Object getUserId() {
        return this.userId;
    }

    /**
     * <p>Gets the type of this event to be logged.</p>
     *
     * @return an <code>int</code> referencing the type of the event.
     */
    public int getEventTypeId() {
        return this.eventTypeId;
    }

    /**
     *
     * @return a <code>Date</code> providing the time when the event has occurred.
     */
    public Date getTime() {
        return this.time;
    }

    /**
     *
     * @return a <code>String</code> referencing the session ID associated with event.
     */
    public String getSessionId() {
        return this.sessionId;
    }

    /**
     *
     * @return a <code>Map</code> mapping the event parameter names to event parameter values.
     */
    public Map getParameters() {
        return this.parameters;
    }

    /**
     * 
     * @return a <code>String</code> providing the IP-address of the user.
     */
    public String getIpAddress() {
        return this.ipAddress;
    }


    /**
     * <p>Gets the textual presentation of this event.</p>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[");
        buf.append("time=");
        buf.append(getTime());
        buf.append(",");
        buf.append("user=");
        buf.append(getUserId());
        buf.append(",");
        buf.append("session=");
        buf.append(getSessionId());
        buf.append(",");
        buf.append("eventType=");
        buf.append(getEventTypeId());
        buf.append(",");
        buf.append("params=[");
        boolean paramAdded = false;
        Map params = getParameters();
        Iterator iterator = params.entrySet().iterator();
        Map.Entry entry;
        while (iterator.hasNext()) {
            entry = (Map.Entry) iterator.next();
            if (paramAdded) {
                buf.append(",");
            } else {
                paramAdded = true;
            }
            buf.append(entry.getKey());
            buf.append("={");
            String[] paramValues = (String[]) entry.getValue();
            if (paramValues != null) {
                for (int i = 0; i < paramValues.length; i++) {
                    if (i > 0) {
                        buf.append(",");
                    }
                    buf.append(paramValues[i]);
                }
            } else {
                buf.append("NULL");
            }
            buf.append("}");
        }
        buf.append("]");
        buf.append("]");
        return buf.toString();
    }
}
