/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.ArrayList;
import java.util.Date;

import com.cronos.onlinereview.ajax.TestHelper;

/**
 * A mock subclass for <code>Project</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProject extends Project {

    /**
     * The phases.
     */
    private ArrayList list = new ArrayList();

    /**
     * Represents the id.
     */
    private long id;

    /**
     * Represents the start date.
     */
    private Date startDate;

    /**
     * Set the id of the project.
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Set the start date.
     * @param startDate the start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the id.
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Get the start date.
     * @return start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Return all the phases.
     *
     * @return all the phases.
     */
    public Phase[] getAllPhases() {
        return (Phase[]) list.toArray(new Phase[0]);
    }

    /**
     * Add phase.
     * @param phase the phase
     */
    public void addPhase(Phase phase) {
        list.add(phase);
    }
}
