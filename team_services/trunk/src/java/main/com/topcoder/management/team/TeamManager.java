package com.topcoder.management.team;

import com.topcoder.search.builder.filter.Filter;

public interface TeamManager {

	/**
	 * Creates the team.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm793c]
	 * @param team The team to be created
	 * @param userId The user Id for logging and auditing purposes
	 * @throws IllegalArgumentException If team is null or userId is negative
	 * @throws InvalidTeamException If the team contains invalid data
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public void createTeam(TeamHeader team, long userId) throws InvalidTeamException;
	    
	/**
	 * Updates the team.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7930]
	 * @param team The team to be updated
	 * @param userId The user Id for logging and auditing purposes
	 * @throws IllegalArgumentException If team is null or userId is negative
	 * @throws InvalidTeamException If the team contains invalid data
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public void updateTeam(TeamHeader team, long userId) throws InvalidTeamException;
	/**
	 * Removes the team and all positions associated with it.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7924]
	 * @param teamId The ID of the team to be removed
	 * @param userId The user Id for logging and auditing purposes
	 * @throws IllegalArgumentException If teamId or userId is negative
	 * @throws UnknownEntityException If teamId is unknown
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public void removeTeam(long teamId, long userId) throws UnknownEntityException;
	/**
	 * Retrieves the team and all associated positions. Returns null if it does not exist.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm791a]
	 * @return Team with the given ID, or null if none found
	 * @param teamId The ID of the team to be retrieved
	 * @throws IllegalArgumentException If teamId is negative
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public Team getTeam(long teamId);
	/**
	 * Finds all teams associated with the project with the given ID. Returns empty array if none found.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm790f]
	 * @return An array of matching TeamHeader, or empty if no matches found
	 * @param projectId The ID of the project whose teams are to be retrieved
	 * @throws IllegalArgumentException If projectId is negative
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public TeamHeader[] findTeams(long projectId);
	/**
	 * Finds all teams associated with the projects with the given IDs. Returns empty array if none found.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm7904]
	 * @return An array of matching TeamHeader, or empty if no matches found
	 * @param projectIds The IDs of the projects whose teams are to be retrieved
	 * @throws IllegalArgumentException If projectIds is null or contains any negative IDs
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public TeamHeader[] findTeams(long[] projectIds);
	/**
	 * Finds all teams that match the criteria in the passed filter. Returns empty array if none found.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm78fa]
	 * @return An array of matching TeamHeader, or empty if no matches found
	 * @param filter The filter criteria to match teams
	 * @throws IllegalArgumentException If filter is null
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public TeamHeader[] findTeams(Filter filter);
	/**
	 * Gets the team, and all its positions, that the position with the given ID belongs to. Returns null if it does not exist.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm78f0]
	 * @return Team that contains the position with the given ID
	 * @param positionId The ID of the position whose team is to be retrieved
	 * @throws IllegalArgumentException If positionId is negative
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public Team getTeamFromPosition(long positionId);
	/**
	 * Adds a position to the existing team with the given teamID
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm78e4]
	 * @param teamId The ID of the team to which the position is to be added
	 * @param position TeamPosition to add to the team with the given teamId
	 * @param userId The user Id for logging and auditing purposes
	 * @throws IllegalArgumentException If position is null, or teamId or userId is negative
	 * @throws InvalidPositionException If the position contains invalid data
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public void addPosition(long teamId, TeamPosition position, long userId) throws InvalidPositionException;
	/**
	 * Updates a position
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm78d7]
	 * @param position TeamPosition to update
	 * @param userId The user Id for logging and auditing purposes
	 * @throws IllegalArgumentException If position is null, userId is negative
	 * @throws InvalidPositionException If the position contains invalid data
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public void updatePosition(TeamPosition position, long userId) throws InvalidPositionException;
	/**
	 * Removes a position
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm78cb]
	 * @param positionId The ID of the position to remove
	 * @param userId The user Id for logging and auditing purposes
	 * @throws IllegalArgumentException If positionId or userId is negative
	 * @throws PositionRemovalException if the position is already published or filled, ot the team is finalized.
	 * @throws UnknownEntityException If positionID is unknown
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public void removePosition(long positionId, long userId) throws UnknownEntityException,PositionRemovalException;
	/**
	 * Retrieves the position with the given ID. Returns null if it does not exist.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm78c0]
	 * @return TeamPosition with the given ID, or null if none found
	 * @param positionId The ID of the position to get
	 * @throws IllegalArgumentException If positionId is negative
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public TeamPosition getPosition(long positionId);
	/**
	 * Finds all positions that match the criteria in the passed filter. Returns empty array if none found.
	 * 
	 * @poseidon-object-id [I5c431f0m111f016c0dcmm78b6]
	 * @return An array of matching TeamPosition, or empty if no matches found
	 * @param filter The filter criteria to match positions
	 * @throws IllegalArgumentException If filter is null
	 * @throws TeamPersistenceException If any unexpected system error occurs
	 */
	    public TeamPosition[] findPositions(Filter filter);
	    
}
