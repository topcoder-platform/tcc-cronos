/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import com.topcoder.management.team.InvalidPositionException;
import com.topcoder.management.team.InvalidTeamException;
import com.topcoder.management.team.PositionRemovalException;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.UnknownEntityException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implementation of TeamManager.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockTeamManager implements TeamManager {

    /**
     * @see com.topcoder.management.team.TeamManager#addPosition(long,
     *      com.topcoder.management.team.TeamPosition, long)
     */
    public void addPosition(long arg0, TeamPosition arg1, long arg2) throws InvalidPositionException {
    }

    /**
     * @see com.topcoder.management.team.TeamManager#createTeam(com.topcoder.management.team.TeamHeader, long)
     */
    public void createTeam(TeamHeader arg0, long arg1) throws InvalidTeamException {
    }

    /**
     * @see com.topcoder.management.team.TeamManager#findPositions(com.topcoder.search.builder.filter.Filter)
     */
    public TeamPosition[] findPositions(Filter arg0) {
        return new TeamPosition[] {new TeamPosition()};
    }

    /**
     * @see com.topcoder.management.team.TeamManager#findTeams(long)
     */
    public TeamHeader[] findTeams(long arg0) {
        return null;
    }

    /**
     * @see com.topcoder.management.team.TeamManager#findTeams(long[])
     */
    public TeamHeader[] findTeams(long[] arg0) {
        return null;
    }

    /**
     * @see com.topcoder.management.team.TeamManager#findTeams(com.topcoder.search.builder.filter.Filter)
     */
    public TeamHeader[] findTeams(Filter arg0) {
        return null;
    }

    /**
     * @see com.topcoder.management.team.TeamManager#getPosition(long)
     */
    public TeamPosition getPosition(long arg0) {
        return null;
    }

    /**
     * @see com.topcoder.management.team.TeamManager#getTeam(long)
     */
    public Team getTeam(long arg0) {
        return null;
    }

    /**
     * @see com.topcoder.management.team.TeamManager#getTeamFromPosition(long)
     */
    public Team getTeamFromPosition(long arg0) {
        Team team = new Team() {

            /**
             * <p>
             * Represents the TeamHeader.
             * </p>
             */
            private TeamHeader teamHeader;

            /**
             * <p>
             * Represents the TeamPositions.
             * </p>
             */
            private TeamPosition[] positions;

            
            /**
             * @see com.topcoder.management.team.Team#setTeamHeader(com.topcoder.management.team.TeamHeader)
             */
            public void setTeamHeader(TeamHeader teamHeader) {
                this.teamHeader = teamHeader;
            }

            /**
             * @see com.topcoder.management.team.Team#setPositions(com.topcoder.management.team.TeamPosition[])
             */
            public void setPositions(TeamPosition[] positions) {
                this.positions = positions;
            }

            /**
             * @see com.topcoder.management.team.Team#getTeamHeader()
             */
            public TeamHeader getTeamHeader() {
                return teamHeader;
            }

            /**
             * @see com.topcoder.management.team.Team#getPositions()
             */
            public TeamPosition[] getPositions() {
                return positions;
            }

        };
        TeamHeader header = new TeamHeader();
        header.setProjectId(101);
        header.setName("Review Team");
        header.setDescription("Accuracy tests");
        team.setTeamHeader(header);
        return team;
    }

    /**
     * @see com.topcoder.management.team.TeamManager#removePosition(long, long)
     */
    public void removePosition(long arg0, long arg1) throws UnknownEntityException, PositionRemovalException {
    }

    /**
     * @see com.topcoder.management.team.TeamManager#removeTeam(long, long)
     */
    public void removeTeam(long arg0, long arg1) throws UnknownEntityException {
    }

    /**
     * @see com.topcoder.management.team.TeamManager#updatePosition(com.topcoder.management.team.TeamPosition,
     *      long)
     */
    public void updatePosition(TeamPosition arg0, long arg1) throws InvalidPositionException {
    }

    /**
     * @see com.topcoder.management.team.TeamManager#updateTeam(com.topcoder.management.team.TeamHeader, long)
     */
    public void updateTeam(TeamHeader arg0, long arg1) throws InvalidTeamException {
    }

}
