/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.impl;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamConfigurationException;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.TeamManager;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Full implementation of the TeamManager interface. This implementation makes
 * use of some TopCoder components to perform configuration and its business
 * methods. It makes use of the Config Manager and Object Factory components to
 * hold properties and obtain class instances, respectively. One such class
 * instance is the TeamPersistence, which is used for all actual persistence
 * operations. The ID Generator component is used to generate IDs for new teams
 * and positions, and the Logging Wrapper is used to log most operations in a
 * method, such as entry and exit, calls to other components, etc.
 * </p>
 * <p>
 * All methods are implemented and supported. Also provided are
 * configuration-backed and programmatic constructors. This allows the user to
 * either create all internal supporting objects from configuration, or to
 * simply pass the instanced directly.
 * </p>
 * <p>
 * All teams and positions are validated when they are created and updated. The
 * specific rules are available in the affected methods
 * </p>
 * <p>
 * Thread Safety: This class is immutable but operates on non thread safe
 * objects, thus making it potentially non thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTeamManagerImpl implements TeamManager {

    /**
     * Represents the default namespace used by the default constructor to
     * access configuration info in the construction.
     *
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.management.team.impl.TeamManagerImpl";

    /**
     * Default constructor. Simply delegates to this(DEFAULT_NAMESPACE).
     *
     *
     * @throws TeamConfigurationException
     *             If any configuration error occurs, such as unknown namespace,
     *             or missing required values, or errors while constructing the
     *             persistence or logger.
     */
    public MockTeamManagerImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Namespace constructor. Initializes this instance from configuration info
     * from the Config Manager. It will use the Object Factory to create
     * instance of TeamPersistence, the IDGeneratorFactory to get the
     * IDGenerator, and LogManager to get the Log instance.
     *
     *
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws TeamConfigurationException
     *             If any configuration error occurs, such as unknown namespace,
     *             or missing required values, or errors while constructing the
     *             persistence or logger.
     */
    public MockTeamManagerImpl(String namespace) {
    }

    /**
     * Creates the team.
     *
     *
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     */
    public void createTeam(TeamHeader team, long userId) {
    }

    /**
     * Updates the team.
     *
     *
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     */
    public void updateTeam(TeamHeader team, long userId) {
    }

    /**
     * Removes the team and all positions associated with it.
     *
     *
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     */
    public void removeTeam(long teamId, long userId) {
    }

    /**
     * Retrieves the team and all associated positions. Returns null if it does
     * not exist.
     *
     *
     * @return Team with the given ID, or null if none found
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     */
    public Team getTeam(long teamId) {
        // Mocks the getTeam method
        if (teamId == 1) {
            Team team = new MockTeamImpl();
            TeamPosition position1 = new TeamPosition();
            position1.setFilled(false);
            TeamPosition position2 = new TeamPosition();
            position1.setFilled(true);
            team.setPositions(new TeamPosition[] { position1, position2 });
            return team;
        } else {
            Team team = new MockTeamImpl();
            TeamPosition position1 = new TeamPosition();
            position1.setFilled(false);
            TeamPosition position2 = new TeamPosition();
            position1.setFilled(false);
            team.setPositions(new TeamPosition[] { position1, position2 });
            return team;
        }

    }

    /**
     * Finds all teams associated with the project with the given ID. Returns
     * empty array if none found.
     *
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     */
    public TeamHeader[] findTeams(long projectId) {
        return null;
    }

    /**
     * Finds all teams associated with the projects with the given IDs. Returns
     * empty array if none found.
     *
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     */
    public TeamHeader[] findTeams(long[] projectIds) {
        return null;
    }

    /**
     * Finds all teams that match the criteria in the passed filter. Returns
     * empty array if none found.
     *
     *
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param filter
     *            The filter criteria to match teams
     * @throws IllegalArgumentException
     *             If filter is null
     */
    public TeamHeader[] findTeams(Filter filter) {
        return null;
    }

    /**
     * Gets the team, and all its positions, that the position with the given ID
     * belongs to. Returns null if it does not exist.
     *
     *
     * @return Team that contains the position with the given ID
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     */
    public Team getTeamFromPosition(long positionId) {
        return null;
    }

    /**
     * Adds a position to the existing team with the given teamID
     *
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
     */
    public void addPosition(long teamId, TeamPosition position, long userId) {
    }

    /**
     * Updates a position
     *
     *
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     */
    public void updatePosition(TeamPosition position, long userId) {
    }

    /**
     * Removes a position
     *
     *
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws PositionRemovalException
     *             if the position is already published or filled, ot the team
     *             is finalized.
     */
    public void removePosition(long positionId, long userId) {
    }

    /**
     * Retrieves the position with the given ID. Returns null if it does not
     * exist.
     *
     *
     * @return TeamPosition with the given ID, or null if none found
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     */
    public TeamPosition getPosition(long positionId) {
        return null;
    }

    /**
     * Finds all positions that match the criteria in the passed filter. Returns
     * empty array if none found.
     *
     *
     * @return An array of matching TeamPosition, or empty if no matches found
     * @param filter
     *            The filter criteria to match positions
     * @throws IllegalArgumentException
     *             If filter is null
     */
    public TeamPosition[] findPositions(Filter filter) {
        return null;
    }
}
