/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.offer.Offer;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.ResourcePosition;
import com.topcoder.registration.team.service.TeamServices;
import com.topcoder.registration.team.service.impl.OperationResultImpl;

/**
 * <p>
 * This is a mock implementation of <code>TeamServices</code>.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class MockTeamServices implements TeamServices {

    /**
     * <p>
     * Constructs an instance.
     * </p>
     */
    public MockTeamServices() {

    }

    /**
     * Creates or updates the team.
     * @return OperationResult with the results of the operation
     * @param team
     *            The team header to either create or update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null, or userId is negative
     */
    public OperationResult createOrUpdateTeam(TeamHeader team, long userId) {
        return null;
    };

    /**
     * Gets all teams that are registered for the project with the given ID. Returns empty array if
     * the there are no teams in this project.
     * @return TeamHeader[] of teams in the given project
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     */
    public TeamHeader[] getTeams(long projectId) {
        return null;
    };

    /**
     * Gets the team members, including the captain, in this team. It will return at least one
     * member in the array since a team always has a captain.
     * @return Resource[] of the participants in the team
     * @param teamId
     *            The ID of the team whose participants are to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     */
    public Resource[] getTeamMembers(long teamId) {
        return new Resource[] {new Resource(1), new Resource(2)};
    };

    /**
     * Removes a team and all its positions. It will send a notification message to all team members
     * and captain informing of the team removal.
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     */
    public OperationResult removeTeam(long teamId, long userId) {
        OperationResultImpl result = new OperationResultImpl();
        result.setSuccessful(true);
        return result;
    };

    /**
     * Gets the details, in terms of the position and resource, of each position. If there are no
     * positions yet configured for a team, it returns an empty array.
     * @return ResourcePosition[] The details of all positions in the team
     * @param teamId
     *            The ID of the team whose position details are to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     */
    public ResourcePosition[] getTeamPositionsDetails(long teamId) {
        return null;
    };

    /**
     * Gets all free agents available to this project. Returns an empty array if there are no
     * available free agents.
     * @return Resource[] of resource whose roles are defined as being free agents
     * @param projectId
     *            The ID of the project whose free agents are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     */
    public Resource[] findFreeAgents(long projectId) {
        return null;
    };

    /**
     * Creates or updates the position.
     * @return OperationResult with the results of the operation
     * @param position
     *            TeamPosition to create or update
     * @param teamId
     *            The ID of the team this position is part of
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, or userId or teamId is negative
     */
    public OperationResult createOrUpdatePosition(TeamPosition position, long teamId, long userId) {
        return null;
    };

    /**
     * Gets the position.
     * @return TeamPosition that matches the ID
     * @param positionId
     *            The ID of the position to retrieve
     * @throws IllegalArgumentException
     *             If positionId is negative
     */
    public TeamPosition getPosition(long positionId) {
        return null;
    };

    /**
     * Removes a position.
     * @return OperationResult with the results of the operation
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     */
    public OperationResult removePosition(long positionId, long userId) {
        return null;
    };

    /**
     * Validates the team for finalization.
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to validate
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     */
    public OperationResult validateFinalization(long teamId, long userId) {
        return null;
    };

    /**
     * Finalizes the team. Performs validation to test if team is finalizable.
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to finalize
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     */
    public OperationResult finalizeTeam(long teamId, long userId) {
        return null;
    };

    /**
     * Sends the offer.
     * @return OperationResult with the results of the operation
     * @param offer
     *            Offer to send
     * @throws IllegalArgumentException
     *             If offer is null, or offer.offerId is negative
     */
    public OperationResult sendOffer(Offer offer) {
        return null;
    };

    /**
     * Gets all offers for the given user that are unanswered or not expired by that user.
     * @return Offer[] of offers that match the search criteria
     * @param userId
     *            The ID of the user whose offer receivables we want
     * @throws IllegalArgumentException
     *             If userId is negative
     */
    public Offer[] getOffers(long userId) {
        return null;
    };

    /**
     * Accepts the offer and sets the position resource ID, payment percentage and making the
     * position filled and sends a message to the user informing of the acceptance.
     * @return OperationResult with the results of the operation
     * @param offerId
     *            The ID of the offer to accept
     * @param userId
     *            The ID of the user who is accepting the offer
     * @throws IllegalArgumentException
     *             If offerId or userId is negative
     */
    public OperationResult acceptOffer(long offerId, long userId) {
        return null;
    };

    /**
     * Rejects the offer. It sends a notification message to offer stating this.
     * @return OperationResult with the results of the operation
     * @param offerId
     *            The ID of the offer to reject
     * @param cause
     *            The reason for the rejection
     * @param userId
     *            The ID of the user who is rejecting the offer
     * @throws IllegalArgumentException
     *             If offerId or userId is negative
     */
    public OperationResult rejectOffer(long offerId, String cause, long userId) {
        return null;
    };

    /**
     * Removes a team member from a team and sends a notification message to the team captain.
     * @return OperationResult with the results of the operation
     * @param resourceId
     *            The resource to remove from the team
     * @param userId
     *            userId The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If resourceId or userId is negative
     */
    public OperationResult removeMember(long resourceId, long userId) {
        OperationResultImpl result = new OperationResultImpl();
        result.setSuccessful(true);
        return result;
    };

    /**
     * Forces the finalization of all not finalized teams.
     * @return OperationResult with the results of the operation
     * @param projectId
     *            The ID of the project whose unfinalized teams are forced to be finalized
     * @param userId
     *            userId The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If projectId or userId is negative
     */
    public OperationResult forceFinalization(long projectId, long userId) {
        return null;
    };

}
