/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.rejectreason.RejectReason;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * <p>This is an abstraction of an entry. It is extended from the TimeTrackerBean.</p>
 *  <p>Thread Safety: This class is not thread-safe as it is mutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public abstract class BaseEntry extends TimeTrackerBean {
    /**
     * This is a date/time representation of this entry (i.e. when it is valid/entered) This is
     * initialized/set/get through the appropriate getter/setter it can not be null once set.
     */
    private Date date;

    /**
     * this is a (possibly empty) map of reject reasons. please note that neither keys not values can be
     * null. This is initialized/set/get through the appropriate getter/setter it can not be null once set but can be
     * empty.<p>The only elements allowed in this map's values are of the type RejectReason. The setter will
     * enforce this.</p>
     */
    private Map rejectReasons;

    /**
     * This is a description of this entry. This is initialized/set/get through the appropriate getter/setter
     * it can not be null once set. Cannot be an empty string.
     */
    private String description;

    /**
     * This is a unique company id that identifies this entry (i.e. which company this belongs to) This is
     * initialized/set/get through the appropriate getter/setter. Generally the user should not change this value once
     * it has been set.
     */
    private long companyId;
    
    /**
     * This is a unique client id that identifies this entry (i.e. which company this belongs to) This is
     * initialized/set/get through the appropriate getter/setter. Generally the user should not change this value once
     * it has been set.
     */
    private long clientId;
    
    /**
     * This is a unique project id that identifies this entry (i.e. which company this belongs to) This is
     * initialized/set/get through the appropriate getter/setter. Generally the user should not change this value once
     * it has been set.
     */
    private long projectId;

/**
     * Empty constructor.
     */
    protected BaseEntry() {
        //does nothing
    }

    /**
     * Return the company id for this entry. This is important in identifying the cot off time for a particular
     * entry based on the company settings.
     *
     * @return company id
     */
    public long getCompanyId() {
        return companyId;
    }
    
    /**
     * Return the client id for this entry. This is important in identifying the cot off time for a particular
     * entry based on the client settings.
     *
     * @return client id
     */
    public long getClientId() {
        return clientId;
    }
    
    /**
     * Return the project id for this entry. This is important in identifying the cot off time for a particular
     * entry based on the project settings.
     *
     * @return project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Return the date for this entry.
     *
     * @return entry date, may be null if not set
     */
    public Date getDate() {
        return (date == null) ? null : new Date(date.getTime());
    }

    /**
     * Return the description of this entry.
     *
     * @return description, may be null or empty
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Return the map of reject reasons for this entry. This is used to track the reasons why the entry has
     * been rejected.
     *
     * @return reject reasons (possibly empty), may be null if it's not set
     */
    public Map getRejectReasons() {
        return (this.rejectReasons == null) ? null : new HashMap(this.rejectReasons);
    }

    /**
     * Set the company id for this entry. This is important in identifying the cut off time for a particular
     * entry based on the company settings.
     *
     * @param companyId company id
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
    
    /**
     * Set the client id for this entry. This is important in identifying the cut off time for a particular
     * entry based on the client settings.
     *
     * @param clientId client id
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
    
    /**
     * Set the project id for this entry. This is important in identifying the cut off time for a particular
     * entry based on the project settings.
     *
     * @param projectId project id
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Set the date for this entry. This is used to identify the date of this entry and is used when deciding
     * if cut off time has been reached.
     *
     * @param date entry date
     *
     * @throws IllegalArgumentException if the parameter is null.
     */
    public void setDate(Date date) {
        ParameterCheck.checkNull("date", date);

        this.date = new Date(date.getTime());
    }

    /**
     * Set the description of this entry.
     *
     * @param description description
     *
     * @throws IllegalArgumentException if the parameter is null
     */
    public void setDescription(String description) {
        ParameterCheck.checkNull("description", description);
        this.description = description;
    }

    /**
     * Set the map of reject reasons for this entry.
     *
     * @param rejectReasons reject reasons
     *
     * @throws IllegalArgumentException if the parameter is null or is a map with null entries (either for key or
     *         value) or of the entry (i.e. the value) is not of RejectReason type
     */
    public void setRejectReasons(Map rejectReasons) {
        ParameterCheck.checkNull("rejectReasons", rejectReasons);

        Map tmp = new HashMap(rejectReasons);

        for (Iterator iter = tmp.entrySet().iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();

            if (entry.getKey() == null) {
                throw new IllegalArgumentException("rejectReasons contains null as key");
            }

            //if it's null or not instance of RejectReason, IAE will be thrown
            if (!(entry.getValue() instanceof RejectReason)) {
                throw new IllegalArgumentException("all entry values should be instances of RejectReason");
            }
        }

        this.rejectReasons = tmp;
    }
}
