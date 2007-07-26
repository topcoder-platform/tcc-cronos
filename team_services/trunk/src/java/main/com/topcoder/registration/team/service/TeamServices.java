/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.offer.Offer;

/**
 * <p>
 * This represents the interface that defines all business methods for team services. The services
 * include being able to manage teams and their positions as well as the resources that fill them.
 * In fact, this service provides the ability to manage the offers for these positions to resources
 * as well as sending notifications regarding those offers.
 * </p>
 * <p>
 * It has one implementation: <code>TeamServicesImpl</code>.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface TeamServices {

    /**
     * <p>
     * Creates or updates the team.
     * </p>
     * <p>
     * The following rules are in effect: Team details cannot be edited once the team has been
     * finalized. Team names must be unique across all teams registered to currently active
     * projects. A Team name cannot be the same as an existing member&rsquo;s handle, except if the
     * member is the Team Captain for this team.
     * </p>
     * @return OperationResult with the results of the operation
     * @param team
     *            The team header to either create or update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null, or userId is negative
     * @throws UnknownEntityException
     *             If teamId in team is non-negative but not known
     */
    public OperationResult createOrUpdateTeam(TeamHeader team, long userId);

    /**
     * <p>
     * Gets all teams that are registered for the project with the given ID. Returns empty array if
     * the there are no teams in this project.
     * </p>
     * @return TeamHeader array of teams in the given project
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws UnknownEntityException
     *             If projectId is not known
     */
    public TeamHeader[] getTeams(long projectId);

    /**
     * <p>
     * Gets the team members, including the captain, in this team. It will return at least one
     * member in the array since a team always has a captain.
     * </p>
     * @return Resource array of the participants in the team
     * @param teamId
     *            The ID of the team whose participants are to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     */
    public Resource[] getTeamMembers(long teamId);

    /**
     * <p>
     * Removes a team and all its positions. It will send a notification message to all team members
     * and captain informing of the team removal. Any team members of the dissolved team are changed
     * to Free Agents. Any pending offers from the Team Captain to Free Agents are expired.
     * </p>
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     */
    public OperationResult removeTeam(long teamId, long userId);

    /**
     * <p>
     * Gets the details, in terms of the position and resource, of each position. If there are no
     * positions yet configured for a team, it returns an empty array.
     * </p>
     * @return ResourcePosition array, the details of all positions in the team
     * @param teamId
     *            The ID of the team whose position details are to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     */
    public ResourcePosition[] getTeamPositionsDetails(long teamId);

    /**
     * <p>
     * Gets all free agents available to this project. A free agent is defined as a resource
     * available to the project but does not yet occupy a position. Returns an empty array if there
     * are no available free agents.
     * </p>
     * @return Resource array of resources whose roles are defined as being free agents
     * @param projectId
     *            The ID of the project whose free agents are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws UnknownEntityException
     *             If projectId is not known
     */
    public Resource[] findFreeAgents(long projectId);

    /**
     * <p>
     * Creates or updates the position.
     * </p>
     * <p>
     * The following rules are in effect: Positions may not be created after the team has been
     * finalized. Position Name cannot be a member handle. Position Name cannot duplicate an
     * existing position name within the team. If the position is filled, the resource must be a
     * member registered in the team&rsquo;s project as Free Agent. If the position is filled, the
     * resource must not be a member of any other team for that project.
     * </p>
     * @return OperationResult with the results of the operation
     * @param position
     *            TeamPosition to create or update
     * @param teamId
     *            The ID of the team this position is part of
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, or userId or teamId is negative
     * @throws UnknownEntityException
     *             If positionId in position is non-negative but not known
     */
    public OperationResult createOrUpdatePosition(TeamPosition position, long teamId, long userId);

    /**
     * <p>
     * Gets the Position with the given a position Id.
     * </p>
     * @return TeamPosition that matches the ID
     * @param positionId
     *            The ID of the position to retrieve
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws UnknownEntityException
     *             If positionId is not known
     */
    public TeamPosition getPosition(long positionId);

    /**
     * <p>
     * Removes a position with the given positionId.
     * </p>
     * @return OperationResult with the results of the operation
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws UnknownEntityException
     *             If positionId is not known
     */
    public OperationResult removePosition(long positionId, long userId);

    /**
     * <p>
     * Validates the team for finalization.
     * </p>
     * <ul type="disc">
     * <li>The team must be associated with an active project</li>
     * <li>The calling user must be the team&rsquo;s captain.</li>
     * <li>The project must be currently in the registration phase</li>
     * <li>All team members must be registered to the project as Free Agents</li>
     * <li>Team captain must be registered as a resource to the project with the Team Captain
     * ResourceRole.</li>
     * <li>All positions must be already filled</li>
     * <li>Sum of payment percentages of team members and captain should be equal to 1.</li>
     * <li>Team must not be already finalized</li>
     * </ul>
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to validate
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     */
    public OperationResult validateFinalization(long teamId, long userId);

    /**
     * <p>
     * Finalizes the team. Performs the actual finalization of the team and updates the resources
     * appropriately following these rules.
     * </p>
     * <ul type="disc">
     * <li>It must first validate the finalization using the Validate Finalization feature</li>
     * <li>It must update the team header to reflect the finalized state</li>
     * <li>It must change the Team Captain&rsquo;s ResourceRole to &ldquo;Submitter&rdquo;. The
     * &ldquo;Submitter&rdquo; ResourceRole id must be configurable.</li>
     * </ul>
     * @return OperationResult with the results of the operation
     * @param teamId
     *            The ID of the team to finalize
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is not known
     */
    public OperationResult finalizeTeam(long teamId, long userId);

    /**
     * <p>
     * Saves the given offer and sends a notification message to the destination member.
     * </p>
     * <p>
     * The following are the rules for sending the offer
     * </p>
     * <ul type="disc">
     * <li>The position must be published and unfilled</li>
     * <li>The project associated with the team must be in the registration phase</li>
     * <li>The project must be currently in the registration phase</li>
     * <li>If the sender is a team captain, the receivers must be free agents registered for the
     * project.</li>
     * <li>If the sender is a free agent, the receiver must be the team&rsquo;s captain.</li>
     * <li>The text template of the notification message must be configurable.</li>
     * </ul>
     * @return OperationResult with the results of the operation
     * @param offer
     *            Offer to send
     * @throws IllegalArgumentException
     *             If offer is null, or offer.offerId is negative
     */
    public OperationResult sendOffer(Offer offer);

    /**
     * <p>
     * Gets all offers for the given user that are unanswered or not expired by that user.
     * </p>
     * @return Offer array of offers that match the search criteria
     * @param userId
     *            The ID of the user whose offer receivables we want
     * @throws IllegalArgumentException
     *             If userId is negative
     */
    public Offer[] getOffers(long userId);

    /**
     * <p>
     * Accepts the offer and sets the position resource ID, payment percentage and making the
     * position filled and sends a message to the user informing of the acceptance.
     * </p>
     * <p>
     * The following must be checked:
     * </p>
     * <ul type="disc">
     * <li>The position must be unfilled.</li>
     * <li>The project associated with the team must be in the registration phase</li>
     * <li>The new payment percentage must not make the team total to exceed 100%. </li>
     * <li>The text template of the notification message must be configurable.</li>
     * <li>The user has not accepted another offer in the project</li>
     * </ul>
     * @return OperationResult with the results of the operation
     * @param offerId
     *            The ID of the offer to accept
     * @param userId
     *            The ID of the user who is accepting the offer
     * @throws IllegalArgumentException
     *             If offerId or userId is negative
     */
    public OperationResult acceptOffer(long offerId, long userId);

    /**
     * <p>
     * Rejects the offer. It sends a notification message to offer sender stating this.
     * </p>
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
    public OperationResult rejectOffer(long offerId, String cause, long userId);

    /**
     * <p>
     * Removes a team member from a team and sends a notification message to the team captain.
     * </p>
     * <p>
     * The following must be checked:
     * </p>
     * <ul type="disc">
     * <li>It only removes team members. Not team captains.</li>
     * <li>It must send a notification of the removal to the team captain.</li>
     * <li>If the team is finalized, the payment percentage of the removed member is distributed
     * evenly along all members including the team captain.</li>
     * <li>The text template of the notification message must be configurable.</li>
     * </ul>
     * @return OperationResult with the results of the operation
     * @param resourceId
     *            The resource to remove from the team
     * @param userId
     *            userId The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If resourceId or userId is negative
     * @throws UnknownEntityException
     *             If resourceId is not known
     */
    public OperationResult removeMember(long resourceId, long userId);

    /**
     * <p>
     * Forces the finalization of all not finalized teams.
     * </p>
     * <p>
     * This method has fewer restrictions that the Finalize Team method so it must not call the
     * validateFinalization method. Still, some restrictions apply for each finalization.
     * </p>
     * <ul type="disc">
     * <li>All unfilled position prize percentages will be split evenly among the registered team
     * members.</li>
     * <li>Unfilled positions are deleted by the system.</li>
     * <li>All team members must be registered to the project as Free Agents</li>
     * <li>Team captain must be registered as a resource to the project with the Team Captain
     * ResourceRole.</li>
     * </ul>
     * @return OperationResult with the results of the operation
     * @param projectId
     *            The ID of the project whose unfinalized teams are forced to be finalized
     * @param userId
     *            userId The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If projectId or userId is negative
     * @throws UnknownEntityException
     *             If projectId is not known
     */
    public OperationResult forceFinalization(long projectId, long userId);
}
