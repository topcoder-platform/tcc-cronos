/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.impl;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;

/**
 * <p>
 * Full implementation of the Team interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTeamImpl implements Team {
    /**
     * team positions.
     */
    private TeamPosition[] teamPositions = null;

    /**
     * Empty constructor.
     */
    public MockTeamImpl() {
    }

    /**
     * Gets the teamHeader field value.
     *
     * @return The teamHeader field value
     */
    public TeamHeader getTeamHeader() {
        return null;
    };

    /**
     * Sets the teamHeader field value.
     *
     * @param teamHeader
     *            The teamHeader field value
     */
    public void setTeamHeader(TeamHeader teamHeader) {
        return;
    };

    /**
     * Gets the positions field value.
     *
     * @return The positions field value
     */
    public TeamPosition[] getPositions() {
        return teamPositions;
    };

    /**
     * Sets the positions field value.
     *
     * @param positions
     *            The positions field value
     */
    public void setPositions(TeamPosition[] positions) {
        teamPositions = (TeamPosition[]) positions.clone();
        return;
    }
}
