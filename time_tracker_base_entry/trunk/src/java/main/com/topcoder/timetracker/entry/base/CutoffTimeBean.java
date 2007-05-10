/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.timetracker.common.TimeTrackerBean;

import java.util.Date;

/**
 * <p>This is a basic java bean, which encapsulates all the data for a cut_off_time table entity. The purpose of
 * this bean is to model the information that a company will use to decide what a cutoff time is for some entry
 * submission. In other words, when a submission is made on a certain date, we might need to know if the date for this
 * submission has expired (i.e. is beyond the cut off date)</p>
 *  <p>Note: This class is extended from TimeTrackerBean which already is serializable.</p>
 *  <p>Thread-Safety:This class is not thread-safe as it is mutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class CutoffTimeBean extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = -7708443999443388610L;

	/**
     * <p>Cut off time which is the actual cut off time where a company will not accept any more submissions.
     * Cannot be null after being set.</p>
     *  <p>Initialized through a setter and obtained through a getter.</p>
     */
    private Date cutoffTime;

    /**
     * Company id which identifies the company for which this particular cut off record entry is made. In other
     * words the cut off time is set for each company.<p>If =0 means unset. Can not be set after that to
     * anything &gt;= 0. Initialized through a setter and obtained through a getter.</p>
     */
    private long companyId = 0;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public CutoffTimeBean() {
        //does nothing
    }

    /**
     * Get the id that represents the company with which this entry is associated.
     *
     * @return company id
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>Get the cut off time for this bean.</p>
     *
     * @return cut off time, may be null if not set
     */
    public Date getCutoffTime() {
        return (this.cutoffTime == null) ? null : new Date(this.cutoffTime.getTime());
    }

    /**
     * Set the id that represents the company with which this entry is associated.
     *
     * @param companyId company id
     *
     * @throws IllegalArgumentException if the parameter is &lt;=0
     */
    public void setCompanyId(long companyId) {
        ParameterCheck.checkNotPositive("companyId", companyId);
        this.companyId = companyId;
    }

    /**
     * <p>Sets the actual cut off time for this bean.</p>
     *
     * @param cutoffTime cut off time - non null
     *
     * @throws IllegalArgumentException if the parameter is null
     */
    public void setCutoffTime(Date cutoffTime) {
        ParameterCheck.checkNull("cutoffTime", cutoffTime);

        this.cutoffTime = new Date(cutoffTime.getTime());
    }
}
