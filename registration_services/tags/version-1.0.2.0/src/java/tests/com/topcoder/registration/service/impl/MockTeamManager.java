/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.management.team.InvalidPositionException;
import com.topcoder.management.team.InvalidTeamException;
import com.topcoder.management.team.PositionRemovalException;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.UnknownEntityException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of <code>TeamManager</code>.
 * </p>
 * <p>
 * It is just used for test.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class MockTeamManager implements TeamManager {

    /**
     * <p>
     * Creates the team.
     * </p>
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void createTeam(TeamHeader team, long userId) throws InvalidTeamException {
    }

    /**
     * <p>
     * Updates the team.
     * </p>
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void updateTeam(TeamHeader team, long userId) throws InvalidTeamException {
    }

    /**
     * <p>
     * Removes the team and all positions associated with it.
     * </p>
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void removeTeam(long teamId, long userId) throws UnknownEntityException {
    }

    /**
     * <p>
     * Retrieves the team and all associated positions. Returns null if it does not exist.
     * </p>
     * @return Team with the given ID, or null if none found
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public Team getTeam(long teamId) {
        return null;
    }

    /**
     * <p>
     * Finds all teams associated with the project with the given ID. Returns empty array if none
     * found.
     * </p>
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(long projectId) {
        return null;
    }

    /**
     * <p>
     * Finds all teams associated with the projects with the given IDs. Returns empty array if none
     * found.
     * </p>
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        return null;
    }

    /**
     * <p>
     * Finds all teams that match the criteria in the passed filter. Returns empty array if none
     * found.
     * </p>
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param filter
     *            The filter criteria to match teams
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamHeader[] findTeams(Filter filter) {
        return null;
    }

    /**
     * <p>
     * Gets the team, and all its positions, that the position with the given ID belongs to. Returns
     * null if it does not exist.
     * </p>
     * @return Team that contains the position with the given ID
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public Team getTeamFromPosition(long positionId) {
        Team team = new TeamImpl();
        TeamHeader header = new TeamHeader();
        header.setProjectId(1);
        header.setName("Dragon Team");
        header.setDescription("This is a strong team.");
        team.setTeamHeader(header);
        return team;
    }

    /**
     * Adds a position to the existing team with the given teamID
     * @param teamId
     *            The ID of the team to which the position is to be added
     * @param position
     *            TeamPosition to add to the team with the given teamId
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, or teamId or userId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void addPosition(long teamId, TeamPosition position, long userId)
        throws InvalidPositionException {
    }

    /**
     * Updates a position
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void updatePosition(TeamPosition position, long userId) throws InvalidPositionException {
    }

    /**
     * Removes a position
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws PositionRemovalException
     *             if the position is already published or filled, ot the team is finalized.
     * @throws UnknownEntityException
     *             If positionID is unknown
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public void removePosition(long positionId, long userId) throws UnknownEntityException,
        PositionRemovalException {
    }

    /**
     * Retrieves the position with the given ID. Returns null if it does not exist.
     * @return TeamPosition with the given ID, or null if none found
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamPosition getPosition(long positionId) {
        return null;
    }

    /**
     * Finds all positions that match the criteria in the passed filter. Returns empty array if none
     * found.
     * @return An array of matching TeamPosition, or empty if no matches found
     * @param filter
     *            The filter criteria to match positions
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any unexpected system error occurs
     */
    public TeamPosition[] findPositions(Filter filter) {
        return new TeamPosition[] {new TeamPosition()};
    }

    /**
     * <p>
     * This is an implementation of Team.
     * </p>
     */
    private class TeamImpl implements Team {

        /**
         * <p>
         * Represents the TeamHeader.
         * </p>
         */
        private TeamHeader teamHeader = null;

        /**
         * <p>
         * Represents the TeamPositions.
         * </p>
         */
        private TeamPosition[] positions = null;

        /**
         * <p>
         * Gets the team header.
         * </p>
         * @return the team header
         */
        public TeamHeader getTeamHeader() {
            return this.teamHeader;
        }

        /**
         * <p>
         * Sets the team header.
         * </p>
         * @param the
         *            team header
         */
        public void setTeamHeader(TeamHeader teamHeader) {
            this.teamHeader = teamHeader;
        }

        /**
         * <p>
         * Gets the positions.
         * </p>
         * @return array of team positions
         */
        public TeamPosition[] getPositions() {
            return this.positions;
        }

        /**
         * <p>
         * Sets the positions.
         * </p>
         * @return the array of positions
         */
        public void setPositions(TeamPosition[] positions) {
            this.positions = positions;
        }

    }
}
